package ru.aston.ogurnoy_na.task1;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class HairdressingSalonWork {
    ArrayList<HairdressingService> services = new ArrayList<>();

    public HairdressingSalonWork(){
    User user1 = new User(62, "Иван", "Иванов", false,false);
    User user2 = new User(30, "Мария", "Петрова", true, false);
    User user4 = new User(44, "Сергей", "Смирнов", false, false);
    User user5 = new User(64, "Елена", "Иванова", true,false);

    User user8 = new User(24, "Светлана", "Иванова", true, true);
    User user9 = new User(31, "Дмитрий", "Сидоров", false, false);
    User user10 = new User(26, "Татьяна", "Петрова", true, true);
    User user12 = new User(54, "Марина", "Иванова", true, false);
    
    services.add(new Haircut(1000,user1, true, Haircut.HaircutType.MALE_BOX));
    services.add(new Haircut(1200,user2, true, Haircut.HaircutType.FEMALE_PIXI));
    services.add(new Haircut(800,user4, false, Haircut.HaircutType.FEMALE_BOB));
    services.add(new Haircut(900,user5, true, Haircut.HaircutType.MALE_CROP));

    services.add(new Coloring(1400,user8, false, Coloring.HairColor.BLACK));
    services.add(new Coloring(1600,user9, true, Coloring.HairColor.BROWN));
    services.add(new Coloring(1500,user10, false, Coloring.HairColor.BLOND));
    services.add(new Coloring(1300,user12, true, Coloring.HairColor.WHITE));

    }

    public double calculateDiscountPrice(){
        double discountPrice = 0;
        for (HairdressingService service : services) {
            discountPrice += service.getDiscountedPrice();
        }
        return discountPrice;
    }

    public String printServices(){
        List<HairdressingService> sortedServicesByUserSurname = services.stream()
                .sorted((s1, s2) -> s1.getUser().getSurname().compareTo(s2.getUser().getSurname()))
                .toList();
        StringBuilder sb = new StringBuilder();

        for (HairdressingService service : sortedServicesByUserSurname) {
            sb.append(service.toString()).append("\n");
        }
        return sb.toString();
        
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(printServices()).append("\nСуммарная выручка ").append(calculateDiscountPrice());
        return sb.toString();
    }
}