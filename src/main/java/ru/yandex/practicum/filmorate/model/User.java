package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
  private LocalDate birthday;

  public User(@NotNull String email, @NotNull String login, String name, LocalDate birthday) {
    this.email = email;
    this.login = login;
    this.name = name;
    this.birthday = birthday;
  }
}
