package in.tech_camp.protospace_c.entity;

import java.util.List;

import lombok.Data;

@Data
public class PrototypeEntity {
    private Integer id;
    private String title;      
    private String catchcopy;  
    private String concept; 
    //private String image;  
    private byte[] image;
    //private Integer userId;
    private Integer userId;

    // ユーザー情報を格納するフィールドを追加
    private UserEntity user;

    private List<CommentEntity> comments;
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
