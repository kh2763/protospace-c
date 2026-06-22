package in.tech_camp.protospace_c.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import in.tech_camp.protospace_c.entity.PrototypeEntity;

@Mapper
public interface  PrototypeRepository {
  @Insert("INSERT INTO prototype (title, catchcopy, concept, image)VALUES (#{title}, #{catchcopy}, #{concept}, #{image})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(PrototypeEntity prototype); //各入力をデータベースに保存
}
