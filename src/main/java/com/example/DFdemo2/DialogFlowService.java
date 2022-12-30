package com.example.DFdemo2;

import com.example.DFdemo2.model.Cards;
import com.example.DFdemo2.model.FulfillmentResponse;
import com.example.DFdemo2.model.QuickReply;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DialogFlowService {

    @Value("${df.properties.projectId}")
    private String projectId;

    @Value("${df.properties.lang-code}")
    private String langCode;

    @Value("${df.properties.client-id}")
    private String clientId;

    @Value("${df.properties.client-email}")
    private String clientEmail;

    @Value("${df.properties.private-key-pkcs8}")
    private String privateKeyPkcs8;

    @Value("${df.properties.private-key-id}")
    private String privateKeyId;


    public FulfillmentResponse getTextIntentResponse(String queryText) throws IOException {

        TextInput.Builder textInput =
                TextInput.newBuilder().setText(queryText).setLanguageCode(langCode);

        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

        return getDullFillmentText(queryInput);

    }

    public FulfillmentResponse getEventIntentResponse(String event) throws IOException {

        EventInput eventInput = EventInput.newBuilder().setName(event).setLanguageCode(langCode).build();

        QueryInput queryInput = QueryInput.newBuilder().setEvent(eventInput).build();

        return getDullFillmentText(queryInput);

    }

    private FulfillmentResponse getDullFillmentText(QueryInput queryInput) throws IOException {
        SessionName sessionName = SessionName.of(projectId, UUID.randomUUID().toString());

        String cleanedPrivateKeyPkcs8 = privateKeyPkcs8.replace("\\n", System.lineSeparator());

        Credentials credentials = ServiceAccountCredentials.fromPkcs8(clientId, clientEmail, cleanedPrivateKeyPkcs8, privateKeyId, null);

        SessionsSettings sessionsSettings =
                SessionsSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();

        DetectIntentResponse response = SessionsClient.create(sessionsSettings).detectIntent(sessionName, queryInput);

        QueryResult queryResult = response.getQueryResult();

        List<Cards> cardsList = getCards(queryResult);
        QuickReply quickReply = getQuickReply(queryResult);

        FulfillmentResponse fulfillmentResponse = new FulfillmentResponse(queryResult.getFulfillmentText(), cardsList, quickReply);

        return fulfillmentResponse;
    }

    private List<Cards> getCards(QueryResult queryResult) {
        List<Cards> cardsList = new LinkedList<>();
        if(queryResult.getFulfillmentMessagesCount() > 1){
            cardsList = queryResult.getFulfillmentMessages(1).getPayload().getFieldsMap().get("cards").getListValue().getValuesList()
                    .stream()
                    .map(card -> new Cards(
                            card.getStructValue().getFieldsMap().get("header").getStringValue(),
                            card.getStructValue().getFieldsMap().get("description").getStringValue(),
                            card.getStructValue().getFieldsMap().get("image").getStringValue(),
                            card.getStructValue().getFieldsMap().get("link").getStringValue()))
                    .collect(Collectors.toList());
        }
        return cardsList;
    }

    private QuickReply getQuickReply(QueryResult queryResult) {

        QuickReply quickReply = new QuickReply();

        var fieldsMap = queryResult.getFulfillmentMessages(2).getPayload().getFieldsMap();

        if(fieldsMap.containsKey("quick_replies")){
            String text = fieldsMap.get("text").getStringValue();
            List<QuickReply.Reply> quickReplies = fieldsMap.get("quick_replies").getListValue().getValuesList()
                                                    .stream()
                                                    .map(reply -> new QuickReply.Reply(
                                                            reply.getStructValue().getFieldsMap().get("text").getStringValue(),
                                                            reply.getStructValue().getFieldsMap().get("payload").getStringValue()))
                                                    .collect(Collectors.toList());
            quickReply.setText(text);
            quickReply.setReplies(quickReplies);
        }

        return quickReply;
    }


}
