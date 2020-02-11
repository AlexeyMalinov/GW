package ru.alexeymalinov.taskautomation.taskpublishingwizard;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.repository.Repository;

public interface TaskPublishingWizard {
    boolean publishTask(Task task);
    void deleteTask(String name);

    static TaskPublishingWizard getInstance(Repository repository) {
        return new TaskPublishingWizardImpl(repository);
    }
}
