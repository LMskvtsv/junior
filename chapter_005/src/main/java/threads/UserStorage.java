package threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private Set<User> storage = new HashSet<>();

    public synchronized boolean add(User user) {
        return storage.add(user);
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        for (User u : storage) {
            if (u.equals(user)) {
                u.setAmount(user.getAmount());
                result = true;
                break;
            }
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user);
    }

    public synchronized boolean transfer(int fromId, int toId, double amount) {
        boolean result = false;
        User from = findByID(fromId);
        User to = findByID(toId);
        if (from != null && to != null && from.getAmount() >= amount) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
            result = true;
        }
        return result;
    }

    private synchronized User findByID(int id) {
        User user = null;
        for (User u : storage) {
            if (u.getId() == id) {
                user = u;
                break;
            }
        }
        return user;
    }
}
