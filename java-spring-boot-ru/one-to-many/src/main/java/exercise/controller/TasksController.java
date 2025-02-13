package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.model.Task;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping(path = "")
    public List<TaskDTO> index(){
        List<TaskDTO> tasksDto = taskRepository.findAll().stream().map(t -> taskMapper.map(t)).toList();

        return tasksDto;
    }

    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable long id){
        var task = taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task with id " + id + " not found"));

        TaskDTO taskDto = taskMapper.map(task);

        return taskDto;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@RequestBody TaskCreateDTO taskCreateDTO){
        User assignee = userRepository.findById(taskCreateDTO.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + taskCreateDTO.getAssigneeId()));

        Task task = taskMapper.map(taskCreateDTO);

        assignee.addTask(task);
        taskRepository.save(task);

        TaskDTO taskDto = taskMapper.map(task);
        return taskDto;
    }

    @PutMapping(path = "/{id}")
    public TaskDTO edit(@PathVariable long id, @RequestBody TaskUpdateDTO taskUpdateDTO){
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task with id " + id + " not found"));

        User assigneeOld = userRepository.findById(task.getAssignee().getId()).orElse(null);
        User assigneeNew = userRepository.findById(taskUpdateDTO.getAssigneeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id " + taskUpdateDTO.getAssigneeId() + " not found"));

        assigneeOld.removeTask(task);
        taskMapper.update(taskUpdateDTO, task);
        assigneeNew.addTask(task);
        taskRepository.save(task);

        TaskDTO taskDto = taskMapper.map(task);
        return taskDto;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task with id " + id + " not found"));

        taskRepository.deleteById(id);
    }
    // END
}
