twitter-analyzer
================

Dependencies
------------

  - [Gradle](https://gradle.org/) (for building from source)
  - [Java](https://www.java.com/) (for building and running)


Running
-------

  1.  Run `gradle distZip` to build the distribution ZIP.  This includes all 
  	  scripts necessary and dependencies necessary.
  	  
  2.  Unpackage the ZIP file.
  
  3.  Set system properties by using `set TWITTER_ANALYZER_OPTS=`, and the list 
  	  of system properties to set.  (See section below for full listing of 
  	  required system properties.)
  	  
  	  For example: `set TWITTER_ANALYZER_OPTS=-Dedu.umbc.is.ta.collection=TestCollection ...`.
  	  
  4.  Run the `twitter-analyzer` script for your OS, passing in the values to 
      filter on as parameters.
      
      For example: `bin\twitter-analyzer.bat "@umbc" "#retrievers"`.
      
Verbose output is logged to the console.  Troubleshooting output is logged 
to `logs\err.log`.  
  

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

When using the streaming API, the following system properties are also 
expected:

  - `edu.umbc.is.ta.numThreads`: the number of threads to use to listen for 
  	Tweets.
  - `edu.umbc.is.ta.collection`: the name of the MongoDB collection to save 
  	Tweets to.
  	