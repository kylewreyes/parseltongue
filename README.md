# README

## ParselTongue

Welcome to the ParselTongue project of Derick Toth, Shalin Patel, Kyle Reyes, and Nick Young! We hope you enjoy your stay.

## Getting Started

To build ParselTongue, first confirm that you have a compatible version of Java (Java 11+), and a compatible version of Maven (Maven 3). Next, navigate to the root directory and run `mvn package`. This will compile the code into an executable file, which you can run from the command line. To run Maps, navigate to the root directory and run `./run`. This will start a CLI implementation of ParselTongue, which supports the following commands:

- `mdb <path/to/database>`, TODO:

## Known bugs

At this time, no bugs are known.

## Design details

### PageRank

### PDF Parsing

### Database

The database is implemented in MongoDB. The DB in question has three collections.

The `user` collection has two fields: `username` and `password`.

The `pdf` collection has five fields: `id`, `user`, `filename`, `query`, and `timestamp`.

The `snippet` collection has three fields: `pdf-id`, `score`, and `content`.

### Front End

## Testing

### How to test

JUnit tests were written for every student-created file, excluding Main.java and Routes.java. These tests are run whenever mvn package is run, and ensure general code functionality. TODO:

Student-created system tests can be found in the `tests/student/parseltongue` directory. To run these tests, navigate to the root directory and run `[python3] ./cs32-test tests/student/timdb/*.test [-t <time::int>]`. Use python3 if your machine has issues handling python environments (like mine). Use the -t flag to specify the time at which tests should time out (recommended: 120). TODO:

### Testing details

JUnit tests were written to consider all edge cases for each class written. For more detail on JUnit tests, see inline comments.

System tests were written to consider a variety of input, both well formed and malformed, as well as a variety of interesting edge cases. A (non-exhaustive) list per command is as follows:

- `parse`: TODO:

## Checkstyle

There are no outstanding Checkstyle errors.

## Development Notes

### Division of Labour

### Problems we Faced
