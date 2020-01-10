package ru.alexeymalinov.taskautomation.robot.handlers;

import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.robot.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Handler implements Runnable {

    private static int CORRECTION_FACTOR_FOR_CONVERSION_FROM_SECOND_TO_MILLS = 1000;
    private final TaskManager taskManager;

    protected Handler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Отправляет задачу на выполнение
     * @param task
     */
    protected void execute(Task task) {
        taskManager.addTask(task);

    }

    /**
     * Отправляет задачу на выполнение в определенный момент времени
     * @param task
     * @param localDateTime
     */
    protected void schedule(Task task, LocalDateTime localDateTime) {
        new Timer().schedule(getTimerTask(task), localDataTimeToData(localDateTime));
    }

    /**
     * Отправляет задачу на выполнение в определенный момент времени и повторяет запуск через определенный промежуток времени
     * @param task
     * @param localDateTime
     * @param duration
     */
    protected void schedule(Task task, LocalDateTime localDateTime, Duration duration) {
        new Timer().schedule(getTimerTask(task), localDataTimeToData(localDateTime), getPeriodInMills(duration));
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
    private Date localDataTimeToData(LocalDateTime localDateTime){
        return new Date(localDateTime.toEpochSecond(ZoneOffset.UTC) * CORRECTION_FACTOR_FOR_CONVERSION_FROM_SECOND_TO_MILLS);
    }

    /**
     * Переводит временной промежуток типа {@Duration} в миллисекунды,
     * с учетом поправочного коэффициента
     * @param duration
     * @return
     */
    private Long getPeriodInMills(Duration duration){
        return duration.toSeconds() * CORRECTION_FACTOR_FOR_CONVERSION_FROM_SECOND_TO_MILLS;
    }

}
