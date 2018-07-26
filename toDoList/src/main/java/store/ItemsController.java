package store;

import models.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemsController implements Controller<Item> {

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    @Override
    public List<Item> getAll() {
        return transaction(session -> session.createQuery("from Item").list());
    }

    @Override
    public Item create(Item item) {
        transaction(session -> session.save(item));
        return item;
    }

    @Override
    public void update(Item entity) {
        transactionVoid(session -> session.update(entity));
    }

    private <T> T transaction(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            transaction.commit();
            session.close();
        }
    }

    private void transactionVoid(final Consumer<Session> command) {
        final Session session = factory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            command.accept(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            transaction.commit();
            session.close();
        }
    }
}
