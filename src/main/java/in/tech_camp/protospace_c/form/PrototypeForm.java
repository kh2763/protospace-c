package in.tech_camp.protospace_c.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrototypeForm {

    @NotBlank()
    private String title;      

    @NotBlank()
    private String catchcopy;  

    @NotBlank()
    private String concept;

    //画像のチェックはコントローラーに記述
    private MultipartFile image;   

    // どのユーザーが投稿したかを識別するために追加
    private Integer userId;
}
/*
  マイグレーションファイルは以下を想定
CREATE TABLE prototype (
    id SERIAL NOT NULL,
    title VARCHAR(256),
    catchcopy VARCHAR(256),
    concept VARCHAR(512),
    image VARCHAR(256),
    PRIMARY KEY (id)
); */
