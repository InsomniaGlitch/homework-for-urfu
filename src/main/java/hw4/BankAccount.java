package hw4;

import java.math.BigDecimal;

public class BankAccount {
    private String owner;
    private BigDecimal balance;

    public BankAccount(String owner, BigDecimal balance) {
        if (owner.replace(" ", "") == "" || owner == null) {
            throw new IllegalArgumentException(
                "Owner field empty, lol."
            );
        }
        this.owner = owner;
        this.balance = balance;
    }

    public Boolean addMoney(BigDecimal money) {
        if(money.compareTo(BigDecimal.ZERO) == -1) {
            throw new IllegalArgumentException("Сумма не та.");
        } else {
            this.balance = this.balance.add(money);
            return true;
        }
    }

    public Boolean withdrawMoney(BigDecimal money) {
        if(money.compareTo(BigDecimal.ZERO) == -1 || money.compareTo(this.balance) == 1) {
            throw new IllegalArgumentException("Либо сумма не та, либо денег нет.");
        } else {
            this.balance = this.balance.subtract(money);
            return true;
        }
    }

    public String info() {
        return "Owner: " + this.owner + "\nBalance: " + this.balance.toPlainString();
    }
}
