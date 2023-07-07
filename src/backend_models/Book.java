/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend_models;

import java.awt.Image;

/**
 *
 * @author jenni
 */
public class Book {

    private String title;
    private String author;
    private String[] genres;
    private String[] reviews;
    private String description;
    private String rating;
    private String price;
    private Image cover;
    private String selfLink;
    private String userNotes;
    private boolean saved;
    private int index;
    private double userRating;
    private String isbn;
    private String coverLink;
    private String publisher;
    
    public Book() {
        this.index = 0;
        this.title = "";
        this.author = "";
        this.genres = new String[10];
        this.reviews = new String[10];
        this.description = "";
        this.rating = "";
        this.price = "";
        this.cover = null;
        this.selfLink = "";
        this.userNotes = "";
        this.saved = false;
        this.userRating = 0;
        this.isbn = "";
        this.coverLink = "";
        this.publisher = "";
    }

    //SETTERS
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }

    public void setDescription(String summary) {
        this.description = summary;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCover(Image image) {
        this.cover = image;
    }

    public void setLink(String link) {
        this.selfLink = link;
    }

    public void setUserNotes(String notes) {
        this.userNotes = notes;
    }

    public void setSaveStatus(boolean saved) {
        this.saved = saved;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setUserRating(double rating) {
        this.userRating = rating;
    }
    
    public void setISBN(String isbn){
        this.isbn = isbn;
    }
    
    public void setCoverLink(String link){
        this.coverLink = link;
    }
    
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    //GETTERS

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String[] getGenres() {
        return this.genres;
    }

    public String[] getReviews() {
        return this.reviews;
    }

    public String getDescription() {
        return this.description;
    }

    public String getRating() {
        return this.rating;
    }

    public String getPrice() {
        return this.price;
    }

    public Image getCover() {
        return this.cover;
    }

    public String getLink() {
        return this.selfLink;
    }

    public String getUserNotes() {
        return this.userNotes;
    }

    public boolean getSaveStatus() {
        return this.saved;
    }

    public int getIndex() {
        return this.index;
    }
    
    public double getUserRating(){
        return this.userRating;
    }
    
    public String getISBN(){
        return this.isbn;
    }
    
    public String getCoverLink(){
        return this.coverLink;
    }
    
    public String getPublisher(){
        return this.publisher;
    }
}
