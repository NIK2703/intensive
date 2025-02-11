package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.aston.ogurnoy_na.task1.exceptions.InvalidServiceParameterException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HairdressingServiceTest {
    private static final BigDecimal MIN_PRICE = BigDecimal.ZERO;
    private static final BigDecimal FREQUENT_USER_DISCOUNT = BigDecimal.valueOf(0.1);
    private static final BigDecimal SHORT_HAIR_DISCOUNT = BigDecimal.valueOf(0.1);

    private static class TestService extends HairdressingService {
        public TestService(BigDecimal price, User user, boolean longHair) {
            super(price, user, longHair);
        }
    }

    private final User frequentUser = new User(30, "Анна", "Иванова", true, false);
    private final User regularUser = new User(25, "Пётр", "Петров", false, true);

    @Test
    void constructor_ShouldThrowExceptionForInvalidParameters() {
        assertAll(
                () -> assertThrows(InvalidServiceParameterException.class,
                        () -> new TestService(BigDecimal.valueOf(-100), frequentUser, true)),
                () -> assertThrows(InvalidServiceParameterException.class,
                        () -> new TestService(BigDecimal.valueOf(1000), null, false)),
                () -> assertThrows(InvalidServiceParameterException.class,
                        () -> new TestService(null, frequentUser, false))
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 500, 10000, Double.MAX_VALUE})
    void validPrices_ShouldBeAccepted(double price) {
        assertDoesNotThrow(() ->
                new TestService(BigDecimal.valueOf(price), frequentUser, true)
        );
    }

    @ParameterizedTest
    @MethodSource("discountTestCases")
    void calculateDiscount_ShouldApplyCorrectRules(
            User user,
            boolean longHair,
            BigDecimal expectedDiscount
    ) {
        HairdressingService service = new TestService(
                BigDecimal.valueOf(1000),
                user,
                longHair
        );

        assertEquals(
                0,
                service.calculateDiscount().compareTo(expectedDiscount),
                "Некорректная сумма скидки"
        );
    }

    private static Stream<Arguments> discountTestCases() {
        return Stream.of(
                // user, longHair, expectedDiscount
                Arguments.of(frequentUser(), false, BigDecimal.valueOf(200)), // 10% + 10%
                Arguments.of(frequentUser(), true, BigDecimal.valueOf(100)),  // 10%
                Arguments.of(regularUser(), false, BigDecimal.valueOf(100)),   // 10%
                Arguments.of(regularUser(), true, BigDecimal.ZERO)            // нет скидок
        );
    }

    @Test
    void calculateFinalPrice_ShouldNotBeNegative() {
        HairdressingService service = new TestService(
                BigDecimal.valueOf(100),
                frequentUser,
                false
        );

        assertEquals(
                0,
                service.calculateFinalPrice().compareTo(BigDecimal.valueOf(80)), // 100 - 20%
                "Итоговая цена меньше нуля"
        );

        service = new TestService(
                BigDecimal.valueOf(50),
                frequentUser,
                false
        );

        assertEquals(
                0,
                service.calculateFinalPrice().compareTo(BigDecimal.valueOf(40)) // 50 - 20%
        );
    }

    @Test
    void toString_ShouldContainDiscountDetails() {
        HairdressingService service = new TestService(
                BigDecimal.valueOf(1500.50),
                regularUser,
                true
        );

        String result = service.toString();

        assertAll(
                () -> assertTrue(result.contains("Пётр Петров")),
                () -> assertTrue(result.contains("1500,50 руб.")),
                () -> assertTrue(result.contains("тип волос: длинные")),
                () -> assertTrue(result.contains("Скидки отсутствуют"))
        );
    }

    @Test
    void equalsAndHashCode_ShouldConsiderAllFields() {
        HairdressingService service1 = new TestService(
                BigDecimal.valueOf(1000),
                frequentUser,
                false
        );

        HairdressingService service2 = new TestService(
                new BigDecimal("1000.00"),
                frequentUser,
                false
        );

        HairdressingService differentPrice = new TestService(
                BigDecimal.valueOf(2000),
                frequentUser,
                false
        );

        assertAll(
                () -> assertEquals(service1, service2),
                () -> assertEquals(service1.hashCode(), service2.hashCode()),
                () -> assertNotEquals(service1, differentPrice)
        );
    }

    @Test
    void getDiscountDetails_ShouldFormatCorrectly() {
        HairdressingService service = new TestService(
                BigDecimal.valueOf(1000),
                frequentUser,
                false
        );

        String details = service.getDiscountDetails();
        assertAll(
                () -> assertTrue(details.contains("Скидка за частые посещения: 10,0%")),
                () -> assertTrue(details.contains("Скидка за короткие волосы: 10,0%")),
                () -> assertTrue(details.contains("Итого скидка: 200,00 руб."))
        );
    }

    @Test
    void edgeCases_ShouldHandleCorrectly() {
        assertAll(
                () -> assertEquals(
                        MIN_PRICE.setScale(2, RoundingMode.HALF_UP),
                        new TestService(MIN_PRICE, frequentUser, false).calculateFinalPrice()
                ),
                () -> assertEquals(
                        BigDecimal.valueOf(100).multiply(FREQUENT_USER_DISCOUNT.add(SHORT_HAIR_DISCOUNT)).setScale(2, RoundingMode.HALF_UP),
                        new TestService(BigDecimal.valueOf(100), frequentUser, false).calculateDiscount()
                )
        );
    }

    private static User frequentUser() {
        return new User(30, "Anna", "Ivanova", true, false);
    }

    private static User regularUser() {
        return new User(25, "Peter", "Petrov", false, true);
    }
}