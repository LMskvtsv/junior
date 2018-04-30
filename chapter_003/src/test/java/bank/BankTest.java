package bank;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BankTest {

    private PrintStream stdout = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void tearDown() {
        System.setOut(this.stdout);
    }

    @Test
    public void addUser() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        bank.addUser(user);
        assertThat(bank.userAccounts.keySet().contains(user), is(true));
    }

    @Test
    public void addAccountToUser() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), new Account(1000.00, "12345"));
        assertThat(bank.userAccounts.get(user).get(0).getAccountNumber(), is("12345"));
    }

    @Test
    public void addSeveralAccounts() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), new Account(1000.00, "12345"));
        bank.addAccountToUser(user.getPassport(), new Account(100.00, "jkju"));
        assertThat(bank.userAccounts.get(user).get(0).getAccountNumber(), is("12345"));
        assertThat(bank.userAccounts.get(user).get(1).getAccountNumber(), is("jkju"));
    }

    @Test
    public void whenUserNotFound() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        bank.addAccountToUser(user.getPassport(), new Account(1000.00, "12345"));
        assertThat(new String(this.out.toByteArray()), is(String.format("Cannot find user by passport %s.%s", user.getPassport(), System.lineSeparator())));
    }

    @Test
    public void whenUserAlreadyExists() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        bank.addUser(user);
        bank.addUser(user);
        assertThat(new String(this.out.toByteArray()), is(String.format("User already exists.%s", System.lineSeparator())));
    }

    @Test
    public void deleteUserNotEmptyAccounts() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), new Account(1000.00, "12345"));
        bank.deleteUser(user);
        assertThat(bank.userAccounts.size(), is(0));
    }

    @Test
    public void deleteUserEmptyAccounts() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        bank.addUser(user);
        bank.deleteUser(user);
        assertThat(bank.userAccounts.size(), is(0));
    }

    @Test
    public void deleteUserDoesntExist() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        bank.deleteUser(user);
        assertThat(bank.userAccounts.size(), is(0));
    }

    @Test
    public void deleteAccountFromUser() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        Account accountToDelete = new Account(100.00, "jkju");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), new Account(1000.00, "12345"));
        bank.addAccountToUser(user.getPassport(), accountToDelete);
        bank.deleteAccountFromUser(user.getPassport(), accountToDelete);
        assertThat(bank.getUserAccounts(user.getPassport()).size(), is(1));
        assertThat(bank.getUserAccounts(user.getPassport()).get(0).getAccountNumber(), is("12345"));
    }

    @Test
    public void deleteAccountUserDoesntExist() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        Account accountToDelete = new Account(100.00, "jkju");
        bank.deleteAccountFromUser(user.getPassport(), accountToDelete);
        assertThat(new String(this.out.toByteArray()), is(String.format("Cannot find user by passport %s.%s", user.getPassport(), System.lineSeparator())));
    }

    @Test
    public void getUserAccouts() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), new Account(1000.00, "12345"));
        bank.addAccountToUser(user.getPassport(), new Account(100.00, "jkju"));
        assertThat(bank.getUserAccounts(user.getPassport()).get(0).getAccountNumber(), is("12345"));
        assertThat(bank.getUserAccounts(user.getPassport()).get(1).getAccountNumber(), is("jkju"));
        assertThat(bank.getUserAccounts(user.getPassport()).size(), is(2));
    }

    @Test
    public void transferEnoughMoney() {
        Bank bank = new Bank();
        User user1 = new User("Ivan", "334Gy76");
        User user2 = new User("Pavel", "gr6yeu77");
        Account user1Acc = new Account(1000.00, "12345");
        Account user2Acc = new Account(100.00, "jkju");
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addAccountToUser(user1.getPassport(), user1Acc);
        bank.addAccountToUser(user2.getPassport(), user2Acc);
        bank.transferMoney(user1.getPassport(),
                user1Acc.getAccountNumber(),
                user2.getPassport(),
                user2Acc.getAccountNumber(),
                150.00);
        assertThat(bank.getUserAccounts(user1.getPassport()).get(0).getValue(), is(850.0));
        assertThat(bank.getUserAccounts(user2.getPassport()).get(0).getValue(), is(250.0));
    }

    @Test
    public void transferBetweenAccountsOfSameUser() {
        Bank bank = new Bank();
        User user = new User("Ivan", "334Gy76");
        Account userAcc1 = new Account(1000.00, "12345");
        Account userAcc2 = new Account(100.00, "jkju");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), userAcc1);
        bank.addAccountToUser(user.getPassport(), userAcc2);
        bank.transferMoney(user.getPassport(),
                userAcc1.getAccountNumber(),
                user.getPassport(),
                userAcc2.getAccountNumber(),
                150.00);
        assertThat(bank.getUserAccounts(user.getPassport()).get(0).getValue(), is(850.0));
        assertThat(bank.getUserAccounts(user.getPassport()).get(1).getValue(), is(250.0));
    }

    @Test
    public void transferEnoughMoneyZeroLeft() {
        Bank bank = new Bank();
        User user1 = new User("Ivan", "334Gy76");
        User user2 = new User("Pavel", "gr6yeu77");
        Account user1Acc = new Account(100.00, "12345");
        Account user2Acc = new Account(0.00, "jkju");
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addAccountToUser(user1.getPassport(), user1Acc);
        bank.addAccountToUser(user2.getPassport(), user2Acc);
        bank.transferMoney(user1.getPassport(),
                user1Acc.getAccountNumber(),
                user2.getPassport(),
                user2Acc.getAccountNumber(),
                100.00);
        assertThat(bank.getUserAccounts(user1.getPassport()).get(0).getValue(), is(0.0));
        assertThat(bank.getUserAccounts(user2.getPassport()).get(0).getValue(), is(100.0));
    }

    @Test
    public void transferNotEnoughMoney() {
        Bank bank = new Bank();
        User user1 = new User("Ivan", "334Gy76");
        User user2 = new User("Pavel", "gr6yeu77");
        Account user1Acc = new Account(100.00, "12345");
        Account user2Acc = new Account(0.00, "jkju");
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addAccountToUser(user1.getPassport(), user1Acc);
        bank.addAccountToUser(user2.getPassport(), user2Acc);
        bank.transferMoney(user1.getPassport(),
                user1Acc.getAccountNumber(),
                user2.getPassport(),
                user2Acc.getAccountNumber(),
                200.00);
        assertThat(bank.getUserAccounts(user1.getPassport()).get(0).getValue(), is(100.0));
        assertThat(bank.getUserAccounts(user2.getPassport()).get(0).getValue(), is(0.0));
        assertThat(new String(this.out.toByteArray()),
                is(String.format("Impossible operation. Cannot find account or not enough money for the transfer.%s",
                        System.lineSeparator())));
    }

    @Test
    public void transferCannotFindUser() {
        Bank bank = new Bank();
        User user1 = new User("Ivan", "334Gy76");
        User user2 = new User("Pavel", "gr6yeu77");
        Account user1Acc = new Account(200.00, "12345");
        Account user2Acc = new Account(0.00, "jkju");
        bank.addUser(user1);
        bank.addAccountToUser(user1.getPassport(), user1Acc);
        bank.transferMoney(user1.getPassport(),
                user1Acc.getAccountNumber(),
                user2.getPassport(),
                user2Acc.getAccountNumber(),
                200.00);
        assertThat(bank.getUserAccounts(user1.getPassport()).get(0).getValue(), is(200.0));
        assertThat(new String(this.out.toByteArray()),
                is(String.format("Cannot find all users with passport %s, %s.%s",
                        user1.getPassport(), user2.getPassport(), System.lineSeparator())));
    }

    @Test
    public void transferCannotFindAccount() {
        Bank bank = new Bank();
        User user1 = new User("Ivan", "334Gy76");
        User user2 = new User("Pavel", "gr6yeu77");
        Account user1Acc = new Account(100.00, "12345");
        Account user2Acc = new Account(200.00, "jkju");
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addAccountToUser(user2.getPassport(), user2Acc);
        bank.transferMoney(user1.getPassport(),
                user1Acc.getAccountNumber(),
                user2.getPassport(),
                user2Acc.getAccountNumber(),
                50.00);
        assertThat(bank.getUserAccounts(user2.getPassport()).get(0).getValue(), is(200.0));
        assertThat(new String(this.out.toByteArray()),
                is(String.format("Impossible operation. Cannot find account or not enough money for the transfer.%s",
                        System.lineSeparator())));
    }
}
