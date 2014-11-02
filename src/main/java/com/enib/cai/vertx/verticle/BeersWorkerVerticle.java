package com.enib.cai.vertx.verticle;

import com.enib.cai.vertx.guice.GuiceModule;
import com.enib.cai.vertx.services.Beers;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;

import javax.inject.Inject;

public class BeersWorkerVerticle extends AbstractGuiceVerticle {

  private Beers beers;

  @Override
  public void start() {
    super.start();
    System.out.println("deploy enibar worker verticle");

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
          exp.printStackTrace();
        }

        // Now reply to it
        message.reply(response);
      }
    };

    eb.registerHandler("beers.service", myHandler);
  }
}
