package exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Task;
import exercise.repository.TaskRepository;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    private Task task1;

    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    private Task generateTask() {
        return Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .create();
    }

    private Task createTask() {
        task1 = generateTask();
        taskRepository.save(task1);
        return task1;
    }

    @Test
    public void testShow() throws Exception {
        task1 = createTask();
        mockMvc.perform(get("/tasks/{id}", task1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        task1 = createTask();
        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(task1));

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        task1 = createTask();
        var task2 = createTask();

        var request = put("/tasks/{id}", task1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(task2));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var actualComment = taskRepository.findById(task1.getId()).get();

        assertThat(task1.getDescription()).isEqualTo(task2.getDescription());
    }

    @Test
    public void testDelete() throws Exception {
       var  task2 = createTask();

        mockMvc.perform(delete("/tasks/{id}", task2.getId()))
                .andExpect(status().isOk());

//        assertThat(taskRepository.findAll()).isEmpty();
    }

}
