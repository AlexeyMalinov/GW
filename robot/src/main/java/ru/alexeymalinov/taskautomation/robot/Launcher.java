package ru.alexeymalinov.taskautomation.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService;
import ru.alexeymalinov.taskautomation.robot.handlers.Handler;
import ru.alexeymalinov.taskautomation.robot.handlers.TaskFileHandler;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    private static ResourceBundle errorsText = ResourceBundle.getBundle("content.errors.text");
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties = readConfig();
            System.out.println(properties.get("local.repos.dir"));
        } catch (IOException exc) {
            LOGGER.error(errorsText.getString("error.io"), exc);
        }
        List<RobotService> services = initializeServices();
        TaskManager taskManager = initializeTaskManager(services);
        List<Handler> handlers = initializeHandlers(taskManager, properties);
        ExecutorService pool = Executors.newCachedThreadPool();
        startThreads(handlers, pool);
        pool.shutdown();
    }

    /**
     * Инициализирует список существующих в роботе сервисов
     * TODO нужно убрать инициализацию из кода, использовать Spring DI
     */
    private static List<RobotService> initializeServices() {
        List<RobotService> services = new ArrayList<>();
        services.add(new CliScriptService());
        services.add(new GuiScriptService());
        return services;
    }

    /**
     * Инициализирует менеджер задач
     * TODO нужно убрать инициализацию из кода, использовать Spring DI
     */
    private static TaskManager initializeTaskManager(List<RobotService> services) {
        return new TaskManager(services);
    }

    /**
     * Инициализирует все доступные в данном роботе обработчики заданий
     * TODO нужно убрать инициализацию из кода, использовать Spring DI
     */
    private static List<Handler> initializeHandlers(TaskManager taskManager, Properties properties) {
        List<Handler>handlers = new ArrayList<>();
        handlers.add(new TaskFileHandler(taskManager, properties));
        return handlers;
    }

    /**
     * Стартует потоки в заданном пуле потоков, на основе списка запускаемых объектов
     * @param runnableList
     * @param pool
     */
    private static void startThreads(List<? extends Runnable> runnableList, ExecutorService pool){
        for (Runnable runnable : runnableList) {
            pool.submit(runnable);
        }
    }

    /**
     * Читает конфигурационный файл с свойствами робота
     * @return
     * @throws IOException
     */
    private static Properties readConfig() throws IOException {
        Properties properties = new Properties();
        try (Reader reader = new FileReader(CONFIG_FILE_PATH)) {
            properties.load(reader);
        }
        return properties;
    }
}
