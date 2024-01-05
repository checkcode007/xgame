package com.proto.mysql.mapper;

import com.proto.mysql.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends IMapper<Student,Long>{

}
