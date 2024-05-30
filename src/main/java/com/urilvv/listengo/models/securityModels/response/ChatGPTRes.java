package com.urilvv.listengo.models.securityModels.response;

import com.urilvv.listengo.dto.Message;

import java.util.List;

public class ChatGPTRes {

    private List<Choice> choices;

    public ChatGPTRes(List<Choice> choices) {
        this.choices = choices;
    }

    public ChatGPTRes(){}

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public static class Choice{

        private int index;
        private Message message;

        public Choice(int index, Message message) {
            this.index = index;
            this.message = message;
        }

        public Choice(){}

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

    }
}
