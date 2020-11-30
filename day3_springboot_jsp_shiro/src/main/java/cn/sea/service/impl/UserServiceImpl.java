package cn.sea.service.impl;

import cn.sea.dao.UserDAO;
import cn.sea.entity.User;
import cn.sea.service.UserService;
import cn.sea.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    // 注册用户
    @Override
    public void register(User user) {

        user.setId(null);
        user.setSalt(null);

        // 2.明文密码进行 md5+salt+hash散列
        // 2.1 生成随机盐
        String salt = SaltUtils.getSalt(8);
        // 2.2 设置随机盐
        user.setSalt(salt);
        // 2.3明文密码进行 md5+salt+hash散列  参数1：密码  参数2：随机盐  参数3：散列次数
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt, 1024);
        // 2.4 设置加密后的密码
        user.setPassword(md5Hash.toHex());

        try {
            // 3. 调用dao保存用户
            userDAO.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 根据用户名查询用户
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User findByUsername(String username) {
        try {
            User user = userDAO.findByUsername(username);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
