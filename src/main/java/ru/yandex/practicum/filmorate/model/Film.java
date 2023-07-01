package ru.yandex.practicum.filmorate.model;

import lombok.Data;


import javax.validation.ValidationException;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.time.LocalDate;

@Data
public class Film {

    @NotNull(message = "Не корректный Id")
    private int id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;        //название
    @Size(min = 1, max = 200, message = "Не корректная длинна строки min 1, max 200")
    private String description; //описание
    private LocalDate releaseDate; //дата релиза
    @Min(value = 1, message = "Продолжительность не меньше 1")
    private long duration;      //продолжительность фильма
    public Film(String name, String description, LocalDate releaseDate, long duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}