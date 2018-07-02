import javax.swing.*;

public class Entwicklungskarten extends JPanel// 14 Ritter, 6 Fortschritt, 5 Siegpunkt
{// 0 Ritter, 1 Siegpunkt, 2 Straßenbau, 3 Erfindung, 4 Monopol
    private JFrame f;
    static Stapel stapel = new Stapel(Stapel.StapelTypen.Entwicklung);

    Entwicklungskarten() {
        framezeug();
        JPanel ritterpanel = new JPanel();
        JLabel ritterlabel = new JLabel("Ritter: " + Main.ich().anzahlEntwicklungskarten(0));
        JButton ritterbutton = new JButton("Ausschicken!");
        JLabel ausgeschicktlabel = new JLabel("Ausgeschickt: " + Main.ich().anzahlGelegteRitter);
        ritterbutton.addActionListener(e ->
        {
            Bandit.ausschicken();
            OnlineInterpreter.entwicklungskarteAusspielen(Main.ich(), 0);
            f.dispose();
        });
        if (Main.spieler().anzahlEntwicklungskarten(0) == 0) {
            ritterbutton.setEnabled(false);
        }

        JLabel rittermachtlabel = new JLabel("Setze drei Ritter ein, um die Größte Rittermacht für 2 Siegpunkte zu bekommen");
        if (Main.rittermacht != null) {
            rittermachtlabel.setText("Die größte Rittermacht hat Spieler "
                    + Main.rittermacht.id + " mit " + Main.rittermacht.anzahlGelegteRitter + " gelegten Rittern.");
        }

        JPanel strassenbaupanel = new JPanel();
        JLabel strassenbaulabel = new JLabel("Straßenbau: " + Main.ich().anzahlEntwicklungskarten(2));
        JButton strassenbaubutton = new JButton("Bauen!");
        strassenbaubutton.addActionListener(e ->
        {
            Main.strassenbau();
            OnlineInterpreter.entwicklungskarteAusspielen(Main.ich(), 2);
            Bildschirm.enableNaechster(false);
            f.dispose();
        });
        if (Main.ich().anzahlEntwicklungskarten(2) == 0) {
            strassenbaubutton.setEnabled(false);
        }

        JPanel erfindungpanel = new JPanel();
        JLabel erfindunglabel = new JLabel("Erfindung: " + Main.ich().anzahlEntwicklungskarten(3));
        JButton erfindungbutton = new JButton("2 Karten nehmen!");
        erfindungbutton.addActionListener(e ->
        {
            new Handel(7);
            OnlineInterpreter.entwicklungskarteAusspielen(Main.ich(), 3);
            f.dispose();
        });
        if (Main.ich().anzahlEntwicklungskarten(3) == 0) {
            erfindungbutton.setEnabled(false);
        }

        JPanel monopolpanel = new JPanel();
        JLabel monopollabel = new JLabel("Monopol: " + Main.ich().anzahlEntwicklungskarten(4));
        JButton monopolbutton = new JButton("Monopol aussuchen!");
        monopolbutton.addActionListener(e ->
        {
            //new Monopol();
            String[] a = new String[5];
            for(int i = 0; i < 5; i++)
            {
                a[i] = Inventar.name(i);
            }
            int typ = JOptionPane.showOptionDialog(f, "Auf welches Gut willst du ein Monopol anmelden?", "Monopol",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, a, a[0]);
            int tmp = 0;
            for(int i = 0; i < Main.anzahlSpieler; i++)
            {
                tmp += Main.spieler[i].inv.rohstoffe[typ];
                int[] bezahlArray = new int[5];
                bezahlArray[typ] = Main.spieler[i].inv.rohstoffe[typ];
                OnlineInterpreter.bezahlen(Main.spieler[i], bezahlArray);
            }
            int[] bekommArray = new int[5];
            bekommArray[typ] = tmp;
            OnlineInterpreter.bekommen(Main.ich(), bekommArray);
            OnlineInterpreter.entwicklungskarteAusspielen(Main.ich(), 4);
            f.dispose();
        });
        if (Main.spieler().anzahlEntwicklungskarten(4) == 0) {
            monopolbutton.setEnabled(false);
        }

        JPanel siegpunktpanel = new JPanel();
        JLabel siegpunktlabel = new JLabel("Siegpunktkarten: " + Main.ich().anzahlEntwicklungskarten(1));

        JButton kaufen = new JButton("Entwicklungskarte kaufen");
        kaufen.setEnabled(Main.ich().inv.bezahlbar(new Inventar(3)));
        kaufen.addActionListener(e -> {
            OnlineInterpreter.bezahlen(Main.spieler[Main.ich], Inventar.entwicklung);
            OnlineInterpreter.entwicklungskarteZiehen(Main.spieler[Main.ich], (int) (Math.random() * Entwicklungskarten.stapel.zaehl()));
            f.dispose();
        });
        add(kaufen);

        ritterpanel.add(ritterlabel);
        ritterpanel.add(ritterbutton);
        ritterpanel.add(ausgeschicktlabel);
        strassenbaupanel.add(strassenbaulabel);
        strassenbaupanel.add(strassenbaubutton);
        erfindungpanel.add(erfindunglabel);
        erfindungpanel.add(erfindungbutton);
        monopolpanel.add(monopollabel);
        monopolpanel.add(monopolbutton);
        siegpunktpanel.add(siegpunktlabel);

        add(ritterpanel);
        add(rittermachtlabel);
        add(strassenbaupanel);
        add(erfindungpanel);
        add(monopolpanel);
        add(siegpunktpanel);

        f.setSize(getLayout().minimumLayoutSize(this).width + 30, getLayout().minimumLayoutSize(this).height + 30);
        f.setVisible(true);

    }

    private void framezeug() {
        f = new JFrame("Entwicklungskarten");
        f.setContentPane(this);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
}
