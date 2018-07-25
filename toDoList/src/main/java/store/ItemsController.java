package store;

import models.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ItemsController implements Controller<Item> {

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    @Override
    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            list.addAll(session.createQuery("from Item").list());
            session.getTransaction().commit();
        }
        return list;
    }

    @Override
    public Item create(Item item) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        }
        return item;
    }

    @Override
    public void update(Item entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }
}
