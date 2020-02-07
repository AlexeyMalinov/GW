package ru.alexeymalinov.taskautomation.robot.connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.JobStatus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ru.alexeymalinov.taskautomation.core.http.HttpRequest.*;
public class OrchestratorConnectionImpl implements OrchestratorConnection {

    private final Logger LOGGER = LoggerFactory.getLogger(OrchestratorConnection.class);

    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static String ADD_JOB_STATUS = "/robots/jobs/status/add";
    private final static String MALFORMED_URL_EXCEPTION_DESCRIPTION = "orchestrator url is not correct";
    private final static String HOST_UNAVAILABLE = "orchestrator unavailable";
    private final static String ROBOT_NOT_FOUND = " not found";
    private final static String UNKNOWN_ERROR = "unknown error";
    private final static String JSON_PROCESSING_EXCEPTION = "Task JSON has an invalid format";
    private final static String CONTENT_TYPE = "application/json";
    private final static String CHARSET_NAME = "utf-8";

    private final String NEW_JOB_PATH = "/robots/jobs/new/get/";
    private final String OLD_JOB_PATH = "/robots/jobs/old/get/";

    private final Integer id;
    private final String orchestratorUrl;

    public OrchestratorConnectionImpl(Integer id, String orchestratorUrl) {
        this.id = id;
        this.orchestratorUrl = orchestratorUrl;
    }

    @Override
    public List<Job> getJobsByJobStatus(JobStatus jobStatus) {
        String endPoint = "";
        if(jobStatus.equals(JobStatus.READY)){
            endPoint = NEW_JOB_PATH;
        } else if (jobStatus.equals(JobStatus.STARTED)){
            endPoint = OLD_JOB_PATH;
        } else {
            return Collections.emptyList();
        }
        LOGGER.info("Сonnection to orchestra: "+ orchestratorUrl + " with robot id: " + id);
        URL url = null;
        try {
            url = new URL(orchestratorUrl + endPoint + id.toString());
        } catch (MalformedURLException e) {
            LOGGER.error(MALFORMED_URL_EXCEPTION_DESCRIPTION, e);
        }
        String message = null;
        try {
            message = getRequest(url);
        } catch (IOException e) {
            LOGGER.error(HOST_UNAVAILABLE, e);
        }
        if (message == null || message.isEmpty() ) {
            LOGGER.error(ROBOT_NOT_FOUND);
            throw new IllegalArgumentException(ROBOT_NOT_FOUND);
        }
        try {
            CollectionType javaType = MAPPER.getTypeFactory().constructCollectionType(List.class, Job.class);
            List<Job> jobs =  MAPPER.readValue(message, javaType);
            LOGGER.info("jobs: " + jobs.size());
            return jobs;
        } catch (JsonProcessingException e) {
            LOGGER.error(JSON_PROCESSING_EXCEPTION, e);
        }
        LOGGER.error(UNKNOWN_ERROR);
        throw new IllegalArgumentException(UNKNOWN_ERROR);
    }

    @Override
    public void setJobStatus(Job job) {
        String taskString = "";
        try {
            taskString = MAPPER.writeValueAsString(job);
        } catch (JsonProcessingException e) {
            LOGGER.error(JSON_PROCESSING_EXCEPTION, e);
        }
        URL url = null;
        try {
            url = new URL(orchestratorUrl + ADD_JOB_STATUS);
        } catch (MalformedURLException e) {
            LOGGER.error(MALFORMED_URL_EXCEPTION_DESCRIPTION, e);
        }
        try {
            System.out.println(postRequest(url, taskString, CONTENT_TYPE, CHARSET_NAME));
        } catch (IOException e) {
            LOGGER.error(HOST_UNAVAILABLE, e);
        }
    }


}
