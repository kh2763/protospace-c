package in.tech_camp.protospace_c.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import in.tech_camp.protospace_c.entity.UserEntity;

@Mapper
public interface UserRepository {
  // フォームの内容を受け取ってデータベースに登録する
  @Insert("INSERT INTO users (email, password, user_name, team, job_title) VALUES (#{email}, #{password}, #{user_name}, #{team}, #{job_title})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(UserEntity userEntity);
}
