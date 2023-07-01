package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
  private int id;
  @NotNull(message = "Введите электронную почту.")
  @Email(message = "Электронная почта введена некорректна.")
  private String email;
  @NotNull(message = "Введите логин.")
  @NotBlank(message = "Введите логин без пробелов.")
  private String login;
  private String name;
  @Past
  private LocalDate birthday;

  public User(String email, String login, String name, LocalDate birthday) {
    this.email = email;
    this.login = login;
    this.name = name;
    this.birthday = birthday;
  }
}
