package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HaircutTest {
    private final User pensioner = new User(65, "Ivan", "Sidorov", false, false);
    private final User adultUser = new User(30, "Olga", "Petrova", true, true);

    @Test
    void calculateDiscount_shouldAddPensionerDiscount() {
        // Базовая скидка: 10% (frequentUser) + 10% (short hair) = 20%
        Haircut service1 = new Haircut(1000, adultUser, false, Haircut.HaircutType.FEMALE_BOB);
        assertEquals(200.0, service1.calculateDiscount(), 0.001);

        // Пенсионерская скидка: 10% (short hair) + 10% (пенсионер) = 30%
        Haircut service2 = new Haircut(1000, pensioner, false, Haircut.HaircutType.MALE_BOX);
        assertEquals(200.0, service2.calculateDiscount(), 0.001);
    }

    @Test
    void pensionerDiscountEdgeCases() {
        // Возраст 60 лет - скидки нет
        User borderUser = new User(60, "Test", "User", false, false);
        Haircut service = new Haircut(1000, borderUser, true, Haircut.HaircutType.FEMALE_PIXI);
        assertEquals(0.0, service.calculateDiscount(), 0.001);

        // Возраст 61 год - скидка есть
        borderUser.setAge(61);
        assertEquals(100.0, service.calculateDiscount(), 0.001);
    }

    @Test
    void getDiscountedPrice_shouldApplyAllDiscounts() {
        Haircut service = new Haircut(1000, pensioner, false, Haircut.HaircutType.MALE_CROP);
        assertEquals(800.0, service.getDiscountedPrice(), 0.001);
    }

    @Test
    void toString_shouldIncludeHaircutType() {
        Haircut service = new Haircut(1500, adultUser, true, Haircut.HaircutType.FEMALE_PIXI);
        String expected = "Olga Petrova, 30 лет, частый пользователь, 1500.0 рублей, "
                + "скидка: 150.0 рублей, длинные волосы FEMALE_PIXI";
        assertEquals(expected, service.toString());
    }

    @Test
    void interactionOfAllDiscounts() {
        User premiumPensioner = new User(65, "Elena", "Ivanova", true, false);
        Haircut service = new Haircut(1000, premiumPensioner, false, Haircut.HaircutType.FEMALE_BOB);

        // 10% (frequent) + 10% (short hair) + 10% (pensioner) = 30%
        assertEquals(300.0, service.calculateDiscount(), 0.001);
        assertEquals(700.0, service.getDiscountedPrice(), 0.001);
    }
}