package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService service;

    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTaskById")
    public Optional<Task> getTaskById() {
        return service.findTaskById(1L);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask (@RequestParam Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(service.findTaskById(taskId).orElseThrow(TaskNotFoundException::new));
//        return new TaskDto(1L, "test title", "test_content");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTask")
    public TaskDto updateTask(TaskDto taskDto) {
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
//        return new TaskDto(1L, "Edited test title", "test_content");
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public void deleteTask(@RequestParam Long taskId) throws TaskNotFoundException {
        taskMapper.mapToTaskDto(service.findTaskById(taskId).orElseThrow(TaskNotFoundException::new));
    }
}
