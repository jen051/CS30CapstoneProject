/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend_models;

import java.util.Comparator;

/**
 *
 * @author 1100020551
 */
public class SortByUserRating implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {

        return (int)(o2.getUserRating()- o1.getUserRating());
    }
}
