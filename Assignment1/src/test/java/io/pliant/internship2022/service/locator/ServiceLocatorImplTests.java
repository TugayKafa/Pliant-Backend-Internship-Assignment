package io.pliant.internship2022.service.locator;

import io.pliant.internship2022.exception.MoreThanOneRegisterException;
import io.pliant.internship2022.exception.NotExistingRegisterException;
import io.pliant.internship2022.exception.NotExistingServiceException;
import io.pliant.internship2022.exception.RegisterExistException;
import io.pliant.internship2022.exception.ZeroRegistersException;
import io.pliant.internship2022.model.EmailService;
import io.pliant.internship2022.model.MessagingService;
import io.pliant.internship2022.model.SMSService;
import org.junit.Assert;
import org.junit.Test;

public class ServiceLocatorImplTests {

    @Test
    public void successfullyRegistrationWithProvidedObjectAndName() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(new EmailService(), "EmailService1");
        MessagingService result = (MessagingService) serviceLocator.get("EmailService1");
        Assert.assertEquals(new EmailService().getMessageBody(), result.getMessageBody());
    }

    @Test(expected = RegisterExistException.class)
    public void unsuccessfullyRegistrationWithProvidedObjectAndName() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(new EmailService(), "EmailService1");
        serviceLocator.register(new EmailService(), "EmailService1");
    }

    @Test
    public void successfullyRegistrationWithProvidedObject() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(new EmailService());
        MessagingService result = (MessagingService) serviceLocator.get("EmailService");
        Assert.assertEquals(new EmailService().getMessageBody(), result.getMessageBody());
    }

    @Test(expected = RegisterExistException.class)
    public void unsuccessfullyRegistrationWithProvidedObject() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(new EmailService());
        serviceLocator.register(new EmailService());
    }

    @Test
    public void successfullyRegistrationWithProvidedServiceClassAndName() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(EmailService.class);
        MessagingService result = (MessagingService) serviceLocator.get("EmailService");
        Assert.assertEquals(new EmailService().getMessageBody(), result.getMessageBody());
    }

    @Test(expected = RegisterExistException.class)
    public void unsuccessfullyRegistrationWithProvidedServiceClassAndName() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(EmailService.class, "EmailService1");
        serviceLocator.register(EmailService.class, "EmailService1");
    }

    @Test(expected = NotExistingServiceException.class)
    public void unsuccessfullyRegistrationWithProvidedNotExistingServiceClassAndName() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(String.class, "String1");
    }

    @Test
    public void successfullyRegistrationWithProvidedServiceClass() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(EmailService.class);
        MessagingService result = (MessagingService) serviceLocator.get("EmailService");
        Assert.assertEquals(new EmailService().getMessageBody(), result.getMessageBody());
    }

    @Test(expected = RegisterExistException.class)
    public void unsuccessfullyRegistrationWithProvidedServiceClass() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(EmailService.class);
        serviceLocator.register(EmailService.class);
    }

    @Test(expected = NotExistingServiceException.class)
    public void unsuccessfullyRegistrationWithProvidedNotExistingServiceClass() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(String.class);
    }

    @Test
    public void successfullyGetByName() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(EmailService.class, "Service1");
        serviceLocator.register(EmailService.class, "Service2");
        serviceLocator.register(SMSService.class, "Service3");
        MessagingService result = (MessagingService) serviceLocator.get("Service3");
        Assert.assertEquals(new SMSService().getMessageBody(), result.getMessageBody());
    }

    @Test
    public void successfullyGetAndRegisterByName() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        MessagingService result = (MessagingService) serviceLocator.get("SMSService");
        Assert.assertEquals(new SMSService().getMessageBody(), result.getMessageBody());
    }

    @Test(expected = NotExistingRegisterException.class)
    public void unsuccessfullyGetByName() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        MessagingService result = (MessagingService) serviceLocator.get("SMSService1");
    }

    @Test
    public void successfullyGetByServiceClass() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(EmailService.class);
        serviceLocator.register(SMSService.class);
        Object object = serviceLocator.get(SMSService.class);
        Assert.assertTrue(SMSService.class.isInstance(object));
    }

    @Test(expected = ZeroRegistersException.class)
    public void unsuccessfullyGetByServiceClassZeroRegisters() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(EmailService.class);
        serviceLocator.register(SMSService.class);
        serviceLocator.get(String.class);
    }

    @Test(expected = MoreThanOneRegisterException.class)
    public void unsuccessfullyGetByServiceClassMoreThanOneRegister() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(EmailService.class);
        serviceLocator.register(SMSService.class);
        serviceLocator.get(MessagingService.class);
    }

    @Test
    public void successfullyGetAllByServiceClass() {
        ServiceLocatorImpl serviceLocator = new ServiceLocatorImpl();
        serviceLocator.register(EmailService.class, "service1");
        serviceLocator.register(EmailService.class, "service2");
        serviceLocator.register(SMSService.class, "service3");
        serviceLocator.register(SMSService.class, "service4");
        Assert.assertEquals(4, serviceLocator.getAll(MessagingService.class).size());
    }

}
