package ru.alexeymalinov.taskautomation.core.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Task;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import static ru.alexeymalinov.taskautomation.core.http.HttpRequest.*;

public class RemoteRepository implements Repository {

    private final static Logger LOGGER = LoggerFactory.getLogger(RemoteRepository.class);
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static String TASK_PATH = "/tasks";
    private final static String DELETE_PATH = "/delete";
    private final static String ADD_PATH = "/add";
    private final static String MALFORMED_URL_EXCEPTION_DESCRIPTION = "repository url is not correct";
    private final static String REPOSITORY_UNAVAILABLE = "repository unavailable";
    private final static String TASK_NOT_FOUND = "Task not found";
    private final static String UNKNOWN_ERROR = "unknown error";
    private final static String JSON_PROCESSING_EXCEPTION = "Task JSON has an invalid format";
    private final static String TASK_DELETED = "task deleted";
    private final static String PARAMETER_NAME_TO_DELETE_TASK = "?name=";
    private final static String CONTENT_TYPE = "application/json";
    private final static String CHARSET_NAME = "utf-8";
    private final String repositoryUrl;

    public RemoteRepository(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    /**
     * {@inheritDoc}
     * @param taskName - имя задачи
     * @return
     */
    @Override
    public Task getTask(String taskName) {
        URL url = null;
        try {
            url = new URL(repositoryUrl + TASK_PATH + "/" + taskName);
        } catch (MalformedURLException e) {
            LOGGER.error(MALFORMED_URL_EXCEPTION_DESCRIPTION, e);
        }
        String message = null;
        try {
            message = getRequest(url);
        } catch (IOException e) {
            LOGGER.error(REPOSITORY_UNAVAILABLE, e);
        }
        if (message == null || message.isEmpty() ) {
            LOGGER.error(TASK_NOT_FOUND);
            throw new IllegalArgumentException(TASK_NOT_FOUND);
        }
        try {
            return MAPPER.readValue(message, Task.class);
        } catch (JsonProcessingException e) {
            LOGGER.error(JSON_PROCESSING_EXCEPTION, e);
        }
        LOGGER.error(UNKNOWN_ERROR);
        throw new IllegalArgumentException(UNKNOWN_ERROR);
    }

    /**
     * {@inheritDoc}
     * @param task
     */
    @Override
    public void publishTask(Task task) {
        String taskString = "";
        try {
            taskString = MAPPER.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            LOGGER.error(JSON_PROCESSING_EXCEPTION, e);
        }
        URL url = null;
        try {
            url = new URL(repositoryUrl + TASK_PATH + "/" + ADD_PATH);
        } catch (MalformedURLException e) {
            LOGGER.error(MALFORMED_URL_EXCEPTION_DESCRIPTION, e);
        }
        try {
            System.out.println(postRequest(url, taskString, CONTENT_TYPE, CHARSET_NAME));
        } catch (IOException e) {
            LOGGER.error(REPOSITORY_UNAVAILABLE, e);
        }
    }

    /**
     * {@inheritDoc}
     * @param taskName
     */
    @Override
    public void removeTask(String taskName) {
        StringBuilder sb = new StringBuilder(repositoryUrl)
                .append(TASK_PATH)
                .append("/")
                .append(DELETE_PATH)
                .append(PARAMETER_NAME_TO_DELETE_TASK)
                .append(taskName);
        URL url = null;
        try {
            url = new URL(sb.toString());
        } catch (MalformedURLException e) {
            LOGGER.error(MALFORMED_URL_EXCEPTION_DESCRIPTION, e);
        }
        try {
            String out = getRequest(url);
            LOGGER.info(TASK_DELETED);
        } catch (IOException e) {
            LOGGER.error(REPOSITORY_UNAVAILABLE, e);
        }
    }

    /**
     * {@inheritDoc}
     * @param taskName
     * @return
     */
    @Override
    public boolean taskExist(String taskName) {
        URL url = null;
        try {
            url = new URL(repositoryUrl + TASK_PATH + "/" + taskName);
        } catch (MalformedURLException e) {
            LOGGER.error(MALFORMED_URL_EXCEPTION_DESCRIPTION, e);
        }
        try {
            getRequest(url);
        } catch (IOException e) {
            LOGGER.info(TASK_NOT_FOUND);
            return false;
        }
        return true;
    }

}
