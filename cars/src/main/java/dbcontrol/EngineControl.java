package dbcontrol;

import models.Engine;

import java.util.List;

public class EngineControl extends AbstractController implements Controller<Engine> {

    @Override
    public List<Engine> getAll() {
        return super.transaction(session -> session.createQuery("from Engine").list());
    }

    @Override
    public Engine create(Engine engine) {
        super.transaction(session -> session.save(engine));
        return engine;
    }

    @Override
    public void update(Engine engine) {
        super.transactionVoid(session -> session.update(engine));
    }


}

