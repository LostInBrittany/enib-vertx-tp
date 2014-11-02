package com.enib.cai.vertx.verticle;

import com.enib.cai.vertx.services.SayPong;
import com.google.inject.Inject;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

public class HelloWorldVerticle extends Verticle {
  @Inject
  public SayPong sayPong;

  public void start() {
    vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        req.response().headers().set("Content-Type", "text/plain");
        req.response().end("Hello World " +  sayPong.sayPong());
      }
    }).listen(44080);
  }
}
