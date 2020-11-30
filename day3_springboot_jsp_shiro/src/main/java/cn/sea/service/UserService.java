package cn.sea.service;

import cn.sea.entity.User;

public interface UserService {

    // 注册用户
    void register(User user);

    // 根据用户名查找用户
    User findByUsername(String username);
}
