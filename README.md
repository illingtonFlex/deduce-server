# deduce-server

Deduce-server is a server-side implementation of a ruleset taken from a vintage board game, based on solving a secret
word puzzle through deductive reasoning. Implemented as a RESTful API, the application is intended to be used as an 
educational tool, enabling players to implement client-side code to either solve the puzzles programmatically, or to 
create a UI enabling interactive play by a human.

## Getting Started


### Runtime Environment

In order to build and run the deduce-server application, you will need the following:

* Maven 3
* Java 8
* MongoDB 3.4
* (optional) Vagrant 2.0.1
* (optional) VirtualBox 5.2.2

### Build & Launch

Build Command:      

    $ mvn package

Before you can run the application, you will need to launch MongoDB configured to listen on the port specified in applications.properties, which is 27018 by default. You will also need to load the database with some reference data. There is a Vagrant configuration available to set this up for you. To use Vagrant, you will first need to install Vagrant and VirtualBox. Once those dependencies are installed, execute the following commands from the project root.

Launch Vagrant environment: 

    $ vagrant up

Log into Vagrant VM:

    $ vagrant ssh

Load reference data:

    $ cd /vagrant/src/main/resources
    
    $ mongoimport --db test --collection deduceWordsList --file deduceWordlist.json --drop --stopOnError

    $ exit

Run Application:

The Maven build should have generated an executable jar in the /target subdirectory. You can execute this jar to start the server:

    $ ./target/deduce-server-{version}.jar

Once this command is executed, you should see terminal output for the Spring Boot process. Once the server has started, it will be available on your local host, listening to port 8080. You can check the status of the server by pointing your web browser to http://localhost:8080/system/health

## Game Rules

A Deduce match begins with the selection of a secret word. The player's task is to deduce the word by asking questions. 
Secret words must adhere to a set of criteria. Each word must be exactly five letters long, and contain no repeated 
letters. For the sake of this project, valid words are taken from the official ENABLE Word List project, used by such 
prominent word games as Scrabble and Words With Friends.

Once a secret word is selected, the letters composing the word are removed from the alphabet, creating a subset. The 
player then asks which letters remain in the subset one at a time by naming indexes. For example, "what is the letter at
 index 0?" would be a valid question. If the answer to that question is "A" then the player knows that the letter A is 
 not used in the secret word. If the answer to that question is "C" then the player may deduce that both letters A and B
  are in the secret word, and may begin solving the puzzle by asking further questions and thinking through anagrams of 
  known letters. The challenge is to solve the puzzle as quickly as possible. A player may only attempt one solution per 
  turn, an incorrect solution must be followed by an index inquiry prior to a subsequent solution attempt.

## Endpoints

All deduce-server endpoints return data as JSON. Response bodies represent a response entity wrapper, 
containing status and message values for indicating successful or erroneous invocations, along with an entity. The entity 
will vary based on the endpoint. The one exception is the listValidWords endpoint, which returns only a JSON list of
words.

- **POST** /deduceMatch/createMatch
    
    Creates a new match, returning the match details, including match id, which will be used for subsequent operations.
    
    Example response:

        Content-Length →246
        Content-Type →application/json
        Date →Thu, 08 Sep 2016 02:46:37 GMT
        Location →http://localhost:8080/57d0d10d85770e0319119843/details
        
        {
          "status": "CREATED",
          "message": "Match created: http://localhost:8080/57d0d10d85770e0319119843/details",
          "entity": {
            "id": "57d0d10d85770e0319119843",
            "solution": "[UNSOLVED]",
            "isSolved": false,
            "events": []
          }
        }
        
        
- **GET** /deduceMatch/{match_id}/details

    Returns details for the specified match.
    
    Example response:
    
        Content-Length →179
        Content-Type →application/json
        Date →Thu, 08 Sep 2016 02:50:35 GMT
        
        {
          "status": "OK",
          "message": "Match found",
          "entity": {
            "id": "57d0d10d85770e0319119843",
            "solution": "[UNSOLVED]",
            "isSolved": false,
            "events": []
          }
        }
        
        
- **GET** /deduceMatch/{match_id}/letterAtIndex/{index}

    Returns the letter at specified index within the alphabet subset for the specified match. This also adds an event 
    to the match details.
    
    Example response:
    
        Content-Length →64
        Content-Type →application/json
        Date →Thu, 08 Sep 2016 03:00:23 GMT
        
        {
          "status": "OK",
          "message": "Success",
          "entity": "P"
        }
        

- **PUT** /deduceMatch/{match_id}/solve/{solution}

    Returns match details, which will indicate successful or unsuccessful solution attempt. This also adds an event to
    the match deetails.
    
    Example response:
    
        Content-Length →437
        Content-Type →application/json
        Date →Thu, 08 Sep 2016 03:11:42 GMT
        
        {
          "status": "OK",
          "message": "Solution Incorrect",
          "entity": {
            "id": "57d0d10d85770e0319119843",
            "solution": "[UNSOLVED]",
            "isSolved": false,
            "events": [
              {
                "eventName": "LETTER_AT_INDEX",
                "details": "index: 12 - letter: P",
                "date": "Wed Sep 07 22:00:23 CDT 2016"
              },
              {
                "eventName": "SOLUTION_ATTEMPT",
                "details": "BLUES",
                "date": "Wed Sep 07 22:11:42 CDT 2016"
              }
            ]
          }
        }

- **GET** /listValidWords

    Returns a list of all valid words fitting the criteria for consideration.

    Example response:

        Content-Type →application/json
        Date →Thu, 08 Sep 2016 03:15:11 GMT
        Transfer-Encoding →chunked
        
        {
          "status": "OK",
          "message": "SUCCESS: Words list found.",
          "entity": {
            "id": "581932f909db5a75a65a814e",
            "words": [
              "DUSKY",
              "HOCUS",
              "QUICK",
              "GRANT",
              "OCTAD",
          ...
        }