package ru.alexeymalinov.taskautomation.core.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Job {
    private String uid;
    private Integer id;
    private Integer robotId;
    private String name;
    private String taskName;
    private String repository;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int starHour;
    private int startMinute;
    private int startSecond;
    private long period;
    private String timeUnit;
    private int count;
    private String status;

    private Job() {
    }

    public Job(String uid,
               Integer id,
               Integer robotId,
               String name,
               String taskName,
               String repository,
               int startYear,
               int startMonth,
               int startDay,
               int starHours,
               int startMinute,
               int startSecond,
               int count,
               long period,
               String timeUnit,
               String status) {
        this.uid = uid;
        this.id = id;
        this.robotId = robotId;
        this.name = name;
        this.taskName = taskName;
        this.repository = repository;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.starHour = starHours;
        this.startMinute = startMinute;
        this.startSecond = startSecond;
        this.count = count;
        this.period = period;
        this.timeUnit = timeUnit;
        this.status = status;
    }


    public Job(String name,
               String taskName,
               String repository,
               LocalDateTime startTime,
               int count,
               long period,
               TimeUnit timeUnit) {
        this(
                "",
                0,
                0,
                name,
                taskName,
                repository,
                startTime.getYear(),
                startTime.getMonthValue(),
                startTime.getDayOfMonth(),
                startTime.getHour(),
                startTime.getMinute(),
                startTime.getSecond(),
                count,
                period,
                timeUnit.name(),
                ""
        );
    }

    public Job(String uid,
               Integer id,
               Integer robotId,
               String name,
               String taskName,
               String repository,
               LocalDateTime startTime,
               int count,
               long period,
               TimeUnit timeUnit,
               String status) {
        this(
                uid,
                id,
                robotId,
                name,
                taskName,
                repository,
                startTime.getYear(),
                startTime.getMonthValue(),
                startTime.getDayOfMonth(),
                startTime.getHour(),
                startTime.getMinute(),
                startTime.getSecond(),
                count,
                period,
                timeUnit.name(),
                status
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStarHour() {
        return starHour;
    }

    public void setStarHour(int starHour) {
        this.starHour = starHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getStartSecond() {
        return startSecond;
    }

    public void setStartSecond(int startSecond) {
        this.startSecond = startSecond;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRobotId() {
        return robotId;
    }

    public void setRobotId(Integer robotId) {
        this.robotId = robotId;
    }

    public LocalDateTime startTime() {
        return LocalDateTime.of(this.startYear, this.startMonth, this.startDay, this.starHour, this.startMinute, this.startSecond);
    }

    public TimeUnit timeUnit() {
        return TimeUnit.valueOf(timeUnit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return startYear == job.startYear &&
                startMonth == job.startMonth &&
                startDay == job.startDay &&
                starHour == job.starHour &&
                startMinute == job.startMinute &&
                startSecond == job.startSecond &&
                period == job.period &&
                count == job.count &&
                name.equals(job.name) &&
                taskName.equals(job.taskName) &&
                repository.equals(job.repository) &&
                timeUnit.equals(job.timeUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, taskName, repository, startYear, startMonth, startDay, starHour, startMinute, startSecond, period, timeUnit, count);
    }

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", taskName='" + taskName + '\'' +
                ", repositoryType='" + repository + '\'' +
                ", startYear=" + startYear +
                ", startMonth=" + startMonth +
                ", startDay=" + startDay +
                ", starHour=" + starHour +
                ", startMinute=" + startMinute +
                ", startSecond=" + startSecond +
                ", period=" + period +
                ", timeUnit='" + timeUnit + '\'' +
                ", count=" + count +
                '}';
    }
}
