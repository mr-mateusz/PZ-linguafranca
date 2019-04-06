package pl.edu.wat.wcy.pz.database.service;

import org.springframework.http.ResponseEntity;
import pl.edu.wat.wcy.pz.database.entity.Category;

import java.security.Principal;
import java.util.List;

public interface CategoryService {
    List<Category> getAvailableCategories(Principal principal);

    Category createNewCategory(Category category, Principal principal);

    ResponseEntity<Object> joinToCategory(Long id, Principal principal);
}
