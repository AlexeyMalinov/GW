package ru.alexeymalinov.taskautomation.core.repository;

import java.util.Properties;

public class RepositoryFactory {

    public Repository getRepository(Properties properties){
        return new LocalRepository(properties);
    }

    public Repository getRepository(String address, int port){
        throw new IllegalStateException("Operation not supported");
    }

}
