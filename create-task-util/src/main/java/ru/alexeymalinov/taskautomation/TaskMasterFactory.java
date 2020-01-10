package ru.alexeymalinov.taskautomation;

import java.util.List;

public class TaskMasterFactory {

    private final List<TaskMaster> masters;

    private TaskMasterFactory(List<TaskMaster> masters) {
        this.masters = masters;
    }

    public static TaskMasterFactory getInstance(List<TaskMaster> masters){
        return new TaskMasterFactory(masters);
    }

    public TaskMaster getInstance(String taskType){
        for (TaskMaster master : masters) {
            if(master.getTaskType().equals(taskType)){
                return master;
            }
        }
        return null;
    }

    public void printTypeTask(){
        for (TaskMaster master : masters) {
            System.out.println(master.getTaskType());
        }
    }
}
