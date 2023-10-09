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

    @XCacheAble(name = "test1", key="k:#p1.pt2")
    public String test1(PPTest p1,String p2,Integer p3,Long p4,Float p5,Object p6) {
        return p1+"===="+p2;
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
