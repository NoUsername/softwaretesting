# Testproject


## Example1:

Class that is hard to test as it is.

> Refactor it + write tests.

## Example2:

Spring-Application + some different kinds of integration tests.



## "Main" Webapp Demoproject:

To run it run this in a shell:

    docker-compose up
    
Then run the "Main" class of the Java-Project from your IDE or run the following in a shell:

    gradlew bootRun

## Example3:

End-to-end tests for the main webapp project.

> Refactor the tests so that they are more reusable/extensible.


## Further:

## Slower tests:

    gradlew slowTests

### UiTests

    # start the server:
    gradlew bootRun
    # in another shell:
    gradlew endToEndTests

### Branches:

	master     "main/base" version
	bug        introduces a bug in the webapp (to show jenkins automated ui-tests)
	junit5     tried to port all tests to junit5
	example1   one example of how you could refactor the PrintMonitor demo + add some tests
