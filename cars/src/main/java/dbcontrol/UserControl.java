package dbcontrol;

import models.User;

import java.util.List;

public class UserControl extends AbstractController implements Controller<User> {

    @Override
    public List<User> getAll() {
        return super.transaction(session -> {
            List<User> list = session.createQuery("from User").list();
            return list;
        });
    }

    @Override
    public User create(User user) {
        super.transaction(session -> session.save(user));
        return user;
    }

    @Override
    public void update(User user) {
        super.transactionVoid(session -> session.update(user));
    }
}

