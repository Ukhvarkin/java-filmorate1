package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
  private int id = 1;
  private final Map<Integer, User> users = new HashMap<>();

  @GetMapping
  public Collection<User> findAll() {
    log.info("GET запрос.");
    return new ArrayList<>(users.values());
  }

  @PostMapping
  public User create(@Valid @RequestBody User user) {
    log.info("Post запрос");
    userValidator(user);
    user.setId(id);
    users.put(id, user);
    id++;
    log.info("Пользователь добавлен.");
    return user;
  }

  @PutMapping
  public User update(@Valid @RequestBody User user) {
    log.info("PUT запрос");
    if (users.containsKey(user.getId())) {
      userValidator(user);
      users.put(user.getId(), user);
      log.info("Пользователь обновлен.");
    } else {
      throw new ValidationException("Пользователь с таким id не найден.");
    }
    return user;
  }

  private void userValidator(User user) {
    log.info("Запущена проверка.");
    if (user.getLogin().contains(" ")) {
      log.warn("Пользователь не добавлен. Неверный логин.");
      throw new ValidationException("Логин не должен содержать пробелов.");
    } else if (user.getName() == null) {
      user.setName(user.getLogin());
    } else if (user.getBirthday().isAfter(LocalDate.now())) {
      log.warn("Неверная дата.");
      throw new ValidationException("Дата рождения не может быть в будущем.");
    }
    log.info("Валидация закончена.");
  }

}
