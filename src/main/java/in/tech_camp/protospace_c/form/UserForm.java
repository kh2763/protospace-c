package in.tech_camp.protospace_c.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;

import in.tech_camp.protospace_c.validation.ValidationPriority1;
import in.tech_camp.protospace_c.validation.ValidationPriority2;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {
  @NotBlank(message = "Email can't be blank", groups = ValidationPriority1.class)
  @Email(message = "Email should be valid", groups = ValidationPriority2.class)
  private String email;
  
  @NotBlank(message = "Password can't be blank", groups = ValidationPriority1.class)
  @Length(min = 6, max = 128, message = "Password should be between 6 and 128 characters", groups = ValidationPriority2.class)
  private String password;

  private String passwordConfirmation;

  //パスワードの正誤確認についてのメソッド
  public void validatePasswordConfirmation(BindingResult result) {
      if (!password.equals(passwordConfirmation)) {
          result.rejectValue("passwordConfirmation", "passwordconfirmation_error", "Password confirmation doesn't match Password");
      }
  }

  @NotBlank(message = "Username can't be blank", groups = ValidationPriority1.class)
  private String user_name;

  @NotBlank(message = "Profile can't be blank", groups = ValidationPriority1.class)
  private String profile;

  @NotBlank(message = "Team can't be blank", groups = ValidationPriority1.class)
  private String team;

  @NotBlank(message = "Job title can't be blank", groups = ValidationPriority1.class)
  private String job_title;
}
