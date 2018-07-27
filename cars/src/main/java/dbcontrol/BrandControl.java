package dbcontrol;

import models.Brand;

import java.util.List;


public class BrandControl extends AbstractController implements Controller<Brand> {

    @Override
    public List<Brand> getAll() {
        return super.transaction(session -> session.createQuery("from Brand").list());
    }

    @Override
    public Brand create(Brand brand) {
        super.transaction(session -> session.save(brand));
        return brand;
    }

    @Override
    public void update(Brand brand) {
        super.transactionVoid(session -> session.update(brand));
    }

}

