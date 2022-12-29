package com.example.DFdemo2;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

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


    public String getTextIntentResponse(String queryText) throws IOException {

        TextInput.Builder textInput =
                TextInput.newBuilder().setText(queryText).setLanguageCode(langCode);

        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

        return getDullFillmentText(queryInput);

    }

    public String getEventIntentResponse(String event) throws IOException {

        EventInput eventInput = EventInput.newBuilder().setName(event).setLanguageCode(langCode).build();

        QueryInput queryInput = QueryInput.newBuilder().setEvent(eventInput).build();

        return getDullFillmentText(queryInput);

    }

    private String getDullFillmentText(QueryInput queryInput) throws IOException {
        SessionName sessionName = SessionName.of(projectId, UUID.randomUUID().toString());

        String cleanedPrivateKeyPkcs8 = privateKeyPkcs8.replace("\\n", System.lineSeparator());

        Credentials credentials = ServiceAccountCredentials.fromPkcs8(clientId, clientEmail, cleanedPrivateKeyPkcs8, privateKeyId, null);

        SessionsSettings sessionsSettings =
                SessionsSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();

        DetectIntentResponse response = SessionsClient.create(sessionsSettings).detectIntent(sessionName, queryInput);

        QueryResult queryResult = response.getQueryResult();

        return queryResult.getFulfillmentText();
    }

}
