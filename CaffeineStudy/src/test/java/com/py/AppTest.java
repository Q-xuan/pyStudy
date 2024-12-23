package com.py;

import com.github.benmanes.caffeine.cache.Cache;
import com.py.util.CaffeineUtil;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    private Cache<String, Object> manualCache;
    @Autowired
    private CaffeineUtil cacheUtil;

    private List<User> selectUser() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            userList.add(User.builder().id(i + 1).age(1).name("py").build());
        }
        return userList;
    }

    public User selectUserById(int id) {
        List<User> users = selectUser();
        Optional<User> first = users.stream().filter(user -> user.getId() == id).findFirst();
        return first.orElse(null);
    }

    @Cacheable(cacheNames = "user")
    public User getUser(int id) {
        return selectUserById(id);
    }

    @Test
    public void test02() {
        List<User> userList = selectUser();
        System.out.println("userList.getClass().getSimpleName() = " + userList.getClass().getSimpleName());
        System.out.println(userList.getClass().arrayType());
    }

    @Test
    public void test01() {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            cacheUtil.get("py_" + i, key -> User.builder().age(1 + finalI).name("py_" + finalI).build());
        }

        for (int i = 0; i < 50; i++) {
            User ifPresent = (User) manualCache.getIfPresent("py_" + i);
            System.out.println("ifPresent = " + ifPresent);
        }

        Map<String, User> all = cacheUtil.getAll();

        all.forEach((k, v) -> System.out.println(k + ":" + v));

        printStats();
    }

    private void printStats() {
        System.out.println("manualCache.stats().evictionCount() = " + manualCache.stats().evictionCount());
        System.out.println("manualCache.stats().hitCount() = " + manualCache.stats().hitCount());
        System.out.println("manualCache.stats().missCount() = " + manualCache.stats().missCount());
        System.out.println("manualCache.stats().averageLoadPenalty() = " + manualCache.stats().averageLoadPenalty());
        System.out.println("manualCache.stats().totalLoadTime() = " + manualCache.stats().totalLoadTime());
    }
}
