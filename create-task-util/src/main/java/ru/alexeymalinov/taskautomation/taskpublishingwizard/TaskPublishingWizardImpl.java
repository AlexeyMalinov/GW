package ru.alexeymalinov.taskautomation.taskpublishingwizard;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.repository.Repository;

public class TaskPublishingWizardImpl implements TaskPublishingWizard{

    private final Repository repository;

    public TaskPublishingWizardImpl(Repository repository){
        this.repository = repository;
    }

    @Override
    public boolean publishTask(Task task) {
        if(!repository.taskExist(task.getName())) {
            repository.publishTask(task);
            return true;
        }
        return false;
    }

    @Override
    public void deleteTask(String name){
        repository.removeTask(name);
    }
}
