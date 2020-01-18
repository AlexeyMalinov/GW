package ru.alexeymalinov.taskautomation.robot.handlers.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.model.Job;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JobFileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobFileParser.class);
    private static final String JOB_FORMAT_ERROR = "Job format is not valid";
    private static final int COUNT_JOB_OPTIONS = 12;

    public static List<Job> parseFile(String filePath) throws IOException {
        List<Job> jobs = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        for (String line : lines) {
            if(!line.startsWith("#")) {
                jobs.add(parseLine(line));
            }
        }
        return jobs;
    }

    public static Job parseLine(String string) {
        if ((string != null) && (!string.isEmpty())) {
            String[] element = string.split(";");
            if (element.length == COUNT_JOB_OPTIONS) {
                LocalDateTime startTime = LocalDateTime.of(
                        Integer.parseInt(element[3]),
                        Integer.parseInt(element[4]),
                        Integer.parseInt(element[5]),
                        Integer.parseInt(element[6]),
                        Integer.parseInt(element[7]),
                        Integer.parseInt(element[8]));
                String repository = (element[2]);
                int count = Integer.parseInt(element[9]);
                long period = Long.parseLong(element[10]);
                TimeUnit timeUnit = TimeUnit.valueOf(element[11]);
                return new Job(element[0],element[1], repository, startTime, count, period, timeUnit);
            }

        }
        LOGGER.error(JOB_FORMAT_ERROR);
        throw new IllegalArgumentException(JOB_FORMAT_ERROR);
    }
}
