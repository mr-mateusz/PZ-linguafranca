package pl.edu.wat.wcy.pz.database;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import pl.edu.wat.wcy.pz.database.entity.*;
import pl.edu.wat.wcy.pz.database.repository.*;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DatabaseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner init(CategoryRepository categoryRepository, CollectionRepository collectionRepository, WordAnswerRepository wordAnswerRepository,
                           WordRepository wordRepository, UserRepository userRepository) {
        return args -> {

            User user = new User();
            user.setUsername("mateusz");

            userRepository.save(user);

            User user2 = new User();
            user2.setUsername("user");
            userRepository.save(user2);

            Category category = Category.builder()
                    .name("Animals")
                    .description("Example animals category!")
                    .owner(user)
                    .isPublic(true)
                    .difficulty(10)
                    .build();

            categoryRepository.save(category);

            category = categoryRepository.findByName("Animals").orElse(null);

            System.out.println(category);

            Category category2 = Category.builder()
                    .name("City")
                    .description("Livin' in the City")
                    .owner(null)
                    .isPublic(true)
                    .difficulty(5)
                    .build();

            categoryRepository.save(category2);

            Collection collection = Collection.builder()
                    .user(user)
                    .isModifiable(false)
                    .learningProgress(0)
                    .category(category)
                    .build();

            System.out.println(collection);
            collectionRepository.save(collection);
            collection = collectionRepository.findAll().get(0);
            System.out.println(collection);

            Word word = Word.builder()
                    .category(category)
                    .difficulty(1)
                    .engTranslation("engTr")
                    .plTranslation("plTr")
                    .build();


            Example example1 = new Example();
            example1.setExample("Example1");
            example1.setWord(word);
            Example example2 = new Example();
            example2.setExample("Example2");
            example2.setWord(word);

            word.setEngExamples(Arrays.asList(example1, example2));

            wordRepository.save(word);

            WordAnswer wordAnswer = WordAnswer.builder()
                    .word(word)
                    .collection(collection)
                    .correctAnswers(2)
                    .incorrectAnswers(1)
                    .counter(1)
                    .build();

            wordAnswerRepository.save(wordAnswer);
        };
    }


}
