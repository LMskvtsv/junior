package dbcontrol;

import ru.domain.Body;

import java.util.List;

public class BodiesControl extends AbstractController implements Controller<Body> {

    @Override
    public List<Body> getAll() {
        return super.transaction(session -> session.createQuery("from Body").list());
    }

    @Override
    public Body create(Body body) {
        super.transaction(session -> session.save(body));
        return body;
    }

    @Override
    public void update(Body body) {
        super.transactionVoid(session -> session.update(body));
    }
}

