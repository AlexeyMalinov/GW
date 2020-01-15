package ru.alexeymalinov.taskautomation.robot.handlers.parser;

import ru.alexeymalinov.taskautomation.core.model.Job;
import ru.alexeymalinov.taskautomation.core.model.TimeIntervalType;
import ru.alexeymalinov.taskautomation.core.repository.RepositoryType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JobFileParser {

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
            if (element.length == 11) {
                LocalDateTime startTime = LocalDateTime.of(
                        Integer.parseInt(element[3]),
                        Integer.parseInt(element[4]),
                        Integer.parseInt(element[5]),
                        Integer.parseInt(element[6]),
                        Integer.parseInt(element[7]),
                        Integer.parseInt(element[8]));
                RepositoryType repositoryType = RepositoryType.valueOf(element[2]);
                long period = Long.parseLong(element[9]);
                TimeIntervalType timeIntervalType = TimeIntervalType.valueOf(element[10]);
                return new Job(element[0],element[1], repositoryType, startTime, period, timeIntervalType);
            }
        }
        throw new IllegalArgumentException("Job format is not valid");
    }
}
