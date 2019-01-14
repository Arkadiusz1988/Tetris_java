package pl.java.tetris.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import pl.java.tetris.entities.User;
import pl.java.tetris.validations.user.*;

@ValidLoginAttempt(groups = LoginAttemptValidationGroup.class)
public class UserDto {

  private Long id;

  @UniqueUsername(groups = RegistrationAttemptValidationGroup.class)
  @NotEmpty(groups = RegistrationAttemptValidationGroup.class)
  private String username;

  @NotEmpty(groups = RegistrationAttemptValidationGroup.class)
  private String password;

  //domyslny regex w tej adnotacji to .*,co oznacza że validacja przepuszczałaby pusty string.
  @Email(regexp = ".+", groups = RegistrationAttemptValidationGroup.class)
  @UniqueEmail(groups = RegistrationAttemptValidationGroup.class)
  private String email;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User toUser() {
    User user = new User();
    user.setId(id);
    user.setEmail(email);
    user.setPassword(password);
    user.setUsername(username);
    return user;
  }


}
