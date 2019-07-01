package com.xbauquet;

import spark.Request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.path;

public class Application {
    public static void main(String[] args) {
        path("/api", () -> {

            before((request, response) -> securityCheck(request));

            get("/data", (request, response) -> {
                System.out.println("get Data");
                securityCheck(request);
                return "Hello World";
            });
        });
    }

    private static void securityCheck(final Request request) {

        String headers = request.headers("Authorization");
        if(headers == null) {
            halt(401, "You are not welcome here");
        }
        String token = headers.split(" ")[1];

        URL url = null;
        try {
            url = new URL("http://auth-service/users/exists");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization","Bearer " + token);
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestMethod("GET");

            System.out.println("Response code : "+conn.getResponseCode());
            if(conn.getResponseCode() == 404) {
                halt(401, "You are not welcome here");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            halt(401, "You are not welcome here");

        }
    }
}
