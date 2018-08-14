import javax.swing.*;
import java.io.*;
import java.util.List;

/**
 * TODO: Das ganze sehr, sehr viel hübscher machen, "Zurück"-Knöpfe etc., Settings
 */
class Hauptmenue extends JPanel {

    private static final String standardhost = "localhost";
    private static final int standardport = 6677;
    private static String host;
    private static int port;
    JButton starten = new JButton(Nuz.HAUPTMENUE_SPIELSTARTEN);

    Hauptmenue() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton local = new JButton(Nuz.HAUPTMENUE_LOKALBUTTON);
        local.addActionListener(e ->
        {
            Main.lokal = true;
            String[] options = {"Neues Spiel", "Spiel laden"};
            int wahl = JOptionPane.showOptionDialog(Main.fenster, "Willst du ein neues Spiel starten oder ein altes laden?",
                    "Neues Spiel?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                    options[0]);
            if (wahl == 0) {
                Main.starter = true;
                OnlineInterpreter.spielStarten();
            } else {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(Nuz.SAVEGAME_LOCATION));
                int fileChooserReturn = chooser.showOpenDialog(Main.fenster);
                if (fileChooserReturn == JFileChooser.APPROVE_OPTION) {
                    List<String> list = OnlineInterpreter.readSaveFile(chooser.getSelectedFile());
                    for (String aString : list) {
                        Client.senden(aString);
                    }
                }
            }
        });

        JButton online = new JButton(Nuz.HAUPTMENUE_ONLINEBUTTON);
        online.addActionListener(e ->
        {
            Main.lokal = false;
            host = (String) JOptionPane.showInputDialog(Bildschirm.getF(), Nuz.HAUPTMENUE_HOSTPOPUP_FRAGE, Nuz.HAUPTMENUE_HOSTPOPUP_TITEL,
                    JOptionPane.QUESTION_MESSAGE, null, null, standardhost);
            boolean erfolgreich = false;
            while (!erfolgreich) {
                try {
                    port = Integer.parseInt((String) JOptionPane.showInputDialog(Bildschirm.getF(), Nuz.HAUPTMENUE_PORTNUMMER_FRAGE, Nuz.HAUPTMENUE_PORTNUMMER_TITEL,
                            JOptionPane.QUESTION_MESSAGE, null, null, standardport));
                    erfolgreich = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Bildschirm.getF(), Nuz.HAUPTMENUE_PORT_NUMBERFORMAT_FRAGE, Nuz.FEHLER, JOptionPane.ERROR_MESSAGE);
                }
            }
            Main.mitServerVerbinden(host, port);
        });

        JButton quit = new JButton(Nuz.HAUPTMENUE_BEENDEN);
        quit.addActionListener(e -> Main.fenster.dispose());

        starten.addActionListener(e ->
        {

            Main.starter = true;
            OnlineInterpreter.spielStarten();
        });

        JCheckBox testModeBox = new JCheckBox("Testmodus", false);
        testModeBox.addActionListener(e ->
                Main.testmodus = testModeBox.isSelected());

        add(local);
        add(online);
        add(quit);
        add(testModeBox);
        /*new Timer((int)100/6, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                repaint();
            }
        }).start();*/
    }
}
