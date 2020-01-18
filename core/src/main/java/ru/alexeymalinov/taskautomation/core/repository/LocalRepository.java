package ru.alexeymalinov.taskautomation.core.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.json.Converter;
import ru.alexeymalinov.taskautomation.core.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class LocalRepository implements Repository{

    private final static Logger LOGGER = LoggerFactory.getLogger(LocalRepository.class);
    private final static String IO_TASK_ERROR = "Error converting task file";

    private final static Converter<Task> TASK_CONVERTER = new Converter<>();

    private final String path;


    public LocalRepository(String path) {
        this.path = path;
    }

    /**
     * {@inheritDoc}
     * @param taskName - имя задачи
     * @return
     */
    @Override
    public Task getTask(String taskName) {
        File file = new File(path + "/" + taskName);
        try {
            return TASK_CONVERTER.toObject(file, Task.class);
        } catch (IOException e) {
            LOGGER.error(IO_TASK_ERROR);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * @param task
     */
    @Override
    public void publishTask(Task task) {
        File file = new File(path + "/" + task.getName());
        try {
            TASK_CONVERTER.toJSON(file, task);
        } catch (IOException e) {
            LOGGER.error(IO_TASK_ERROR, e);
        }
    }

    /**
     * {@inheritDoc}
     * @param taskName
     */
    @Override
    public void removeTask(String taskName) {
        File file = new File(path + "/" + taskName);
        boolean isDelete = file.delete();
        if(isDelete){
            //TODO корректно описать ситуацию когда файл не удалился
        }
    }

    /**
     * {@inheritDoc}
     * @param taskName
     * @return
     */
    @Override
    public boolean taskExist(String taskName) {
        return Files.exists(Paths.get(path + "/" + taskName));
    }
}
