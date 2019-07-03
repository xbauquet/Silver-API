package com.xbauquet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgresService {

    private String url = "jdbc:postgresql://postgres-service:5432/silver";

    public void save(PlantSize plantSize) {
        try(Connection connection = DriverManager.getConnection(url, "postgres", "1234")) {
            System.out.println("Opened database successfully");
            try(Statement statement = connection.createStatement()) {
                String sql = "INSERT INTO plantsize (NAME, DATE, SIZE) VALUES (" + plantSize.getPlantName() + ", " + plantSize.getDate() + ", " + plantSize.getSize() + ");";
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlantSize> getAll() {
        List<PlantSize> sizes = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, "postgres", "1234")) {
            System.out.println("Opened database successfully");
            try(Statement statement = connection.createStatement()) {
                String sql = "SELECT * FROM plantsize;";
                ResultSet resultSet = statement.executeQuery(sql);
                while ( resultSet.next() ) {
                    PlantSize plantSize = new PlantSize();
                    plantSize.setPlantName(resultSet.getString("name"));
                    plantSize.setDate(resultSet.getLong("date"));
                    plantSize.setSize(resultSet.getFloat("size"));
                    sizes.add(plantSize);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sizes;
    }
}
