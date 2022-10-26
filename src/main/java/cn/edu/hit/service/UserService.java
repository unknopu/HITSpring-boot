package cn.edu.hit.service;

import cn.edu.hit.dao.UserDao;
import cn.edu.hit.po.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {

    void test();

    int changeName(String name);

    void addUser(User user);

    User login(User user);
}
