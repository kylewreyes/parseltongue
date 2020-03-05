# cs0320 Term Project 2020

**Team Members:** 
Shalin Patel, Kyle Reyes, Derick Toth, Nick Young

**Team Strengths and Weaknesses:**
Shalin
- S: Algorithms, Debugging, Data Science, Architecture
- W: Front-end, Style
- Stack: Python, R, C++, SQL, Mathematica, Java
Kyle
- S: Algorithms, Testing, Project/Team Management
- W: Front-end, Optimization
- Stack: Java, Racket, Pyret
Derick
- S:  Visual Art, Optimization, Implementing Functionality, Code Style
- W: Front-end, Project Structure, Testing
- Stack: Java, Python, Pyret, Racket, SQL, C++, MATLAB
Nick
- S: Front-end, Design, Project Management
- W: Algorithms, OOP, Optimization, Memory management
- Stack: Java, Javascript, HTML, CSS, React, React Native, Python, MATLAB, MongoDB, Node.js, Flutter, Dart
TEAM
- S: Algorithms, Architecting, Managing teams and projects
- W: Front-end, Style
- OVERALL: Balanced!

**Project Ideas:**
### Idea 1 - Election/Voting Tool
The Problem
- Tallying votes in a political election securely with active protection mechanisms is nearly impossible, and physical ballots often introduce a layer of error. A secure mechanism to gather and count ballots in an election could help prevent fraud and speed up the democratic process.
Our Solution
- An open-source application integrating a paper ballot reader, with analysis for detecting fraudulent activity.
Critical Features
- A way to register new elections and generate ballots based on candidates and proposals
- A tool to read in ballot data reliably and cost-ebased on photo of ballot to integrate a paper trail into the election.
- Keep backups of original ballot and metadata associated with the ballot like time submitted, what precinct.
- Fast, intuitive visualization of election results. Live Results?
- Integrated sharing of results to social media sites/news networks.
- Security around scanning in ballots to prevent bad actors from adding in falsified or modified ballots.
- Analysis of ballot metadata to provide an automated fraud checking mechanism.
Challenges
- Reliable reading through computer vision based on picture of ballot
- Data analysis and developing a useful fraud metric
- Visualization of Data

Rejected - Not enough "good" data and if there were enough data we could just throw an ML or CV algorithm at this

### Idea 2 - Automated Travel Planner
The Problem
- Planning a fun and interesting trip within a certain budget effectively can be difficult in a busy lifestyle. Moreover, many of the parts of booking are tedious and difficult to keep track of.
Our Solution
- A web application that creates a detailed planner/itinerary for a certain area given a few keywords.
Critical Features
- The web application takes in a certain region (i.e. city) and timeframe
- It also takes in various constraints, such as budget, modes of transportations, hours of operations, specific locations. This will allow for a realistic planner that can be used by the tourist.
- Weighs specific interests higher than others (e.g. historic sites, food, nature, etc.) in order to provide a more customized experience compared to other existing solutions
- Outputs a day-by-day itinerary
- Minimizes travel time and other cost metrics automatically
- Displays an interactive map for ease of use
Challenges
- Implementing a generic approximate Traveling Salesman solution that is robust under user input
- Where to find all the data and how to consolidate them
- Dealing with each restriction given by the user
- Scraping data to match with user inputs

Approved - algorithm for computing "score" of trip and ranking should be the main component. 

### Idea 3 - Parseltongue (Efficient Academic Paper Parsing)
The Problem
- Research is difficult, and having to read through pages of repetitive content is a waste of time and resources. Especially considering that the most “useful” part of any given paper is generally buried amidst a mass of context and other unrelated content, it would be helpful to be able to parse and filter papers for their most salient content.
Our Solution
- Parseltongue is a paper parser that, when given a list of key words to search for, highlights the portions of a grouping of papers that are most relevant to those topics.
Critical Features
- Uploading papers for parsing.
- View uploaded paper with annotations for the most relevant information.
- Maybe an automated abstract generator, based on the content needed.
- Page-rank type algorithm for determining importance
Challenges
- Natural Language Processing?
- Dealing with non-parsable documents.
- Creating an intuitive and beautiful UI.

Approved - lots of interesting parsing algorithms. Should avoid just throwing an ML algorithm at a large dataset

Note: You do not need to resubmit your final project ideas.


**Mentor TA:** _Put your mentor TA's name and email here once you're assigned one!_

## Meetings
_On your first meeting with your mentor TA, you should plan dates for at least the following meetings:_

**Specs, Mockup, and Design Meeting:** _(Schedule for on or before March 13)_

**4-Way Checkpoint:** _(Schedule for on or before April 23)_

**Adversary Checkpoint:** _(Schedule for on or before April 29 once you are assigned an adversary TA)_

## How to Build and Run
_A necessary part of any README!_
