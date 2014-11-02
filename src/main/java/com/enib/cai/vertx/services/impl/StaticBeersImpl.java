package com.enib.cai.vertx.services.impl;

import com.enib.cai.vertx.model.Beer;
import com.enib.cai.vertx.services.Beers;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class StaticBeersImpl implements Beers {
  
  private Map<String, Beer> beers = new HashMap<>();
  
  
  public StaticBeersImpl() {
    Beer beer = new Beer();
    beer.alcohol= 6.8;
    beer.availability= "Year round";
    beer.brewery= "Brasserie Affligem (Heineken)";
    beer.description= "Affligem Blonde, the classic clear blonde abbey ale, with a gentle roundness and 6.8% alcohol. Low on bitterness, it is eminently drinkable.";
    beer.id= "AffligemBlond";
    beer.img= "beers/img/AffligemBlond.jpg";
    beer.label= "beers/img/AffligemBlond-label.png";
    beer.name= "Affligem Blond";
    beer.serving= "Serve in a Snifter";
    beer.style= "Belgian-Style Blonde Ale";


    beers.put(beer.id, beer);
  }
  
  
  
  public JsonArray getBeers() {
    JsonArray jsonBeers = new JsonArray();

    for (Beer javaBeer: beers.values()) {
      JsonObject beer = new JsonObject();
      beer.putNumber("alcohol", javaBeer.alcohol= 6.8);
      beer.putString("description",javaBeer.description);
      beer.putString("id",javaBeer.id);
      beer.putString("img",javaBeer.img);
      beer.putString("name",javaBeer.name);

      jsonBeers.addObject(beer);
    }

    return jsonBeers;
  }

  @Override
  public JsonObject getBeer(String id) {

    Beer javaBeer = beers.get(id);

    JsonObject beer = new JsonObject();

    beer.putString("availability", javaBeer.availability);
    beer.putNumber("alcohol", javaBeer.alcohol= 6.8);
    beer.putString("brewery",javaBeer.brewery);
    beer.putString("description",javaBeer.description);
    beer.putString("id",javaBeer.id);
    beer.putString("img",javaBeer.img);
    beer.putString("label",javaBeer.label);
    beer.putString("name",javaBeer.name);
    beer.putString("serving",javaBeer.serving);
    beer.putString("style",javaBeer.style);

    return beer;
  }

  @Override
  public JsonObject addBeer(Beer beer) throws Exception {
    beers.put(beer.id, beer);
    return getBeer(beer.id);
  }

}
