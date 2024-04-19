package org.gameoflife.view;

import java.util.Locale;
import java.util.ResourceBundle;

public class LangController {

    private Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
    public ResourceBundle messages = ResourceBundle.getBundle("lang", locale);
    public final String MENU_SETSIZE = "menu.setsize";
    public final String MENU_SETWIDTH = "menu.setwidth";
    public final String MENU_SETHEIGHT = "menu.setheight";
    public final String MENU_CHOOSELANG = "menu.chooselang";
    public final String MENU_CONFIRM = "menu.confirm";
    public final String MENU_SETCOMP = "menu.setcompaction";
    public final String MENU_PL = "menu.pl";
    public final String MENU_EN = "menu.en";
    public final String MENU_LOAD="menu.load";

    public final String SIM_NEXTSTEP = "sim.nextstep";
    public final String SIM_START = "sim.start";
    public final String SIM_STOP = "sim.stop";
    public final String SIM_RESET = "sim.reset";
    public final String SIM_SAVE = "sim.save";


    public void setEN(){
        this.locale = new Locale("en", "GB");
        messages = ResourceBundle.getBundle("lang", this.locale);
    }

    public void setPL(){
        this.locale = new Locale("pl", "PL");
        this.messages = ResourceBundle.getBundle("lang", this.locale);
    }

    public static void main(String[] args) {
        LangController lc = new LangController();
        //System.out.println(lc.messages.getString(MENU_SETHEIGHT));
    }
}
