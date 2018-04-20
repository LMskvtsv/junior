package bank;

public class Account {
    private double value;
    private String accountNumber;

    public Account(double value, String accountNumber) {
        this.value = value;
        this.accountNumber = accountNumber;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
