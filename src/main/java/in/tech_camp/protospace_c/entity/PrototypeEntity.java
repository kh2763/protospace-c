package in.tech_camp.protospace_c.entity;

import lombok.Data;

@Data
public class PrototypeEntity {
  private Integer id;
  private String title;
  private String catchCopy;
  private String concept;
  private String imageUrl;
  private Integer userId;

}
