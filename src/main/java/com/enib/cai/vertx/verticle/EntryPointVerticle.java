package com.enib.cai.vertx.verticle;

import org.vertx.java.platform.Verticle;

/**
 * User: lambour
 * Date: 02/11/14 10:20
 * Copyright: Cityzen Data
 */
public class EntryPointVerticle extends Verticle {

  public void start() {
    System.out.println("deploy entry point verticle");

    //deploy worker verticles
    container.deployWorkerVerticle("com.enib.cai.vertx.verticle.BeersWorkerVerticle", container.config(), 4);
    container.deployWorkerVerticle("com.enib.cai.vertx.verticle.ImagesWorkerVerticle", container.config(), 4);

    // deploy Enibar Verticle programaticaly
    container.deployVerticle("com.enib.cai.vertx.verticle.EnibarVerticle", container.config());



  }

}
