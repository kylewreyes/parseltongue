# ParselTongue

## Specifications

### User Story/Motivation:

Motivation:
The motivation and user story for ParselTongue is that when a researcher wants to begin the research process, whether it be for English, history, or science, they often need to search through extremely long academic documents that contain extraneous information for their specific research question. This means wasting hours upon hours parsing irrelevant information to try to find the few specific sections relevant to their thesis.

Solution:
ParselTongue's essential function is to take PDFs and key search phrases, like a research/thesis question, and to produce a list of snippets sorted by their relevance to the input phrase. These would be presented with a flashcard-like interface from which the user can scroll through the results and figure out where to look in the PDF to kickstart the research process.

Overarching Design Goals:

- Accessibility
- Ease of Use
- Clarity
- Efficiency

### Requirements

As a group, we decided that our project, ParselTongue, should be able to process multiple large PDFs containing text and search for certain keywords and rank snippets based on relevance and utility to the user.

Our user interface needs to be simple, easy to use, and intuitive for new users, especially those who may not be accustomed to using technology. This is important to make sure that our product is accessible to all potential users. Research should not be limited to the cyber-fluent.

The first requirement that we have to meet is that the user should be able to upload PDF files in an easy to use, painless process. At this step, we will check to ensure that the PDFs are accessible to our program and are correctly formatted and contain parsable text for our algorithm to use. Achieving this is necessary to create an easy-to-use tool.

Additionally, When the user is creating their query, they should be given feedback on which keywords are going to be factored into generating the relevance metric based on how useful the word will be. This should be based on information like frequency of the word, and how it relates to the other words in the query. This relevance will be clearly communicated through the user through the UI either with color or intensity of highlight over the important keywords. This requirement goes towards all of our design goals.

The actual ranking algorithm needs to be efficient enough that it can take multiple large pdfs of approximately 300 pages and generate relevant snippets in around a minute. This works for our goal of increasing the time efficiency of the research process.

After this, our program needs to be able to output a list of snippets where they are sorted by relevance to the keywords in such a manner that the snippets provided contain genuinely useful information for the researcher to further investigate. Furthermore, these snippets should reference where they came from so the user can follow the snippet to investigate further. This goes towards the goal of Ease of Use and Clarity.

### Program Interaction

The user will be interacting with our program through a web GUI. When the user lands onto our webpage, they should be greeted by a clean and intuitive website. It will contain 2 main buttons. The first one will be a “Get Started” button that takes the user to the main functionality of the website. The second button will be a “Learn More” button, introducing the user to the tool and taking them to a guided tour of the program.

#### Getting Started:

After clicking the getting started button the user is taken to a loading screen where they can pass a link to a locally or remotely hosted PDF[s] to upload and a phrase of keywords to base a search off of. They will then submit that form and be taken to a loading page while the parsing algorithm does its magic.

The final page where all the information is loaded would then appear and would display snippets of text sorted by their relevance to the search parameters. Snippets include a block of text with words highlighted based on their relevance to the keywords. The cards include which keywords made the snipped a candidate for selection as well as which file and page the snippet came from so the user can follow up in more depth if the snippet requisites further investigation. If time permits, it would be good to have a link to that specific section so the user could jump immediately and begin reading the section in full.

#### Learn More:

This will link to a page that essentially acts like a read-me, displaying a page with text on how to use the program.

Most users will mostly have the same experience as this program focuses on a single function. The differences will stem from edge cases. In essence, the program will be able to handle unexpected inputs gracefully. For instance, the program should be able to recognize incorrect file types. There will be a nice error message that dictates what filetype is supported. When given a PDF, it should also be to recognize whether the PDF already contains searchable text. If the PDF does not, this will also display an error message. The user should be able to add/remove keywords and PDFs before clicking search. Finally, it should be able to handle keywords that have no matches with the given files by also displaying a message that says that there are no matches.

### Mockup [https://www.figma.com/file/kWwoXekP2h4ryIho9cW2kM/ParselTongue?node-id=0%3A1]

### Design Presentation [https://docs.google.com/presentation/d/1M9Zc7NcSX_NgOGlvQR2Yc7F1edQ-uUV4i0p1ZvVz0Og/edit#slide=id.p]

## Design Document

### Individual Components

There are a few components that we can break the program into. These components will be individually tackled by the team members so that they can be completed in parallel. The components are as follows:

- Front end and UX design: Nick
- PDF parser including sectioning and tagging various parts of the PDFs to be passed into the interpreter: Kyle
- Page Rank Algorithm and Difference Metrics: Shalin [https://www.geeksforgeeks.org/page-rank-algorithm-implementation/]
- Closeness Metrics for Passages: Derick

The front end entails creating the entirety of the website and understanding how to grab data from the Java backend and push it to the frontend using ajax and other packages. In addition, the front end will require design and implementation in HTML and CSS.

The UX design will require creating graphics and other sprites for use in the website such as the logo.

The PDF parser will entail grabbing the PDFs from the front end and sectioning them into small pieces that will then be able to be passed into the relevance determiners. In addition, this section of the code will require a lot of work on validating and formatting the data in a manner that will be understandable by the rest of the program (i.e. converting images to text).. Furthermore, this section will have to make sure that the PDFs that the user has inputted are actually usable.

The page rank algorithm will involve implementing a version of the standard page rank algorithm so that it can process data on a complete graph in a time-efficient manner. In addition, it should be fine-tuned so that it gives results that are actually useful.

The closeness metric needs to determine how relevant a page is to another page to be used in the page rank algorithm. In essence, this component will attempt to discover the weight of how closely connected two sections of text are based on word density and the coexpression of words throughout the text corpus.

### Testing Plan

The division of labor and components allow for individual testing of each component. Each component will be able to be instantiated with dummy test cases and dummy test classes. Our hope is that this component of the project can be completed around the time of April 10.

After this, we need to start integrating our code which will require broader system tests and larger JUnit tests. We hope that this component can be completed around April 15 or so.

One other large component of this project is figuring out how this project performs with real users who would be the target of our system. We want to target multiple types of users and hope to target people that are researchers across different disciplines. We would also like to target young users that are just learning how to research as well as seasoned researchers. Finally, we would also like to target users like college students that may use the program to enhance their readings. This will be difficult due to COVID-19 but we will try our best to get this done by April 22.

### Design Snapshot

As mentioned before there are going to be four major components. As such we need to negotiate three contracts (interfaces). The core of the project is the page rank algorithm which necessarily will have to interact with the PDF parser to get the actual data and also connect with the metric determiner. Finally, the page rank will have to pass data to the front end which will be negotiated through a contract.

The metric determiner also needs to connect to the PDF parser through a shared contract with the page rank algorithm as it needs to be able to get the salient data from the parsed data.

### Timeline

April 17 - Finish individual components listed in the previous slide
April 19 - Finish testing individual components
April 23 - Individual component meeting deadline; Start integration of each component
Passing PDFs from front-end (GUI) to PDF parser
Passing text from the parser to the PageRank algorithm
Integrating the closeness metric to the PageRank algorithm
April 27 - Finish integration and start system testing
April 28 - Finish system testing
April 29 - Adversary TA meeting; Make changes to program and start demo
May 2 - Finished with project; Have a working demo

### Expected Issues

We expect that there will be issues actually delivering relevant results. This is primarily dependent on our generated search metrics for the PageRank algorithm. In addition, delivering results within a reasonable amount of time might also be an issue. Once each part is complete, integrating the many facets of our program together will almost certainly be tough with all the different interfaces to be used. This project will also be more difficult as we will not be able to work together in the same physical location. This will be difficult with four people in different time zones.
