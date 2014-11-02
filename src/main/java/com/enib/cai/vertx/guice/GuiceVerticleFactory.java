package com.enib.cai.vertx.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Container;
import org.vertx.java.platform.Verticle;
import org.vertx.java.platform.VerticleFactory;
import org.vertx.java.platform.impl.java.CompilingClassLoader;
import org.vertx.java.platform.impl.java.JavaVerticleFactory;

public class GuiceVerticleFactory extends JavaVerticleFactory {
  private Injector injector;

  @Override
  public void init(Vertx vertx, Container container, ClassLoader cl) {
    super.init(vertx, container,cl);

    injector = Guice.createInjector(new GuiceModule(container));
  }

  @Override
  public Verticle createVerticle(String main) throws Exception {
    System.out.println("create verticle" + main);
    Verticle verticle = super.createVerticle(main);

    injector.injectMembers(verticle);

    return verticle;
  }

}
