package com.example.DFdemo2.model;

import java.util.List;

public class FulfillmentResponse {
    private String response;
    private List<Cards> cards;
    private QuickReply quickReply;

    public FulfillmentResponse(String response, List<Cards> cards, QuickReply quickReply) {
        this.response = response;
        this.cards = cards;
        this.quickReply = quickReply;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Cards> getCards() {
        return cards;
    }

    public void setCards(List<Cards> cards) {
        this.cards = cards;
    }

    public QuickReply getQuickReply() {
        return quickReply;
    }

    public void setQuickReply(QuickReply quickReply) {
        this.quickReply = quickReply;
    }
}
