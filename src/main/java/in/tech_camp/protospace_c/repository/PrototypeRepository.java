package in.tech_camp.protospace_c.repository;

public class PrototypeRepository {
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import in.tech_camp.protospace_c.entity.PrototypeEntity;
@Mapper
public interface PrototypeRepository {
  
  @Select("SELECT * FROM prototypes")
  List<PrototypeEntity> findAll();
  
}
}

