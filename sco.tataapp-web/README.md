# sco.tataapp-web

Service backend, REST APIs and administration web console.

# Usage

## Requiriments
To compile server stuff you need:
* Apache Maven 3.x
* Bower

## Instructions
1. First you need a google calendar application to use the google calendar APIs
    * Go to [google calendar console](https://console.developers.google.com)
    * Create credential for a Oauth client ID of type `Web Application`
    * Put as Authorized redirect URIs `<APPLICATION_URL>/google-auth`
    * Download credentials as `client_secret.json` and put it in `src/main/resources`
2. Configure the application
    * Go to `src/main/resources` and configure `application.properties`, `settings.yml`,`users.yml`
3. Compile and package the application
    * Go to `src/main/resources/public` and run command `bower install` to download web console dependencies
    * Open a console to `sco.tataapp-web` folder and run command `mvn clean package -Dmaven.test.skip=true`
4. Run application
    * Run command `java -jar <APP_FOLDER}>/target/tataapp.web.jar --spring.profiles.active=sec,prod --server.contextPath=/<APPLICATION_CONTEXT_PATH> --server.port=<PORT>`

## Note
Application will store google credential in folder `<USER_HOME>/.tataapp-credential`
