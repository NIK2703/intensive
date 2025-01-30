package ru.aston.ogurnoy_na.task1;

public class User {
    int age;
    String name;
    String surname;
    boolean frequentUser;
    boolean coloredHair;


    User(int age, String name, String surname, boolean frequentUser, boolean coloredHair) {
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.frequentUser = frequentUser;
        this.coloredHair = coloredHair;
    }

    public void setAge(int age) {
        this.age = age;
    }   

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFrequentUser(boolean frequentUser) {
        this.frequentUser = frequentUser;
    }

    public void setColoredHair(boolean coloredHair) {
        this.coloredHair = coloredHair;
    }

    public boolean getFrequentUser() {
        return frequentUser;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isColoredHair() {
        return coloredHair;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(surname).append(", ").append(age).append(" лет, ").append(frequentUser?"частый пользователь":"не частый пользователь");
        return sb.toString();
    }
}
