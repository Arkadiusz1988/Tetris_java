package pl.java.tetris.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.java.tetris.dto.UserDto;
import pl.java.tetris.entities.User;
import pl.java.tetris.exception.UserUnauthenticated;
import pl.java.tetris.repositories.UserRepository;
import pl.java.tetris.service.AuthService;
import pl.java.tetris.service.UserService;
import pl.java.tetris.tetris_files.Tetris;
import pl.java.tetris.validations.user.LoginAttemptValidationGroup;
import pl.java.tetris.validations.user.RegistrationAttemptValidationGroup;

import java.awt.*;
import java.util.List;


@Controller
@Service
public class UserController {

  private final AuthService authService;
  private final UserRepository userRepository;
  private final UserService userService;

  @Autowired
  public UserController(AuthService authService, UserRepository userRepository, UserService userService) {
    this.authService = authService;
    this.userRepository = userRepository;
    this.userService = userService;
  }

//  @GetMapping("/")
//  public List<UserDto> usersList() {
//    return userService.findAllDtoUser();
//  }
//
//  @GetMapping
//  public User getBasicInfo() {
//    if (!authService.isUserLoggedIn()) {
//      throw new UserUnauthenticated("user not logged");
//    }
//    return authService.getUser();
//  }
//
//  @PostMapping("/login")
//  public User login(
//      @Validated(LoginAttemptValidationGroup.class) @RequestBody UserDto loginAttempt) {
//      return  authService.setUser(userRepository.findByEmail(loginAttempt.getEmail()));
//  }
//
//  @PostMapping("/register")
//  public User register(@Validated(RegistrationAttemptValidationGroup.class) @RequestBody UserDto registrationAttempt) {
//    User newUser = userRepository.save(registrationAttempt.toUser());
//    authService.setUser(newUser);
//    return newUser;
//  }
//
//  @PostMapping("/logout")
//  public void logout() {
//    authService.logout();
//  }
//
//  @DeleteMapping("/delete/{id}")
//  public User deleteUser(@PathVariable Long id){
//    User user = userRepository.getOne(id);
//    if (!(authService.isUserLoggedIn())) {
//      throw new UserUnauthenticated("user not logged or is not Admin");
//    }
//    userRepository.delete(user);
//    return user;
//  }

  @GetMapping
  public String home(Model model) {
    if (!authService.isUserLoggedIn()) {
      return "redirect:/login";
    }

    model.addAttribute("user", authService.getUser());
    return "index";
  }

  @GetMapping("/login")
  public String showLoginForm(Model model) {
    if (authService.isUserLoggedIn()) {
      return "redirect:/";
    }

    model.addAttribute("user", new UserDto());
    return "forms/login";
  }

  @PostMapping("/login")
  public String login(
          @Validated(LoginAttemptValidationGroup.class) @ModelAttribute("user") UserDto loginAttempt,
          BindingResult bindingResult) {

    if (authService.isUserLoggedIn()) {
      return "redirect:/";
    }

    if (bindingResult.hasErrors()) {
      return "forms/login";
    }

    authService.setUser(userRepository.findByEmail(loginAttempt.getEmail()));
    return "redirect:/";
  }

  @GetMapping("/register")
  public String showRegisterForm(Model model) {
    if (authService.isUserLoggedIn()) {
      return "redirect:/";
    }

    model.addAttribute("user", new UserDto());
    return "forms/register";
  }

  @PostMapping("/register")
  public String register(
          @Validated(RegistrationAttemptValidationGroup.class) @ModelAttribute("user")
                  UserDto registrationAttempt,
          BindingResult bindingResult) {

    if (authService.isUserLoggedIn()) {
      return "redirect:/";
    }

    if (bindingResult.hasErrors()) {
      return "forms/register";
    }

    User newUser = userRepository.save(registrationAttempt.toUser());
    authService.setUser(newUser);
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout() {

    authService.logout();
    return "redirect:/login";
  }

  @GetMapping("/start1")
  @ResponseBody
  public void start1(){




//    Tetris tet = new Tetris();
//    tet.star

//    ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Tetris.class)
//            .headless(false).run();
//
//    EventQueue.invokeLater(() -> {
//      Tetris game = ctx.getBean(Tetris.class);
//      game.setVisible(true);
//    });
    // EventQueue.invokeLater(() -> {
     Tetris game = new Tetris();
    game.setVisible(true);
      //});

  }

}
