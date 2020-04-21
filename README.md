# README - ParselTongue
##Command Line Functionality
`parse  <any number of pdf file path> "<query>"`

example:

`parse test_pdf.pdf anothertest.pdf this/that/then.pdf "I have a very interesting question, here it is!"`

## Database
In order for this to work locally, copy the `parseltongueTemplate.sqlite3` file and rename it to `parseltongue.sqlite3`. You need a database of this form for it to work.

### Tables
The DB in question has three tables.

The `user` table has two columns: `username` and `password`.

The `pdf` table has five columns: `id`, `user`, `filename`, `keywords`, and `timestamp`.

The `snippet` table has three columns: `pdf-id`, `score`, and `content`.