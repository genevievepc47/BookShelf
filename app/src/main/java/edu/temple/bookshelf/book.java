package edu.temple.bookshelf;

import java.io.Serializable;

public class book implements Serializable {


        public String title;
        public String author;
        public int id;



    public String coverURL;

        public book(String bookName, String bookAuthor, int id, String coverURL) {
            this.title = bookName;
            this.author = bookAuthor;
            this.id = id;
            this.coverURL = coverURL;
        }

    //retrieve book's name
    public String getBookName(){
        return title;
    }

    //retrieve books' author
    public String getBookAuthor(){
        return author;
    }


    //retrieve books' id
    public int getbookId(){
        return id;
    }

    //retrieve books' coverURL
    public String getbookCoverURL(){
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }
}

