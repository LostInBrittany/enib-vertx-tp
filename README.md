# AngularBeers - Vertx - batckend tutorial #


[The AngularBeers](https://github.com/LostInBrittany/angular-beers) tutorial is integrated into a vert.x batckend server.                               
Yeah ! Lets code Java !
But... form the moment it only serve the static Json files.
Ok, build a backend with vertx is not painfull let's go.


# Step 0 - checklist #
 - gradle is working (enter gradle in a console)
 - You have IntelliJ installed on your desktop
 - a JDK 7 or upper
 - last vert.x version
 
Ok go ahead !
 
# Step 1 - open and run the project #
In the project directory, generate the idea configuration  
./gradlew idea 

Open the project with intelliJ 
 - accept the gradle import
 - in the project settings (F4)
   - check the JDK (8) and language level (7)
   - select "inherit project compile output path" in modules / paths
 - build the project (Build/make project)
 - in the terminal run './gradlew runMod -i'
 
 Open a browser on [local page served by vert.x](http://localhost:44081/index.html)
 
 Enjoy vert.x serves your AngularBeers project

# Step 2 - Create an route for the api path #

The module starts the EntryPointVerticle (declared in the mod.json)
This verticle deploy programmaticly :
  - EnibVerticle (the eventloop verticle)
  - BeersWorkerVerticle (worker verticle provides the beers list and details)
  - ImagesWorkerVerticle (worker verticle provider the beer images)
  
    routeMatcher.put("/static_path/:param_path", new Handler<HttpServerRequest>() {
        public void handle(HttpServerRequest req) {
            String paramPath = req.params().get("param_path");

            req.response().end("hello my param path is " + paramPath);
        }
    });
    
   - on /api/beers/beers serve the JSONObject produced by StaticBeersImpl
   - on /api/beers/AffligemBlond serve the details of the beer
   
   Option you can try to inject StaticBeersImpl 
    
   Test your api 
   [beers](http://localhost:44081/api/beers/beeers)
   [Affligem](http://localhost:44081/api/beers/AffligemBlond)
   
# Step 3 - Plug the Angular JS view
 
 Modify services.js in order to read the api uri 
 Now your view is served by your API
  
  
   