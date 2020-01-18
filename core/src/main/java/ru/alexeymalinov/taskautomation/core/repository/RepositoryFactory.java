package ru.alexeymalinov.taskautomation.core.repository;

public class RepositoryFactory {

    public static Repository getRepository(String path) {
        if (path.startsWith("http://")) {
            return new RemoteRepository(path);
        } else {
            return new LocalRepository(path);
        }
    }

}
