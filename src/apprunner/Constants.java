/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apprunner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author 1100020551
 */
public class Constants {

    public static final Font SERIF_BOLD_16 = new Font(Font.SERIF, Font.BOLD, 16);
    public static final Font SERIF_BOLD_18 = new Font(Font.SERIF, Font.BOLD, 18);

    public static final Font SANS_SERIF_PLAIN_12 = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    public static final Font SANS_SERIF_PLAIN_10 = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
    public static final Font SANS_SERIF_PLAIN_14 = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

    public static final Color BROWN = new Color(140, 124, 95);
    public static final Color BEIGE = new Color(227, 211, 182);
    public static final Color TRANSLUCENT_DARK_CYAN = new Color(59, 123, 150, 100);

    public static final int BOOKSHELF_ICON_X = 80;
    public static final int BOOKSHELF_ICON_Y = 120;
    public static final Dimension BOOKSHELF_ICON_SIZE = new Dimension(Constants.BOOKSHELF_ICON_X, Constants.BOOKSHELF_ICON_Y);
    public static final Dimension INITIAL_SIZE = new Dimension(1000, 600);

    public static final String FORMAT = "<html><body style='width: %1spx'>%1s";

    public static ImageIcon getImageUnavailableIcon() throws MalformedURLException, IOException {
        return new ImageIcon(ImageIO.read(new File("assets\\imageUnavailable.jpg")).getScaledInstance(BOOKSHELF_ICON_X, BOOKSHELF_ICON_Y, Image.SCALE_SMOOTH));
    }

    public static ImageIcon getLeftArrowIcon() throws MalformedURLException, IOException {
        return new ImageIcon(ImageIO.read(new File("assets\\leftArrowIcon.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    }

    public static ImageIcon getRightArrowIcon() throws MalformedURLException, IOException {
        return new ImageIcon(ImageIO.read(new File("assets\\rightArrowIcon.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBookIcon() throws MalformedURLException, IOException {
        return new ImageIcon(ImageIO.read(new File("assets\\openBook.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH));
    }
    public static Image getImageUnavailableImage() throws MalformedURLException, IOException {
        return (ImageIO.read(new File("assets\\imageUnavailable.jpg")).getScaledInstance(BOOKSHELF_ICON_X, BOOKSHELF_ICON_Y, Image.SCALE_SMOOTH));
    }
}
