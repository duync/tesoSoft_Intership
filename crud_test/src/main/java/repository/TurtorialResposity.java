package repository;

import model.Turtorial;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TurtorialResposity extends MongoRepository<Turtorial, String> {
    List<Turtorial> findByTitleContaining(String title);

    List<Turtorial> findByPublished(Boolean published);
}
