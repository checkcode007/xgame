package com.xirui.mysql;

import com.xirui.cache.annotation.XCacheAble;
import com.xirui.cache.annotation.XCacheEvict;
import com.xirui.mysql.entity.Student;
import com.xirui.mysql.mapper.AbstractMapperService;
import com.xirui.mysql.mapper.IMapper;
import com.xirui.mysql.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheTestService {


    @XCacheAble(group = "cacheTest",name = "cache1", params={"p1.pt2","p2"})
    public String add(PPTest p1,String p2,Integer p3,Long p4,Float p5,Object p6) {
        return "==result=";
    }
    @XCacheEvict(group = "cacheTest",name = "cache1", params={"p1.pt2","p2"})
    public String del(PPTest p1,String p2,Integer p3,Long p4,Float p5,Object p6) {
        return "==result=";
    }

    public static class PPTest{
         int pt1;
        String pt2;

        public int getPt1() {
            return pt1;
        }

        public void setPt1(int pt1) {
            this.pt1 = pt1;
        }

        public String getPt2() {
            return pt2;
        }

        public void setPt2(String pt2) {
            this.pt2 = pt2;
        }
    }
}
