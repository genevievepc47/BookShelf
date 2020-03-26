package edu.temple.bookshelf;

public class book {


        public String bookName;
        public String bookAuthor;

        public book(String bookName, String bookAuthor) {
            this.bookName = bookName;
            this.bookAuthor = bookAuthor;
        }

    //retrieve book's name
    public String getBookName(){
        return bookName;
    }

    //retrieve books' author
    public String getBookAuthor(){
        return bookAuthor;
    }
    }

