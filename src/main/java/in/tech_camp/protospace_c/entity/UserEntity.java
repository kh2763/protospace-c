package in.tech_camp.protospace_c.entity;

import lombok.Data;

@Data
public class UserEntity {
  private Integer id;
  private String email;
  private String password;
  private String user_name;
  private String profile;
  private String team;
  private String job_title;
}
