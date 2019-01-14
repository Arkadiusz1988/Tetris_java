package pl.java.tetris.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.java.tetris.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  User findUserByUsername(String username);

  User findUserByEmail(String email);

  List<User> findAllById(Long id);



}
