package com.yukismimi.rubbishmanagement.category;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CategoryController {

    private CategoryRepository repository;

    @GetMapping("/category")
    @ApiOperation("查询垃圾分类的种类")
    public List findAll(){
        return repository.findAll();
    }

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }
}
