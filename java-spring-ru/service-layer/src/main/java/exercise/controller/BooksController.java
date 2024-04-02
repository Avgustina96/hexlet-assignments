package exercise.controller;

import java.util.List;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private AuthorMapper authorMapper;

    @GetMapping(path = "")
    public List<BookDTO> index() {
        var books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::map)
                .toList();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO show(@PathVariable long id) {

        var product = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));


        var productDTO = bookMapper.map(product);
        var category = authorRepository.findById(productDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        var categoryDTO = authorMapper.map(category);
        productDTO.setAuthorFirstName(categoryDTO.getFirstName());
        productDTO.setAuthorLastName(categoryDTO.getLastName());

        return productDTO;
    }


    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> create(@Valid @RequestBody BookCreateDTO productCreateDTO) {
        var product = bookMapper.map(productCreateDTO);
        var category = authorRepository.findById(productCreateDTO.getAuthorId());
        if (category.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        bookRepository.save(product);
        var productDTO = bookMapper.map(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookDTO update(@RequestBody @Valid BookUpdateDTO productData, @PathVariable Long id) {
        var product = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not Found: " + id));

        bookMapper.update(productData, product);
        bookRepository.save(product);
        var productDto = bookMapper.map(product);
        return productDto;
    }
}
