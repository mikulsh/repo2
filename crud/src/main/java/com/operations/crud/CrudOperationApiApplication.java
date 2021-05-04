package com.operations.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;


@SpringBootApplication
public class CrudOperationApiApplication {

    public static void main(String[] args) throws SQLException {

        SpringApplication.run(CrudOperationApiApplication.class, args);

    }

}
