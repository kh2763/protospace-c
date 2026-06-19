package in.tech_camp.protospace_c.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserForm {
  @NotBlank(message = "Email can't be blank")
  @Email(message = "Email should be valid")
  private String email;
  
  @NotBlank(message = "Password can't be blank")
  @Length(min = 6, max = 128, message = "Password should be between 6 and 128 characters")
  private String password;

  private String passwordConfirmation;

  //パスワードの正誤確認についてのメソッド
  public void validatePasswordConfirmation(BindingResult result) {
      if (!password.equals(passwordConfirmation)) {
          result.rejectValue("passwordConfirmation", "passwordconfirmation_error", "Password confirmation doesn't match Password");
      }
  }

  @NotBlank(message = "Username can't be blank")
  private String user_name;

  @NotBlank(message = "Team can't be blank")
  private String team;

  @NotBlank(message = "Job tytle can't be blank")
  private String job_tytle;
}
