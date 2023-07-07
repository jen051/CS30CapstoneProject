/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main_viewcontroller;

/**
 *
 * @author jenni
 */
import backend_models.BackendModelSetup;
import apprunner.Constants;
import backend_models.Book;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import second_viewcontroller.SecondViewDisplay;

public class MainViewDisplay extends JFrame {

    JTabbedPane tabbedPane = new JTabbedPane();
    BackendModelSetup theBackendModel;

    //Search Panel
    JLabel userInputLabel;
    JTextArea userInputArea;
    JButton searchButton;
    JLabel bookListLabel;
    JList bookList;
    JScrollPane bookListScrollPane;
    JLabel searchHistoryLabel;
    JList searchHistory;
    JScrollPane searchHistoryScrollPane;
    JButton viewButton;
    
    //Bookshelf Panel
    JPanel page;
    JPanel bookshelf;
    CardLayout card;
    ArrayList<JPanel> pages = new ArrayList<>();
    ArrayList<JLabel> bookCovers;
    ArrayList<JLabel> bookTitles;
    JLabel bookshelfLabel;
    JLabel savedListLabel;
    JButton prevPageButton;
    JButton nextPageButton;
    JButton viewInfoButton;
    JButton saveButton;
    JTextArea notesArea;
    JList savedBookList;
    JComboBox<String> sortOptions;
    JLabel yourRatingLabel;
    JLabel yourNotesLabel;
    JComboBox<Double> ratingOptions;
    JButton getRecsButton;
    Double[] ratings = {0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5,6.0,6.5,7.0,7.5,8.0,8.5,9.0,9.5,10.0};
    String[] options = {"Sort by Date Added", "Sort by Title", "Sort by Author", "Sort by User Rating"};

    int currentPg = 0;

    public MainViewDisplay(BackendModelSetup aBackend, SecondViewDisplay aSecondView) {
        this.theBackendModel = aBackend;
        this.initComponents();
    }

    private void initComponents() {

        Container displayPane = this.getContentPane();

        displayPane.setLayout(new GridBagLayout());
        displayPane.setBackground(Constants.BROWN);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bookshelf = new JPanel(new CardLayout());
        page = new JPanel();
        bookCovers = new ArrayList<>();
        bookTitles = new ArrayList<>();
        sortOptions = new JComboBox<>(options);
        savedBookList = new JList();
        prevPageButton = new JButton();
        nextPageButton = new JButton();

        try {
            prevPageButton.setIcon(Constants.getLeftArrowIcon());
            nextPageButton.setIcon(Constants.getRightArrowIcon());
        } catch (IOException ex) {
            Logger.getLogger(MainViewDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        createSearchTab();
        createMyBooksTab(page);
        displayPane.add(tabbedPane);
        card = (CardLayout) bookshelf.getLayout();
        this.pack();
    }

    private void createMyBooksTab(JPanel panel) {
        panel.setLayout(new GridBagLayout());

        panel.setBackground(Constants.BROWN);

        int index = 0;
        for (int gridY = 1; gridY <= 5; gridY += 2) {
            for (int gridX = 0; gridX < 5; gridX++) {
                JLabel bookCoverLabel = new JLabel();
                bookCoverLabel.setName("" + index);
                bookCoverLabel.setPreferredSize(Constants.BOOKSHELF_ICON_SIZE);
                try {
                    bookCoverLabel.setIcon(Constants.getImageUnavailableIcon());
                } catch (IOException ex) {
                    Logger.getLogger(MainViewDisplay.class.getName()).log(Level.SEVERE, null, ex);
                }

                JLabel bookTitleLabel = new JLabel("");
                theBackendModel.setColorAndFont(bookTitleLabel, Constants.SANS_SERIF_PLAIN_12, Color.WHITE);

                bookCovers.add(bookCoverLabel);
                bookTitles.add(bookTitleLabel);

                panel.add(bookCoverLabel, theBackendModel.setConstraints(gridX, gridY, 1, 1, 0, 0, 20, 0, GridBagConstraints.FIRST_LINE_START, 0));
                panel.add(bookTitleLabel, theBackendModel.setConstraints(gridX, gridY + 1, 1, 1, 0, 0, 0, 30, GridBagConstraints.FIRST_LINE_START, 0));
                index++;
            }
        }

        panel.add(bookshelfLabel = new JLabel("Bookshelf"), theBackendModel.setConstraints(0, 0, 2, 1, 0, 0, 0, 20, GridBagConstraints.FIRST_LINE_START, 0));
        panel.add(viewInfoButton = new JButton("View Info"), theBackendModel.setConstraints(0, 7, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        panel.add(prevPageButton, theBackendModel.setConstraints(3, 7, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
        panel.add(nextPageButton, theBackendModel.setConstraints(4, 7, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
        panel.add(saveButton = new JButton("Save"), theBackendModel.setConstraints(6, 7, 2, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
        panel.add(savedBookList, theBackendModel.setConstraints(8, 1, 1, 6, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0, new Insets(0, 30, 0, 0)));
        panel.add(savedListLabel = new JLabel("Saved Books"), theBackendModel.setConstraints(8, 0, 1, 1, 0, 0, 0, 20, GridBagConstraints.FIRST_LINE_START, 0, new Insets(0, 30, 0, 0)));
        panel.add(notesArea = new JTextArea("Select a book to write and save your notes!"), theBackendModel.setConstraints(6, 1, 2, 5, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        panel.add(sortOptions, theBackendModel.setConstraints(8, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
        panel.add(yourRatingLabel = new JLabel("Your rating: "), theBackendModel.setConstraints(6, 6, 2, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        panel.add(ratingOptions = new JComboBox<>(ratings), theBackendModel.setConstraints(7, 6, 1, 1, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
        panel.add(yourNotesLabel = new JLabel("Your Notes"), theBackendModel.setConstraints(6, 0, 2, 1, 0, 0, 0, 20, GridBagConstraints.FIRST_LINE_START, 0));
//        panel.add(getRecsButton = new JButton("Get Recs"), theBackendModel.setConstraints(6, 0, 2, 1, 0, 0, 0, 20, GridBagConstraints.FIRST_LINE_START, 0));
        panel.add(getRecsButton= new JButton("Get Recs"), theBackendModel.setConstraints(8, 7, 1, 6, 0, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0, new Insets(0, 30, 0, 0)));

        theBackendModel.setColorAndFont(bookshelfLabel, Constants.SERIF_BOLD_18, Color.WHITE);
        theBackendModel.setColorAndFont(savedListLabel, Constants.SERIF_BOLD_18, Color.WHITE);
        theBackendModel.setColorAndFont(notesArea, Constants.SANS_SERIF_PLAIN_12, Color.BLACK, Constants.BEIGE);
        theBackendModel.setColorAndFont(savedBookList, Constants.SANS_SERIF_PLAIN_12, Color.BLACK, Constants.BEIGE);
        theBackendModel.setColorAndFont(viewInfoButton, Constants.SERIF_BOLD_16, Color.WHITE, Constants.BROWN);
        theBackendModel.setColorAndFont(prevPageButton, Constants.SERIF_BOLD_16, Color.WHITE, Constants.BROWN);
        theBackendModel.setColorAndFont(nextPageButton, Constants.SERIF_BOLD_16, Color.WHITE, Constants.BROWN);
        theBackendModel.setColorAndFont(saveButton, Constants.SERIF_BOLD_16, Color.WHITE, Constants.BROWN);
        theBackendModel.setColorAndFont(sortOptions, Constants.SANS_SERIF_PLAIN_12, Color.WHITE, Constants.BROWN);
        theBackendModel.setColorAndFont(yourRatingLabel, Constants.SERIF_BOLD_16, Color.WHITE);
        theBackendModel.setColorAndFont(ratingOptions, Constants.SANS_SERIF_PLAIN_12, Color.WHITE, Constants.BROWN);
        theBackendModel.setColorAndFont(yourNotesLabel, Constants.SERIF_BOLD_18, Color.WHITE);

        notesArea.setPreferredSize(new Dimension(200, 430));
        notesArea.setMinimumSize(new Dimension(200, 430));
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);

        savedBookList.setPreferredSize(new Dimension(250, 500));
        savedBookList.setMinimumSize(new Dimension(250, 500));

        panel.setPreferredSize(Constants.INITIAL_SIZE);



        bookshelf.add(panel);
        tabbedPane.addTab("Bookshelf", bookshelf);
        pages.add(panel);
    }

    private void createSearchTab() {
        JPanel search = new JPanel();
        search.setLayout(new GridBagLayout());
        search.setBackground(Constants.BROWN);
        userInputLabel = new JLabel("Enter search prompt: (author, title, etc)");

        userInputArea = new JTextArea();
        userInputArea.setPreferredSize(new Dimension(350, 30));
        userInputArea.setMinimumSize(new Dimension(350, 30));
        userInputArea.setLineWrap(true);

        searchButton = new JButton("Search");

        bookListLabel = new JLabel("Book List:");

        bookList = new JList();
        bookList.setFixedCellHeight(30);

        bookListScrollPane = new JScrollPane(bookList);
        bookListScrollPane.setAutoscrolls(true);
        bookListScrollPane.setVerticalScrollBar(new JScrollBar());
        bookListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bookListScrollPane.setPreferredSize(new Dimension(350, 450));

        searchHistoryLabel = new JLabel("Search History");

        searchHistory = new JList();
        searchHistory.setFixedCellHeight(30);

        searchHistoryScrollPane = new JScrollPane(searchHistory);
        searchHistoryScrollPane.setAutoscrolls(true);
        searchHistoryScrollPane.setVerticalScrollBar(new JScrollBar());
        searchHistoryScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        searchHistoryScrollPane.setPreferredSize(new Dimension(350, 450));

        viewButton = new JButton("View Selected Book Info");

        theBackendModel.setColorAndFont(userInputLabel, Constants.SERIF_BOLD_16, Color.WHITE);
        theBackendModel.setColorAndFont(userInputArea, Constants.SANS_SERIF_PLAIN_12, Color.BLACK, Constants.BEIGE);
        theBackendModel.setColorAndFont(searchButton, Constants.SERIF_BOLD_16, Color.WHITE, Constants.BROWN);
        theBackendModel.setColorAndFont(bookListLabel, Constants.SERIF_BOLD_16, Color.WHITE);
        theBackendModel.setColorAndFont(bookList, Constants.SANS_SERIF_PLAIN_12, Color.BLACK, Constants.BEIGE);
        theBackendModel.setColorAndFont(viewButton, Constants.SERIF_BOLD_16, Color.WHITE, Constants.BROWN);
        theBackendModel.setColorAndFont(searchHistory, Constants.SANS_SERIF_PLAIN_12, Color.BLACK, Constants.BEIGE);
        theBackendModel.setColorAndFont(searchHistoryLabel, Constants.SERIF_BOLD_16, Color.WHITE);

        search.add(userInputLabel, theBackendModel.setConstraints(0, 0, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        search.add(userInputArea, theBackendModel.setConstraints(0, 1, 2, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        search.add(searchButton, theBackendModel.setConstraints(1, 1, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_END, 0));
        search.add(bookListLabel, theBackendModel.setConstraints(0, 2, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        search.add(bookListScrollPane, theBackendModel.setConstraints(0, 3, 1, 1, 0.5, 0.5, 350, 450, GridBagConstraints.FIRST_LINE_START, 0));
        search.add(viewButton, theBackendModel.setConstraints(0, 4, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        search.add(searchHistoryScrollPane, theBackendModel.setConstraints(1, 3, 1, 1, 0.5, 0.5, 300, 450, GridBagConstraints.FIRST_LINE_START, 0));
        search.add(searchHistoryLabel, theBackendModel.setConstraints(1, 2, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));

        search.setPreferredSize(Constants.INITIAL_SIZE);
        tabbedPane.addTab("Search", search);

    }

    public void updateInfo() {
        try {
            Book aBook = BackendModelSetup.savedBooks.get(BackendModelSetup.theSavedBookID);
            if (aBook != null) {

                if (BackendModelSetup.theSavedBookID > 14 && BackendModelSetup.theSavedBookID % 15 == 0) {
                    JPanel newPage = new JPanel();
                    createMyBooksTab(newPage);
                    card.next(bookshelf);
                    currentPg++;
                }

                bookCovers.get(BackendModelSetup.theSavedBookID).setIcon(new ImageIcon(BackendModelSetup.savedBooks.get(BackendModelSetup.theSavedBookID).getCover().getScaledInstance(Constants.BOOKSHELF_ICON_X, Constants.BOOKSHELF_ICON_Y, Image.SCALE_SMOOTH)));
                String title = BackendModelSetup.savedBooks.get(BackendModelSetup.theSavedBookID).getTitle();
                if (title.length() > 15) {
                    title = title.substring(0, 15) + "...";
                }
                bookTitles.get(BackendModelSetup.theSavedBookID).setText(title);

                BackendModelSetup.theSavedBookID++;
            }

            String[] bookInfoArray = new String[BackendModelSetup.savedBooks.size()];
            for (int i = 0; i < BackendModelSetup.savedBooks.size(); i++) {
                bookInfoArray[i] = BackendModelSetup.savedBooks.get(i).getTitle() + "         " + BackendModelSetup.savedBooks.get(i).getAuthor();
            }
            savedBookList.setListData(bookInfoArray);
        } catch (Exception e) {
        }
    }

    

}
