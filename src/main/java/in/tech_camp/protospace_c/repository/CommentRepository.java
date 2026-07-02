package in.tech_camp.protospace_c.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace_c.entity.CommentEntity;

@Mapper
public interface CommentRepository {

    // コメントを保存する
    @Insert("INSERT INTO comments (text, prototype_id, user_id) VALUES (#{text}, #{prototypeId}, #{userId})")
    void insert(CommentEntity comment);

    // 特定のプロトタイプに紐づくコメント一覧を取得（ユーザー情報も紐付ける）
    @Select("SELECT * FROM comments WHERE prototype_id = #{prototypeId}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "text", column = "text"),
        @Result(property = "prototypeId", column = "prototype_id"),
        @Result(property = "userId", column = "user_id"),
        // コメントしたユーザーの情報を取得して格納する
        @Result(property = "user", column = "user_id",
                one = @One(select = "in.tech_camp.protospace_c.repository.UserRepository.findById"))
    })
    List<CommentEntity> findByPrototypeId(Integer prototypeId);
}