package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColoringTest {
    private final User firstTimeUser = new User(25, "Anna", "Ivanova", true, false);
    private final User regularUser = new User(30, "Peter", "Petrov", false, true);

    @Test
    void calculateDiscount_shouldAddFirstTimeDiscount() {
        // Тест для первой покраски
        Coloring service1 = new Coloring(1000, firstTimeUser, true, Coloring.HairColor.BLOND);
        assertEquals(200.0, service1.calculateDiscount(), 0.001); // 10% (frequent) + 10% (first time)

        // Тест для повторной покраски
        Coloring service2 = new Coloring(1000, regularUser, false, Coloring.HairColor.BLACK);
        assertEquals(100.0, service2.calculateDiscount(), 0.001); // 10% (short hair)
    }

    @Test
    void combinedDiscountsCalculation() {
        // Комбинация: frequent user + short hair + first time
        User user = new User(25, "Test", "User", true, false);
        Coloring service = new Coloring(1000, user, false, Coloring.HairColor.BROWN);

        // 10% (frequent) + 10% (short hair) + 10% (first time) = 30%
        assertEquals(300.0, service.calculateDiscount(), 0.001);
    }

    @Test
    void getDiscountedPrice_shouldApplyAllDiscounts() {
        Coloring service = new Coloring(2000, firstTimeUser, false, Coloring.HairColor.WHITE);
        // 10% (frequent) + 10% (short hair) + 10% (first time) = 30% от 2000
        assertEquals(1400.0, service.getDiscountedPrice(), 0.001);
    }

    @Test
    void toString_shouldIncludeHairColorInfo() {
        Coloring service = new Coloring(1500, regularUser, true, Coloring.HairColor.BLACK);
        String expected = "Peter Petrov, 30 лет, не частый пользователь, 1500.0 рублей, "
                + "скидка: 0.0 рублей, длинные волосы BLACK";
        assertEquals(expected, service.toString());
    }

    @Test
    void edgeCase_zeroPriceAndDiscounts() {
        User user = new User(0, "", "", false, false);
        Coloring service = new Coloring(0, user, true, Coloring.HairColor.WHITE);

        assertEquals(0.0, service.calculateDiscount(), 0.001);
        assertEquals(0.0, service.getDiscountedPrice(), 0.001);
    }

    @Test
    void stateAfterService_shouldNotAutoUpdateUser() {
        // Проверяем, что сервис не меняет состояние волос автоматически
        Coloring service = new Coloring(1000, firstTimeUser, true, Coloring.HairColor.BLOND);
        service.calculateDiscount();
        assertFalse(firstTimeUser.isColoredHair(), "Состояние волос не должно меняться автоматически");
    }
}