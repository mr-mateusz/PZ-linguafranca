package pl.edu.wat.wcy.pz.database;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import pl.edu.wat.wcy.pz.database.entity.Category;
import pl.edu.wat.wcy.pz.database.entity.Collection;
import pl.edu.wat.wcy.pz.database.repository.CategoryRepository;
import pl.edu.wat.wcy.pz.database.repository.CollectionRepository;
import pl.edu.wat.wcy.pz.database.repository.WordAnswerRepository;
import pl.edu.wat.wcy.pz.database.repository.WordRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class DatabaseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner init(CategoryRepository categoryRepository, CollectionRepository collectionRepository, WordAnswerRepository wordAnswerRepository,
                           WordRepository wordRepository) {
        return args -> {

            Category category = Category.builder()
                    .name("Animals")
                    .description("Example animals category")
                    .owner(1L)
                    .isPublic(true)
                    .difficulty(10)
                    .build();

            categoryRepository.save(category);

            category = categoryRepository.findByName("Animals").orElse(null);

            System.out.println(category);
//
//            Collection collection = Collection.builder()
//                    .userId(1L)
//                    .isModifiable(false)
//                    .learningProgress(0)
//                    .category()
//
        };
    }


}
