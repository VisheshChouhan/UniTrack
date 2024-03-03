package com.hfad.login;


import java.util.Date;

public class ChatMessage
{
    String message;
    String user;
    Date timeStamp;
    String messageType;
    String imageUrl;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public ChatMessage()
    {

    }





    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ChatMessage(String message, String user, Date timeStamp, String messageType, String imageUrl) {
        this.message = message;
        this.user = user;
        this.timeStamp = timeStamp;
        this.messageType = messageType;
        this.imageUrl = imageUrl;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}


