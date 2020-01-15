package ru.alexeymalinov.taskautomation.taskpublishingwizard;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryFactory;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryType;

import java.util.Properties;
import java.util.Scanner;

public class TaskPublishingWizard {

    Repository repository;

    private TaskPublishingWizard(RepositoryType type, Properties properties) {
        repository = new RepositoryFactory().getRepository(type, properties);
    }

    public static TaskPublishingWizard getInstance(Properties properties) {
        System.out.println("Please select a repository type (LOCAL/REMOTE)");
        String reposType = new Scanner(System.in).nextLine();
        while (true) {
            if(reposType.equals("LOCAL")){
                return new TaskPublishingWizard(RepositoryType.LOCAL, properties);
            } else if (reposType.equals("REMOTE")){
                return new TaskPublishingWizard(RepositoryType.REMOTE, properties);
            }
            System.out.println("Please try again");
            reposType = new Scanner(System.in).nextLine();
        }
    }

    public void publishTask(Task task) {
        repository.publishTask(task);
    }
}
