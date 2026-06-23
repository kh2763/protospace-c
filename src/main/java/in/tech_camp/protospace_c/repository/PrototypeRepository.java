package in.tech_camp.protospace_c.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace_c.entity.PrototypeEntity;

@Mapper
public interface  PrototypeRepository {
  @Insert("INSERT INTO prototype (title, catchcopy, concept, image, user_id) VALUES (#{title}, #{catchcopy}, #{concept}, #{image}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(PrototypeEntity prototype); //各入力をデータベースに保存
  
  @Select("SELECT * FROM prototype")
  List<PrototypeEntity> findAll();

  @Select("SELECT * FROM prototype WHERE id = #{id}")
  PrototypeEntity findById(Integer id);
  
  @Select("SELECT * FROM prototype WHERE user_id = #{userId}")
  List<PrototypeEntity> findByUserId(Integer userId);
}

