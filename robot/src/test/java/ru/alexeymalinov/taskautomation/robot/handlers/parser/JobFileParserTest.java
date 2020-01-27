package ru.alexeymalinov.taskautomation.robot.handlers.parser;

import org.junit.jupiter.api.Test;
import ru.alexeymalinov.taskautomation.core.model.Job;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JobFileParserTest {

    @Test
    void parseFile() {
        Job job = new Job("job","task","http://localhost:8080",2020,1,1,0,0,0,0,0,"MINUTES");
        List<Job> jobOut = null;
        try {
            jobOut = JobFileParser.parseFile("./src/test/resources/jobs.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(job,jobOut.get(0));
    }

    @Test
    void parseLine() {
        String string = "job;task;REMOTE;2020;1;1;0;0;0;0;0;MINUTES";
        Job job = new Job("job","task","REMOTE",2020,1,1,0,0,0,0,0,"MINUTES");
        Job jobOut = JobFileParser.parseLine(string);
        assertEquals(job,jobOut);
    }
}