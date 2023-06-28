package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> users = Arrays.asList(
        new User(null, "c", "email@mail.ru", "password", Role.ADMIN),
        new User(null, "b", "email@mail.ru", "password", Role.ADMIN),
        new User(null, "a", "email@mail.ru", "password", Role.ADMIN),
        new User(null, "d", "email@mail.ru", "password", Role.ADMIN),
        new User(null, "d", "email@mail.ru", "password", Role.ADMIN),
        new User(2, "dd", "email@mail.ru", "password", Role.ADMIN)
    );


}
