package com.xirui.mysql;

import com.xirui.cache.annotation.XCacheAble;
import com.xirui.mysql.entity.Student;
import com.xirui.mysql.mapper.AbstractMapperService;
import com.xirui.mysql.mapper.IMapper;
import com.xirui.mysql.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheTestService {

    @XCacheAble
    public String test1(String p1,String p2) {
        return p1+"===="+p2;
    }

}
