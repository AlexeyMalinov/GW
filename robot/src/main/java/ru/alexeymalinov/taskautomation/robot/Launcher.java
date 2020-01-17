package ru.alexeymalinov.taskautomation.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.robot.handlers.Handler;
import ru.alexeymalinov.taskautomation.robot.handlers.JobFileHandler;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
        ScheduledExecutorService taskPool = Executors.newScheduledThreadPool(1);
        List<Handler> handlers = initializeHandlers(properties, taskPool);
        ExecutorService pool = Executors.newCachedThreadPool();
        startThreads(handlers, pool);
        String exit = "";
        while(!exit.equals("exit")){
            System.out.println("Please enter exit to stop the robot");
            exit = new Scanner(System.in).nextLine();
        }
        stopApp(taskPool, pool);
    }


    /**
     * Инициализирует все доступные в данном роботе обработчики заданий
     * TODO нужно убрать инициализацию из кода, использовать Spring DI
     */
    private static List<Handler> initializeHandlers(Properties properties, ScheduledExecutorService pool) {
        List<Handler>handlers = new ArrayList<>();
        handlers.add(new JobFileHandler(properties, pool));
        return handlers;
    }

    private static void stopApp(ExecutorService...pools){
        LOGGER.info("application shutdown");
        for (ExecutorService pool : pools) {
            pool.shutdownNow();
        }
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
