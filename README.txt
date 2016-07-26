getshorty -- A URL shortener

## Introduction
My goal was to work within the "few hours" timebox to write a service that is functionally the same as  https://goo.gl/ for creation and lookup+redirect for links. 

## System Requirements: 
-I used Maven 3.3.9/Oracle Java 1.8.0_31 on OS/X 

## Limitations/shortcuts:
-Though goo.gl does this, I did not attempt to have a concept of users or associate links with users (potential enhancement). Due to time constraints and maven headaches, I didn't track any information about the link. Info like the IP address and the time that the link was created would be nice to have. 
-Given the statement "in-memory storage is perfectly acceptable" and my assumption that this does not need to be HA/scalable across multiple servers, the service only uses a Java collection for the URLStore. Using some sort of distributed store with a combination of in-memory caching for fast lookups and persistence for HA/horizontal scaling/etc. would be better. 
-Given the mention of "web framework",  I took the shortcut of using embedded Jetty/Jersey for REST APIs. REST APIs seem to serve this use case well. I used plain-text instead of JSON/XML for simplicity's sake since I didn't have much time and wasn't writing a client. 
-I know your team has some great stuff for tracking requests and monitoring Netflix API services, but sadly that's also potential enhancement for this service. 
-"Vanity plate"/custom short URLs are an obvious, useful extension. Doing something that Rick Rolls the client before it redirects to the real URL has probably already been done. 
-Additional comments are in the code.

## Usage

    ### Extract src
  
    ### Build

    mvn clean package // does it all!!

    mvn clean package -Dmaven.test.skip=true      // skip unit tests if desired
  
  
    ### Run

    Run from a jar, specify optional port


      java  -jar target/getshorty-1.0-SNAPSHOT-jar-with-dependencies.jar org.chertzer.getshorty.App <port>
  
  
    ### Manual tests using curl

    Run server as above and use curl as follows
  

    1) Correct usage to create link:
    curl -ivs --raw  -X POST -H "Content-Type: text/plain;" --data "http://google.com" http://localhost:8080/getshorty/link
  
  
    Output
 
     Trying 127.0.0.1...
        * Connected to localhost (127.0.0.1) port 8082 (#0)
        > POST /getshorty/link HTTP/1.1
        > Host: localhost:8082
        > User-Agent: curl/7.43.0
        > Accept: */*
        > Content-Type: text/plain;
        > Content-Length: 17
        >
        * upload completely sent off: 17 out of 17 bytes
        < HTTP/1.1 200 OK
        HTTP/1.1 200 OK
        < Date: Tue, 26 Jul 2016 00:02:04 GMT
        Date: Tue, 26 Jul 2016 00:02:04 GMT
        < Content-Type: text/plain
        Content-Type: text/plain
        < Content-Length: 8
        Content-Length: 8
        < Server: Jetty(9.3.z-SNAPSHOT)
        Server: Jetty(9.3.z-SNAPSHOT)

        <
        * Connection #0 to host localhost left intact
    
     2) Correct usage to lookup link:
     curl -ivs --raw http://localhost:8080/getshorty/<value returned from POST>

    Output

       Trying 127.0.0.1...
       * Connected to localhost (127.0.0.1) port 8082 (#0)
       > GET /getshorty/JqkCtAxD HTTP/1.1
       > Host: localhost:8082
       > User-Agent: curl/7.43.0
       > Accept: */*
       >
       < HTTP/1.1 307 Temporary Redirect
       HTTP/1.1 307 Temporary Redirect
       < Date: Tue, 26 Jul 2016 00:02:35 GMT
       Date: Tue, 26 Jul 2016 00:02:35 GMT
       < Location: http://google.com
       Location: http://google.com
       < Content-Length: 0
       Content-Length: 0
       < Server: Jetty(9.3.z-SNAPSHOT)
       Server: Jetty(9.3.z-SNAPSHOT)

       <
       * Connection #0 to host localhost left intact
   
    3) curl -ivs --raw -X POST  -H "Content-Type: text/plain;" --data "http://goo  gle.com" http://localhost:8080/getshorty/link
  
    Output

      Trying 127.0.0.1...
      * Connected to localhost (127.0.0.1) port 8082 (#0)
      > POST /getshorty/link HTTP/1.1
      > Host: localhost:8082
      > User-Agent: curl/7.43.0
      > Accept: */*
      > Content-Type: text/plain;
      > Content-Length: 19
      >
      * upload completely sent off: 19 out of 19 bytes
      < HTTP/1.1 400 Bad Request
      HTTP/1.1 400 Bad Request
      < Date: Tue, 26 Jul 2016 00:01:29 GMT
      Date: Tue, 26 Jul 2016 00:01:29 GMT
      < Content-Type: text/plain
      Content-Type: text/plain
      < Content-Length: 12
      Content-Length: 12
      < Server: Jetty(9.3.z-SNAPSHOT)
      Server: Jetty(9.3.z-SNAPSHOT)

      <
      * Connection #0 to host localhost left intact


  
