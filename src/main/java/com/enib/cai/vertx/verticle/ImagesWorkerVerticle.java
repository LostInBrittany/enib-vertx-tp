package com.enib.cai.vertx.verticle;

import com.enib.cai.vertx.guice.GuiceModule;
import com.enib.cai.vertx.services.Beers;
import com.enib.cai.vertx.services.Files;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

import javax.inject.Inject;

public class ImagesWorkerVerticle extends Verticle {
  private Injector injector;

  @Inject
  private Files files;

  public void start() {
    System.out.println("deploy images worker verticle");

    System.out.println("inject dependencies");
    injector = Guice.createInjector(new GuiceModule(container));
    injector.injectMembers(this);


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
