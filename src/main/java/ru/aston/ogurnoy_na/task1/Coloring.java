package ru.aston.ogurnoy_na.task1;

/**
 * Класс представляет услугу окрашивания волос в парикмахерской.
 * Учитывает выбранный цвет волос и предоставляет скидку при первом окрашивании.
 */
public class Coloring extends HairdressingService {
    /**
     * Доступные цвета волос для окрашивания.
     */
    enum HairColor {
        BLACK,
        BROWN,
        BLOND,
        WHITE
    }

    private final HairColor hairColor;

    /**
     * Создает услугу окрашивания волос.
     *
     * @param price     стоимость услуги (должна быть положительной)
     * @param user      пользователь, для которого выполняется услуга
     * @param longHair  признак длинных волос
     * @param hairColor выбранный цвет волос (не может быть null)
     * @throws IllegalArgumentException если цена не положительна или цвет null
     */
    public Coloring(float price, User user, boolean longHair, HairColor hairColor) {
        super(price, user, longHair);

        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (hairColor == null) {
            throw new IllegalArgumentException("Hair color cannot be null");
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
    public double calculateDiscount() {
        double discount = super.calculateDiscount();

        if (!getUser().isColoredHair()) {
            discount += 0.1 * getPrice();
        }

        return Math.min(discount, getPrice()); // Гарантирует, что скидка не превысит цену
    }

    /**
     * Форматированное строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("%s, цвет покраски: %s",
                super.toString(),
                hairColor.toString().toLowerCase().replace('_', ' '));
    }
}