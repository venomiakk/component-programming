package org.gameoflife.view;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class aaa {

    public static void  printLRB(Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("authors", locale);

        Enumeration<String> en = rb.getKeys();
        while (en.hasMoreElements()){
            String key = en.nextElement();
            System.out.println(key + ". " + rb.getString(key));
        }
    }

    public static void main(String[] args) {
        Locale locale = new Locale("pl", "PL");
        ResourceBundle authors = ResourceBundle.getBundle("org.gameoflife.view.authors", locale);
        System.out.println(authors.getString("1"));
        System.out.println(authors.getString("2"));
    }
}
