package pl.edu.wat.wcy.pz.database.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.pz.database.entity.Category;

import java.security.Principal;

@RestController
@RequestMapping("/linguafranca/categories")
public class CategoryController {

    @GetMapping("/")
    public Object getAvailableCategories(Principal principal) {
        return principal.getName(); //test
    }

    @PostMapping("/")
    public Category createNewCategory() {
        return null;
    }

    @PutMapping("/{id}")
    public Category updateCategory() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory() {
        return null;
    }

    @GetMapping("/id")
    public Category getCategory() {
        return null;
    }
}
