package pl.edu.wat.wcy.pz.database.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.pz.database.entity.Category;
import pl.edu.wat.wcy.pz.database.service.CategoryService;

import javax.validation.Valid;
import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/linguafranca/categories")
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("/")
    public Object getAvailableCategories(Principal principal) {
        return categoryService.getAvailableCategories(principal);
    }

    @PostMapping("/")
    public Category createNewCategory(@Valid @RequestBody Category category, Principal principal) {
        return categoryService.createNewCategory(category, principal);
    }

    @PutMapping("/{id}")
    public Category updateCategory() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory() {
        return null;
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable(name = "id") Long id, Principal principal) {
        return null;
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Object> joinToCategory(@PathVariable(name = "id") Long id, Principal principal) {
        return categoryService.joinToCategory(id, principal);
    }
}
