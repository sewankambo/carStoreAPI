package com.ronnie.vehicles.service;

import com.ronnie.vehicles.client.maps.MapsClient;
import com.ronnie.vehicles.client.prices.PriceClient;
import com.ronnie.vehicles.domain.Location;
import com.ronnie.vehicles.domain.car.Car;
import com.ronnie.vehicles.domain.car.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private MapsClient mapsClient;
    private PriceClient priceClient;

    public CarService(CarRepository repository, MapsClient maps, PriceClient priceClient) {
        this.repository = repository;
        this.mapsClient = maps;
        this.priceClient = priceClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Car car = this.repository.findById(id).orElseThrow(CarNotFoundException::new);
        car.setPrice(priceClient.getPrice(car.getId()));
        Location location = mapsClient.getAddress(car.getLocation());
        car.setLocation(location);
        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setCondition(car.getCondition());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Car car = this.repository.findById(id).orElseThrow(CarNotFoundException::new);
        repository.delete(car);

    }
}
