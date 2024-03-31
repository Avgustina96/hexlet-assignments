package exercise.controller;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.CategoryMapper;
import exercise.mapper.ProductMapper;
import exercise.repository.CategoryRepository;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping(path = "")
    public List<ProductDTO> index() {
        var products = productRepository.findAll();
        return products.stream()
                .map(productMapper::map)
                .toList();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));


        var productDTO = productMapper.map(product);
        var category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        var categoryDTO = categoryMapper.map(category);
        productDTO.setCategoryName(categoryDTO.getName());
        return productDTO;
    }


    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        var product = productMapper.map(productCreateDTO);
        var category = categoryRepository.findById(productCreateDTO.getCategoryId());
        if (category.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        productRepository.save(product);
        var productDTO = productMapper.map(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public ProductDTO update(@RequestBody @Valid ProductUpdateDTO postData, @PathVariable Long id) {
//        var product = productRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
//
//        var category = categoryRepository.findById(postData.getCategoryId().get())
//                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
//        var categoryDTO = categoryMapper.map(category);
//        var productDTO = productMapper.map(product);
//        productDTO.setCategoryName(category.getName());
////        productDTO.setTitle(categoryDTO.get.getProducts().get(0).getTitle());
//
//        productDTO.setCategoryId(category.getId());
//        productMapper.update(postData, productDTO);
//         productMapper.map(productDTO);
//
//        productRepository.save(product);
//
//        return productDTO;
//    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductDTO update(@RequestBody @Valid ProductUpdateDTO productData, @PathVariable Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not Found: " + id));

        productMapper.update(productData, product);
        productRepository.save(product);
        var productDto = productMapper.map(product);
        return productDto;
    }

}
