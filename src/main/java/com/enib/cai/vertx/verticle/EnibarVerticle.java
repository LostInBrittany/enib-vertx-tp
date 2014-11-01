package com.enib.cai.vertx.verticle;

import com.enib.cai.vertx.services.Beers;
import com.enib.cai.vertx.services.Files;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

import javax.inject.Inject;

/**
 * User: lambour
 * Date: 30/10/14 22:07
 * Copyright: Cityzen Data
 */
public class EnibarVerticle extends Verticle {

  @Inject
  Beers beers;

  @Inject
  Files files;

  public void start() {
    RouteMatcher routeMatcher = new RouteMatcher();

    // API Route Matcher send to the event bus
    routeMatcher.get("/api/beers/:id", new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        try {
          String id = req.params().get("id");
          if ("beers".equals(id)) {
            req.response().end(beers.getBeers().encodePrettily());
          } else {
            req.response().end(beers.getBeer(id).encodePrettily());
          }
        } catch(Exception exp) {
          req.response().end("Huston, we have a problem");
        }
      }
    });

    // API Route Matcher send to the event bus
    routeMatcher.get("/img/:name", new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        String filename = req.params().get("name");

        Buffer buffer = new Buffer(files.get(filename));
        req.response().putHeader("Content-Length", Integer.toString(buffer.length()));
        req.response().write(buffer);

        req.response().end();
      }
    });

    // otherwise  send file
    routeMatcher.noMatch(new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        String file = "";
        if (req.path().equals("/")) {
          file = "index.html";
        } else if (!req.path().contains("..")) {
          file = req.path();
        }
        req.response().sendFile("web/" + file);
      }
    });

    vertx.createHttpServer().requestHandler(routeMatcher).listen(44081);
  }
}
