package dbcontrol;

import ru.domain.Car;
import org.hibernate.Hibernate;

import java.util.List;

public class CarControl extends AbstractController implements Controller<Car> {

    @Override
    public List<Car> getAll() {

        return super.transaction(session -> {
            List<Car> list = session.createQuery("from Car").list();
            list.forEach(c -> {
                Hibernate.unproxy(c.getBrand());
                Hibernate.unproxy(c.getTransmission());
                Hibernate.unproxy(c.getBody());
                Hibernate.unproxy(c.getEngine());
                Hibernate.unproxy(c.getUser());
            });
            return list;
        });
    }


    @Override
    public Car create(Car car) {
        super.transaction(session -> session.save(car));
        return car;
    }

    @Override
    public void update(Car car) {
        super.transactionVoid(session -> session.update(car));
    }
}

