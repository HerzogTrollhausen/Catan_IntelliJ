import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO: Das ganze sehr, sehr viel hübscher machen, "Zurück"-Knöpfe etc.
 */
public class Hauptmenue extends JPanel
{
    JButton local;
    JButton online;
    JButton settings;//TODO
    JButton quit;

    private static final String standardhost = "localhost";
    private static final int standardport = 6677;
    private static String host;
    private static int port;
    JButton starten = new JButton("Starte... das... SPIEL DU HURENSOHN!!!");

    public Hauptmenue()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        local = new JButton("Lokales Spiel");
        local.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.lokal = true;
                Main.starter = true;
                OnlineInterpreter.spielStarten();
            }
        });

        online = new JButton("Online");
        online.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.lokal = false;
                host = (String)JOptionPane.showInputDialog(Bildschirm.getF(), "Hostname eingeben:", "Hostname",
                        JOptionPane.QUESTION_MESSAGE, null, null, "localhost");
                boolean erfolgreich = false;
                while(!erfolgreich)
                {
                    try
                    {
                        port = Integer.parseInt((String) JOptionPane.showInputDialog(Bildschirm.getF(), "Portnummer eingeben:", "Port",
                                JOptionPane.QUESTION_MESSAGE, null, null, "6677"));
                        erfolgreich = true;
                    } catch (NumberFormatException ex)
                    {
                        JOptionPane.showMessageDialog(Bildschirm.getF(), "Beim Host bitte eine Zahl eingeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                }
                Main.mitServerVerbinden(host, port);
            }
        });

        quit = new JButton("Beenden");
        quit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.fenster.dispose();
            }
        });

        starten.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                Main.starter = true;
                OnlineInterpreter.spielStarten();
            }
        });

        add(local);
        add(online);
        add(quit);

        /*new Timer((int)100/6, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                repaint();
            }
        }).start();*/
    }
}
