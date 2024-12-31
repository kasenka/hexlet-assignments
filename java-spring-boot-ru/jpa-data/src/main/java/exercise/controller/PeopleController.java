package exercise.controller;

import exercise.repository.PersonRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import exercise.model.Person;

@RestController
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/people/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    @GetMapping("/people")
    public List<Person> index(){
        return personRepository.findAll();
    }

    @PostMapping("/people")
    public Person create(@RequestBody Person person){
        personRepository.save(person);
        return person;
    }

    @DeleteMapping("/people/{id}")
    public void del(@PathVariable Long id){
        personRepository.deleteById(id);
    }

    
    // END
}
