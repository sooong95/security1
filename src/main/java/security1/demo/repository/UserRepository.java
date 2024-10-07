package security1.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security1.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
