package kg.alatoo.demooauth.service;

import kg.alatoo.demooauth.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User newUser);

    User createAdmin(User newAdmin);

    List<User> findAllUsers();

    boolean CreateFirstAdmin();
}
