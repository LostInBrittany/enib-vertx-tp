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
 - mongodb running
 
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
  
# Step 4 - get the beers from the event bus
  
  Send the name of the service 'beers or <beer_id>' to the address 'beers.service'
  
    eb.send("ADDRESS", DATA_TO_SEND, new Handler<Message<String>>() {
     public void handle(Message<String> eventBusResponse) {       
       System.out.println("Yeah the response is " + message.body() );
     }
    });
  
   The reply on the EventBus if the JSON flow of the request. You send it directly.
   
    req.response.end("content sended to the user agent");
   
   #Dammed, I have a NPE (NullPointerException or NullProfessorException) 
   The service always respond "Huston we have a problem" on the eventbus #WTF???
   
# Step 5 - inject the service
  In BeersWorkerVerticle.java the Beers service is not injected. Adds @Inject annotation just before.
  
  But the Beers interface is not binded with Guice.
  
  So bind the interface Beers.class to MongoBeersImpl.class in the scope Singleton

  Test your api 
  [beers](http://localhost:44081/api/beers/beeers)
  [Affligem](http://localhost:44081/api/beers/AffligemBlond)

  It works... but dammed the images are no displayed!!! #WTF? go to the next step

# Step 6 - serve the images

  If you look at the JSON feed the images are served on the URI /img/<filename>
  
  You're now a vert.x rock star, so:
   
   - create the route /img/<filename> in the EnibarVerticle
   - send the message <filename> at the address "images.service" on the eventbus
   - the reply is a type "Buffer"
   - write the buffer content to the user agent
   
 Warning before write data to the response you should add the header "Content-Length" to the response... 
 [reasons, the truth is in the RFC](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html)
 
 But easy with vert.x... req.response().putHeader("Content-Length", 176437647);
# Step 7- hack the app

Ok, having the list of beers, is fun but can you add one with a form ???

On the uri /api/beers/add

