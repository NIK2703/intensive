package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class HairdressingServiceTest {
    private static class TestService extends HairdressingService {
        public TestService(double price, User user, boolean longHair) {
            super(price, user, longHair);
        }
    }

    private final User frequentUser = new User(30, "Anna", "Ivanova", true, false);
    private final User regularUser = new User(25, "Peter", "Petrov", false, true);

    @Test
    void constructor_shouldThrowExceptionForInvalidParameters() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new TestService(-100, frequentUser, true)),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> new TestService(1000, null, false))
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 500, 10000, Double.MAX_VALUE})
    void validPrices_shouldBeAccepted(double price) {
        assertDoesNotThrow(() -> new TestService(price, frequentUser, true));
    }

    @Test
    void calculateDiscount_shouldApplyCorrectRules() {
        // Тест-кейсы с разными комбинациями параметров
        assertAll(
                () -> assertEquals(200.0, createService(1000, frequentUser, false).calculateDiscount(),
                        "Частый + короткие"),

                () -> assertEquals(100.0, createService(1000, frequentUser, true).calculateDiscount(),
                        "Частый + длинные"),

                () -> assertEquals(100.0, createService(1000, regularUser, false).calculateDiscount(),
                        "Обычный + короткие"),

                () -> assertEquals(0.0, createService(1000, regularUser, true).calculateDiscount(),
                        "Обычный + длинные")
        );
    }

    @Test
    void getDiscountedPrice_shouldNotBeNegative() {
        HairdressingService service = createService(100, frequentUser, false);
        assertEquals(80.0, service.getDiscountedPrice(), 0.001); // 100 - 20% = 80

        HairdressingService expensiveDiscount = createService(50, frequentUser, false);
        assertEquals(40.0, expensiveDiscount.getDiscountedPrice(), 0.001); // 50 - 20% = 40, но 50 - 10 = 40?
    }

    @Test
    void toString_shouldFormatCorrectly() {
        HairdressingService service = createService(1500.5, regularUser, true);
        String expected = "Peter Petrov, 25 лет, не частый пользователь, окрашенные волосы, 1500,50 руб., скидка: 0,00 руб., длинные волосы";
        assertEquals(expected, service.toString());
    }

    @Test
    void equalsAndHashCode_shouldWorkCorrectly() {
        HairdressingService service1 = createService(1000, frequentUser, false);
        HairdressingService service2 = createService(1000, frequentUser, false);
        HairdressingService differentPrice = createService(2000, frequentUser, false);

        assertAll(
                () -> assertEquals(service1, service2),
                () -> assertNotEquals(service1, differentPrice),
                () -> assertEquals(service1.hashCode(), service2.hashCode())
        );
    }

    @Test
    void edgeCases_shouldHandleCorrectly() {
        assertAll(
                () -> assertEquals(0.0, createService(0, frequentUser, false).getDiscountedPrice()),
                () -> assertEquals(2.0, createService(10, frequentUser, false).calculateDiscount())
        );
    }

    private TestService createService(double price, User user, boolean longHair) {
        return new TestService(price, user, longHair);
    }
}