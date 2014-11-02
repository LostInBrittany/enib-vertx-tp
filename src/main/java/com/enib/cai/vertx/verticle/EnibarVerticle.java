package com.enib.cai.vertx.verticle;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

public class EnibarVerticle extends Verticle {

  public void start() {
    System.out.println("deploy enibar verticle");


    final EventBus eb = vertx.eventBus();
    eb.setDefaultReplyTimeout(5000);

    System.out.println("Launch the verticle routeMatcher");

    RouteMatcher routeMatcher = new RouteMatcher();

    // API Route Matcher /api/beers/{beers or beerid}
    // STEP 2 Add here the /api/beers route matcher

    // Images Route Matcher /img/{fileName}


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
