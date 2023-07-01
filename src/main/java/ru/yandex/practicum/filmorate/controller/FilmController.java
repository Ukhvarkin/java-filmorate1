package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
  private int id = 1;
  private Map<Integer, Film> films = new HashMap<>();
  private final LocalDate startFilmDate = LocalDate.of(1895, 12, 28);

  @GetMapping
  public Collection<Film> findAll() {
    log.info("GET запрос.");
    return new ArrayList<>(films.values());
  }

  @PostMapping
  public Film create(@Valid @RequestBody Film film) {
    log.info("POST запрос.");
      filmValidator(film);
      film.setId(id);
      films.put(id, film);
      id++;
      log.info("Фильм добавлен.");
    return film;
  }

  @PutMapping
  public Film update(@Valid @RequestBody Film film) {
    log.info("PUT запрос.");
    if (films.containsKey(film.getId())) {
      filmValidator(film);
      films.put(id, film);
      log.info("Фильм обновлен.");
    } else {
      throw new ValidationException("Фильм с таким id не найден.");
    }
    return film;
  }

  private void filmValidator(Film film) {
    if (film.getName()==null) {
      log.warn("Название фильма отсутствует.");
      throw new ValidationException("Введите название фильма.");
    } else if (film.getDescription().length() > 200) {
      log.warn("Больше 200 символов в названии.");
      throw new ValidationException("Название должно содержать не больше 200 символов.");
    } else if (film.getReleaseDate().isBefore(startFilmDate)) {
      log.warn("Дата релиза раньше 28.12.1895.");
      throw new ValidationException("Дата фильма должна быть после 28.12.1895.");
    } else if (film.getDuration() < 0) {
      log.warn("Продолжительность фильма меньше 1.");
      throw new ValidationException("Введите продолжительность равную больше 0.");
    }
    log.info("Валидация закончена.");
  }
}
