package ru.aston.ogurnoy_na.task1;

public class Coloring extends HairdressingService {
    enum HairColor {
        BLACK,
        BROWN,
        BLOND,
        WHITE
    }

    HairColor hairColor;

    public Coloring(float price, User user, boolean longHair, HairColor hairColor) {
        super(price, user, longHair);
        this.hairColor = hairColor;

    }

    public HairColor getHairColor() {
        return hairColor;
    }

    @Override
    public double calculateDiscount() {
        double discount = super.calculateDiscount();
        if(!getUser().isColoredHair()){//скидка на первую покраску
            discount += 0.1 * getPrice();
        }
        return discount;
    }

    public String toString(){
        StringBuilder sb = (new StringBuilder()).append(super.toString()).append(" ").append(hairColor);
        return sb.toString();
    }
}
