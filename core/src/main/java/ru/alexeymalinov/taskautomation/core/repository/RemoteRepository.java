package ru.alexeymalinov.taskautomation.core.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.alexeymalinov.taskautomation.core.json.Converter;
import ru.alexeymalinov.taskautomation.core.model.Task;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class RemoteRepository implements Repository {

    private final static Converter<Task> TASK_CONVERTER = new Converter<>();
    private final static String TASK_PATH = "/tasks";
    private final static String DELETE_PATH = "/delete";
    private final static String ADD_PATH = "/add";

    private final String repositoryUrl;

    public RemoteRepository(Properties properties) {
        repositoryUrl = properties.getProperty("remote.repos.url");
    }

    @Override
    public Task getTask(String taskName) {
        URL url = null;
        try {
            url = new URL(repositoryUrl + TASK_PATH + "/" + taskName);
        } catch (MalformedURLException e) {
            //TODO логирование
            e.printStackTrace();
        }
        String message = getRequest(url);
        if (message.isEmpty()) {
            throw new IllegalArgumentException("Task not found");
        }
        try {
            return TASK_CONVERTER.toObject(message, Task.class);
        } catch (JsonProcessingException e) {
            //TODO логирование
            e.printStackTrace();
        }
        //TODO логирование и проверка статусов репозитория
        throw new IllegalStateException("");
    }

    @Override
    public void publishTask(Task task) {
        String taskString = "";
        try {
            taskString = TASK_CONVERTER.toJSON(task);
        } catch (JsonProcessingException e) {
            //TODO логирование
            e.printStackTrace();
        }
        URL url = null;
        try {
            url = new URL(repositoryUrl + TASK_PATH + "/" + ADD_PATH);
        } catch (MalformedURLException e) {
            //TODO логирование
            e.printStackTrace();
        }
        //TODO логирование и проверка статусов репозитория
        System.out.println(postRequest(url, taskString));
    }

    @Override
    public void removeTask(String taskName) {
        StringBuilder sb = new StringBuilder(repositoryUrl)
                .append(TASK_PATH)
                .append("/")
                .append(DELETE_PATH)
                .append("?name=")
                .append(taskName);
        URL url = null;
        try {
            url = new URL(sb.toString());
        } catch (MalformedURLException e) {
            //TODO логирование
            e.printStackTrace();
        }
        //TODO логирование и проверка статусов репозитория
        System.out.println(getRequest(url));
    }

    private String getRequest(URL url) {
        String message = "";
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            try(InputStream in = connection.getInputStream()) {
                message = readInputStream(in);
            }
        } catch (IOException e) {
            //TODO Логирование
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return message;
    }

    private String postRequest(URL url, String string) {
        String message = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            try(OutputStream out = connection.getOutputStream()){
                writeOutputStream(out,string);
            }
            try(InputStream in = connection.getInputStream()){
                message = readInputStream(in);
            }
        } catch (IOException e) {
            //TODO Логирование
            e.printStackTrace();
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
