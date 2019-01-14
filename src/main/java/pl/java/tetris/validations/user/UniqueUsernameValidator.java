package pl.java.tetris.validations.user;

import org.springframework.beans.factory.annotation.Autowired;
import pl.java.tetris.repositories.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

  @Autowired
  UserRepository userRepository;

  @Override
  public void initialize(UniqueUsername constraintAnnotation) {}

  public boolean isValid(String username, ConstraintValidatorContext context) {
    return username == null || userRepository.findUserByUsername(username) == null;
  }
}
