package pl.java.tetris.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.java.tetris.entities.Game_Results;

public interface GameResultRepository extends JpaRepository<Game_Results, Long> {
}
