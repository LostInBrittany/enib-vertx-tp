package com.enib.cai.vertx.guice;

import com.enib.cai.vertx.guice.provider.MongoProvider;
import com.enib.cai.vertx.services.Beers;
import com.enib.cai.vertx.services.Files;
import com.enib.cai.vertx.services.SayPong;
import com.enib.cai.vertx.services.impl.FIlesImpl;
import com.enib.cai.vertx.services.impl.MongoBeersImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.mongodb.DB;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

/**
 * User: lambour
 * Date: 14/10/14 23:10
 * Copyright: Cityzen Data
 */
public class GuiceModule extends AbstractModule {

  private Container container;

  public GuiceModule(Container container){
    this.container = container;
  }

  @Override
  protected void configure() {
    //get the vertx configuration

    // BUG
    // FAIL THE CONFIG IS NOT YEY INITIALIZED

    //container.config().getObject("mongo");
    JsonObject mongoConfig = new JsonObject();

    mongoConfig.putString("host","localhost");
    mongoConfig.putNumber("port", 27017);
    mongoConfig.putString("dbname","enib");

    bind(JsonObject.class)
        .annotatedWith(Names.named("mongo"))
        .toInstance(mongoConfig);



    bind(DB.class).toProvider(MongoProvider.class).in(Singleton.class);

    bind(SayPong.class).in(Singleton.class);

    //bind(Beers.class).to(StaticBeers.class).in(Singleton.class);
    bind(Beers.class).to(MongoBeersImpl.class).in(Singleton.class);
    bind(Files.class).to(FIlesImpl.class).in(Singleton.class);
  }
}
