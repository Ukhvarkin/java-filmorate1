package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.LocalDate;

@Data
public class User {
    @NotNull(message = "Не корректный Id")
    private int id;
    @Email(message = "Email не корректен")
    @NotNull(message = "Email не может быть пустым")
    private String email;       //почта
    @NotBlank(message = "Логин не может быть пустым")
    private String login;          //логин
    @Size(max = 20)
    private String name;        //имя
    private LocalDate birthday;    //дата рождения

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}