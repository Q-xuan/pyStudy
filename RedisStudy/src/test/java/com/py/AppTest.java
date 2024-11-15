package com.py;

import com.py.entity.User;
import com.py.pojo.User1;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testApp() {
        ValueOperations<String, Object> v = redisTemplate.opsForValue();
        User user = new User();
        user.setName("py");
        user.setId(1);
        user.setLevel(1000);
        v.set("user_001", user);
        User1 get = (User1) v.get("user_001");
        System.out.println(get);
    }
}
