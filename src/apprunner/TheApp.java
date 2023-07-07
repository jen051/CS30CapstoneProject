/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apprunner;

import java.awt.Container;
import javax.swing.JFrame;
import backend_models.BackendModelSetup;
import backend_models.Tester;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main_viewcontroller.*;
import second_viewcontroller.*;

/**
 *
 * @author jenni
 */
public class TheApp extends JFrame implements Runnable {

    static BackendModelSetup theBackendModel;
    static SecondViewDisplay theSecondViewDisplay;
    static MainViewDisplay theMainViewDisplay;
    static MainController theMainController;
    static SecondController theSecondController;
    static Container mainContentPane;

    @Override
    public void run() {
        theBackendModel = new BackendModelSetup();
        theSecondViewDisplay = new SecondViewDisplay(theBackendModel);

        theMainViewDisplay = new MainViewDisplay(theBackendModel, theSecondViewDisplay);
        theMainController = new MainController(theBackendModel, theMainViewDisplay);
        theSecondController = new SecondController(theBackendModel, theMainViewDisplay, theSecondViewDisplay);

        mainContentPane = theMainViewDisplay.getContentPane();
        theMainViewDisplay.setVisible(true);
        
//        System.out.println(System.getProperty("java.classpath"));
//        Tester t = new Tester();
//        try {
//            t.runTester();
//        } catch (IOException ex) {
//            Logger.getLogger(TheApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static void goToScreen(int screen) {
        if (screen == 2) {
            theMainViewDisplay.setContentPane(theSecondViewDisplay.getContentPane());
            theSecondViewDisplay.updateInfo();
            theMainViewDisplay.pack();
        }
        if (screen == 1) {
            theMainViewDisplay.setContentPane(mainContentPane);
            theMainViewDisplay.updateInfo();
            theMainViewDisplay.pack();
        }
    }
}
