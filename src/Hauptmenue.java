import javax.swing.*;

/**
 * TODO: Das ganze sehr, sehr viel hübscher machen, "Zurück"-Knöpfe etc., Settings
 */
public class Hauptmenue extends JPanel
{

    private static final String standardhost = "localhost";
    private static final int standardport = 6677;
    private static String host;
    private static int port;
    JButton starten = new JButton("Starte... das... SPIEL DU HURENSOHN!!!");

    Hauptmenue()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton local = new JButton("Lokales Spiel");
        local.addActionListener(e ->
        {
            Main.lokal = true;
            Main.starter = true;
            OnlineInterpreter.spielStarten();
        });

        JButton online = new JButton("Online");
        online.addActionListener(e ->
        {
            Main.lokal = false;
            host = (String)JOptionPane.showInputDialog(Bildschirm.getF(), "Hostname eingeben:", "Hostname",
                    JOptionPane.QUESTION_MESSAGE, null, null, standardhost);
            boolean erfolgreich = false;
            while(!erfolgreich)
            {
                try
                {
                    port = Integer.parseInt((String) JOptionPane.showInputDialog(Bildschirm.getF(), "Portnummer eingeben:", "Port",
                            JOptionPane.QUESTION_MESSAGE, null, null, standardport));
                    erfolgreich = true;
                } catch (NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(Bildschirm.getF(), "Beim Host bitte eine Zahl eingeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
            Main.mitServerVerbinden(host, port);
        });

        JButton quit = new JButton("Beenden");
        quit.addActionListener(e -> Main.fenster.dispose());

        starten.addActionListener(e ->
        {

            Main.starter = true;
            OnlineInterpreter.spielStarten();
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
