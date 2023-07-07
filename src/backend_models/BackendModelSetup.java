/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend_models;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author jenni
 */
public class BackendModelSetup {

    public static Book[] searchResultBooks = new Book[30];
    public static ArrayList<Book> savedBooks = new ArrayList<Book>();
    public static ArrayList<String> theSearchHistory = new ArrayList();
    public static Book[] theViewHistory = new Book[30];
    public WebScraper theWebScraper = new WebScraper();
    public Tester theTester = new Tester();
    public static int theBookID = 0;
    public static int theSavedBookID = 0;
    public static boolean bookIsSaved = false;
    public static int lastSelectedInShelf = -1;

    public BackendModelSetup() {

    }

    public GridBagConstraints setConstraints(int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int ipadx, int ipady, int anchor, int fill) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.weightx = weightx;
        c.weighty = weighty;
        c.ipadx = ipadx;
        c.ipady = ipady;
        c.anchor = anchor;
        c.fill = fill;
        return c;
    }

    public GridBagConstraints setConstraints(int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int ipadx, int ipady, int anchor, int fill, Insets inset) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.weightx = weightx;
        c.weighty = weighty;
        c.ipadx = ipadx;
        c.ipady = ipady;
        c.anchor = anchor;
        c.fill = fill;
        c.insets = inset;
        return c;
    }

    public void setColorAndFont(JComponent j, Font f, Color foreground, Color background) {
        j.setFont(f);
        j.setForeground(foreground);
        j.setBackground(background);
    }

    public void setColorAndFont(JComponent j, Font f, Color foreground) {
        j.setFont(f);
        j.setForeground(foreground);
    }

}
