package in.tech_camp.protospace_c.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentForm {
  @NotBlank(message = "コメントを入力してください")
  private String tect;
}
