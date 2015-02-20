twitter-analyzer
================

Dependencies
------------

  - [Gradle](https://gradle.org/) (for building from source)
  - [Java](https://www.java.com/) (for building and running)


Building
--------

  1.  Run `gradle build` from the project root.
  2.  Execute the Java executable JAR in `build/libs`.
  

System Properties
-----------------

The application requires the following system properties to be set on the 
Java VM before executing:

  - `edu.umbc.is.ta.userKey`: a user's key 
  - `edu.umbc.is.ta.userSecret`: a user's secret key 
  - `edu.umbc.is.ta.appKey`: an application key 
  - `edu.umbc.is.ta.appSecret`: an application secret

For example:

```
java -Dedu.umbc.is.ta.userKey=XXX -Dedu.umbc.is.ta.userSecret=XXX -Dedu.umbc.is.ta.appKey=XXX -Dedu.umbc.is.ta.appSecret=XXX -jar twitter-analyzer-0.1.jar
```