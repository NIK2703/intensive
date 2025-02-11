package ru.aston.ogurnoy_na.task1;

import ru.aston.ogurnoy_na.task1.exceptions.InvalidUserParameterException;

import java.util.Objects;

/**
 * Класс, представляющий пользователя системы.
 */
public class User {
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 150;

    private int age;
    private String name;
    private String surname;
    private boolean frequentUser;
    private boolean coloredHair;

    /**
     * Конструктор для создания объекта пользователя.
     *
     * @param age возраст пользователя (должен быть в диапазоне [0, 150])
     * @param name имя пользователя (не может быть null или пустым)
     * @param surname фамилия пользователя (не может быть null или пустой)
     * @param frequentUser признак частого пользователя
     * @param coloredHair признак окрашенных волос
     * @throws InvalidUserParameterException если не соблюдены ограничения для параметров
     */
    public User(int age, String name, String surname, boolean frequentUser, boolean coloredHair) {
        setAge(age);
        setName(name);
        setSurname(surname);
        this.frequentUser = frequentUser;
        this.coloredHair = coloredHair;
    }

    // Геттеры
    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isFrequentUser() {
        return frequentUser;
    }

    public boolean isColoredHair() {
        return coloredHair;
    }

    // Сеттеры с валидацией
    public void setAge(int age) {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new InvalidUserParameterException("Возраст должен быть между " + MIN_AGE + " и " + MAX_AGE);
        }
        this.age = age;
    }

    public void setName(String name) {
        validateName(name, "Имя");
        this.name = name.trim();
    }

    public void setSurname(String surname) {
        validateName(surname, "Фамилия");
        this.surname = surname.trim();
    }

    public void setFrequentUser(boolean frequentUser) {
        this.frequentUser = frequentUser;
    }

    public void setColoredHair(boolean coloredHair) {
        this.coloredHair = coloredHair;
    }

    private void validateName(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidUserParameterException(fieldName + " не может быть null или пустым");
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s, %d лет, %s, %sокрашенные волосы",
                name, surname, age,
                frequentUser ? "частый пользователь" : "не частый пользователь",
                coloredHair ? "" : "не ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age
                && frequentUser == user.frequentUser
                && coloredHair == user.coloredHair
                && Objects.equals(name, user.name)
                && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, surname, frequentUser, coloredHair);
    }
}