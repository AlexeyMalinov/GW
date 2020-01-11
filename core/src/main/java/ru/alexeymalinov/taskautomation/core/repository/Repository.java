package ru.alexeymalinov.taskautomation.core.repository;

import ru.alexeymalinov.taskautomation.core.model.Task;

public interface Repository {

    /**
     * Возвращает задачу из репозитория
     * @param taskName - имя задачи
     * @return - объект класса {@code Task}
     */
    Task getTask(String taskName);

    /**
     * Публикует задачу в репозитории
     * @param task
     */
    void publishTask(Task task);

    /**
     * Удаляет задачу из репощитория
     * @param taskName
     * @return
     */
    void removeTask(String taskName);
}
