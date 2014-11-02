package com.enib.cai.vertx.services;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

public interface Beers {

  public JsonArray getBeers();

  public JsonObject getBeer(String id) throws Exception;
}
