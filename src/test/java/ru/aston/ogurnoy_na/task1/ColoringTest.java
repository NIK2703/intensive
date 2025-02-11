package ru.aston.ogurnoy_na.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.aston.ogurnoy_na.task1.enums.HairColor;
import ru.aston.ogurnoy_na.task1.exceptions.InvalidHairColorException;
import ru.aston.ogurnoy_na.task1.exceptions.InvalidServiceParameterException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class ColoringTest {
    private static final BigDecimal BASE_PRICE = new BigDecimal("1000").setScale(2, RoundingMode.HALF_UP);


    private User firstTimeUser;
    private User regularUser;
    private User frequentUser;

    @BeforeEach
    void setUp() {
        firstTimeUser = new User(25, "Анна", "Иванова", true, false);
        regularUser = new User(30, "Пётр", "Петров", false, true);
        frequentUser = new User(35, "Ольга", "Сидорова", true, true);
    }

    /**
     * Проверяет корректность расчета скидки для первого окрашивания.
     */
    @Test
    void calculateDiscount_shouldAddFirstTimeDiscount() {
        Coloring service1 = createColoring(BASE_PRICE, firstTimeUser, true, HairColor.BLOND);
        assertEquals(new BigDecimal("200.00"), service1.calculateDiscount());

        Coloring service2 = createColoring(BASE_PRICE, frequentUser, true, HairColor.BROWN);
        assertEquals(new BigDecimal("100.00"), service2.calculateDiscount());
    }

    /**
     * Проверяет комбинированный расчет скидок.
     */
    @Test
    void combinedDiscountsCalculation() {
        Coloring service = createColoring(BASE_PRICE, frequentUser, false, HairColor.BROWN);
        assertEquals(new BigDecimal("200.00"), service.calculateDiscount());
    }

    /**
     * Проверяет, что скидка не превышает цену услуги.
     */
    @Test
    void discountShouldNotExceedPrice() {
        User maxDiscountUser = new User(20, "Max", "Discount", true, false);

        Coloring service1 = createColoring(new BigDecimal("100"), maxDiscountUser, false, HairColor.WHITE);
        assertEquals(new BigDecimal("30.00"), service1.calculateDiscount());

        Coloring service2 = createColoring(new BigDecimal("50"), maxDiscountUser, false, HairColor.WHITE);
        assertEquals(new BigDecimal("15.00"), service2.calculateDiscount());
    }

    /**
     * Проверяет применение всех скидок к итоговой цене.
     */
    @Test
    void getDiscountedPrice_shouldApplyAllDiscounts() {
        Coloring service = createColoring(new BigDecimal("2000"), frequentUser, false, HairColor.WHITE);
        assertEquals(new BigDecimal("1600.00"), service.calculateFinalPrice());
    }

    /**
     * Проверяет строковое представление объекта.
     */
    @Test
    void toString_shouldIncludeHairColorInfo() {
        Coloring service = createColoring(new BigDecimal("1500"), regularUser, true, HairColor.BLACK);
        String expected = "Услуга для Пётр Петров, цена: 1500,00 руб., Скидки отсутствуют, тип волос: длинные, цвет покраски: black";
        assertEquals(expected, service.toString());
    }

    /**
     * Проверяет обработку недопустимых параметров.
     */
    @Test
    void shouldThrowExceptionForInvalidParameters() {
        assertThrows(InvalidServiceParameterException.class, () ->
                new Coloring(new BigDecimal("-100"), firstTimeUser, true, HairColor.BLOND)
        );
    }

    /**
     * Проверяет обработку null для цвета волос.
     */
    @Test
    void shouldThrowExceptionForNullHairColor() {
        assertThrows(InvalidHairColorException.class, () ->
                new Coloring(BASE_PRICE, firstTimeUser, true, null)
        );
    }

    /**
     * Проверяет обработку разных длин волос.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldHandleDifferentHairLengths(boolean longHair) {
        Coloring service = createColoring(BASE_PRICE, frequentUser, longHair, HairColor.BLOND);
        assertNotNull(service.calculateDiscount());
    }

    /**
     * Проверяет, что состояние пользователя не обновляется автоматически после расчета скидки.
     */
    @Test
    void stateAfterService_shouldNotAutoUpdateUser() {
        Coloring service = createColoring(BASE_PRICE, firstTimeUser, true, HairColor.BLOND);
        service.calculateDiscount();
        assertFalse(firstTimeUser.isColoredHair());
    }

    /**
     * Проверяет детализацию скидок.
     */
    @Test
    void getDiscountDetails_shouldIncludeAllDiscounts() {
        Coloring service = createColoring(BASE_PRICE, frequentUser, false, HairColor.BROWN);
        String details = service.getDiscountDetails();

        assertTrue(details.contains("Скидка за частые посещения"));
        assertTrue(details.contains("Скидка за короткие волосы"));
        assertTrue(details.contains("Итого скидка: 200,00 руб."));
    }

    /**
     * Вспомогательный метод для создания объекта Coloring.
     */
    private Coloring createColoring(BigDecimal price, User user, boolean longHair, HairColor hairColor) {
        return new Coloring(price, user, longHair, hairColor);
    }
}