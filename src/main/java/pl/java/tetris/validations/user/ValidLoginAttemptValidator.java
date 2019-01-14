package pl.java.tetris.validations.user;


import org.springframework.beans.factory.annotation.Autowired;
import pl.java.tetris.dto.UserDto;
import pl.java.tetris.entities.User;
import pl.java.tetris.repositories.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidLoginAttemptValidator implements ConstraintValidator<ValidLoginAttempt, UserDto> {

  @Autowired
  UserRepository userRepository;

  public void initialize(ValidLoginAttempt constraint) {}

  public boolean isValid(UserDto loginAttempt, ConstraintValidatorContext context) {
    User user = userRepository.findByEmail(loginAttempt.getEmail());
    return user != null && user.passwordMatches(loginAttempt.getPassword());
  }
}
