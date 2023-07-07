/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend_models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import jep.Interpreter;
import jep.JepException;
import jep.SharedInterpreter;
//import org.python.util.PythonInterpreter;

/**
 *
 * @author jenni
 */
public class Tester {

    public Tester() {
//        ProcessBuilder builder = new ProcessBuilder("python","C:\\Users\\jenni\\Desktop\\script.py" );
//        Process process = builder.start();
        System.out.println("tester constructed");
//        System.loadLibrary("jep-4.1.1.jar");
//        System.setProperty("java.library.path", "C:\\Users\\jenni\\Desktop\\NetBeansProjects\\CS30CapstoneProject\\jep-4.1.1.jar");
        System.out.println("CSV READ");
    }

    public Object getUserChoices() throws IOException {
        FileWriter writer = new FileWriter("SavedBooks.csv");
        writer.append("User-ID,Book-Title,Book-Author,Publisher,Image-URL-L,Book-Rating");
        writer.append("\n");
        for (int j = 0; j < BackendModelSetup.savedBooks.size(); j++) {
            writer.append(0 + "," + BackendModelSetup.savedBooks.get(j).getTitle().replace(",", "") + "," + BackendModelSetup.savedBooks.get(j).getAuthor().replace(",", "").replace(",", "") + "," + BackendModelSetup.savedBooks.get(j).getPublisher().replace(",", "") + "," + BackendModelSetup.savedBooks.get(j).getCoverLink() + "," + BackendModelSetup.savedBooks.get(j).getUserRating());
            writer.append("\n");
        }
        writer.close();
        File f = new File("script.py");
        System.out.println(f.getAbsolutePath());
        String s = f.getAbsolutePath().replace("script.py", "");
        Object ret = runScript("script.py", s);
        return ret;
    }

    public Object runScript(String pythonScriptFullPath, String p) {
        System.out.println("1");
        System.out.println(pythonScriptFullPath);
        Object result = null;
        try (Interpreter interp = new SharedInterpreter()) {
            System.out.println("2");
            interp.set("books_path", p + "Books.csv");
            interp.set("ratings_path", p + "Ratings.csv");
            interp.set("saved_path", p + "SavedBooks.csv");
            interp.runScript(pythonScriptFullPath);
            System.out.println("2");
            result = interp.getValue("books_for_user");
            System.out.println(result);
            System.out.println("3");
        } catch (JepException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
