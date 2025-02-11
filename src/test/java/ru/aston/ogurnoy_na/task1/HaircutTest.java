package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.aston.ogurnoy_na.task1.enums.HaircutType;
import ru.aston.ogurnoy_na.task1.exceptions.InvalidHaircutException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class HaircutTest {
    private static final BigDecimal BASE_PRICE = new BigDecimal("1000").setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal MIN_PRICE = BigDecimal.ZERO;
    private static final int PENSION_AGE = 60;
    private static final double FREQUENT_USER_DISCOUNT_RATE = 0.1;
    private static final double PENSIONER_DISCOUNT_RATE = 0.1;

    private User pensioner;
    private User adultUser;

    @BeforeEach
    void setUp() {
        pensioner = new User(PENSION_AGE + 5, "Иван", "Сидоров", false, false);
        adultUser = new User(30, "Ольга", "Петрова", true, true);
    }


    /**
     * Проверяет обработку недопустимых параметров в конструкторе.
     */
    @Test
    void constructor_shouldThrowForNullHaircutType() {
        assertThrows(InvalidHaircutException.class,
                () -> new Haircut(BASE_PRICE, adultUser, false, null));
    }

    /**
     * Проверяет корректность расчета скидок, включая пенсионную скидку.
     */
    @Test
    void calculateDiscount_shouldAddPensionerDiscount() {
        Haircut service1 = createHaircut(BASE_PRICE, adultUser, false, HaircutType.FEMALE_BOB);
        assertEquals(new BigDecimal("200.00"), service1.calculateDiscount());

        Haircut service2 = createHaircut(BASE_PRICE, pensioner, false, HaircutType.MALE_BOX);
        assertEquals(new BigDecimal("200.00"), service2.calculateDiscount());
    }

    /**
     * Проверяет граничные условия для пенсионной скидки.
     */
    @ParameterizedTest
    @ValueSource(ints = {PENSION_AGE - 1, PENSION_AGE, PENSION_AGE + 1})
    void pensionerDiscountAgeBoundary(int age) {
        User user = new User(age, "Test", "User", false, false);
        Haircut service = createHaircut(BASE_PRICE, user, false, HaircutType.MALE_CROP);

        BigDecimal expectedDiscount = age > PENSION_AGE
                ? BASE_PRICE.multiply(BigDecimal.valueOf(FREQUENT_USER_DISCOUNT_RATE + PENSIONER_DISCOUNT_RATE))
                : BASE_PRICE.multiply(BigDecimal.valueOf(FREQUENT_USER_DISCOUNT_RATE));

        assertEquals(expectedDiscount.setScale(2, RoundingMode.HALF_UP), service.calculateDiscount());
    }

    /**
     * Проверяет, что итоговая цена не может быть отрицательной.
     */
    @Test
    void getDiscountedPrice_shouldNotBeNegative() {
        Haircut service = createHaircut(new BigDecimal("100"), pensioner, false, HaircutType.FEMALE_BOB);
        assertTrue(MIN_PRICE.compareTo(service.calculateFinalPrice()) < 0);
    }

    /**
     * Проверяет форматирование строкового представления объекта.
     */
    @Test
    void toString_shouldFormatHaircutType() {
        Haircut service = createHaircut(new BigDecimal("1500"), adultUser, true, HaircutType.FEMALE_PIXI);
        String expected = "Услуга для Ольга Петрова, цена: 1500,00 руб., Скидка за частые посещения: 10,0%. Итого скидка: 150,00 руб., тип волос: длинные, тип стрижки: female pixi";
        assertEquals(expected, service.toString());
    }

    /**
     * Проверяет корректность работы методов equals и hashCode.
     */
    @Test
    void equalsAndHashCode() {
        Haircut haircut1 = createHaircut(BASE_PRICE, adultUser, false, HaircutType.FEMALE_BOB);
        Haircut haircut2 = createHaircut(BASE_PRICE, adultUser, false, HaircutType.FEMALE_BOB);
        Haircut differentType = createHaircut(BASE_PRICE, adultUser, false, HaircutType.MALE_BOX);

        assertAll(
                () -> assertEquals(haircut1, haircut2),
                () -> assertNotEquals(haircut1, differentType),
                () -> assertEquals(haircut1.hashCode(), haircut2.hashCode())
        );
    }

    /**
     * Проверяет расчет максимальной скидки.
     */
    @Test
    void maximumDiscountCalculation() {
        User maxDiscountUser = new User(PENSION_AGE + 5, "Max", "Discount", true, false);
        Haircut service = createHaircut(new BigDecimal("100"), maxDiscountUser, false, HaircutType.MALE_CROP);

        // 10% (frequent) + 10% (short) + 10% (пенсионер) = 30% от 100 = 30
        assertEquals(new BigDecimal("30.00"), service.calculateDiscount());
        assertEquals(new BigDecimal("70.00"), service.calculateFinalPrice());
    }

    /**
     * Проверяет детализацию скидок.
     */
    @Test
    void getDiscountDetails_shouldIncludeAllDiscounts() {
        Haircut service = createHaircut(BASE_PRICE, pensioner, false, HaircutType.MALE_BOX);
        String details = service.getDiscountDetails();

        assertTrue(details.contains("Скидка за короткие волосы"));
        assertTrue(details.contains("Скидка для пенсионеров"));
        assertTrue(details.contains("Итого скидка: 200,00 руб."));
    }

    /**
     * Вспомогательный метод для создания объекта Haircut.
     */
    private Haircut createHaircut(BigDecimal price, User user, boolean longHair, HaircutType haircutType) {
        return new Haircut(price, user, longHair, haircutType);
    }
}