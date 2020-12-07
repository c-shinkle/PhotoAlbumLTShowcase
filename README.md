# Photo Album Showcase

This is a demo project for Lean Technique's interview process. It is a Java CLI app built using Gradle. It will showcase
how to use Spring's WebClient to retrieve data from a REST API.

## Build and Run

This Java app is built using Gradle 6.7.1 and is run with Java 11. Information about installing gradle can be found
[here](https://gradle.org/install/). While I already had Gradle installed on my local machine, it appears that the
Gradle wrapper likes to install itself by just running the app. If you're feeling lucky, you may be able to install
everything by just running on of the commands listed below.

### Windows

To run the app on Windows, navigate to the app's top-level directory of the project and use this command:
```gradlew.bat run --args='albumId'``` where ```albumId``` is the integer used for the query parameter.

To run the tests, simply use: ```gradlew.bat test```.

### Unix

To run the app on Unix, navigate to the app's top-level directory of the project and use this command:
```./gradlew run --args='albumId'``` where ```albumId``` is the integer used for the query parameter.

To run the tests, simply use: ```./gradlew test```.

## BIG DISCLAIMER

I was able to test this project on my Windows 8.1 machine and my Ubuntu 20.04 laptop. However, I don't own a macOs
device, so your mileage may vary. In case you encounter issues, please refer to
[the docs](https://docs.gradle.org/current/userguide/userguide.html).

## Sources

* https://hellokoding.com/spring-webclient-tutorial-with-examples/
* https://www.developer.com/tech/article.php/10923_3669436_2/Test-Driving-a-Java-Command-Line-Application.htm
* https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html
* https://www.baeldung.com/spring-mocking-webclient

