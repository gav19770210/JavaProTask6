package ru.gav19770210.javapro.task05.services;

import ru.gav19770210.javapro.task05.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByName(String name);

    User createUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);
}
