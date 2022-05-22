package io.pliant.internship2022.service.factory;

public interface ServiceFactory {
    Object lookup(String serviceName);
}
