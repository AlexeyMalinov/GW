package ru.alexeymalinov.taskautomation.taskpublishingwizard;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.repository.Repository;

public class TaskPublishingWizard {

    private final Repository repository;

    private TaskPublishingWizard(Repository repository){
        this.repository = repository;
    }

    public static TaskPublishingWizard getInstance(Repository repository) {
        return new TaskPublishingWizard(repository);
    }

    public boolean publishTask(Task task) {
        if(!repository.taskExist(task.getName())) {
            repository.publishTask(task);
            return true;
        }
        return false;
    }

    public void deleteTask(String name){
        repository.removeTask(name);
    }
}
