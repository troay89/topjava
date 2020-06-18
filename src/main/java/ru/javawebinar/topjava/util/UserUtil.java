package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.List;

public class UserUtil {

    public static final List<User> USERS = Arrays.asList(
            new User(1,"Саня", "piccc89@gmail.com", "12311", Role.USER),
            new User(2,"Ваня", "pitt11@gmail.com", "12311", Role.USER),
            new User(3,"Таня", "lic9@gmail.com", "12311", Role.USER),
            new User(4,"Катя", "poezd811@gmail.com", "12311", Role.USER),
            new User(5,"Валя", "lord891@gmail.com", "12311", Role.USER),
            new User(6,"Аня", "soc11@gmail.com", "12311", Role.USER),
            new User(7,"Оля", "pic11cc@gmail.com", "2000", Role.ADMIN)
    );
}
