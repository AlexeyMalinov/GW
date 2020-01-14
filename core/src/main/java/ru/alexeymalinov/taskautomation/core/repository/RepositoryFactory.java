package ru.alexeymalinov.taskautomation.core.repository;

import java.util.Properties;

public class RepositoryFactory {

    public Repository getRepository(RepositoryType type, Properties properties){
        switch(type.name()){
            case "LOCAL":
                return new LocalRepository(properties);
            case "REMOTE":
                return new RemoteRepository(properties);
        }
        return new LocalRepository(properties);
    }

}
