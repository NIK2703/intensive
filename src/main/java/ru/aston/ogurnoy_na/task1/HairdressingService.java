package ru.aston.ogurnoy_na.task1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import ru.aston.ogurnoy_na.task1.exceptions.InvalidServiceParameterException;

/**
 * Абстрактный класс, представляющий услугу парикмахерской.
 */
public abstract class HairdressingService {
    protected static final BigDecimal FREQUENT_USER_DISCOUNT = BigDecimal.valueOf(0.1);
    protected static final BigDecimal SHORT_HAIR_DISCOUNT = BigDecimal.valueOf(0.1);
    private static final BigDecimal MIN_PRICE = BigDecimal.ZERO;
    private static final BigDecimal MAX_DISCOUNT_RATE = BigDecimal.ONE;
    private final BigDecimal price;
    private final User user;
    private final boolean longHair;

    /**
     * Конструктор для создания услуги парикмахерской.
     *
     * @param price    стоимость услуги (должна быть положительной)
     * @param user     пользователь, для которого выполняется услуга (не может быть null)
     * @param longHair признак длинных волос
     * @throws InvalidServiceParameterException если параметры невалидны
     */
    public HairdressingService(BigDecimal price, User user, boolean longHair) {
        validateParameters(price, user);
        this.price = price.setScale(2, RoundingMode.HALF_UP); // Устанавливаем два знака после запятой
        this.user = user;
        this.longHair = longHair;
    }

    private void validateParameters(BigDecimal price, User user) {
        if (price == null) {
            throw new InvalidServiceParameterException("Цена не может быть null");
        }
        if (price.compareTo(MIN_PRICE) < 0) {
            throw new InvalidServiceParameterException("Цена не может быть отрицательной: " + price);
        }
        if (user == null) {
            throw new InvalidServiceParameterException("Пользователь не может быть null");
        }
        validateDiscountRates();
    }

    private void validateDiscountRates() {
        validateDiscountRate(FREQUENT_USER_DISCOUNT, "Скидка для постоянных клиентов");
        validateDiscountRate(SHORT_HAIR_DISCOUNT, "Скидка за короткие волосы");
    }

    private void validateDiscountRate(BigDecimal rate, String discountName) {
        if (rate.compareTo(BigDecimal.ZERO) < 0 || rate.compareTo(MAX_DISCOUNT_RATE) > 0) {
            throw new InvalidServiceParameterException(
                    String.format("%s должна быть в диапазоне от 0 до 1: %s", discountName, rate)
            );
        }
    }

    public final BigDecimal getPrice() {
        return price.setScale(2, RoundingMode.HALF_UP); // Всегда возвращаем цену с двумя знаками после запятой
    }

    public User getUser() {
        return user;
    }

    public boolean isLongHair() {
        return longHair;
    }

    /**
     * Рассчитывает итоговую цену с учетом всех скидок.
     *
     * @return итоговая цена (не может быть отрицательной)
     */
    public BigDecimal calculateFinalPrice() {
        BigDecimal discount = calculateDiscount();
        BigDecimal finalPrice = price.subtract(discount);
        return finalPrice.max(MIN_PRICE).setScale(2, RoundingMode.HALF_UP); // Два знака после запятой
    }

    /**
     * Рассчитывает общую сумму скидки.
     *
     * @return суммарная скидка на услугу
     */
    protected BigDecimal calculateDiscount() {
        BigDecimal discount = BigDecimal.ZERO;
        if (user.isFrequentUser()) {
            discount = discount.add(price.multiply(FREQUENT_USER_DISCOUNT));
        }
        if (!longHair) {
            discount = discount.add(price.multiply(SHORT_HAIR_DISCOUNT));
        }
        return discount.setScale(2, RoundingMode.HALF_UP); // Два знака после запятой
    }

    /**
     * Возвращает детализацию скидок.
     */
    public String getDiscountDetails() {
        StringBuilder details = new StringBuilder();
        BigDecimal totalDiscount = calculateDiscount();
        if (user.isFrequentUser()) {
            details.append(String.format("Скидка за частые посещения: %.1f%%",
                    FREQUENT_USER_DISCOUNT.multiply(BigDecimal.valueOf(100))));
        }
        if (!longHair) {
            if (!details.isEmpty()) details.append("; ");
            details.append(String.format("Скидка за короткие волосы: %.1f%%",
                    SHORT_HAIR_DISCOUNT.multiply(BigDecimal.valueOf(100))));
        }
        if (details.isEmpty()) {
            details.append("Скидки отсутствуют");
        } else {
            details.append(String.format(". Итого скидка: %.2f руб.", totalDiscount));
        }
        return details.toString();
    }

    @Override
    public String toString() {
        return String.format("Услуга для %s %s, цена: %.2f руб., %s, тип волос: %s",
                user.getName(),
                user.getSurname(),
                price,
                getDiscountDetails(),
                longHair ? "длинные" : "короткие"
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HairdressingService that)) return false;
        return longHair == that.longHair &&
                price.compareTo(that.price) == 0 &&
                user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price.stripTrailingZeros(), user, longHair);
    }
}