# README - ParselTongue
##Command Line Functionality
`parse  [any number of pdf file path] "[query]"`

example:

`parse test_pdf.pdf anothertest.pdf this/that/then.pdf "I have a very interesting question, here it is!"`

## Database
In order for this to work locally, rename the `parseltongueSKELETON.sqlite3` file to `parseltongue.sqlite3`. You need a database of this form for it to work.

### Tables
The DB in question has three tables.

The `user` table has two columns: `username` and `password`.

The `pdf` table has four columns: `id`, `user`, `filename`, and `timestamp`.

The `snippet` table has two columns: `pdf-id` and `content`.