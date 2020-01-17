package ru.alexeymalinov.taskautomation.core.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.json.Converter;
import ru.alexeymalinov.taskautomation.core.model.Task;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class RemoteRepository implements Repository {

    private final static Logger LOGGER = LoggerFactory.getLogger(RemoteRepository.class);
    private final static Converter<Task> TASK_CONVERTER = new Converter<>();
    private final static String TASK_PATH = "/tasks";
    private final static String DELETE_PATH = "/delete";
    private final static String ADD_PATH = "/add";
    private final static String MALFORMED_URL_EXCEPTION_DESCRIPTION = "repository url is not correct";
    private final static String REMOTE_REPOS_URL_PARAM_NAME = "remote.repos.url";
    private final static String REPOSITORY_UNAVAILABLE = "repository unavailable";
    private final static String TASK_NOT_FOUND = "Task not found";
    private final static String UNKNOWN_ERROR = "unknown error";
    private final static String JSON_PROCESSING_EXCEPTION = "Task JSON has an invalid format";
    private final static String TASK_DELETED = "task deleted";
    private final static String PARAMETER_NAME_TO_DELETE_TASK = "?name=";
    private final String repositoryUrl;

    public RemoteRepository(Properties properties) {
        repositoryUrl = properties.getProperty(REMOTE_REPOS_URL_PARAM_NAME);
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
            return TASK_CONVERTER.toObject(message, Task.class);
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
            taskString = TASK_CONVERTER.toJSON(task);
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
            System.out.println(postRequest(url, taskString));
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

    private String getRequest(URL url) throws IOException {
        String message = "";
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            try (InputStream in = connection.getInputStream()) {
                message = readInputStream(in);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return message;
    }

    private String postRequest(URL url, String string) throws IOException {
        String message = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            try (OutputStream out = connection.getOutputStream()) {
                writeOutputStream(out, string);
            }
            try (InputStream in = connection.getInputStream()) {
                message = readInputStream(in);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return message;
    }

    private String readInputStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private void writeOutputStream(OutputStream out, String string) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(string);
        writer.flush();
    }

}
