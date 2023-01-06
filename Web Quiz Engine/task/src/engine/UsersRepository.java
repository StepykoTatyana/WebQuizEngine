package engine;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UsersRepository extends CrudRepository<Users, String> {
    @Query(value = "Select top 1 * from users where email=?1", nativeQuery = true)
    Users findByEmail(@Param("email") String email);
}
