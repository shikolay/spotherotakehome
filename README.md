# SpotHero Homework

Homework to build web service that gonna help query prices 
over time ranges in JSON file

## Getting Started
This is a maven project, so this 3 commands should get you up and running
(assuming you have maven(3.x.x) installed )
```
mvn install
mvn package
mvn exec:java
```

## Using API

App expose only 3 endpoints 

`/application.wadl` - XML file describing functional endpoints in this app

###Rate endpoint
`/rate` main endpoint, let you query your time intervals. 
This endpoint accept 2 query params `from` and `to` which 
should be ISO 8601 instants in time 

(Accept HTTP header will determine mime type of response)
(If you omit query params, it's gonna fallback to current UTC time)
#### Example request
Simplest curl command
```
curl "http://localhost:8080/rate?from=2015-07-01T07:00:00Z&to=2015-07-01T12:00:00Z"
```
#### Example response
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><resultedRate><rate>1750</rate></resultedRate>
```


###Metrics endpoint
`/rate/metrics` will return accumulated performance stats on `/rate` endpoint


## Running the tests
```
mvn test
```

### Break down into end to end tests
End to end test live in same realm as all unit tests
Specific file called `RateServiceTest.java`
This test load mock data in DAO, fire up server and check proper response
In real prod application those test should live separately.

## Deployment
Ready to go docker file included, simplest way to see this app 
in container is to run this commands in same dir where Dockerfile is
```
docker build --tag=testrateapp .
docker run -p <PORT>:8080 -t -i testrateapp
```
will result in app running on PORT

