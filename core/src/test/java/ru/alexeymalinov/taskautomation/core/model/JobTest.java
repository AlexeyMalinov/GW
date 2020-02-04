package ru.alexeymalinov.taskautomation.core.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JobTest {

    Job job = new Job("", 0, 0, "job","task","http://localhost:8080",2020,1,1,0,0,0,0,0,"SECONDS","");

    @Test
    void startTime() {
        LocalDateTime time = LocalDateTime.of(2020,1,1,0,0,0);
        assertEquals(job.startTime(), time);
    }

    @Test
    void timeUnit() {
        assertEquals(job.timeUnit(), TimeUnit.SECONDS);
    }
}