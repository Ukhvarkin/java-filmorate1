package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
class FilmControllerTest {
  FilmController filmController = new FilmController();

  @BeforeEach
  @DisplayName("Добавление фильма.")
  public void createFilm() {
    Film film = new Film("Название", "Описание", LocalDate.of(2010, 10, 10), 10);
    filmController.create(film);
  }

  @Test
  @DisplayName("Проверка на соответствие.")
  public void shouldBeCheckNamesMatch(){
    for(Film film : filmController.findAll()){
      assertEquals(film.getId(),1,"id не совпадает.");
      assertEquals(film.getName(),"Название","Названия не совпадают.");
      assertEquals(film.getDescription(),"Описание","Описание не совпадает.");
      assertEquals(film.getReleaseDate(),LocalDate.of(2010, 10, 10),"Дата релиза не совпадает.");
      assertEquals(film.getDuration(),10,"Время продолжительности не совпадает.");
    }
  }
  @Test
  @DisplayName("Проверка на валидацию.")
  public void filmValidationTest() {
    Film noName = new Film("", "Описание", LocalDate.of(2000, 10, 10),50);
    Film maxSymbol = new Film("Почта", "Жизнь Томаса Андерсона разделена на две части: " +
            "днём он — самый обычный офисный работник, получающий нагоняи от начальства, " +
            "а ночью превращается в хакера по имени Нео, и нет места в сети, куда он бы не смог проникнуть. " +
            "Но однажды всё меняется. Томас узнаёт ужасающую правду о реальности. ", LocalDate.of(2000, 10, 10),50);
    Film minData = new Film("Почта", "Описание", LocalDate.of(1111, 10, 10),50);
    Film negativeDuration = new Film("Почта", "Описание", LocalDate.of(2000, 10, 10),-1);

    assertThrows(ValidationException.class,() -> filmController.create(noName));
    assertThrows(ValidationException.class,() -> filmController.create(maxSymbol));
    assertThrows(ValidationException.class,() -> filmController.create(minData));
    assertThrows(ValidationException.class,() -> filmController.create(negativeDuration));

    assertEquals(1, filmController.findAll().size(), "Неверное количество пользователей.");
  }
}