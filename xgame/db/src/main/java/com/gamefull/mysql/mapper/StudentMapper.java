package com.gamefull.mysql.mapper;

import com.gamefull.mysql.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends IMapper<Student,Long>{

}
