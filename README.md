# CS30CapstoneProject

## Introduction

Many existing book search programs such as Goodreads cannot provide a thorough book overview. Oftentimes, readers need to do multiple specific searches to piece together all the fragmented information about the book’s different attributes. This can be a very tedious process. The objective of the Book Searcher is to create an all-in-one program that provides information including title, author, price, rating, summary, etc, with an option for users to save books, write notes, and give books a rating. Additionally, it will recommend books based on the user’s book preferences. 

## Methodology
### User Interface
- Built using GridBag and Java Swing
elements
- Incorporates two display panes: main
display with search interface and user
library; secondary display with detailed
information of individual books.
### Book search system
- Connect to the Google Books API
- Using Gson library, parse API Json output
and obtain book titles. Display titles in the
book list.
- To speed up the initial search process, the
specific data such as author, price, rating,
and book cover is only obtained when the
user clicks “view selected book info”. A
URL specific to the book selected is called.
### Book recommendation system
- The Books.csv and the Ratings.csv datasets
contain data including book titles, ISBNs,
publishers, user IDs, user ratings of
various books, and more
- In Java, store user data into another csv
file
- Using a python script, combine the Books
and Ratings datasets as well as user data.
Then create recommendations by
comparing other people’s ratings with the
user ratings using a cosine similarity
calculation.
- Run the python script in Java using the Jep
(Java embedded python) library.

## Results
### Search Interface
<img width="629" alt="Screenshot of search interface" src="https://github.com/jen051/CS30CapstoneProject/assets/108763710/0b88241c-1d7a-4261-913e-e3666c17c8cb">

- User input
area
- Output
book list
- Search
history
function
- View Book
Info button
### User Library Interface
<img width="657" alt="Screenshot of user library interface" src="https://github.com/jen051/CS30CapstoneProject/assets/108763710/5eb9968e-7c37-473d-af1d-aaf8cff2ce70">

- Display
book cover
and title
- Click on a
book to save
notes and
rating
- Sort list by
Date Added,
Author,
Title, or
Rating
- Option to
view book
info
### Book Info Interface
<img width="467" alt="Screenshot of book info interface" src="https://github.com/jen051/CS30CapstoneProject/assets/108763710/bf60f805-e4a5-423c-8066-ae5b1d95f8b3">

- Display book info
including book title,
summary, author,
price, rating, and
book cover.
- Option for user to add
the book to their
bookshelf.
### Example Book Recommendation
<img width="594" alt="Screenshot of an example book recommendation" src="https://github.com/jen051/CS30CapstoneProject/assets/108763710/f5a85ea5-09be-4e3f-95f7-d79500ee720d">

- Recommends 5 books
that the user
might also
like based
on the
books saved
in their
library and
their ratings
for those
books.

## Future Improvements
- Better search algorithm/better book search
API. Current searches can be quite inaccurate.
For example, when an author’s name is
entered, the search results mainly consists of
works done on the author, such as
biographies, rather than the author’s works.
- Implement precise content-based search,
potentially using NLP or book content
datasets.
- Better book recommendation system. Rather
than a user rating-based recommendation
system, a book content-based system may be
more accurate.
- Code structure improvements. Since the book
recommendation functionality was
implemented near the end of the
development process, the code is in a
separate tester class. The code should be in
the MainController class.
- Quality of life improvements such as adding
books to library without having to go to the
detailed info page
- Option to remove books from library

## Acknowledgments
- Datasets used:
https://www.kaggle.com/datasets/arashnic/bookrecommendation-dataset
- Book recommendation python script adapted from:
https://www.kaggle.com/code/hilalmleykeyuksel/bookrecommender/notebook
