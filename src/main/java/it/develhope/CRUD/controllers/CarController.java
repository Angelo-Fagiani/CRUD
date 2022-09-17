package it.develhope.CRUD.controllers;

import it.develhope.CRUD.entities.Car;
import it.develhope.CRUD.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    CarRepository carRepository;

    //METODO PER CREARE UNA MACCHINA
    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carRepository.saveAndFlush(car);
    }

    //METODO CHE RESTITUISCE UNA LISTA DELLE MACCHINE CREATE
    @GetMapping
    public List<Car> carsList(){
        return carRepository.findAll();
    }

    //METODO CHE RESTITUISCE UN MACCHINE INSERENDO L'ID
    @GetMapping("/{id}")
    public Car getSingleCar(@PathVariable long id) {
        Car car = carRepository.getReferenceById(id);
        boolean carExists = carRepository.existsById(id);
        if (carExists) {
            return car;
        }
        return new Car();
    }

    //METODO PER MODIFICARE DEI VALORI
    //PERCHE' SI INSERISCE car.setId(id) ?? in teoria non devo settare l'id ma il type
    @PutMapping("/{id}")
    public Car editCar(@PathVariable long id, @RequestBody Car car){
        car.setId(id);
        Car car2 = carRepository.saveAndFlush(car);
        boolean carExists = carRepository.existsById(id);
        if (carExists){
            return car2;
        }
        return new Car();
    }

    //METODO PER ELIMINARE MACCHINA ISERENDO IL SUI ID
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        boolean carExist = carRepository.existsById(id);

        if (carExist) {
            carRepository.deleteById(id);
        } else {
            System.out.println(HttpStatus.CONFLICT);
        }
        try {
            response.sendError(409, "CONFLIT");
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    /*@DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.CONFLICT,reason = "NOT FOUND")
    public void deleteCar(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        boolean carExist = carRepository.existsById(id);
        if (carExist) {
            carRepository.deleteById(id);
        }
            System.out.println(HttpStatus.CONFLICT);
        }*/




    //METODO PER ELIMINARE TUTTE LE AUTO PRECEDENTEMENTE CREATE
    @DeleteMapping()
    public void deleteAllCar(){
        carRepository.deleteAll();
        }

    }
