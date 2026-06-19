package in.tech_camp.protospace_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import in.tech_camp.protospace_c.form.UserForm;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {
  

  // ユーザー登録ページの表示
  @GetMapping("/users/sign_up")
  public String showSignUp(Model model){
    model.addAttribute("userForm", new UserForm());
    return "users/signUp";
  }
  
}
