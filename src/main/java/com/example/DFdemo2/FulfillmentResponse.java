package com.example.DFdemo2;

import java.util.List;

public class FulfillmentResponse {
    private String response;
    private List<Cards> cards;

    public FulfillmentResponse(String response, List<Cards> cards) {
        this.response = response;
        this.cards = cards;
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
}
