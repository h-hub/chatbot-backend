package com.example.DFdemo2.model;

import java.util.List;

public class QuickReply {
    private String text;
    private List<Reply> replies;

    public QuickReply() {}

    public QuickReply(String text, List<Reply> replies) {
        this.text = text;
        this.replies = replies;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public static class Reply{
        private String text;
        private String payload;

        public Reply(String text, String payload) {
            this.text = text;
            this.payload = payload;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
    }
}

