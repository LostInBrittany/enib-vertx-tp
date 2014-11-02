package com.enib.cai.vertx.verticle;

import com.enib.cai.vertx.guice.GuiceModule;
import com.enib.cai.vertx.services.Beers;
import com.enib.cai.vertx.services.Files;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

import javax.inject.Inject;

/**
 * User: lambour
 * Date: 02/11/14 11:20
 * Copyright: Cityzen Data
 */
public class BeersWorkerVerticle extends Verticle {
  private Injector injector;

  @Inject
  private Beers beers;

  @Inject
  private Files files;

  public void start() {
    System.out.println("deploy enibar worker verticle");

    System.out.println("inject dependencies");
    injector = Guice.createInjector(new GuiceModule(container));
    injector.injectMembers(this);


    EventBus eb = vertx.eventBus();

    // Handler beers service
    Handler<Message<String>> myHandler = new Handler<Message<String>>() {
      String response = "";
      public void handle(Message<String> message) {
        try {
          switch (message.body()) {
            case "beers":
              response = beers.getBeers().encodePrettily();
              break;

            //other beers
            default:
              response = beers.getBeer(message.body()).encodePrettily();
          }
        }catch(Exception exp) {
          response = "Huston we have a problem";
        }

        // Now reply to it
        message.reply(response);
      }
    };

    eb.registerHandler("beers.service", myHandler);
  }
}
