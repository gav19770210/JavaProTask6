package ru.gav19770210.javapro.task05.services;

import org.springframework.stereotype.Service;
import ru.gav19770210.javapro.task05.repositories.UserDAO;
import ru.gav19770210.javapro.task05.entities.User;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDao;

    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найден пользователь с id = " + id));
    }

    @Override
    public User getUserByName(String name) {
        return userDao.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Не найден пользователь с name = " + name));
    }

    @Override
    public User createUser(User user) {
        if (userDao.findByName(user.getName()).isPresent()) {
            throw new IllegalArgumentException("Уже существует пользователь с name = " + user.getName());
        }
        return userDao.create(user);
    }

    @Override
    public User updateUser(User user) {
        var userById = userDao.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("Не найден пользователь с id = " + user.getId()));

        var userByName = userDao.findByName(user.getName()).orElse(null);
        if (userByName != null && !userByName.getId().equals(user.getId())) {
            throw new IllegalArgumentException("Уже существует пользователь с name = " + user.getName());
        }
        if (!userById.equals(user)) {
            return userDao.update(user);
        }
        return userById;
    }

    @Override
    public void deleteUserById(Long id) {
        if (userDao.findById(id).isEmpty()) {
            throw new NoSuchElementException("Не найден пользователь с id = " + id);
        }
        userDao.deleteById(id);
    }
}
