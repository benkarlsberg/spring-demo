package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class WeatherController {

    @Autowired
    private WeatherRepository weatherRepository;

    @PostMapping("/weather")
    public ResponseEntity<WeatherData> createWeatherData(@RequestBody WeatherData weatherData) {
        // save the weather data to the database and assign a unique ID
        WeatherData savedWeatherData = weatherRepository.save(weatherData);

        // set the ID in the response body
        savedWeatherData.setId(savedWeatherData.getId());

        // return a 201 response with the created record in the response body
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWeatherData);
    }

    @GetMapping("/weather")
    public ResponseEntity<List<WeatherData>> getWeatherData() {
        // retrieve all weather data records from the database and sort by id in increasing order
        List<WeatherData> weatherDataList = weatherRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        // return a 200 response with the array of matching records in the response body
        return ResponseEntity.ok(weatherDataList);
    }


    @GetMapping("/weather/{id}")
    public ResponseEntity<WeatherData> getWeatherDataById(@PathVariable int id) {
        // retrieve the weather data record from the database by id
        Optional<WeatherData> weatherData = weatherRepository.findById(id);

        // check if the record exists in the database
        if (weatherData.isPresent()) {
            // return a 200 response with the matching record in the response body
            return ResponseEntity.ok(weatherData.get());
        } else {
            // return a 404 response if the record doesn't exist in the database
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/weather/{id}")
    public ResponseEntity<Void> deleteWeatherDataById(@PathVariable int id) {
        // check if the record exists in the database
        if (weatherRepository.existsById(id)) {
            // delete the record from the database
            weatherRepository.deleteById(id);
            // return a 204 response with no content
            return ResponseEntity.noContent().build();
        } else {
            // return a 404 response if the record doesn't exist in the database
            return ResponseEntity.notFound().build();
        }
    }
}