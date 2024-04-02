package exercise.controller;

import exercise.dto.AuthorDTO;
import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper authorMapper;

    @GetMapping(path = "")
    public List<AuthorDTO> index() {
        var categories = authorRepository.findAll();
        return categories.stream()
                .map(authorMapper::map)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public AuthorDTO show(@PathVariable long id) {

        var category =  authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        var categoryDTO = authorMapper.map(category);
        return categoryDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@Valid @RequestBody AuthorCreateDTO categoryData) {
        var category = authorMapper.map(categoryData);
        authorRepository.save(category);
        var categoryDto = authorMapper.map(category);
        return categoryDto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AuthorDTO update(@RequestBody @Valid AuthorUpdateDTO productData, @PathVariable Long id) {
        var product = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not Found: " + id));

        authorMapper.update(productData, product);
        authorRepository.save(product);
        var productDto = authorMapper.map(product);
        return productDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroy(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }
}
