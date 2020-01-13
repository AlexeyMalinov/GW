package ru.alexeymalinov.taskautomation.taskpublishingwizard;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryFactory;

import java.util.Properties;
import java.util.Scanner;

public class TaskPublishingWizard {
    Repository repository;

    private TaskPublishingWizard(Properties properties){

        repository = new RepositoryFactory().getRepository(properties);
    }

    private TaskPublishingWizard(String address, int port){
        repository = new RepositoryFactory().getRepository(address, port);
    }

    public static TaskPublishingWizard getInstance(Properties properties){
        System.out.println("Please select a repository type (LOCAL/REMOTE)");
        String reposType = new Scanner(System.in).nextLine();
        while(true)
            switch (reposType){
                case "LOCAL":
                    return getTaskPublishingWizard(properties);
                case "REMOTE":
                    return getTaskPublishingWizard();
                default:
                    System.out.println("Please try again");
                    reposType = new Scanner(System.in).nextLine();
            }
    }

    public static TaskPublishingWizard getTaskPublishingWizard(Properties properties){
        return new TaskPublishingWizard(properties);
    }

    public static TaskPublishingWizard getTaskPublishingWizard(){
        System.out.println("Please set server address");
        String address = new Scanner(System.in).nextLine();
        System.out.println("Please set server address");
        String port = new Scanner(System.in).nextLine();
        return new TaskPublishingWizard(address, Integer.parseInt(port));
    }

    public void publishTask(Task task){
        repository.publishTask(task);
    }
}
