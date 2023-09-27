package com.xirui.mysql;

import com.xirui.mysql.entity.Student;
import com.xirui.mysql.mapper.AbstractMapperService;
import com.xirui.mysql.mapper.IMapper;
import com.xirui.mysql.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService extends AbstractMapperService<Student,Long> {
    @Autowired
    private StudentMapper maper;
    @Override
    public String cacheNamespace() {
        return "student";
    }

    @Override
    protected IMapper getMapper() {
        return maper;
    }

    @Override
    protected String getTableName(Long aLong) {
        return "student";
    }

    @Override
    protected String[] getAllTableName() {
        return new String[]{"student"};
    }


    public List<Student> getAll(){
       return super.getAll(null);
    }




}
