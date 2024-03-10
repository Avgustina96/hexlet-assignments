package exercise.controller;

import exercise.model.Person;
import exercise.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable long id) {
        log.info("show ");
        return personRepository.findById(id).get();
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@Valid @RequestBody Person person) {
        personRepository.save(person);
        return person;
    }

    @GetMapping("")
    public ResponseEntity<List<Person>> showAll() {
        List<Person> people = personRepository.findAll();
        return ResponseEntity.ok(people);
    }



    @DeleteMapping(path = "/{id}")
    public void del(@PathVariable long id) {
        personRepository.deleteById(id);
    }

}
