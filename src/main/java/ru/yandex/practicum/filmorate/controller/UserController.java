package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
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
    log.debug("Текущее количество пользователей: " + users.size());
    return users.values();
  }

  @PostMapping
  public User create(@Valid @RequestBody User user) {
    log.info("POST запрос.");
    userValidator(user);
    user.setId(id);
    users.put(id, user);
    id++;
    log.debug("Добавлен пользователь: " + user.getLogin() +
            ". Количество пользователей: " + users.size());
    return user;
  }

  @PutMapping
  public User update(@Valid @RequestBody User user) {
    log.info("PUT запрос.");
    if (users.containsKey(user.getId())) {
      userValidator(user);
      users.put(id, user);
      log.debug("Обновлен пользователь: " + user.getLogin() +
              ". Количество пользователей: " + users.size());
    } else {
      throw new ValidationException("Пользователя с таким id = " + user.getId() + ", не существует.");
    }
    return user;
  }

  private void userValidator(User user) {
    log.info("Запущена проверка.");
    if (!user.getEmail().contains("@")) {
      throw new ValidationException("Некорректный e-mail.");
    }
    else if (user.getLogin().contains(" ")){
      throw new ValidationException("Логин не должен содержать пробелы.");
    }
    else if (user.getName() == null) {
      log.info("Name обновлен на такой же, как логин.");
      user.setName(user.getLogin());
    } else if (user.getBirthday().isAfter(LocalDate.now())) {
      log.info("Неверная дата.");
      throw new ValidationException("Дата рождения не может быть в будущем.");
    }
    log.info("Валидация закончена.");
  }

}
