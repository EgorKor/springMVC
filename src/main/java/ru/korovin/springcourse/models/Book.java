package ru.korovin.springcourse.models;

public class Book {
    private int book_id;
    private String name;
    private String author;
    private int year;


    public Book(){

    }

    public Book(int book_id,String name, String author, int year){
        this.book_id = book_id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }


    @Override
    public String toString(){
        return String.format("%s, %s, %d",name,author,year);
    }
}
