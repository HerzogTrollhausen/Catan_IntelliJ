import javax.swing.*;
import java.awt.Dimension;

public class Chat extends JPanel
{
    private static JTextArea area;
    private JTextField field;

    Chat()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setAutoscrolls(true);
        JPanel unten = new JPanel();
        field = new JTextField();
        field.setPreferredSize(new Dimension(200, 40));
        JButton button = new JButton("Senden");
        button.addActionListener(e ->
        {
            OnlineInterpreter.chatNachricht("Spieler "+Main.ich+": "+field.getText());
            field.setText("");
        });
        button.setMaximumSize(new Dimension(150, 40));
        unten.setMaximumSize(new Dimension(20000, 45));
        unten.add(field);
        unten.add(button);
        add(area);
        add(unten);
    }

    public static void displayMessage(String msg)
    {
        area.append('\n'+msg);
    }
}
