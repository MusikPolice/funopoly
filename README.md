# funopoly
Monopoly is often accused of being a bad board game, with its detractors claiming that it isn't fun, takes too long to play, or is poorly designed. Fans of the game often counter by pointing out that most people who play Monopoly do not play by the official rules, instead cobbling together a game out of arcane house rules that ruin the balance of the game.

This project is a simple Monopoly implementation written in Kotlin that has the goal of determining which set of rules is objectively best. When complete, it will simulate hundreds of games of Monopoly with a configurable rule set and produce reports that compare the observed results of minor rule tweaks.

## Dependencies
To run funopoly, you'll need [Java 12 SE](https://www.oracle.com/ca-en/java/technologies/javase/jdk12-archive-downloads.html) or higher. To develop, you'll need [Open JDK 12](https://openjdk.java.net/projects/jdk/12/) or higher.

## Running the code
funopoly is a gradle-based project. After cloning the repository, you can build the code like this:
```bash
$ ./gradlew build
```
Once the build succeeds, you can run the test harness like this:
```bash
$ ./gradlew run
```
To change the ruleset, you can tweak the values in [application-monopoly.yaml](src/main/resources/application-monopoly.yaml).

## Useful Resources
* [The official Monopoly rules (hasbro.com)](https://www.hasbro.com/common/instruct/00009.pdf)
* [The Monopoly Wiki (monopoly.fandom.com)](https://monopoly.fandom.com/wiki/Main_Page)
* [Monopoly (Wikipedia)](https://en.wikipedia.org/wiki/Monopoly_(game))
