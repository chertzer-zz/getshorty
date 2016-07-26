getshorty -- A URL shortener

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


  