package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class ColoringTest {
    private final User firstTimeUser = new User(25, "Anna", "Ivanova", true, false);
    private final User regularUser = new User(30, "Peter", "Petrov", false, true);
    private final User frequentUser = new User(35, "Olga", "Sidorova", true, true);

    @Test
    void calculateDiscount_shouldAddFirstTimeDiscount() {
        Coloring service1 = new Coloring(1000, firstTimeUser, true, Coloring.HairColor.BLOND);
        // Базовая скидка 10% (frequent user) + 10% (first time)
        assertEquals(200.0, service1.calculateDiscount(), 0.001);

        Coloring service2 = new Coloring(1000, frequentUser, true, Coloring.HairColor.BROWN);
        // Только базовая скидка 10% (frequent user)
        assertEquals(100.0, service2.calculateDiscount(), 0.001);
    }

    @Test
    void combinedDiscountsCalculation() {
        Coloring service = new Coloring(1000, frequentUser, false, Coloring.HairColor.BROWN);
        // 10% (frequent) + 10% (short hair) = 20%
        assertEquals(200.0, service.calculateDiscount(), 0.001);
    }

    @Test
    void discountShouldNotExceedPrice() {
        User maxDiscountUser = new User(20, "Max", "Discount", true, false);
        Coloring service = new Coloring(100, maxDiscountUser, false, Coloring.HairColor.WHITE);
        // 10% (frequent) + 10% (short hair) + 10% (first time) = 30% (30 руб)
        assertEquals(30.0, service.calculateDiscount(), 0.001);

        Coloring expensiveService = new Coloring(50, maxDiscountUser, false, Coloring.HairColor.WHITE);
        // 30% от 50 = 15 руб
        assertEquals(15.0, expensiveService.calculateDiscount(), 0.001);
    }

    @Test
    void getDiscountedPrice_shouldApplyAllDiscounts() {
        Coloring service = new Coloring(2000, frequentUser, false, Coloring.HairColor.WHITE);
        // 10% (frequent) + 10% (short hair) = 20% от 2000 → 1600
        assertEquals(1600.0, service.getDiscountedPrice(), 0.001);
    }

    @Test
    void toString_shouldIncludeHairColorInfo() {
        Coloring service = new Coloring(1500, regularUser, true, Coloring.HairColor.BLACK);
        String expected = "Peter Petrov, 30 лет, не частый пользователь, окрашенные волосы, 1500,00 руб., скидка: 0,00 руб., длинные волосы, цвет покраски: black";
        assertEquals(expected, service.toString());
    }

    @Test
    void shouldThrowExceptionForInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () ->
                new Coloring(-100, firstTimeUser, true, Coloring.HairColor.BLOND)
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Coloring(0, regularUser, false, Coloring.HairColor.BLACK)
        );
    }

    @Test
    void shouldThrowExceptionForNullHairColor() {
        assertThrows(IllegalArgumentException.class, () ->
                new Coloring(1000, firstTimeUser, true, null)
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldHandleDifferentHairLengths(boolean longHair) {
        Coloring service = new Coloring(1000, frequentUser, longHair, Coloring.HairColor.BLOND);
        assertNotNull(service.calculateDiscount());
    }

    @Test
    void stateAfterService_shouldNotAutoUpdateUser() {
        Coloring service = new Coloring(1000, firstTimeUser, true, Coloring.HairColor.BLOND);
        service.calculateDiscount();
        assertFalse(firstTimeUser.isColoredHair());
    }
}