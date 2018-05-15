package bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Bank {

    Map<User, List<Account>> userAccounts = new HashMap<>();

    /**
     * Adds user into bank storage. It is possible to add same user only once.
     *
     * @param user - user that needs to be added.
     */
    public void addUser(User user) {
        if (userAccounts.putIfAbsent(user, new ArrayList<>()) != null) {
            System.out.println(String.format("User already exists."));
        }
    }

    /**
     * Deletes user from bank storage. It is possible to delete user even if user has accounts.
     *
     * @param user - user that needs to be deleted.
     */
    public void deleteUser(User user) {
        userAccounts.remove(user);
    }

    /**
     * Adds account to User. User can have several accounts.
     *
     * @param passport - passport number user can be found with.
     * @param account  - account that needs to be added.
     */
    public void addAccountToUser(String passport, Account account) {
        User user = findUserByPassport(passport);
        if (user != null) {
            userAccounts.get(user).add(account);
        } else {
            System.out.println(String.format("Cannot find user by passport %s.", passport));
        }
    }

    /**
     * Deletes account from User.
     *
     * @param passport - passport number user can be found with.
     * @param account  - account that needs to be deleted.
     */
    public void deleteAccountFromUser(String passport, Account account) {
        User user = findUserByPassport(passport);
        if (user != null) {
            userAccounts.get(user).remove(account);
        } else {
            System.out.println(String.format("Cannot find user by passport %s.", passport));
        }
    }

    /**
     * Retrieve all user's accounts.
     *
     * @param passport - passport number user can be found with.
     * @return - list of all accounts that user has.
     */
    public List<Account> getUserAccounts(String passport) {
        User user = findUserByPassport(passport);
        List<Account> accountArrayList = new ArrayList<>();
        if (user != null) {
            accountArrayList = userAccounts.get(user);
        } else {
            System.out.println(String.format("Cannot find user by passport %s.", passport));
        }
        return accountArrayList;
    }

    /**
     * Money transfer between different accounts same or different users.
     *
     * @param srcPassport       - passport number user can be found with.
     * @param srcAccountNumber  - account where money will be transferred from.
     * @param destPassport      - passport number user can be found with.
     * @param destAccountNumber - account money will be transferred to.
     * @param amount            - amount of money to be transferred.
     * @return true if transfer was successful. In case user or account was not found - returns false.
     */
    public boolean transferMoney(String srcPassport,
                                 String srcAccountNumber,
                                 String destPassport,
                                 String destAccountNumber,
                                 double amount) {

        User srcUser = findUserByPassport(srcPassport);
        User destUser = findUserByPassport(destPassport);
        Optional<Account> srcAccount;
        Optional<Account> destAccount;
        boolean result = false;
        if (srcUser == null || destUser == null) {
            System.out.println(String.format("Cannot find all users with passport %s, %s.", srcPassport, destPassport));
        } else {
            srcAccount = findAccountByAccNumber(srcAccountNumber, userAccounts.get(srcUser));
            destAccount = findAccountByAccNumber(destAccountNumber, userAccounts.get(destUser));
            if (!srcAccount.isPresent() || !destAccount.isPresent() || srcAccount.get().getValue() < amount) {
                System.out.println(String.format("Impossible operation. Cannot find account or not enough money for the transfer."));
            } else {
                srcAccount.get().setValue(srcAccount.get().getValue() - amount);
                destAccount.get().setValue(destAccount.get().getValue() + amount);
                result = true;
            }
        }
        return result;
    }

    /**
     * Find account by account number.
     *
     * @param srcAccountNumber -  number account can be found with.
     * @param srcAccounts      - list of accounts.
     * @return account is it was found, null - if account wasn't found.
     */
    private Optional<Account> findAccountByAccNumber(String srcAccountNumber, List<Account> srcAccounts) {
        Optional<Account> optional = srcAccounts.stream()
                .filter(x -> srcAccountNumber.equals(x.getAccountNumber()))
                .findFirst();
        return optional;
    }

    /**
     * Find user by account passport.
     *
     * @param passport - passport number user can be found with.
     * @return user is it was found, null - if user wasn't found.
     */
    private User findUserByPassport(String passport) {
        User result = null;
        for (User user : userAccounts.keySet()) {
            if (user.getPassport().equals(passport)) {
                result = user;
            }
        }
        return result;
    }
}
