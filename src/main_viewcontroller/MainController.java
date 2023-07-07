/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main_viewcontroller;

import apprunner.Constants;
import backend_models.BackendModelSetup;
import apprunner.TheApp;
import backend_models.Book;
import backend_models.SortByAuthor;
import backend_models.SortByDateAdded;
import backend_models.SortByTitle;
import backend_models.SortByUserRating;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author jenni
 */
public class MainController {

    BackendModelSetup theBackendModel;
    MainViewDisplay theMainViewDisplay;

    private class ViewBookAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource().equals(theMainViewDisplay.viewButton)) {
                BackendModelSetup.theBookID = theMainViewDisplay.bookList.getSelectedIndex();
                BackendModelSetup.bookIsSaved = false;
            } else if (ae.getSource().equals(theMainViewDisplay.viewInfoButton)) {
                BackendModelSetup.bookIsSaved = true;
            }
            TheApp.goToScreen(2);
        }
    }

    private class SearchAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String userInput = theMainViewDisplay.userInputArea.getText();
            search(userInput);
        }
    }

    private class SearchHistoryListner implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            Object history = theMainViewDisplay.searchHistory.getSelectedValue();
            if (history != null) {
                int index = theMainViewDisplay.searchHistory.getSelectedIndex();
                BackendModelSetup.theSearchHistory.remove(index);
                String historyStr = history.toString();
                theMainViewDisplay.userInputArea.setText(historyStr);
                search(historyStr);
            }
        }
    }

    private class SelectBookAction extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel selectedLabel = (JLabel) e.getSource();
            int selectedIndex = Integer.parseInt(selectedLabel.getName());

            if (selectedIndex < BackendModelSetup.theSavedBookID) {
                Book theSelectedBook = BackendModelSetup.savedBooks.get(selectedIndex);
                Image originalImage = theSelectedBook.getCover();
                BufferedImage img = new BufferedImage(originalImage.getWidth(selectedLabel), originalImage.getHeight(selectedLabel), BufferedImage.TYPE_INT_RGB);

                Graphics2D g = img.createGraphics();
                g.setColor(Constants.TRANSLUCENT_DARK_CYAN);
                g.setStroke(new BasicStroke(20.0f));
                Rectangle s = new Rectangle(0, 0, img.getWidth(), img.getHeight());
                g.drawImage(originalImage, 0, 0, null);
                g.fill(s);
                g.dispose();

                if (selectedIndex != BackendModelSetup.lastSelectedInShelf) {
                    selectedLabel.setIcon(new ImageIcon(img.getScaledInstance(Constants.BOOKSHELF_ICON_X, Constants.BOOKSHELF_ICON_Y, Image.SCALE_SMOOTH)));
                    theMainViewDisplay.notesArea.setText(theSelectedBook.getUserNotes());
                    theMainViewDisplay.ratingOptions.setSelectedIndex((int) (theSelectedBook.getUserRating() / 0.5));

                    //deselect the last selected book
                    if (BackendModelSetup.lastSelectedInShelf != -1) {
                        theMainViewDisplay.bookCovers.get(BackendModelSetup.lastSelectedInShelf).setIcon(new ImageIcon(BackendModelSetup.savedBooks.get(BackendModelSetup.lastSelectedInShelf).getCover().getScaledInstance(Constants.BOOKSHELF_ICON_X, Constants.BOOKSHELF_ICON_Y, Image.SCALE_SMOOTH)));
                    }
                    BackendModelSetup.lastSelectedInShelf = selectedIndex;
                } else if (selectedIndex == BackendModelSetup.lastSelectedInShelf) {
                    theMainViewDisplay.bookCovers.get(BackendModelSetup.lastSelectedInShelf).setIcon(new ImageIcon(originalImage.getScaledInstance(Constants.BOOKSHELF_ICON_X, Constants.BOOKSHELF_ICON_Y, Image.SCALE_SMOOTH)));
                    BackendModelSetup.lastSelectedInShelf = -1;
                    theMainViewDisplay.notesArea.setText("Select a book to write and save your notes!");
                    theMainViewDisplay.ratingOptions.setSelectedIndex(0);
                }
            }
        }
    }

    private class SaveNotesAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (BackendModelSetup.lastSelectedInShelf != -1) {
                Book theSelectedBook = BackendModelSetup.savedBooks.get(BackendModelSetup.lastSelectedInShelf);
                theSelectedBook.setUserNotes(theMainViewDisplay.notesArea.getText());
                theSelectedBook.setUserRating(theMainViewDisplay.ratings[theMainViewDisplay.ratingOptions.getSelectedIndex()]);
            } else {
                JOptionPane.showMessageDialog(theMainViewDisplay, "Please select a book first!");
            }
        }
    }

    private class SortListAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int selected = theMainViewDisplay.sortOptions.getSelectedIndex();
            switch (selected) {
                case 0 ->
                    sort(new SortByDateAdded());
                case 1 ->
                    sort(new SortByTitle());
                case 2 ->
                    sort(new SortByAuthor());
                case 3 ->
                    sort(new SortByUserRating());
            }
        }
    }

    private class PrevPageAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (theMainViewDisplay.currentPg > 0) {
                theMainViewDisplay.card.previous(theMainViewDisplay.bookshelf);
                theMainViewDisplay.currentPg--;

                String[] bookInfoArray = new String[BackendModelSetup.savedBooks.size()];
                for (int i = 0; i < BackendModelSetup.savedBooks.size(); i++) {
                    bookInfoArray[i] = BackendModelSetup.savedBooks.get(i).getTitle() + "         " + BackendModelSetup.savedBooks.get(i).getAuthor();
                }
                theMainViewDisplay.savedBookList.setListData(bookInfoArray);
                theMainViewDisplay.pages.get(theMainViewDisplay.currentPg).add(theMainViewDisplay.sortOptions, theBackendModel.setConstraints(8, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
                theMainViewDisplay.pages.get(theMainViewDisplay.currentPg).add(theMainViewDisplay.savedBookList, theBackendModel.setConstraints(8, 1, 1, 6, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0, new Insets(0, 30, 0, 0)));
                theMainViewDisplay.pages.get(theMainViewDisplay.currentPg).add(theMainViewDisplay.prevPageButton, theBackendModel.setConstraints(3, 7, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
                theMainViewDisplay.pages.get(theMainViewDisplay.currentPg).add(theMainViewDisplay.nextPageButton, theBackendModel.setConstraints(4, 7, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
            }

            System.out.println(theMainViewDisplay.currentPg);

        }
    }

    private class NextPageAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (theMainViewDisplay.currentPg < theMainViewDisplay.pages.size() - 1) {
                theMainViewDisplay.card.next(theMainViewDisplay.bookshelf);
                theMainViewDisplay.currentPg++;
                theMainViewDisplay.pages.get(theMainViewDisplay.currentPg).add(theMainViewDisplay.sortOptions, theBackendModel.setConstraints(8, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
                String[] bookInfoArray = new String[BackendModelSetup.savedBooks.size()];
                for (int i = 0; i < BackendModelSetup.savedBooks.size(); i++) {
                    bookInfoArray[i] = BackendModelSetup.savedBooks.get(i).getTitle() + "         " + BackendModelSetup.savedBooks.get(i).getAuthor();
                }
                theMainViewDisplay.savedBookList.setListData(bookInfoArray);
                theMainViewDisplay.pages.get(theMainViewDisplay.currentPg).add(theMainViewDisplay.savedBookList, theBackendModel.setConstraints(8, 1, 1, 6, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0, new Insets(0, 30, 0, 0)));
                theMainViewDisplay.pages.get(theMainViewDisplay.currentPg).add(theMainViewDisplay.prevPageButton, theBackendModel.setConstraints(3, 7, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
                theMainViewDisplay.pages.get(theMainViewDisplay.currentPg).add(theMainViewDisplay.nextPageButton, theBackendModel.setConstraints(4, 7, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
            }
            System.out.println(theMainViewDisplay.currentPg);
        }
    }

    private class GetRecsAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Object recs = theBackendModel.theTester.getUserChoices();
                String recStr = recs.toString().replace(", ", "\n").replace("[", "").replace("]", "");
                JOptionPane.showMessageDialog(theMainViewDisplay,  recStr,"You might like:\n",JOptionPane.PLAIN_MESSAGE,Constants.getBookIcon());
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public MainController(BackendModelSetup aBackend, MainViewDisplay aMainViewDisplay) {
        this.theBackendModel = aBackend;
        this.theMainViewDisplay = aMainViewDisplay;
        this.initController();
        InputMap im = theMainViewDisplay.userInputArea.getInputMap();
        ActionMap am = theMainViewDisplay.userInputArea.getActionMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), "search");
        am.put("search", new SearchAction());
    }

    private void initController() {
        this.theMainViewDisplay.viewButton.addActionListener(new ViewBookAction());
        this.theMainViewDisplay.searchButton.addActionListener(new SearchAction());
        this.theMainViewDisplay.searchHistory.addListSelectionListener(new SearchHistoryListner());
        this.theMainViewDisplay.bookCovers.forEach(j -> {
            j.addMouseListener(new SelectBookAction());
        });
        this.theMainViewDisplay.saveButton.addActionListener(new SaveNotesAction());
        this.theMainViewDisplay.viewInfoButton.addActionListener(new ViewBookAction());
        this.theMainViewDisplay.sortOptions.addActionListener(new SortListAction());
        this.theMainViewDisplay.prevPageButton.addActionListener(new PrevPageAction());
        this.theMainViewDisplay.nextPageButton.addActionListener(new NextPageAction());
        this.theMainViewDisplay.getRecsButton.addActionListener(new GetRecsAction());
    }

    private void search(String searchStr) {
        try {
            theBackendModel.theWebScraper.get(searchStr);
            BackendModelSetup.theSearchHistory.add(0, searchStr);
            String[] titles = new String[BackendModelSetup.searchResultBooks.length];
            for (int i = 0; i < theBackendModel.theWebScraper.index; i++) {
                titles[i] = BackendModelSetup.searchResultBooks[i].getTitle();

            }
            theMainViewDisplay.bookList.setListData(titles);
            theMainViewDisplay.searchHistory.setListData(BackendModelSetup.theSearchHistory.toArray());

        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sort(Comparator<Book> c) {
        Collections.sort(BackendModelSetup.savedBooks, c);
        String[] titlesArray = new String[BackendModelSetup.savedBooks.size()];

        for (int i = 0; i < BackendModelSetup.savedBooks.size(); i++) {
            theMainViewDisplay.bookCovers.get(i).setIcon(new ImageIcon(BackendModelSetup.savedBooks.get(i).getCover().getScaledInstance(Constants.BOOKSHELF_ICON_X, Constants.BOOKSHELF_ICON_Y, Image.SCALE_SMOOTH)));
            String title = BackendModelSetup.savedBooks.get(i).getTitle();
            titlesArray[i] = title + "         " + BackendModelSetup.savedBooks.get(i).getAuthor();

            if (title.length() > 15) {
                title = title.substring(0, 15) + "...";
            }
            theMainViewDisplay.bookTitles.get(i).setText(title);
        }
        theMainViewDisplay.savedBookList.setListData(titlesArray);
    }

}
