package in.tech_camp.protospace_c.entity;

import lombok.Data;

@Data
public class UserEntity {
  private Integer id;
  private String email;
  private String password;
  private String userName;
  private String profile;
  private String team;
  private String jobTitle;
}
