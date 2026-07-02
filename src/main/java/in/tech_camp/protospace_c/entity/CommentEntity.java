package in.tech_camp.protospace_c.entity;

import lombok.Data;

@Data
public class CommentEntity {
    private Integer id;
    private String text;        // コメント本文
    private Integer prototypeId; // どの投稿へのコメントか
    private Integer userId;      // 誰のコメントか

    // 💡 コメントしたユーザーの情報を格納するフィールド（HTML側で `comment.user.userName` を呼ぶため）
    private UserEntity user;
}