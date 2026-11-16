package hw4;

import java.time.LocalDate;

public class ticketTest {
    public static void main(String[] args) {

        // Создайте массив родительского типа.
        // Разместите в нём минимум по два объекта каждого производного класса.
        ticket[] tickets = {
            new trainTicket(
                    "T001", "Иван Иванов", 1200,
                    LocalDate.of(2026, 11, 10),
                    "Таллинн", "Тарту", 10),

            new trainTicket(
                    "T002", "Неиван Неиванов", 300,
                    LocalDate.of(6202, 12, 30),
                    "Неталлинн", "Нетарту", 100),

            new airTicket(
                    "A001", "Олег Олегов", 8500,
                    LocalDate.of(2026, 11, 15),
                    "Таллинн", "Берлин", "BT101", 800),

            new airTicket(
                    "A002", "Неолег Неолегов", 85,
                    LocalDate.of(2031, 1, 1),
                    "Неталлинн", "Неберлин", "BT999", 850),

            new theaterTicket(
                    "TH001", "Пётр Петрович", 45,
                    LocalDate.of(2026, 11, 20),
                    "Гамлет", "12", "VIP", 15),

            new theaterTicket(
                    "TH100", "Непётр Непетрович", 54,
                    LocalDate.of(2001, 9, 11),
                    "Падение башен", "1", "PILOT", 0)
        };

        // Обработайте весь массив одним циклом.
        // Вызовите внутри цикла общий переопределённый метод.
        // Создайте экземпляры всех производных классов и покажите полиморфный вызов общего метода.
        for (ticket t : tickets) {
            // ... переопределённый метод.
            System.out.println(t.getDescription());
            // Выведите объекты через toString()
            System.out.println(t.toString());
        }

        // equals() и hashCode()
        ticket t1 = new theaterTicket(
                "TH002", "Георгий Георгиевич", 50,
                LocalDate.of(2026, 12, 1),
                "Ревизор", "5", "A", 10);

        ticket t2 = new theaterTicket(
                "TH002", "Георгий Георгиевич", 50,
                LocalDate.of(2026, 12, 1),
                "Ревизор", "5", "A", 10);

        System.out.println("Билеты равны: "
                + t1.equals(t2));
        System.out.println("Хеш-коды совпадают: "
                + (t1.hashCode() == t2.hashCode()));
    }
}


