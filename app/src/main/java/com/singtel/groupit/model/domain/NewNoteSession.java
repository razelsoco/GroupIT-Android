package com.singtel.groupit.model.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razelsoco on 12/7/16.
 */
public class NewNoteSession {
    private int templateId;
    private List<User> recipients;
    private String message;

    private static NewNoteSession ourInstance;

    public static NewNoteSession getInstance() {
        if(ourInstance == null)
            ourInstance = new NewNoteSession();

        return ourInstance;
    }

    private NewNoteSession() {
        this.recipients = new ArrayList<>();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRecipients(List<User> recipients) {
        this.recipients = recipients;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public void addRecipient(User user){
        this.recipients.add(user);
    }

    public void deleteRecipient(User user){
        this.recipients.remove(user);
    }

    public String toJsonString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("{")
                .append("Message : \"").append(message).append("\",")
                .append("ImageId : \"").append(templateId).append(",")
                .append("Users : [");

        for(int i = 0; i < recipients.size()-1; i++){
            User user = recipients.get(i);

            buffer.append("{ Id : \"").append(user.getId()).append("\"}");
            if( !(i == recipients.size()-1) )
                buffer.append(",");
        }

        buffer.append("]}");


        return buffer.toString();

    }
}
