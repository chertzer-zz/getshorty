# getshorty

## Usage

### Extract src
  
### Build
  ```mvn clean package -Dmaven.test.skip=true      // skip unit tests if desired
  mvn clean package
  ```

### Run
Run from a jar, specify optional port


  ```java  -jar target/getshorty-1.0-SNAPSHOT-jar-with-dependencies.jar org.chertzer.getshorty.App <port>```
  
### Manual tests using curl
Run server as above and use curl as follows
  
  ```curl -ivs --raw -X POST  -H "Content-Type: text/plain;" --data "http://goo  gle.com" http://localhost:8080/getshorty/link```
  
  ```curl -ivs --raw  -X POST -H "Content-Type: text/plain;" --data "http://google.com" http://localhost:8080/getshorty/link```
  
  ```curl -ivs --raw http://localhost:8080/getshorty/<value returned from POST>```
  