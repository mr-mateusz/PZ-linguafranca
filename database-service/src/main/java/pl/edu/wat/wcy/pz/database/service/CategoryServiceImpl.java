package pl.edu.wat.wcy.pz.database.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.pz.database.entity.Category;
import pl.edu.wat.wcy.pz.database.entity.Collection;
import pl.edu.wat.wcy.pz.database.entity.User;
import pl.edu.wat.wcy.pz.database.repository.CategoryRepository;
import pl.edu.wat.wcy.pz.database.repository.CollectionRepository;
import pl.edu.wat.wcy.pz.database.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private CollectionRepository collectionRepository;

    @Override
    public List<Category> getAvailableCategories(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User: " + principal.getName() + "not found"));

        List<Category> categories = categoryRepository.findAllByIsPublicIsTrueOrOwner(user);
        System.out.println(categories);
        List<Collection> collections = collectionRepository.findAllByUser(user);
        System.out.println(collections);
        categories.removeIf(category -> collections.stream().map(Collection::getCategory).collect(Collectors.toList()).contains(category));
        return categories;
    }

    @Override
    public Category createNewCategory(Category category, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User: " + principal.getName() + "not found"));

        category.setOwner(user);
        category.setPublic(false);
        Category createdCategory = categoryRepository.save(category);

        Collection collection = Collection.builder()
                .category(createdCategory)
                .user(user)
                .isModifiable(true)
                .learningProgress(0)
                .build();
        collectionRepository.save(collection);

        return createdCategory;
    }

    @Override
    public ResponseEntity<Object> joinToCategory(Long id, Principal principal) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category: " + id + " not found!"));
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User: " + principal.getName() + "not found"));

        if (!category.isPublic())
            return ResponseEntity.badRequest().build();

        Collection collection = Collection.builder()
                .user(user)
                .category(category)
                .learningProgress(0)
                .isModifiable(false)
                .build();

        collectionRepository.save(collection);
        return ResponseEntity.ok().build();
    }
}
