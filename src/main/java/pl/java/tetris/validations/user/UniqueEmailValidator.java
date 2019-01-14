package pl.java.tetris.validations.user;

import org.springframework.beans.factory.annotation.Autowired;
import pl.java.tetris.repositories.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  @Autowired
  UserRepository userRepository;

  public void initialize(UniqueEmail constraint) {}

  public boolean isValid(String email, ConstraintValidatorContext context) {
    return email == null || userRepository.findUserByEmail(email) == null;
  }
}
