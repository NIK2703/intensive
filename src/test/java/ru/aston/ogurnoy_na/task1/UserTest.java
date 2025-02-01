package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private final User user = new User(
            25,
            "Иван",
            "Иванов",
            true,
            false
    );

    @Test
    void constructorInitialization() {
        assertEquals(25, user.getAge());
        assertEquals("Иван", user.getName());
        assertEquals("Иванов", user.getSurname());
        assertTrue(user.isFrequentUser());
        assertFalse(user.isColoredHair());
    }

    @Test
    void validAgeSetterGetter() {
        user.setAge(30);
        assertEquals(30, user.getAge());
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 200})
    void setInvalidAgeShouldThrowException(int invalidAge) {
        assertThrows(IllegalArgumentException.class,
                () -> user.setAge(invalidAge),
                "Должно выбрасываться исключение для возраста: " + invalidAge);
    }

    @Test
    void setNameWithValidation() {
        user.setName("  Петр  ");
        assertEquals("Петр", user.getName());
    }

    @Test
    void setInvalidNameShouldThrowException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> user.setName(null),
                        "Null имя должно вызывать исключение"),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> user.setName("   "),
                        "Пустое имя должно вызывать исключение")
        );
    }

    @Test
    void testToString() {
        String expected = "Иван Иванов, 25 лет, частый пользователь, не окрашенные волосы";
        assertEquals(expected, user.toString());

        user.setFrequentUser(false);
        user.setColoredHair(true);
        String updated = "Иван Иванов, 25 лет, не частый пользователь, окрашенные волосы";
        assertEquals(updated, user.toString());
    }

    @Test
    void equalsAndHashCode() {
        User sameUser = new User(25, "Иван", "Иванов", true, false);
        User differentUser = new User(30, "Петр", "Петров", false, true);

        assertAll(
                () -> assertEquals(user, sameUser),
                () -> assertNotEquals(user, differentUser),
                () -> assertEquals(user.hashCode(), sameUser.hashCode())
        );
    }

    @Test
    void invalidConstructorParameters() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new User(-5, "Иван", "Иванов", true, false)),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> new User(25, null, "Иванов", true, false)),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> new User(25, "  ", "Иванов", true, false)),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> new User(25, "Иван", "", true, false))
        );
    }

    @Test
    void trimNameAndSurname() {
        User userWithSpaces = new User(30, "  Анна  ", "  Петрова  ", false, true);
        assertEquals("Анна", userWithSpaces.getName());
        assertEquals("Петрова", userWithSpaces.getSurname());
    }
}