import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Stuff{

    public static void main(String[] args)
    {
        ArrayList<Object> aL= new ArrayList<>();
        Object[] aR = aL.toArray();
        for(Object aO : aR)
        {
            System.out.println(aO);
        }
    }
}
