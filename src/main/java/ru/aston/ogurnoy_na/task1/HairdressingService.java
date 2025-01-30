package ru.aston.ogurnoy_na.task1;

public abstract class HairdressingService {

    private float price;
    private User user;
    private boolean longHair;

    public HairdressingService(float price, User user, boolean longHair) {
        this.price = price;
        this.user = user;
        this.longHair = longHair;
    }


    public double getPrice() {
        return price;
    };

    public User getUser() {
        return user;
    }

    public boolean isLongHair() {
        return longHair;
    }

    public double getDiscountedPrice() {
        return price - calculateDiscount();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(user).append(", ").append(price).append(" рублей, скидка: ").append(calculateDiscount()).append(" рублей, ").append(longHair?"длинные волосы":"короткие волосы");
        return sb.toString();
                
    }

    public double calculateDiscount() {
        double discount = 0;
        if (user.getFrequentUser()) { // скидка для частых пользователей
            discount += 0.1 * getPrice();
        }
        if(!longHair) {
            discount += 0.1 * getPrice();
        }
        return discount;
    };
}
