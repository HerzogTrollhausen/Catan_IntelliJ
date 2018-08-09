import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

public class Stuff{

    public static void main(String[] args)
    {
        System.out.println(JOptionPane.showOptionDialog(Main.fenster, "Möchtest du ein neues Spiel" +
                        " erstellen oder ein altes laden?", "Erstellen oder laden?", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new String[]{"Neu erstellen", "Laden"}, "Neu erstellen"));
    }
}
