package io.pliant.internship2022.model;

public class SMSService implements MessagingService {

    @Override
    public String getMessageBody() {
        return "SMS message";
    }

    @Override
    public String getServiceName() {
        return "SMSService";
    }
}
