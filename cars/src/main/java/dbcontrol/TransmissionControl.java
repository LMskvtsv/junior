package dbcontrol;

import models.Transmission;

import java.util.List;

public class TransmissionControl extends AbstractController implements Controller<Transmission> {

    @Override
    public List<Transmission> getAll() {
        return super.transaction(session -> session.createQuery("from Transmission").list());
    }

    @Override
    public Transmission create(Transmission transmission) {
        super.transaction(session -> session.save(transmission));
        return transmission;
    }

    @Override
    public void update(Transmission transmission) {
        super.transactionVoid(session -> session.update(transmission));
    }
}

