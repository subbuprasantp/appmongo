package com.spring.mongo.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "appmongo")
public class SuperHero implements Serializable {

    @Id
    private String id;

    private String name;
    private String clientId;
    private String application;
    private String url;
    private String api;
    private String errorCode;
    private String email;
    private String message;

    // Constructor, Getter and Setter
}



