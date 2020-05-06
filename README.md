# README

## ParselTongue

Welcome to the ParselTongue project of Derick Toth, Shalin Patel, Kyle Reyes, and Nick Young! We hope you enjoy your stay.

## Getting Started

To build ParselTongue, first confirm that you have a compatible version of Java (Java 11+), and a compatible version of Maven (Maven 3). Next, navigate to the root directory and run `mvn package`. This will compile the code into an executable file, which you can run from the command line. To run Maps, navigate to the root directory and run `./run`. This will start a webserver alongside a CLI implementation of ParselTongue, which supports the following command:

- `parsel <list of /path/to/pdfs> <list of keywords>` which will output the snippets extracted from the PDFs and display them in decreasing score as determined by our algorithm.

The solution is deployed at https://parseltongue-cs32.herokuapp.com/ as well. Note that both a local deployment and the Heroku deployment will access the same database.

## Known bugs

The database is currently being connected through a hardcoded URI string. This presents a significant security problem, and we need to figure out a way to store this string in a keystore. However, due to time constraints, this wasn't possible.

Heroku has a local file size limit, which means that users cannot upload too many files at a time, or upload files that are too large. However, this limit is quite high, and should not be hit under most general use cases.

## Design details

### Similarity Metrics and Keyword Extraction

The primary goal in the creation of these systems was extensibility and the ability to replace strategies quickly. As these components deal with the semantic details of the text to generate edge weights, the problem essentially boils down to a natural language processing problem of determining relevance between two documents given a set of important keywords.

The `KeywordExtractor` Interface describes the contract that we expect any Extractor to use. The idea behind it is to generate a set of keywords with an associated weight which we can then use to generate feature vectors for each snippet by representing each keyword as a dimension in a vector with the value in that dimension represents that keywords occurance in a given document. It allows us to embed each document in a feature space defined by the keywords. The motivation behind using an extractor is that there may be semantically relevant keywords to the users query which are not included in the initial query but can be identified through analysis of the text.

The current iteration of the project uses the `StatisticalKeywordExtractor` to produce these keyword mappings. It relies heavily on the tfidf (text frequency, inverse document frequency) function to produce a weighting of initial keywords where they are filtered by a heuristic measure which cuts off a certain amount below the mean. The secondary keywords are extracted through a modified tfidf metric which identifies words that are semi-covariant with the primary keywords. This means that the secondary keywords are those which occur in equal parts with the primary keywords and without.

Eventually we plan to migrate the keyword extraction to a more NLP based approach as we feel that this would yield a more effective ranking but for the purposes of the demo this is unnecessary

The `RelevanceMetric` Interface takes in two feature embedded vectors and returns a value representing the two documents similarity to each other based on the primary keywords. We created two methods for this, one is the `CosineSimilarity` metric which uses vector operations to extract the angle between the vectors, and the other being `Jaccardish` which is inspired by the Jaccard Index. Both of these are relatively simple and simply convert the two feature vectors into a number which is higher the more similar the documents are. These values then populate the adjacency matrix of the snippet graph.

### PageRank

The Page Rank algorithm that was used in this project is a modified version of that used in the original paper from Stanford. Indeed, our version is a modification that takes edge weighting into account. Mathematically, the original algorithm assumed that moving from one page to any of the adjoining pages was a uniform distribution. We have modified that to be weighted on the edge weights for a given node. Hence, the same properties hold as the original algorithm, but we are able to reward a tight connection between two passages.

The algorithm is defined generically on our `Graph<V, E, T>` abstraction as it can work for any graph type as long as they adhere to the interface methods. Additionally, we abstracted the idea of PageRank and have made it adaptable for change by introducing the `Rankable` interface that allows for quick code modification whereby people can use algorithms other than PageRank to determine the importance of nodes in a graph. One such algorithm could be an Ordinary Differential Equations method that has also shown good results.

Internally, the implementation of PageRank utilizes an iterative method of updating rank scores as we have defined our graph class to have adjacency lists rather than an adjacency matrix. The reason for this is two-fold. Firstly, since we are dealing with sparse matrices, the adjacency list implementation is faster. The second reason is that this makes the program more adaptable to loading data in a lazy format for massive datasets and for live analysis.

### ParselGraph

Our `Graph<V, E, T>` implementation that is the core of the project is the `RankGraph = Graph<RankVertex, RankEdge, RankMetadata>` type. This type has useful a useful internal implementation that allows it to be efficient with the program we are implementing. The first optimization is that we lazily add in the edges for the graph rather than at construction. This means that a user can requery a set of documents more efficiently as they dont need to parse the PDFs over and over again.

Other optimizations include creating an `inboundMap` private instance variable that allows us to cache the edges that are inbound on a vertex rather than redetermining them each time. This is done on construction of the edges and reduces the runtime complexity of the PageRank algorithm as a call to the cache is in O(1).

Finally, the entirety of our graph implementation implements `Serializable` for the purpose of reuse. In the database, we could have just stored the Snippets, but we realized that storing the entire graph to the MongoDB instance was valuable as it would allow the user to quickly requery a set of PDFs and would save us time in generating configuration files for a given query for reconstruction later.

### PDF Parsing

PDF parsing is done with the help of Apache PDFBox, an open-source Java library that can extract text from PDFs. This tool takes care of acquiring every instance of text found in any given PDF. Using various heuristics, the `Snippet` class takes care of filtering out irrelevant information and splitting the text into various chunks. One limitation is that PDFBox only works on text-based PDFs. There is no guarantee that ParselTongue will recognize PDFs that contain images of text. 

We also created a `SourceParser` interface, in the event that we want to expand functionality to other data sources, such as Microsoft Word documents and HTML files.

### Database and Persistence

The database is implemented in MongoDB. The DB in question has three collections.

The `user` collection has two fields: `username` and `password`.

The `pdf` collection has five fields: `id`, `user`, `filename`, `query`, and `timestamp`.

The `snippet` collection has three fields: `pdf-id`, `score`, and `content`.

Database object schemas for each collection were used to simplify data transfer between the backend and the database.

Persistence is handled through a combination of the database's userdata storage and sessions, which keeps users logged in and stores some userdata in the browser.

## Testing

### How to test

JUnit tests were written for every student-created file, excluding Main.java and Routes.java. These tests are run whenever mvn package is run, and ensure general code functionality. TODO:

Student-created system tests can be found in the `tests/student/parseltongue` directory. To run these tests, navigate to the root directory and run `[python3] ./cs32-test tests/student/timdb/*.test [-t <time::int>]`. Use python3 if your machine has issues handling python environments (like mine). Use the -t flag to specify the time at which tests should time out (recommended: 120). TODO:

### Testing details

JUnit tests were written to consider all edge cases for each class written. For more detail on JUnit tests, see inline comments. Do note that many test cases for larger components are not possible as the output is non-deterministic in some cases due to how the PDF reader may react from PDF to PDF. Specifically, we cannot predict how Apache PDFBox would react to any give PDF and would only be able to use its output to test itself which defeats the purpose of testing. All other components of the program have been tested.

System tests were written to consider a variety of input, both well formed and malformed, as well as a variety of interesting edge cases. Note that there is no way to test for correctness of a given output, but we can consider its properties. A (non-exhaustive) list per command is as follows:

- `parsel`: improper REPL input, improper input into parsel

## Checkstyle

There are no outstanding Checkstyle errors.

## Development Notes

### Division of Labour

- Nick: Front-End and Database integration.
- Kyle: PDF parsing and parts of ParselGraph.
- Derick: Similarity metrics and keyword extraction.
- Shalin: PageRank and ParselGraph.

### Problems we Faced

A few problems we first faced were in integrating the various parts of our product. There were a lot of threads involved and, thus, integrating them took a bit of work as they were each very different in function. Furthermore, it took some time to callibrate the various constants and design choices we made to ensure that we got meaningful results from the program.

The final problem we really faced was creating testing for the project that would be meaningful and test its boundaries as the overall program creates very subjective results. We did manage to break it down and focus on testable sections to achieve the level of confidence we wanted in the product.
