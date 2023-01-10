package engine;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuizzesRepositoryWithCrud extends PagingAndSortingRepository<Quizzes, Long> {
    @Query(value = "Select top 1 quiz_id from quizzes order by quiz_id desc", nativeQuery = true)
    long lastId();
}
