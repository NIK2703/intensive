package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class HaircutTest {
    private final User pensioner = new User(65, "Ivan", "Sidorov", false, false);
    private final User adultUser = new User(30, "Olga", "Petrova", true, true);

    @Test
    void constructor_shouldThrowForNullHaircutType() {
        assertThrows(IllegalArgumentException.class,
                () -> new Haircut(1000, adultUser, false, null));
    }

    @Test
    void calculateDiscount_shouldAddPensionerDiscount() {
        Haircut service1 = new Haircut(1000, adultUser, false, Haircut.HaircutType.FEMALE_BOB);
        assertEquals(200.0, service1.calculateDiscount(), 0.001);

        Haircut service2 = new Haircut(1000, pensioner, false, Haircut.HaircutType.MALE_BOX);
        assertEquals(200.0, service2.calculateDiscount(), 0.001);
    }

    @ParameterizedTest
    @ValueSource(ints = {60, 61, 100})
    void pensionerDiscountAgeBoundary(int age) {
        User user = new User(age, "Test", "User", false, false);
        Haircut service = new Haircut(1000, user, false, Haircut.HaircutType.MALE_CROP);

        double expectedDiscount = age > 60 ? 200.0 : 100.0;
        assertEquals(expectedDiscount, service.calculateDiscount(), 0.001);
    }

    @Test
    void getDiscountedPrice_shouldNotBeNegative() {
        Haircut service = new Haircut(100, pensioner, false, Haircut.HaircutType.FEMALE_BOB);
        assertEquals(0.0, service.getDiscountedPrice(), 0.001); // 100 - 30% = 70, но 100 - (10+10+10) = 70
    }

    @Test
    void toString_shouldFormatHaircutType() {
        Haircut service = new Haircut(1500, adultUser, true, Haircut.HaircutType.FEMALE_PIXI);
        String expected = "Olga Petrova, 30 лет, частый пользователь, 1500.00 руб., "
                + "скидка: 150.00 руб., длинные волосы, тип стрижки: female pixi";
        assertEquals(expected, service.toString());
    }

    @Test
    void equalsAndHashCode() {
        Haircut haircut1 = new Haircut(1000, adultUser, false, Haircut.HaircutType.FEMALE_BOB);
        Haircut haircut2 = new Haircut(1000, adultUser, false, Haircut.HaircutType.FEMALE_BOB);
        Haircut differentType = new Haircut(1000, adultUser, false, Haircut.HaircutType.MALE_BOX);

        assertAll(
                () -> assertEquals(haircut1, haircut2),
                () -> assertNotEquals(haircut1, differentType),
                () -> assertEquals(haircut1.hashCode(), haircut2.hashCode())
        );
    }

    @Test
    void maximumDiscountCalculation() {
        User maxDiscountUser = new User(65, "Max", "Discount", true, false);
        Haircut service = new Haircut(100, maxDiscountUser, false, Haircut.HaircutType.MALE_CROP);

        // 10% (frequent) + 10% (short) + 10% (пенсионер) = 30% от 100 = 30
        assertEquals(30.0, service.calculateDiscount(), 0.001);
        assertEquals(70.0, service.getDiscountedPrice(), 0.001);
    }
}