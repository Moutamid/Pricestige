package com.moutamid.pricestige.model;

public class BookmarkModel {
    String title, price, from, image;
    boolean book;

    public BookmarkModel(String title, String price, String from, String image, boolean book) {
        this.title = title;
        this.price = price;
        this.from = from;
        this.image = image;
        this.book = book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isBook() {
        return book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }
}
