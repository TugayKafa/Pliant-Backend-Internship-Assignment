package io.pliant.internship2022.service.factory;

import io.pliant.internship2022.model.EmailService;
import io.pliant.internship2022.model.SMSService;

public class ServiceFactoryImpl implements ServiceFactory {
    public Object lookup(String serviceName) {
        if (serviceName.equalsIgnoreCase("EmailService")) {
            return new EmailService();
        } else if (serviceName.equalsIgnoreCase("SMSService")) {
            return new SMSService();
        }
        return null;
    }
}
