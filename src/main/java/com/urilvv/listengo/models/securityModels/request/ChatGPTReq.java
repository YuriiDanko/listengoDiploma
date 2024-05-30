package com.urilvv.listengo.models.securityModels.request;

import com.urilvv.listengo.dto.Message;
import java.util.ArrayList;
import java.util.List;

public class ChatGPTReq {

    private String model;
    private List<Message> messages;

    public ChatGPTReq(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", "Imagine you are an music advisor. Return an answers in this format \"SongName\" - \"SongArtist\""));
        this.messages.add(new Message("user", prompt));
    }

    public ChatGPTReq() {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messageList) {
        this.messages = messageList;
    }

}
