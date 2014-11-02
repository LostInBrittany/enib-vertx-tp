package com.enib.cai.vertx.verticle;

import com.enib.cai.vertx.services.Files;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;

import javax.inject.Inject;

public class ImagesWorkerVerticle extends AbstractGuiceVerticle {

  @Inject
  private Files files;

  public void start() {
    super.start();
    System.out.println("deploy images worker verticle");

    EventBus eb = vertx.eventBus();

    // Handler beers service
    Handler<Message<String>> myHandler = new Handler<Message<String>>() {

      public void handle(Message<String> message) {
        try {
          byte[] file = files.get(message.body());
          // Now reply to it
          message.reply(new Buffer(file));
        }catch(Exception exp) {
          byte[] file = files.get("fail.jpg");
          message.reply(new Buffer(file));
        }
      }
    };

    eb.registerHandler("images.service", myHandler);
  }
}
