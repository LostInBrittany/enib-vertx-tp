package com.enib.cai.vertx.services;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

/**
 * User: lambour
 * Date: 01/11/14 21:32
 * Copyright: Cityzen Data
 */
public interface Beers {

  public JsonArray getBeers();

  public JsonObject getBeer(String id) throws Exception;
}
