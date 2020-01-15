package ru.alexeymalinov.taskautomation.robot.handlers;

import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.model.TimeIntervalType;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryFactory;
import ru.alexeymalinov.taskautomation.robot.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Handler implements Runnable {

    private static int CORRECTION_FACTOR_FOR_CONVERSION_FROM_SECOND_TO_MILLS = 1000;
    private final TaskManager taskManager;
    private final Properties properties;

    protected Handler(TaskManager taskManager, Properties properties) {
        this.taskManager = taskManager;
        this.properties = properties;
    }

    /**
     * Отправляет задачу на выполнение
     * @param task
     */
    private void execute(Task task) {
        taskManager.addTask(task);
    }

    /**
     * Отправляет задачу на выполнение в определенный момент времени
     * @param task
     * @param localDateTime
     */
    private void schedule(Task task, LocalDateTime localDateTime) {
        new Timer().schedule(getTimerTask(task), localDataTimeToDate(localDateTime));
    }

    /**
     * Отправляет задачу на выполнение в определенный момент времени и повторяет запуск через определенный промежуток времени
     * @param task
     * @param localDateTime
     * @param duration
     */
    private void schedule(Task task, LocalDateTime localDateTime, Duration duration) {
        new Timer().schedule(getTimerTask(task), localDataTimeToDate(localDateTime), duration.toMillis());
    }

    private void schedule(Task task, LocalDateTime localDateTime, long period, TimeIntervalType timeIntervalType) {
        System.out.println("schedule with period");
        new Timer().schedule(getTimerTask(task), localDataTimeToDate(localDateTime), getPeriodInMills(period, timeIntervalType));
    }

    public Properties getProperties() {
        return properties;
    }

    protected void schedule(Job job){
        if(job.getPeriod() == 0){
            schedule(getTask(job), job.startTime());
        } else if (job.getPeriod() > 0){
            schedule(getTask(job), job.startTime(), job.getPeriod(), job.intervalType());
        }
    }

    private Task getTask(Job job){
        Repository repository = new RepositoryFactory().getRepository(job.repositoryType(), properties);
        return repository.getTask(job.getTaskName());
    }

    private TimerTask getTimerTask(Task task){
        return new TimerTask() {
            @Override
            public void run() {
                execute(task);
            }
        };
    }

    /**
     * Преобразует объект типа {@code LocalDateTime} в объект типа {@code Date},
     * с учетом поправочного коэффициента
     * @param localDateTime
     * @return
     */
    private Date localDataTimeToDate(LocalDateTime localDateTime){
        return new Date(localDateTime.toEpochSecond(ZoneOffset.UTC) * CORRECTION_FACTOR_FOR_CONVERSION_FROM_SECOND_TO_MILLS);
    }

    /**
     * Переводит временной промежуток типа {@code TimeIntervalType} в миллисекунды
     * @param period
     * @param type
     * @return
     */
    private Long getPeriodInMills(long period, TimeIntervalType type){
        return period * type.getFactor();
    }

}
