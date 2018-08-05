package logic;

import dbcontrol.CarControl;
import ru.domain.Car;

import java.util.List;

public class ValidateServiceCars {
    private final CarControl carControl = new CarControl();

    public boolean create(Car car) {
        boolean result = validate(car);
        if (result) {
            carControl.create(car);
        }
        return result;
    }

    public boolean update(Car car) {
        boolean result = validate(car);
        if (result) {
            carControl.update(car);
        }
        return result;
    }

    public List<Car> getAll(){
        return carControl.getAll();
    }

    private boolean validate(Car car) {
        boolean result = false;
        if (car != null
                && car.getBody() != null && !car.getBody().equals("")
                && car.getBrand() != null && !car.getBrand().equals("")
                && car.getEngine() != null && !car.getEngine().equals("")
                && car.getTransmission() != null && !car.getTransmission().equals("")
                && car.getUser() != null && !car.getUser().equals("")
                && car.getPhotoPath() != null && !car.getPhotoPath().equals("")) {
            result = true;
        }
        return result;
    }

}
