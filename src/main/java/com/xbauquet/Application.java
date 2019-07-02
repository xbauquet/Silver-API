package com.xbauquet;

import spark.Request;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.path;

public class Application {

    private final static String token = "222lsfhdsqlkfhidp4xfhm<kdsfhjm5464wfjs54dlf5dvg9kbbdhvhfdogfdjohkgjfgb543575xfvjhfwlgf2";

    public static void main(String[] args) {
        path("/api", () -> get("/hello", (request, response) -> {
            securityCheck(request);
            return "Hello World";
        }));
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
