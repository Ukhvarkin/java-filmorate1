package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
  private int id;
  @NotNull(message = "Название не может быть пустым.")
  @NotBlank(message = "Название не может быть пустым.")
  private String name;
  @Size(max = 200, message = "Название может содержать не более 200 символов.")
  private String description;
  private LocalDate releaseDate;
  @Min(1)
  private long duration;

  public Film(@NotNull String name, String description, LocalDate releaseDate, long duration) {
    this.name = name;
    this.description = description;
    this.releaseDate = releaseDate;
    this.duration = duration;
  }
}
