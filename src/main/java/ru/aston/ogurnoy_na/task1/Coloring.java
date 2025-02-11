package ru.aston.ogurnoy_na.task1;

import ru.aston.ogurnoy_na.task1.enums.HairColor;
import ru.aston.ogurnoy_na.task1.exceptions.InvalidHairColorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Класс представляет услугу окрашивания волос в парикмахерской.
 * Учитывает выбранный цвет волос и предоставляет скидку при первом окрашивании.
 */
public class Coloring extends HairdressingService {
    private static final BigDecimal FIRST_COLORING_DISCOUNT_RATE = BigDecimal.valueOf(0.1);
    private final HairColor hairColor;

    /**
     * Создает услугу окрашивания волос.
     *
     * @param price     стоимость услуги (должна быть положительной)
     * @param user      пользователь, для которого выполняется услуга
     * @param longHair  признак длинных волос
     * @param hairColor выбранный цвет волос (не может быть null)
     * @throws InvalidHairColorException если цена не положительна или цвет null
     */
    public Coloring(BigDecimal price, User user, boolean longHair, HairColor hairColor) {
        super(price.setScale(2, RoundingMode.HALF_UP), user, longHair); // Устанавливаем два знака после запятой
        if (hairColor == null) {
            throw new InvalidHairColorException("Цвет волос не может быть null");
        }
        this.hairColor = hairColor;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    /**
     * Рассчитывает итоговую скидку на услугу.
     * Добавляет 10% скидку при первом окрашивании пользователя.
     *
     * @return суммарная скидка в денежном выражении
     */
    @Override
    protected BigDecimal calculateDiscount() {
        BigDecimal discount = super.calculateDiscount(); // Базовая скидка из родительского класса

        // Дополнительная скидка за первое окрашивание
        if (!getUser().isColoredHair()) {
            discount = discount.add(getPrice().multiply(FIRST_COLORING_DISCOUNT_RATE));
        }

        return discount.min(getPrice()).setScale(2, RoundingMode.HALF_UP); // Два знака после запятой
    }

    /**
     * Форматированное строковое представление объекта.
     * Включает информацию о типе услуги и цвете покраски.
     *
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return String.format("%s, цвет покраски: %s",
                super.toString(),
                hairColor.toString().toLowerCase().replace('_', ' '));
    }

    /**
     * Возвращает детализацию скидок.
     * Добавляет информацию о скидке за первое окрашивание.
     */
    @Override
    public String getDiscountDetails() {
        StringBuilder details = new StringBuilder(super.getDiscountDetails());

        // Добавляем информацию о скидке за первое окрашивание
        if (!getUser().isColoredHair()) {
            if (!details.isEmpty()) details.append("; ");
            details.append(String.format("Скидка за первое окрашивание: %.1f%%",
                    FIRST_COLORING_DISCOUNT_RATE.multiply(BigDecimal.valueOf(100))));
        }

        return details.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coloring that)) return false;
        return super.equals(o) && hairColor == that.hairColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hairColor);
    }
}