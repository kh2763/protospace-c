package in.tech_camp.protospace_c.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace_c.validation.ValidationOrder;
import in.tech_camp.protospace_c.entity.UserEntity;
import in.tech_camp.protospace_c.form.UserForm;
import in.tech_camp.protospace_c.repository.UserRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {
  
  private final UserRepository userRepository;

  // ユーザー登録ページの表示
  @GetMapping("/users/sign_up")
  public String showSignUp(Model model){
    model.addAttribute("userForm", new UserForm());
    return "users/signUp";
  }

  // ユーザーの新規登録
  @PostMapping("/user")
  public String createUser(@ModelAttribute("userForm") @Validated(ValidationOrder.class) UserForm userForm, BindingResult result, Model model) {
    userForm.validatePasswordConfirmation(result);
  
    if (userRepository.existsByEmail(userForm.getEmail())) {
      result.rejectValue("email", "email_duplication", "Email already exists");
    }

    if (result.hasErrors()) {
      List<String> errorMessages = result.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.toList());

      model.addAttribute("errorMessages", errorMessages);
      model.addAttribute("userForm", userForm);
      return "users/signUp";
    }

    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(userForm.getEmail());
    userEntity.setPassword(userForm.getPassword());
    userEntity.setUser_name(userForm.getUser_name());
    userEntity.setProfile(userForm.getProfile());
    userEntity.setTeam(userForm.getTeam());
    userEntity.setJob_title(userForm.getJob_title());

    try {
      userRepository.insert(userEntity);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      return "redirect:/";
    }

    return "redirect:/";
  }
}
