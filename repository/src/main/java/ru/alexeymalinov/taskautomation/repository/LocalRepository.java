package ru.alexeymalinov.taskautomation.repository;

import ru.alexeymalinov.taskautomation.core.model.Task;

import java.nio.file.Path;

public class LocalRepository implements Repository {

    Path path;

    public LocalRepository(String path){
        this.path = Path.of(path);
    }
    public Task getTask(String taskName) {
        return null;
    }


}
