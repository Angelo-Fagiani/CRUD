package it.develhope.CRUD.repositories;

import it.develhope.CRUD.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {}
