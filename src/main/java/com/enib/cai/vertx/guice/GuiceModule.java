package com.enib.cai.vertx.guice;

import com.enib.cai.vertx.guice.provider.MongoProvider;
import com.enib.cai.vertx.services.Files;
import com.enib.cai.vertx.services.impl.FIlesImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.mongodb.DB;
import org.vertx.java.core.json.JsonObject;

public class GuiceModule extends AbstractModule {

  private JsonObject config;

  public GuiceModule(JsonObject config){
    this.config = config;
  }

  @Override
  protected void configure() {
    //get the vertx configuration
    JsonObject mongoConfig = config.getObject("mongo");

    bind(JsonObject.class)
        .annotatedWith(Names.named("mongo"))
        .toInstance(mongoConfig);

    bind(DB.class).toProvider(MongoProvider.class).in(Singleton.class);

    // MAYBE ALL MongoBeerImpl is not binded ???

    bind(Files.class).to(FIlesImpl.class).in(Singleton.class);
  }
}
