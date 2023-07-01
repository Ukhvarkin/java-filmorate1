package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
class UserControllerTest {
  UserController userController = new UserController();

  @BeforeEach
  @DisplayName("Добавление пользователя.")
  public void createUser() {
    User user = new User("yandex@yandex.ru", "Yandex", "Яндекс", LocalDate.of(1997, 9, 23));
    userController.create(user);

  }

  @Test
  @DisplayName("Проверка на соответствие.")
  public void checkNamesMatch() {
    for (User user : userController.findAll()) {
      assertEquals(user.getId(), 1, "id не совпадает.");
      assertEquals(user.getLogin(), "Yandex", "Описание не совпадает.");
      assertEquals(user.getName(), "Яндекс", "Имя не совпадает.");
      assertEquals(user.getBirthday(), LocalDate.of(1997, 9, 23), "Дата рождения не совпадает.");
    }
  }

  @Test
  @DisplayName("Проверка на валидацию.")
  public void userValidationTest() {
    User noEmail = new User("Почта", "Yandex", "Яндекс", LocalDate.of(1997, 9, 23));
    User noName = new User("yandex@yandex.ru", "Yandex", "", LocalDate.of(1997, 9, 23));
    User noLogin = new User("yandex@yandex.ru", " ", "Яндекс", LocalDate.of(1997, 9, 23));
    User futureData = new User("yandex@yandex.ru", "Yandex", "Яндекс", LocalDate.of(2070, 12, 12));

    assertThrows(ValidationException.class,() -> userController.create(noEmail));
    assertThrows(ValidationException.class,() -> userController.create(noLogin));
    assertThrows(ValidationException.class,() -> userController.create(futureData));

    assertEquals(1, userController.findAll().size(), "Неверное количество пользователей.");
    userController.create(noName);
    assertEquals(2, userController.findAll().size(), "Неверное количество пользователей.");

  }
}