# README

## ParselTongue

Welcome to the ParselTongue project of Derick Toth, Shalin Patel, Kyle Reyes, and Nick Young! We hope you enjoy your stay.

## Getting Started

To build ParselTongue, first confirm that you have a compatible version of Java (Java 11+), and a compatible version of Maven (Maven 3). Next, navigate to the root directory and run `mvn package`. This will compile the code into an executable file, which you can run from the command line. To run Maps, navigate to the root directory and run `./run`. This will start a CLI implementation of ParselTongue, which supports the following commands:

- `parsel <list of /path/to/pdfs> <list of keywords>` which will output the snippets extracted from the pdfs and display them in decreasing score as determined by our algorithm.

The solution is deployed at https://parseltongue-cs32.herokuapp.com/ as well. Note that both a local deployment and the heroku deployment will access the same DB.

## Known bugs

At this time, no bugs are known.

## Design details

### Similarity Metrics and Keyword Extraction

### PageRank

The Page Rank algorithm that was used in this project is a modified version of that used in the original paper from Stanford. Indeed, our version is a modification that takes edge weighting into account. Mathematically, the original algorithm assumed that moving from one page to any of the adjoining pages was a uniform distribution. We have modified that to be weighted on the edge weights for a given node. Hence, the same properties hold as the original algorithm, but we are able to reward a tight connection between two passages.

The algorithm is defined generically on our `Graph<V, E, T>` abstraction as it can work for any graph type as long as they adhere to the interface methods. Additionally, we abstracted the idea of PageRank and have made it adaptable for change by introducing the `Rankable` interface that allows for quick code modification whereby people can use algorithms other than PageRank to determine the importance of nodes in a graph. One such algorithm could be an Ordinary Differential Equations method that has also shown good results.

Internally, the implementation of PageRank utilizes an iterative method of updating rank scores as we have defined our graph class to have adjacency lists rather than an adjacency matrix. The reason for this is two-fold. Firstly, since we are dealing with sparse matrices, the adjacency list implementation is  faster. The second reason is that this makes the program more adaptable to loading data in a lazy format for massive datasets and for live analysis. 

### ParselGraph

Our `Graph<V, E, T>` implementation that is the core of the project is the `RankGraph = Graph<RankVertex, RankEdge, RankMetadata>` type. This type has useful a useful internal implementation that allows it to be efficient with the program we are implementing. The first optimization is that we lazily add in the edges for the graph rather than at construction. This means that a user can requery a set of documents more efficiently as they dont need to parse the PDFs over and over again. 

Other optimizations include creating an `inboundMap` private instance variable that allows us to cache the edges that are inbound on a vertex rather than redetermining them each time. This is done on construction of the edges and reduces the runtime complexity of the PageRank algorithm as  a call to the cache is in O(1).

Finally, the entirety of our graph implementation implements `Serializable` for the purpose of resuse. In the database, we could have just stored the Snippets, but we realized that storing the entire graph to the MongoDB instance was valuable as it would allow the user to quickly requery a set of PDFs and would save us time in generating configuration files for a given query for reconstruction later.

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

JUnit tests were written to consider all edge cases for each class written. For more detail on JUnit tests, see inline comments. Do note that many test cases for larger components are not possible as the output is non-deterministic in some cases due to how the PDF reader may react from PDF to PDF. Specifically, we cannot predict how Apache PDFBox would react to any give PDF and would only be able to use its output to test itself which defeats the purpose of testing. All other components of the program have been tested.

System tests were written to consider a variety of input, both well formed and malformed, as well as a variety of interesting edge cases. Note that there is no way to test for correctness of a given output, but we can consider its properties. A (non-exhaustive) list per command is as follows:

- `parsel`: 


## Checkstyle

There are no outstanding Checkstyle errors.

## Development Notes

### Division of Labour

- Nick: Front-End and Database integration
- Kyle: PDF parsing and parts of parselgraph
- Derick: Similarity metrics and keyword extraction
- Shalin: PageRank and parselgraph

### Problems we Faced
A few problems we first faced were in integrating the various parts of our product. There were a lot of threads involved and, thus, integrating them took a bit of work as they were each very different in function. Furthermore, it took some time to callibrate  the various constants and design choices we made to ensure that we got meaningful results from the program.

The final problem we really faced was creating testing for the project that would be meaningful and test its boundaries as the overall program creates very subjective results. We did manage to break it down and focus on testable sections to achieve the level of confidence we wanted in the product.
