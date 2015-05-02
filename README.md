## Synopsis

This little sample of code is made to show how it's simple to test ReST API (integration test) with [Rest-Assured](https://code.google.com/p/rest-assured/) compare to home-made code.

## Code Example

You will find a stupid ReST Service (http://localhost:9000/worlds) that pretend to create, get and destroy a world. This list of worlds is stored in a H2 database. Nothing terrible. I use Spring-Boot heavily to save time and code ;). It's very straightforward.

## Motivation

I have done this code to show how to test a ReST API and how is it simple with the right framework instead of home made code.

## Installation

`mvn clean install` to build the app and run the test.
`mvn spring-boot:run` to run the app and test it with postman or paw for exemple.

## API Reference

**Create a world :**

`POST http://localhost:9000/worlds
{
    "name": "Munster",
    "type": "PVP"
}
`

**Destroy a world :**

`DELETE http://localhost:9000/worlds/{id}
`

**Retrieve a world:**

`GET http://localhost:9000/worlds/{id}
`


## Tests

To run tests, just type `mvn test` to run the tests.
Easy, no ?

## Contributors

Please feel free to add some tests, add some code, I havn't yet done tests for POST and DELETE nor my home-made code is good enough. Some refactoring is needed ...


## License

			DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                   Version 2, December 2004
 
Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 
Everyone is permitted to copy and distribute verbatim or modified
copies of this license document, and changing it is allowed as long
as the name is changed.
 
           DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
  	TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 
 0. You just DO WHAT THE FUCK YOU WANT TO.
