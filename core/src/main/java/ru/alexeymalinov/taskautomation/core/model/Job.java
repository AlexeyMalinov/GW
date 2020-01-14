package ru.alexeymalinov.taskautomation.core.model;

import ru.alexeymalinov.taskautomation.core.repository.RepositoryType;

import java.time.LocalDateTime;

public class Job {
    private String name;
    private String taskName;
    private String repositoryType;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int starHour;
    private int startMinute;
    private int startSecond;
    private long period;
    private String unitOfTime;

    private Job() {
    }

    private Job(String name,
               String taskName,
               String repositoryType,
               int startYear,
               int startMonth,
               int startDay,
               int starHours,
               int startMinute,
               int startSecond,
               long period,
               String unitOfTime) {
        this.name = name;
        this.taskName = taskName;
        this.repositoryType = repositoryType;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.starHour = starHours;
        this.startMinute = startMinute;
        this.startSecond = startSecond;
        this.period = period;
        this.unitOfTime = unitOfTime;
    }

    public Job (String name,
                String taskName,
                RepositoryType repositoryType,
                LocalDateTime startTime,
                long period,
                TimeIntervalType timeType){
        this(
                name,
                taskName,
                repositoryType.name(),
                startTime.getYear(),
                startTime.getMonthValue(),
                startTime.getDayOfMonth(),
                startTime.getHour(),
                startTime.getMinute(),
                startTime.getSecond(),
                period,
                timeType.name()
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

    public String getRepositoryType() {
        return repositoryType;
    }

    public void setRepositoryType(String repositoryType) {
        this.repositoryType = repositoryType;
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

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public String getUnitOfTime() {
        return unitOfTime;
    }

    public void setUnitOfTime(String unitOfTime) {
        this.unitOfTime = unitOfTime;
    }

    public RepositoryType repositoryType(){
        return RepositoryType.valueOf(repositoryType);
    }

    public LocalDateTime startTime(){
        return LocalDateTime.of(this.startYear, this.startMonth, this.startDay, this.starHour, this.startMinute, this.startSecond);
    }

    public TimeIntervalType intervalType(){
        return TimeIntervalType.valueOf(unitOfTime);
    }
}