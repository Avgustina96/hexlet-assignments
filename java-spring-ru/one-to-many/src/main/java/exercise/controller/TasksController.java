package exercise.controller;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.TaskMapper;
import exercise.model.User;
import exercise.repository.TaskRepository;
import exercise.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping(path = "")
    public List<TaskDTO> index() {
        var users = taskRepository.findAll();
        return users.stream()
                .map(p -> taskMapper.map(p))
                .toList();
    }

    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable long id) {

        var user = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        var userDto = taskMapper.map(user);
        return userDto;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);
        taskRepository.save(task);
        var userDto = taskMapper.map(task);
        return userDto;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO update(@PathVariable long id, @RequestBody TaskUpdateDTO taskUpdateData) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        var user = userRepository.findById(taskUpdateData.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + taskUpdateData.getAssigneeId() + " not found"));
        taskMapper.update(taskUpdateData, task);
        task.setAssignee(user);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        taskRepository.delete(task);
    }
}
