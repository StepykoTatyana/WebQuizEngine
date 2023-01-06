package engine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuizzesRepository extends CrudRepository<Quizzes, Long>  {
    @Query(value = "Select*from quizzes", nativeQuery = true)
    List<Quizzes> findAllQuizzes();

    @Query(value = "Select top 1 id from quizzes order by id desc", nativeQuery = true)
    long lastId();
}
