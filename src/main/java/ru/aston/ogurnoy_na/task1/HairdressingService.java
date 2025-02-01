package ru.aston.ogurnoy_na.task1;

import java.util.Objects;

/**
 * Абстрактный класс, представляющий услугу парикмахерской.
 */
public abstract class HairdressingService {
    private static final double FREQUENT_USER_DISCOUNT = 0.1;
    private static final double SHORT_HAIR_DISCOUNT = 0.1;
    private static final double MIN_PRICE = 0.0;

    private final double price;
    private final User user;
    private final boolean longHair;

    /**
     * Конструктор для создания услуги парикмахерской.
     *
     * @param price стоимость услуги (должна быть неотрицательной)
     * @param user пользователь, для которого выполняется услуга (не может быть null)
     * @param longHair признак длинных волос
     * @throws IllegalArgumentException если нарушены условия валидации параметров
     */
    public HairdressingService(double price, User user, boolean longHair) {
        validatePrice(price);
        validateUser(user);

        this.price = price;
        this.user = user;
        this.longHair = longHair;
    }

    private void validatePrice(double price) {
        if (price < MIN_PRICE) {
            throw new IllegalArgumentException("Цена не может быть отрицательной: " + price);
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null");
        }
    }

    public final double getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }

    public boolean isLongHair() {
        return longHair;
    }

    /**
     * Рассчитывает итоговую цену с учетом скидки.
     *
     * @return итоговая цена после применения скидок (не менее 0)
     */
    public double getDiscountedPrice() {
        double discount = calculateDiscount();
        double finalPrice = price - discount;
        return Math.max(finalPrice, MIN_PRICE);
    }

    @Override
    public String toString() {
        return String.format("%s, %.2f руб., скидка: %.2f руб., %s",
                user,
                price,
                calculateDiscount(),
                longHair ? "длинные волосы" : "короткие волосы");
    }

    /**
     * Рассчитывает общий размер скидки.
     *
     * @return суммарная скидка на услугу
     */
    protected double calculateDiscount() {
        double discount = 0.0;

        if (user.isFrequentUser()) {
            discount += price * FREQUENT_USER_DISCOUNT;
        }

        if (!longHair) {
            discount += price * SHORT_HAIR_DISCOUNT;
        }

        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HairdressingService that = (HairdressingService) o;
        return Double.compare(price, that.price) == 0 &&
                longHair == that.longHair &&
                user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, user, longHair);
    }
}