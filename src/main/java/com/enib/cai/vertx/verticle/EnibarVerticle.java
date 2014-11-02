package com.enib.cai.vertx.verticle;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

/**
 * User: lambour
 * Date: 30/10/14 22:07
 * Copyright: Cityzen Data
 */
public class EnibarVerticle extends Verticle {

  public void start() {
    System.out.println("deploy enibar verticle");


    final EventBus eb = vertx.eventBus();
    eb.setDefaultReplyTimeout(5000);

    System.out.println("Launch the verticle routeMatcher");

    RouteMatcher routeMatcher = new RouteMatcher();

    // API Route Matcher send to the event bus
    routeMatcher.get("/api/beers/:id", new Handler<HttpServerRequest>() {
      public void handle(final HttpServerRequest req) {
        String id = req.params().get("id");
        // send the message throw the eventbus
        eb.send("beers.service", id, new Handler<Message<String>>() {
          public void handle(Message<String> message) {
            // get the response from the eventbus and send it as response
            System.out.println("I received a reply before the timeout of 5 seconds");
            req.response().end(message.body());
          }
        });
      }
    });

    // API Route Matcher send to the event bus
    routeMatcher.get("/img/:name", new Handler<HttpServerRequest>() {
      public void handle(final HttpServerRequest req) {
        String filename = req.params().get("name");

        eb.send("images.service", filename, new Handler<Message<Buffer>>() {
          public void handle(Message<Buffer> message) {
            req.response().putHeader("Content-Length", Integer.toString(message.body().length()));
            req.response().write(message.body());
            req.response().end();
          }
        });
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
