package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HairdressingServiceTest {
    // Мок-класс для тестирования абстрактного класса
    private static class TestHairdressingService extends HairdressingService {
        public TestHairdressingService(float price, User user, boolean longHair) {
            super(price, user, longHair);
        }
    }

    private final User frequentUser = new User(30, "Anna", "Ivanova", true, false);
    private final User regularUser = new User(25, "Peter", "Petrov", false, true);

    @Test
    void calculateDiscount_shouldReturnCorrectDiscount() {
        // Случай 1: Частый пользователь + короткие волосы
        HairdressingService service1 = new TestHairdressingService(1000, frequentUser, false);
        assertEquals(200.0, service1.calculateDiscount(), 0.001);

        // Случай 2: Частый пользователь + длинные волосы
        HairdressingService service2 = new TestHairdressingService(1000, frequentUser, true);
        assertEquals(100.0, service2.calculateDiscount(), 0.001);

        // Случай 3: Обычный пользователь + короткие волосы
        HairdressingService service3 = new TestHairdressingService(1000, regularUser, false);
        assertEquals(100.0, service3.calculateDiscount(), 0.001);

        // Случай 4: Обычный пользователь + длинные волосы
        HairdressingService service4 = new TestHairdressingService(1000, regularUser, true);
        assertEquals(0.0, service4.calculateDiscount(), 0.001);
    }

    @Test
    void getDiscountedPrice_shouldApplyDiscountCorrectly() {
        HairdressingService service = new TestHairdressingService(500, frequentUser, false);
        assertEquals(400.0, service.getDiscountedPrice(), 0.001);
    }

    @Test
    void toString_shouldReturnFormattedString() {
        HairdressingService service = new TestHairdressingService(1500, regularUser, true);
        String expected = "Peter Petrov, 25 лет, не частый пользователь, 1500.0 рублей, "
                + "скидка: 0.0 рублей, длинные волосы";
        assertEquals(expected, service.toString());
    }

    @Test
    void edgeCases_shouldHandleZeroPrice() {
        HairdressingService service = new TestHairdressingService(0, frequentUser, false);
        assertEquals(0.0, service.calculateDiscount(), 0.001);
        assertEquals(0.0, service.getDiscountedPrice(), 0.001);
    }
}