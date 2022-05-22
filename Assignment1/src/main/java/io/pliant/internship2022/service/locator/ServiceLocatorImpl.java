package io.pliant.internship2022.service.locator;

import io.pliant.internship2022.service.factory.ServiceFactoryImpl;
import io.pliant.internship2022.exception.MoreThanOneRegisterException;
import io.pliant.internship2022.exception.NotExistingRegisterException;
import io.pliant.internship2022.exception.NotExistingServiceException;
import io.pliant.internship2022.exception.RegisterExistException;
import io.pliant.internship2022.exception.ServiceLocatorException;
import io.pliant.internship2022.exception.ZeroRegistersException;
import io.pliant.internship2022.model.MessagingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceLocatorImpl implements ServiceLocator {

    private final Map<String, Object> registers = new HashMap<>();

    @Override
    public void register(Object service, String name) throws ServiceLocatorException {

        if (registers.containsKey(name)) {
            throw new RegisterExistException(name);
        }

        registers.put(name, service);
    }

    @Override
    public void register(Object service) throws ServiceLocatorException {

        if (registers.containsKey(service.getClass().getSimpleName())) {
            throw new RegisterExistException(service.getClass().getSimpleName());
        }

        registers.put(service.getClass().getSimpleName(), service);
    }

    @Override
    public void register(Class<?> serviceClass, String name) throws ServiceLocatorException {

        ServiceFactoryImpl context = new ServiceFactoryImpl();
        MessagingService service1 = (MessagingService) context.lookup(serviceClass.getSimpleName());

        if (service1 == null) {
            throw new NotExistingServiceException(serviceClass.getSimpleName());
        }

        if (registers.containsKey(name)) {
            throw new RegisterExistException(name);
        }

        registers.put(name, service1);

    }

    @Override
    public void register(Class<?> serviceClass) throws ServiceLocatorException {

        ServiceFactoryImpl context = new ServiceFactoryImpl();
        MessagingService service1 = (MessagingService) context.lookup(serviceClass.getSimpleName());

        if (service1 == null) {
            throw new NotExistingServiceException(serviceClass.getSimpleName());
        }

        if (registers.containsKey(service1.getServiceName())) {
            throw new RegisterExistException(service1.getServiceName());
        }

        registers.put(service1.getServiceName(), service1);

    }

    @Override
    public Object get(String name) throws ServiceLocatorException {

        Object service = registers.get(name);

        if (service != null) {
            return service;
        }

        ServiceFactoryImpl context = new ServiceFactoryImpl();
        MessagingService service1 = (MessagingService) context.lookup(name);

        if (service1 == null) {
            throw new NotExistingRegisterException(name);
        }

        registers.put(name, service1);
        return service1;
    }

    @Override
    public <T> T get(Class<T> serviceClass) throws ServiceLocatorException {

        List<T> result = getServiceClasses(serviceClass);

        if (result.size() == 0) {
            throw new ZeroRegistersException(serviceClass.getSimpleName());
        } else if (result.size() > 1) {
            throw new MoreThanOneRegisterException(serviceClass.getSimpleName());
        }

        return result.get(0);
    }

    @Override
    public <T> List<T> getAll(Class<T> serviceClass) throws ServiceLocatorException {

        return getServiceClasses(serviceClass);
    }

    private <T> List<T> getServiceClasses(Class<T> serviceClass) {
        List<T> result = new ArrayList<>();

        for (Object register : registers.values()) {
            if (serviceClass.isInstance(register)) {
                result.add((T) register);
            }
        }

        if (!registers.containsKey(serviceClass.getSimpleName())) {
            ServiceFactoryImpl context = new ServiceFactoryImpl();
            MessagingService service1 = (MessagingService) context.lookup(serviceClass.getSimpleName());

            if (service1 != null) {
                registers.put(service1.getServiceName(), service1);
                result.add((T) service1);
            }
        }

        return result;
    }
}
