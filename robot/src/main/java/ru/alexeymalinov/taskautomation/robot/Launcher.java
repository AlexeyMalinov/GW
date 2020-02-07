package ru.alexeymalinov.taskautomation.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;
import ru.alexeymalinov.taskautomation.core.services.RobotService;
import ru.alexeymalinov.taskautomation.core.services.clirobotservice.CliScriptService;
import ru.alexeymalinov.taskautomation.core.services.guirobotservice.GuiScriptService;
import ru.alexeymalinov.taskautomation.robot.handlers.Handler;
import ru.alexeymalinov.taskautomation.robot.handlers.JobFileHandler;
import ru.alexeymalinov.taskautomation.robot.handlers.OrchestratorHandler;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Launcher {

    private static ResourceBundle errorsText = ResourceBundle.getBundle("content.errors.text");
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);


    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties = readConfig();
        } catch (IOException exc) {
            LOGGER.error(errorsText.getString("error.io"), exc);
        }
        ScheduledExecutorService taskPool = Executors.newScheduledThreadPool(1);
        ScheduledExecutorService handlerPool = Executors.newScheduledThreadPool(1);
        List<RobotService> robotServices = initializeServices(properties);
        Handler fileJobHandler = new JobFileHandler(initializeServices(properties), taskPool, properties.getProperty("local.job.file.path"));
        Handler orchestratorHandler = new OrchestratorHandler(robotServices,taskPool,Integer.valueOf(properties.getProperty("orchestrator.robot.id")),properties.getProperty("orchestrator.url"));
        Handler restartHandler = new OrchestratorHandler(robotServices,taskPool,Integer.valueOf(properties.getProperty("orchestrator.robot.id")),properties.getProperty("orchestrator.url"), JobStatus.STARTED);
        handlerPool.submit(restartHandler);
        handlerPool.scheduleAtFixedRate(orchestratorHandler, 0, 1 , TimeUnit.MINUTES);
        handlerPool.submit(fileJobHandler);

        String exit = "";
        while(!exit.equals("exit")){
            System.out.println("Please enter exit to stop the robot");
            exit = new Scanner(System.in).nextLine();
        }
        stopApp(taskPool, handlerPool);
    }


    /**
     * Инициализирует список существующих в роботе сервисов
     */
    private static List<RobotService> initializeServices(Properties properties) {
        List<RobotService> services = new ArrayList<>();
        services.add(new CliScriptService(properties.getProperty("workspace.path")));
        services.add(new GuiScriptService());
        return services;
    }

    private static void stopApp(ExecutorService...pools){
        LOGGER.info("application shutdown");
        for (ExecutorService pool : pools) {
            pool.shutdownNow();
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
