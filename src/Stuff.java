import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Stuff extends JPanel {

    public Stuff() {

        JButton btn1 = new JButton("Button1");
        add(btn1);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new Stuff());

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new FrameListener());

        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}
class FrameListener extends WindowAdapter
{
    public void windowClosing(WindowEvent e)
    {
        System.out.println("Hi");
        System.exit(4);
    }
}
