package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
public class WeatherData {
    @Id
    private int id;
    private String date;
    private String lat;
    private String cite;
}
