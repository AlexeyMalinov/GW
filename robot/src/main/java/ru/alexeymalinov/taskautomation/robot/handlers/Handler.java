package ru.alexeymalinov.taskautomation.robot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.core.repository.Repository;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryFactory;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.robot.TaskExecutor;
import ru.alexeymalinov.taskautomation.robot.executor.TaskExecutorFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Handler implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    private final ScheduledExecutorService pool;
    private final List<RobotService> services;

    protected Handler(ScheduledExecutorService pool, List<RobotService> services) {
        this.pool = pool;
        this.services = services;
    }

    protected void schedule(Job job) {
        Repository repository = RepositoryFactory.getRepository(job.getRepository());
        if (job.getPeriod() == 0 || job.getCount() > 1) {
            for (int i = 1; i <= job.getCount(); i++) {
                LOGGER.debug("job: " + job.getName() + " started without period");
                pool.schedule(getTaskExecutor(job,repository, services), getDelay(job.startTime()), TimeUnit.SECONDS);
            }
        } else if (job.getPeriod() > 0) {
            LOGGER.debug("job: " + job.getName() + " started with period in seconds: " + TimeUnit.SECONDS.convert(job.getPeriod(), job.timeUnit()));
            pool.scheduleAtFixedRate(getTaskExecutor(job, repository, services)
                    , getDelay(job.startTime())
                    , TimeUnit.SECONDS.convert(job.getPeriod(), job.timeUnit())
                    , TimeUnit.SECONDS);
        }
        LOGGER.debug("job: " + job.getName() + " completed");
    }

    /**
     * Возвращает исполнителя задач
     * @param job
     * @param repository
     * @param services
     * @return
     */
    protected TaskExecutor getTaskExecutor(Job job, Repository repository, List<RobotService> services){
        return TaskExecutorFactory.getTaskExecutor(getTask(job, repository), services);
    }

    protected Task getTask(Job job, Repository repository) {
        return repository.getTask(job.getTaskName());
    }

    /**
     * Возвращает задержку между настоящим моментом времени и целевым, в секундах
     *
     * @param time
     * @return
     */
    private Long getDelay(LocalDateTime time) {
        return Duration.between(LocalDateTime.now(), time).toSeconds();
    }

}
