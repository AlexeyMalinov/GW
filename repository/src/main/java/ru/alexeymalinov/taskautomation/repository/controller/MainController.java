package ru.alexeymalinov.taskautomation.repository.controller;

import org.springframework.web.bind.annotation.*;
import ru.alexeymalinov.taskautomation.core.model.Task;
import ru.alexeymalinov.taskautomation.repository.db.entity.TaskEntity;
import ru.alexeymalinov.taskautomation.repository.service.TaskService;

import java.util.List;

@RestController
public class MainController {

    private final TaskService taskService;

    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("/tasks")
    public List<TaskEntity> allTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/{taskName}")
    public String getTaskByName(@PathVariable("taskName") String taskName) {
        return taskService.getTask(taskName).get(0).getValue();
    }

    @RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
    public String addTask(@RequestBody Task task) {
        try {
            taskService.addTask(task);
        } catch (Exception exc) {
            return String.format("{\"result\":\"success\", \"error\": \"%s\"}", exc.getMessage());
        }
        return "{\"result\":\"success\", \"error\": \"\"}";
    }

    @GetMapping("/tasks/delete")
    public String deleteTask(@RequestParam(value = "name") String name) {
        try {
            taskService.deleteTask(name);
        } catch (Exception exc) {
            return String.format("{\"result\":\"success\", \"error\": \"%s\"}", exc.getMessage());
        }
        return "{\"result\":\"success\", \"error\": \"\"}";
    }
}
