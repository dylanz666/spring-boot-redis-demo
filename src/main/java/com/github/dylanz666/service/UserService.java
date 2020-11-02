package com.github.dylanz666.service;

import com.github.dylanz666.annotation.Ttl;
import com.github.dylanz666.constant.DurationUnitEnum;
import com.github.dylanz666.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author : dylanz
 * @since : 10/31/2020
 */
@Service
public class UserService {

    @Cacheable(value = "content", key = "'id_'+#userName")
    @Ttl(duration = 10, unit = DurationUnitEnum.SECOND)
    public User getUserByName(String userName) {
        System.out.println("io operation : getUserByName()");

        //模拟从数据库中获取数据
        User userInDb = new User();
        userInDb.setUserName("dylanz");
        userInDb.setRoleName("adminUser");

        return userInDb;
    }

    @CachePut(value = "content", key = "'id_'+#user.userName")
    @Ttl(duration = 1, unit = DurationUnitEnum.HOUR)
    public User updateUser(User user) {
        System.out.println("io operation : updateUser()");

        //模拟从数据库中获取数据
        User userInDb = new User();
        userInDb.setUserName(user.getUserName());
        userInDb.setRoleName(user.getRoleName());

        return userInDb;
    }

    @CacheEvict(value = "content", key = "'id_'+#user.userName")
    public void deleteUser(User user) {
        System.out.println("io operation : deleteUser()");
    }

    @Caching(cacheable = {
            @Cacheable(cacheNames = "test", key = "'id_'+#userName")
    }, put = {
            @CachePut(value = "testGroup", key = "'id_'+#userName"),
            @CachePut(value = "user", key = "#userName")
    })
    public User getUserByNameAndDoMultiCaching(String userName) {
        System.out.println("io operation : getUserByNameAndDoMultiCaching()");

        //模拟从数据库中获取数据
        User userInDb = new User();
        userInDb.setUserName("dylanz");
        userInDb.setRoleName("adminUser");

        return userInDb;
    }
}
