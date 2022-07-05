package io.pliant.internship2022;

import io.pliant.internship2022.model.EmailService;
import io.pliant.internship2022.model.MessagingService;
import io.pliant.internship2022.model.SMSService;
import io.pliant.internship2022.service.locator.ServiceLocatorImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Assignment1 {

    public static void main(String[] args){
        ServiceLocatorImpl locator = new ServiceLocatorImpl();
        String john = "I am John";
        locator.register(john,"John");
        locator.register(String.class);
        locator.register(new EmailService());
        locator.register(SMSService.class);
        System.out.println(locator.get(String.class));
        System.out.println(locator.getAll(Object.class));
        System.out.println();
    }

}
