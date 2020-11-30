package cn.sea.dao;

import cn.sea.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {

    // 添加用户
    void save(User user);

    // 根据用户名查找用户
    User findByUsername(String username);
}
