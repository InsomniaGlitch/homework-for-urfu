package hw4;

import java.math.BigDecimal;

public class bankTest {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount(
            "Ваня", new BigDecimal("26345.25346")
        );

        BankAccount account2 = new BankAccount(
            "Паша", new BigDecimal("34567.47563")
        );

        BankAccount account3 = new BankAccount(
            "Н И К И Т А", new BigDecimal("6785.2456")
        );

        System.out.println("До:");
        System.out.println(account1.info());
        System.out.println(account2.info());
        System.out.println(account3.info());

        account1.addMoney(new BigDecimal("643.14435435"));
        System.out.println("\nПосле add:");
        System.out.println(account1.info());

        account2.withdrawMoney(new BigDecimal("98765.63475"));
        System.out.println("\nПосле withdraw:");
        System.out.println(account2.info());

        account3.addMoney(new BigDecimal("86556.256"));
        account3.withdrawMoney(new BigDecimal("2346565.4235"));
        System.out.println("\nПосле add И withdraw на одном акке:");
        System.out.println(account3.info());

        try {
            account2.withdrawMoney(new BigDecimal("653262526534266263236.6324236236362"));
        } catch (IllegalArgumentException e) {
            System.out.println("\nВыдаёт " + e.getMessage());
        }

        System.out.println("Ошибка (не) убивает:");
        System.out.println(account2.info());
    }
}
