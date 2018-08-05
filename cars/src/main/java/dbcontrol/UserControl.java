package dbcontrol;

import ru.domain.User;
import ru.domain.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public List<User> getByCredentials(String login, String password) {
        return super.transaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class );
            Root<User> userRoot = criteria.from(User.class);
            criteria.select(userRoot);
            criteria.where(builder.and(
                    builder.equal(userRoot.get(User_.login), login),
                    builder.equal(userRoot.get(User_.password), password)
                    ));
            List<User> people = session.createQuery(criteria).getResultList();
            return people;
        });
    }
}

