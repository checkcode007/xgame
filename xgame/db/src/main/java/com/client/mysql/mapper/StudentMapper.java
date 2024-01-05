package com.client.mysql.mapper;

import com.client.mysql.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends IMapper<Student,Long>{

}
