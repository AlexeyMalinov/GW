package ru.alexeymalinov.taskautomation.core.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryFactoryTest {

    private String remote = "http://localhost:8080";
    private String local = "C:\\Data\\";
    @Test
    void getRepository() {
        Repository actualLocalRepository = RepositoryFactory.getRepository(local);
        Repository actualRemoteRepository = RepositoryFactory.getRepository(remote);
        assertTrue(actualLocalRepository instanceof LocalRepository);
        assertTrue(actualRemoteRepository instanceof RemoteRepository);
    }
}