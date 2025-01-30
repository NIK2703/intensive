package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.Test;
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
        assertTrue(user.getFrequentUser());
        assertFalse(user.isColoredHair());
    }

    @Test
    void ageSetterGetter() {
        user.setAge(30);
        assertEquals(30, user.getAge());

        user.setAge(-5);
        assertEquals(-5, user.getAge());
    }

    @Test
    void nameSetterGetter() {
        user.setName("Петр");
        assertEquals("Петр", user.getName());
    }

    @Test
    void surnameSetterGetter() {
        user.setSurname("Петров");
        assertEquals("Петров", user.getSurname());
    }

    @Test
    void frequentUserSetterGetter() {
        user.setFrequentUser(false);
        assertFalse(user.getFrequentUser());
    }

    @Test
    void coloredHairSetterGetter() {
        user.setColoredHair(true);
        assertTrue(user.isColoredHair());
    }

    @Test
    void testToString() {
        String expected = "Иван Иванов, 25 лет, частый пользователь";
        assertEquals(expected, user.toString());

        user.setFrequentUser(false);
        String expectedNonFrequent = "Иван Иванов, 25 лет, не частый пользователь";
        assertEquals(expectedNonFrequent, user.toString());
    }

    @Test
    void edgeCases() {
        User edgeUser = new User(0, "", "", false, false);

        assertEquals(0, edgeUser.getAge());
        assertEquals("", edgeUser.getName());
        assertEquals("", edgeUser.getSurname());
        assertFalse(edgeUser.getFrequentUser());
        assertFalse(edgeUser.isColoredHair());

        assertEquals(" , 0 лет, не частый пользователь", edgeUser.toString());
    }
}