package in.tech_camp.protospace_c.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace_c.entity.PrototypeEntity;

@Mapper
public interface PrototypeRepository {

  @Insert("INSERT INTO prototype (title, catchcopy, concept, image, user_id) VALUES (#{title}, #{catchcopy}, #{concept}, #{image}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(PrototypeEntity prototype); // 各入力をデータベースに保存
  
  @Select("SELECT * FROM prototype")
  List<PrototypeEntity> findAll();

  @Select("SELECT * FROM prototype WHERE user_id = #{userId}")
  List<PrototypeEntity> findByUserId(Integer userId);

  @Delete("DELETE FROM prototype WHERE id = #{id}")
  void deleteById(Integer id);

  @Select("SELECT * FROM prototype WHERE id = #{id}")
  @Results(value = {
    @Result(property = "id", column = "id"),
    @Result(property = "userId", column = "user_id"),
    // 💡 1対1: 該当するプロトタイプの user_id を使って、自動的に投稿者（UserEntity）を取得してセットする
    @Result(property = "user", column = "user_id",
            one = @One(select = "in.tech_camp.protospace_c.repository.UserRepository.findById"))
  })
  PrototypeEntity findById(Integer id);
}