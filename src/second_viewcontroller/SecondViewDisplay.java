/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package second_viewcontroller;

/**
 *
 * @author jenni
 */
import apprunner.Constants;
import backend_models.BackendModelSetup;
import backend_models.Book;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIDefaults;
import javax.swing.WindowConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class SecondViewDisplay extends JFrame {

    BackendModelSetup theBackendModel;

    JLabel title;
    JLabel author;
    JLabel price;
    JLabel rating;

    JTextPane summary;
    JLabel summaryLabel;
    JScrollPane summaryScrollPane;

    JButton returnButton;
    JButton addToBookshelfButton;
    JLabel cover;

    public SecondViewDisplay(BackendModelSetup aBackend) {
        this.theBackendModel = aBackend;
        this.initComponents();
    }

    private void initComponents() {
        Container displayPane = this.getContentPane();
        displayPane.setLayout(new GridBagLayout());
        displayPane.setBackground(Constants.BROWN);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        title = new JLabel("title");

        author = new JLabel("author");

        price = new JLabel("price");

        summaryLabel = new JLabel("Summary:");

        summary = new JTextPane();
        UIDefaults defaults = new UIDefaults();
        defaults.put("TextPane[Enabled].backgroundPainter", Constants.BEIGE);
        summary.putClientProperty("Nimbus.Overrides", defaults);
        summary.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
        summary.setBackground(Constants.BEIGE);
        SimpleAttributeSet aSet = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(aSet, (float) 0.5);
        StyleConstants.setFontSize(aSet, 13);
        summary.setParagraphAttributes(aSet, false);

        rating = new JLabel("rating");

        cover = new JLabel();

        returnButton = new JButton("Back");
        returnButton.setToolTipText("Back to main screen");

        addToBookshelfButton = new JButton("Add to Bookshelf");

        summaryScrollPane = new JScrollPane(summary);
        summaryScrollPane.setAutoscrolls(true);
        summaryScrollPane.setVerticalScrollBar(new JScrollBar());
        summaryScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        summaryScrollPane.setPreferredSize(new Dimension(480, 400));

        theBackendModel.setColorAndFont(title, Constants.SERIF_BOLD_16, Color.WHITE);
        theBackendModel.setColorAndFont(author, Constants.SERIF_BOLD_16, Color.WHITE);
        theBackendModel.setColorAndFont(price, Constants.SERIF_BOLD_16, Color.WHITE);
        theBackendModel.setColorAndFont(summaryLabel, Constants.SERIF_BOLD_16, Color.WHITE);
        theBackendModel.setColorAndFont(rating, Constants.SERIF_BOLD_16, Color.WHITE);
        theBackendModel.setColorAndFont(returnButton, Constants.SERIF_BOLD_16, Color.WHITE, Constants.BROWN);
        theBackendModel.setColorAndFont(addToBookshelfButton, Constants.SERIF_BOLD_16, Color.WHITE, Constants.BROWN);

        //setConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty, ipadx, ipady, anchor, fill)
        displayPane.add(title, theBackendModel.setConstraints(0, 0, 1, 3, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        displayPane.add(author, theBackendModel.setConstraints(1, 0, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        displayPane.add(price, theBackendModel.setConstraints(1, 1, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        displayPane.add(rating, theBackendModel.setConstraints(1, 2, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        displayPane.add(cover, theBackendModel.setConstraints(1, 4, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        displayPane.add(summaryLabel, theBackendModel.setConstraints(0, 3, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        displayPane.add(summaryScrollPane, theBackendModel.setConstraints(0, 4, 3, 1, 0.5, 0.5, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        displayPane.add(returnButton, theBackendModel.setConstraints(0, 5, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));
        displayPane.add(addToBookshelfButton, theBackendModel.setConstraints(1, 5, 1, 1, 0.5, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, 0));

        displayPane.setPreferredSize(new Dimension(730, 630));

        this.pack();

    }

    public void updateInfo() {
        Book theRequestedBook;
        if (BackendModelSetup.bookIsSaved) {
            theRequestedBook = BackendModelSetup.savedBooks.get(BackendModelSetup.lastSelectedInShelf);
        } else {
            try {
                this.theBackendModel.theWebScraper.getDetails(BackendModelSetup.theBookID);
            } catch (Exception ex) {
                Logger.getLogger(SecondController.class.getName()).log(Level.SEVERE, null, ex);
            }
            theRequestedBook = BackendModelSetup.searchResultBooks[BackendModelSetup.theBookID];
        }
        title.setText(String.format(Constants.FORMAT, 350, theRequestedBook.getTitle()));
        author.setText(theRequestedBook.getAuthor());
        summary.setText(theRequestedBook.getDescription());
        rating.setText("Rating: " + theRequestedBook.getRating());
        price.setText("Price: $" + theRequestedBook.getPrice());
        cover.setIcon(new ImageIcon(theRequestedBook.getCover().getScaledInstance(200, 300, Image.SCALE_SMOOTH)));

    }

}
