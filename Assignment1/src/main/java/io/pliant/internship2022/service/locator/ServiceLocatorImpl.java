package io.pliant.internship2022.service.locator;

import io.pliant.internship2022.exception.*;
import io.pliant.internship2022.service.factory.ServiceFactoryImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ServiceLocatorImpl implements ServiceLocator {

    private final Map<String, Object> registers = new HashMap<>();
    private final ServiceFactoryImpl factory = new ServiceFactoryImpl();

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

        if (registers.containsKey(name)) {
            throw new RegisterExistException(name);
        }

        registers.put(name, serviceClass);
    }

    @Override
    public void register(Class<?> serviceClass) throws ServiceLocatorException {

        if (registers.containsKey(serviceClass.getSimpleName())) {
            throw new RegisterExistException(serviceClass.getSimpleName());
        }

        registers.put(serviceClass.getSimpleName(), serviceClass);
    }

    @Override
    public Object get(String name) throws ServiceLocatorException{

        Object result = registers.get(name);

        if (result != null) {
            if (result instanceof Class<?>) {
                try {
                    result = Class.forName(result.getClass().getName()).getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException | ClassNotFoundException e) {
                    throw new UnsuccessfullyCreatingLazyInstanceException(result.getClass().getSimpleName());
                }
                registers.put(name, result);
            }
            return result;
        }

        throw new NotExistingServiceException(name);
    }


    @Override
    public <T> T get(Class<T> serviceClass) throws ServiceLocatorException {

        Map<String, Object> results = getServiceClasses(serviceClass);

        if (results.size() == 0) {
            throw new ZeroRegistersException(serviceClass.getSimpleName());
        } else if (results.size() > 1) {
            throw new MoreThanOneRegisterException(serviceClass.getSimpleName());
        }

        T result = null;
        for (Map.Entry<String, Object> result1 : results.entrySet()) {
            if (result1.getValue() instanceof Class) {
                Object newInstance;
                try {
                    newInstance = Class.forName(((Class<?>) result1.getValue()).getName()).getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException | ClassNotFoundException e) {
                    throw new UnsuccessfullyCreatingLazyInstanceException(((Class<?>) result1.getValue()).getSimpleName());
                }
                registers.put(result1.getKey(), newInstance);
                result = (T) newInstance;
            } else {
                result = (T) result1.getValue();
            }
        }
        return result;
    }

    @Override
    public <T> List<T> getAll(Class<T> serviceClass){
        Map<String, Object> resultAsMap = getServiceClasses(serviceClass);
        List<T> result = new ArrayList<>();

        for (Map.Entry<String, Object> service : resultAsMap.entrySet()) {
            if (service.getValue() instanceof Class) {
                Object newInstance;
                try {
                    newInstance = Class.forName(((Class<?>) service.getValue()).getName()).getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException | ClassNotFoundException e) {
                    throw new UnsuccessfullyCreatingLazyInstanceException(((Class<?>) service.getValue()).getSimpleName());
                }
                registers.put(service.getKey(), newInstance);
                result.add((T) newInstance);
            } else {
                result.add((T) service.getValue());
            }
        }

        return result;
    }

    private Map<String, Object> getServiceClasses(Class<?> serviceClass) {
        Map<String, Object> result = new LinkedHashMap<>();

        //I can not understand why it captures only instantiated objects when serviceClass is not Object.class
        for (Map.Entry<String, Object> register : registers.entrySet()) {
            if (serviceClass.isAssignableFrom(register.getValue().getClass())) {
                result.put(register.getKey(), register.getValue());
            }
        }

        return result;
    }
}
