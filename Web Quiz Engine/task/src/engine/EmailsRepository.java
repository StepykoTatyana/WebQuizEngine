package engine;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmailsRepository extends PagingAndSortingRepository<Emails, Long> {

//    @Query(value = "Select * from EMAILS where email=?1", nativeQuery = true)
//    List<Emails> getCompletedQuizzesByUser(@Param("emails") String email);
    List<Emails> findByEmail(String email, Pageable pageable);

    @Query(value = "Select top 1 * from emails where quiz_id=?1", nativeQuery = true)
    Emails findByQuizId(@Param("quiz_id") Long quiz_id);

}
