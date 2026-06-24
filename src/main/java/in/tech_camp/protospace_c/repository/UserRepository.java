package in.tech_camp.protospace_c.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace_c.entity.UserEntity;

@Mapper
public interface UserRepository {
  // フォームの内容を受け取ってデータベースに登録する
  @Insert("INSERT INTO users (email, password, user_name, profile, team, job_title) VALUES (#{email}, #{password}, #{userName}, #{profile}, #{team}, #{jobTitle})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(UserEntity userEntity);

  // Emailの一意性のチェック
  @Select("SELECT EXISTS(SELECT 1 FROM users WHERE email = #{email})")
  boolean existsByEmail(String email);

  // ログイン時に入力してもらうemailの情報からユーザーを見つける
  // スネークケースによるエラー解決のためAS句で名前変更
  @Select("SELECT id, email, password, user_name AS userName, profile, team, job_title AS jobTitle FROM users WHERE email = #{email}")
  UserEntity findByEmail(String email);

  @Select("SELECT * FROM users WHERE id = #{id}")
  UserEntity findById(Integer id);
}
