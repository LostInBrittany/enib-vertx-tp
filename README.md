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

# Step 1 - open and run the project #