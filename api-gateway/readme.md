## Aplication Gateway

This is a simple spring boot application using Netflix Zuul for API Gateway.
It has two parts-
* routing-and-filtering
* routing-and-filtering-gateway

The first application is simple book service which accepts request on **/available** and **/checked-out** and simply returns a string response.

The second application is an API Gateway which sends requests to the routing-and-filtering application on 8090.
Gateway listens on 8080.