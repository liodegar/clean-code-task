# Clean Code Task

The purpose of this task is to refactor `de.com.lio.badcode.BadCode` class by using the software development best practices, refactoring techniques and Uncle Bob's recommendations.
I present the following two solutions:

## Utility class with pure functions approach.
This solution is coded in ` de.com.lio.cleancode.RefactoringOne ` class, and basically the refactoring is done by using an utility class with pure functions, i.e. methods without any side effects.

## New PageDataRenderer class approach.
This solution is coded in ` de.com.lio.cleancode.RefactoringTwo ` class, and it is based by introducing a new  `PageDataRenderer ` class that encapsulates the main logic, in conjunction with applying OOP refactoring techniques like "Introduce Parameter objects", "Extract method", "Move method", etc.
In this approach, methods change only the state of its owning object, i.e. only inside ` de.com.lio.cleancode.PageDataRenderer ` class.

## General considerations

- Consistency: both solutions ensure idempotence and consistency across different method invocations by effectively using defensive copies of parameters.

- Exception handling: all the errors and exceptions are gracefully managed in the solutions and wrapped into higher level exceptions.

- Documentation: all the core classes are intradocumented (Javadocs).

- No hard coded values. All the main constant values are defined in the `de.com.lio.domain.Constants` class.

- Test coverage: all the approaches, including the BadCode one, were tested to validate their proper functionality. 
The BadCode test case enabled us to capture the initial contract that the new refactored solutions must comply. 
The test coverage percentage achieved was 96 %.

- Legacy class: StringBuilder was used instead of the legacy StringBuffer class. StringBuffer is thread-safe but synchronization has a big 
negative impact on performance, even when using this legacy class from a single thread.

- Domain objects: The `de.com.lio.domain` package contains the domain objects with the basic required functionality.

## Stack
- Java 11
- Maven 3.3.9
- JUnit 5

## Getting Started

In order to run the test cases, you should only build it by executing the following command:

` mvn clean install`
 
## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

Although I have tried my best, this guide undoubtedly may contain omissions, inaccuracies
and mistakes. You can help me to improve it by sending your suggestions or questions to:

* **Liodegar Bracamonte** - *Initial work* - (liodegar@gmail.com)


## License

Apache License 2.0.

## Acknowledgments

* To the all open source software contributors.