package ru.alexeymalinov.taskautomation.core.repository;

import ru.alexeymalinov.taskautomation.core.json.Converter;
import ru.alexeymalinov.taskautomation.core.model.Task;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class LocalRepository implements Repository{

    private final static Converter<Task> TASK_CONVERTER = new Converter<>();

    private final String path;


    public LocalRepository(Properties properties) {
        path = properties.getProperty("local.repos.dir");
    }

    @Override
    public Task getTask(String taskName) {
        File file = new File(path + "/" + taskName);
        try {
            return TASK_CONVERTER.toObject(file, Task.class);
        } catch (IOException e) {
            //TODO корректно описать обработку исключения
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void publishTask(Task task) {
        File file = new File(path + "/" + task.getName());
        try {
            TASK_CONVERTER.toJSON(file, task);
        } catch (IOException e) {
            //TODO корректно описать обработку исключения
            e.printStackTrace();
        }
    }

    @Override
    public void removeTask(String taskName) {
        File file = new File(path + "/" + taskName);
        boolean isDelete = file.delete();
        if(isDelete){
            //TODO корректно описать ситуацию когда файл не удалился
        }
    }

}
