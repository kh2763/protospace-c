package in.tech_camp.protospace_c.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace_c.entity.PrototypeEntity;

@Mapper
public interface PrototypeRepository {

  @Insert("INSERT INTO prototype (title, catchcopy, concept, image, user_id) VALUES (#{title}, #{catchcopy}, #{concept}, #{image}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(PrototypeEntity prototype); // 各入力をデータベースに保存
  
  // 1. ここで「ユーザー情報も一緒に持ってくる設定」を定義し、名前（id）をつける
  @Select("SELECT * FROM prototype")
  @Results(id = "prototypeWithUser", value = {
    @Result(property = "id", column = "id"),
    @Result(property = "title", column = "title"),
    @Result(property = "catchcopy", column = "catchcopy"),
    @Result(property = "concept", column = "concept"),
    @Result(property = "image", column = "image"),
    @Result(property = "userId", column = "user_id"),
    @Result(property = "user", column = "user_id",
            one = @One(select = "in.tech_camp.protospace_c.repository.UserRepository.findById"))
  })
  List<PrototypeEntity> findAll(); // トップページ用（全件）
  
  
  @Select("SELECT * FROM prototype WHERE user_id = #{userId}")
  List<PrototypeEntity> findByUserId(Integer userId);

  @Delete("DELETE FROM prototype WHERE id = #{id}")
  void deleteById(Integer id);


  // 2. 既存のメソッドでは、上で作った設定の名前（ResultMap）を指定するだけでOK
  //    これで長い設定を2回書く必要がなくなり、重複がなくなる
  @Select("SELECT * FROM prototype WHERE id = #{id}")
  @ResultMap("prototypeWithUser") 
  PrototypeEntity findById(Integer id); // 詳細ページ用（1件）


  //JOINで安全に取得する新しいメソッドを追加する
  @Select("SELECT p.id, p.title, p.catchcopy, p.concept, p.image, p.user_id AS userId, " +
    "u.id AS \"user.id\", u.user_name AS \"user.userName\" " +
    "FROM prototype p " +
    "LEFT JOIN users u ON p.user_id = u.id " +
    "WHERE p.user_id = #{userId}")
  List<PrototypeEntity> findByUserIdWithUser(Integer userId);


  // ★データ更新用メソッドを追加
  @org.apache.ibatis.annotations.Update(
    "UPDATE prototype SET title = #{title}, catchcopy = #{catchcopy}, concept = #{concept}, image = #{image} WHERE id = #{id}"
  )
  void update(PrototypeEntity prototype);
}