/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package second_viewcontroller;

import apprunner.TheApp;
import backend_models.BackendModelSetup;
import backend_models.Book;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import main_viewcontroller.MainViewDisplay;

/**
 *
 * @author jenni
 */
public class SecondController {
    
    BackendModelSetup theBackendModel;
    MainViewDisplay theMainViewDisplay;
    SecondViewDisplay theSecondViewDisplay;
    
    public SecondController(BackendModelSetup aBackend, MainViewDisplay aMainViewDisplay, SecondViewDisplay aSecondViewDisplay) {
        this.theBackendModel = aBackend;
        this.theMainViewDisplay = aMainViewDisplay;
        this.theSecondViewDisplay = aSecondViewDisplay;
        this.initController();
    }
    
    private class ReturnToMainAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            TheApp.goToScreen(1);
        }
    }
    
    private class AddToBookshelfAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            
            Book theBook = BackendModelSetup.searchResultBooks[BackendModelSetup.theBookID];
            
            if (!theBook.getSaveStatus()) {
                theBook.setSaveStatus(true);
                theBook.setIndex(BackendModelSetup.theSavedBookID);
                BackendModelSetup.savedBooks.add(BackendModelSetup.theSavedBookID, theBook);
//                JOptionPane.showMessageDialog(theMainViewDisplay, "Saved!");
            } else {
                JOptionPane.showMessageDialog(theMainViewDisplay, "Book is already on shelf!");
            }
        }
    }
    
    private void initController() {
        this.theSecondViewDisplay.returnButton.addActionListener(new ReturnToMainAction());
        this.theSecondViewDisplay.addToBookshelfButton.addActionListener(new AddToBookshelfAction());
    }
}
