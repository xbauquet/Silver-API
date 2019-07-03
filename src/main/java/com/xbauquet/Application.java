package com.xbauquet;

import org.json.JSONObject;
import spark.Request;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.path;
import static spark.Spark.post;

public class Application {

    private final static String token = "222lsfhdsqlkfhidp4xfhm<kdsfhjm5464wfjs54dlf5dvg9kbbdhvhfdogfdjohkgjfgb543575xfvjhfwlgf2";
    private final static PostgresService postgresService = new PostgresService();

    public static void main(String[] args) {
        path("/api", () -> {
            get("/hello", (request, response) -> {
                securityCheck(request);
                return "Hello World";
            });

            get("/all", (request, response) -> {
                securityCheck(request);
                return postgresService.getAll();
            });

            post("/new", (request, response) -> {
                securityCheck(request);
                JSONObject body = new JSONObject(request.body());
                PlantSize plantSize = new PlantSize();
                plantSize.setPlantName(body.getString("name"));
                plantSize.setDate(body.getLong("date"));
                plantSize.setSize(body.getFloat("size"));
                postgresService.save(plantSize);
                return plantSize;
            });
        });
    }

    private static void securityCheck(final Request request) {
        System.out.println("securityCheck");
        String headers = request.headers("Authorization");
        if(headers == null) {
            halt(401, "You are not welcome here");
        }
        String token = headers.split(" ")[1];
        if(!token.equals(Application.token)) {
            halt(401, "You are not welcome here");
        }
    }
}
