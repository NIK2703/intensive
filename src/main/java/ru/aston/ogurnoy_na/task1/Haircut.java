package ru.aston.ogurnoy_na.task1;

public class Haircut extends HairdressingService {
    enum HaircutType {
        MALE_BOX, MALE_CROP,
        FEMALE_PIXI, FEMALE_BOB
    }

    HaircutType haircutType;

    public Haircut(float price, User user,
            boolean longHair, HaircutType haircutType) {
        super(price, user, longHair);
        this.haircutType = haircutType;

    }

    public HaircutType getHaircutType() {
        return haircutType;
    }

    @Override
    public double calculateDiscount() {
        double discount = super.calculateDiscount();
        if(getUser().getAge() > 60) { //скидка пенсионерам
            discount += 0.1 * getPrice();
        }
        return discount;
    }

    public String toString() {
        StringBuilder sb = (new StringBuilder()).append(super.toString()).append(" ").append(haircutType);
        return sb.toString();
    }
}
