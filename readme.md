### Usage

- Run the application and go on http://localhost:8080/

### Build and run

#### Configurations
The template uses an H2 database for development and automatically configures a connection to a PostgreSQL database when deployed on Heroku.

#### Prerequisites

- Java 8
- Maven 3
- PostgreSQL
- Lombok IDE plugin

#### In IDE
This project was built using IntelliJ IDEA Community edition, which is free to download online. Many may find opening the project in IntelliJ to be the simplest method for building and running the application. 

**Note that you must download and install the IntelliJ Lombok plugin to run the app in the IDE.   

#### From terminal

Open a terminal window at the project's root directory, then type:

    $ mvn spring-boot:run

### Heroku Deployment

In project root directory:

    $ git init
    $ echo target > .gitignore
    $ git add .
    $ git commit -m"initial commit"
    $ heroku login
    $ heroku create
    $ heroku addons:create heroku-postgresql
    $ git push heroku master
    $ heroku ps:scale web=1
    $ heroku open
