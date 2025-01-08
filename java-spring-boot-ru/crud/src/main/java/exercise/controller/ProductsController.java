package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import exercise.model.Category;
import exercise.model.Product;
import exercise.repository.CategoryRepository;
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
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN

    @GetMapping(path = "")
    public List<ProductDTO> index(){
        List<ProductDTO> productsDTO = productRepository.findAll().stream().map(t -> productMapper.map(t)).toList();

        return productsDTO;
    }

    @GetMapping(path = "/{id}")
    public ProductDTO show(@PathVariable long id){
        var product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product with id " + id + " not found"));

        ProductDTO productDTO = productMapper.map(product);
        productDTO.setCategoryName(product.getCategory().getName());
        return productDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateDTO productCreateDTO){
        Category category = categoryRepository.findById(productCreateDTO.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"));

        Product product = productMapper.map(productCreateDTO);

        productRepository.save(product);

        ProductDTO productDTO = productMapper.map(product);
        return productDTO;
    }

    @PutMapping(path = "/{id}")
    public ProductDTO edit(@PathVariable long id, @RequestBody ProductUpdateDTO productUpdateDTO){
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task with id " + id + " not found"));

        if (productUpdateDTO.getCategoryId().isPresent()){
            Category category  = categoryRepository.findById(productUpdateDTO.getCategoryId().get())
                    .orElseThrow(() ->  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"));
        }

        productMapper.update(productUpdateDTO, product);
        productRepository.save(product);

        ProductDTO productDto = productMapper.map(product);
        return productDto;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
//        Task task = taskRepository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Task with id " + id + " not found"));

        productRepository.deleteById(id);
    }
    // END
}
