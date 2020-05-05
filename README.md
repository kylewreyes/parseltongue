# README - ParselTongue
##Command Line Functionality
`parse  <any number of pdf file path> "<query>"`

example:

`parse test_pdf.pdf anothertest.pdf this/that/then.pdf "I have a very interesting question, here it is!"`



## Database
The database is implemented in MongoDB.

### Collections
The DB in question has three collections.

The `user` collection has two fields: `username` and `password`.

The `pdf` collection has five fields: `id`, `user`, `filename`, `query`, and `timestamp`.

The `snippet` collection has three fields: `pdf-id`, `score`, and `content`.