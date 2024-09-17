package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
public class CategoryRestController {

    @Autowired
    private CategoryService service;

    @GetMapping("/api/categories")
    public List<Category> getCategories() {
        return service.listAll();
    }
}
