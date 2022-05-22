package io.pliant.internship2022.model;

public class EmailService implements MessagingService {

    @Override
    public String getMessageBody() {
        return "email message";
    }

    @Override
    public String getServiceName() {
        return "EmailService";
    }
}
