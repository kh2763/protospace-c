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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.protospace_c.entity.UserEntity;
import in.tech_camp.protospace_c.form.UserForm;
import in.tech_camp.protospace_c.repository.PrototypeRepository;
import in.tech_camp.protospace_c.repository.UserRepository;
import in.tech_camp.protospace_c.service.UserService;
import in.tech_camp.protospace_c.validation.ValidationOrder;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {
  
  private final UserRepository userRepository;
  private final UserService userService;
  private final PrototypeRepository prototypeRepository;

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
    userEntity.setUserName(userForm.getUserName());
    userEntity.setProfile(userForm.getProfile());
    userEntity.setTeam(userForm.getTeam());
    userEntity.setJobTitle(userForm.getJobTitle());

    try {
      userService.createUserWithEncryptedPassword(userEntity);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      return "redirect:/";
    }

    return "redirect:/";
  }

  //ログインページの表示
  @GetMapping("/users/login")
  public String showLogin() {
      return "users/login";
  }

  //ログインエラーの表示　エラーを渡す
  @GetMapping("/login")
  public String showLoginWithError(@RequestParam(value = "error", required = false) String error, Model model) {
    if (error != null) {
      model.addAttribute("loginError", "Invalid email or password.");
    }
    return "users/login";
  }


  
  //ユーザー詳細ページ（マイページ）の表示
  @GetMapping("/users/{id}")
  public String showUserDetail(@PathVariable("id") Integer id, Model model) {
      // 1. URLのIDから対象のユーザー情報を取得して画面に渡す
      UserEntity user = userRepository.findById(id); // ※Repositoryのメソッド名は環境に合わせて調整してください
      model.addAttribute("user", user);

      // 2. そのユーザーが投稿したプロトタイプ一覧をデータベースから取得して画面に渡す
      // ※現在は一旦、そのユーザーのID（userId）を元に全件取得する想定のダミーを置いておきます
      model.addAttribute("prototypes", prototypeRepository.findByUserId(id));

      // 3. マイページのHTML（渡したUserFormの記述があるHTML）を呼び出す
      // フォルダ構造に合わせて変更してください（例: "users/show" など）
      return "users/mypage"; 
  }

}
