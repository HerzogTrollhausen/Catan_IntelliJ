import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bildschirm extends JPanel {
    private static JFrame f;
    static ArrayList<RectangleImage> grafikobjekte;
    static double plattenr = 3;
    private static Eckpanel eckpanel;
    private static Spielerpanel spielerpanel;
    private static Anderepanel[] anderePanel;
    private static JPanel farbe;
    private static JButton naechster;
    private static JLabel momentanSpielerLabel;
    private JButton handel;
    private Chat chat;
    static JButton entwicklung;
    private Optionspanel optionspanel;
    private JPanel baukosten;

    private void setBoundsZeug() {
        eckpanel.setBounds(f.getWidth() - Guz.eckpanelX(), 0, Guz.eckpanelX(), Guz.eckpanelY());
        spielerpanel.setBounds(0, 0, Guz.eckpanelX(), Guz.eckpanelY());
        farbe.setBounds(f.getWidth() - Guz.farbeX(), f.getHeight() - Guz.andereY(), Guz.farbeX(), Guz.andereY());
        naechster.setBounds(f.getWidth() - 2 * Guz.farbeX(), f.getHeight() - Guz.andereY(), Guz.farbeX(), Guz.andereY());
        handel.setBounds(0, Guz.eckpanelY() + Guz.farbeX(), Guz.farbeX(), Guz.farbeX());
        entwicklung.setBounds(0, Guz.eckpanelY(), Guz.farbeX(), Guz.farbeX());
        optionspanel.setBounds(f.getWidth() - Guz.farbeX(), Guz.eckpanelY(),
                Guz.farbeX(), f.getHeight() - Guz.andereY() - Guz.eckpanelY());
        for (int i = 0; i < Main.anzahlSpieler; i++) {
            anderePanel[i].setBounds(i * Guz.andereX(),
                    f.getHeight() - Guz.andereY(), Guz.andereX(), Guz.andereY());
        }
        if (!Main.lokal) {
            chat.setBounds(Guz.farbeX(), Guz.eckpanelY(),
                    Guz.farbeX(), f.getHeight() - Guz.andereY() - Guz.eckpanelY());
        }
        baukosten.setBounds(f.getWidth()-Guz.eckpanelX(), Guz.eckpanelY(), Guz.farbeX(),
                f.getHeight()-Guz.andereY()-Guz.eckpanelY());
    }

    public Bildschirm(JFrame frame) {

        f = frame;
        f.setContentPane(this);
        setVisible(false);

        setLayout(null);
        setBackground(new Color(4417163));
        grafikobjekte = new ArrayList<>();

        eckpanel = new Eckpanel();
        add(eckpanel);

        spielerpanel = new Spielerpanel();
        add(spielerpanel);

        momentanSpielerLabel = new JLabel();

        farbe = new JPanel();
        farbe.add(momentanSpielerLabel);
        add(farbe);

        naechster = new JButton(Nuz.NAECHSTER_DEFAULT);
        naechster.addActionListener(ev ->
                OnlineInterpreter.wuerfel(Main.wuerfel())
        );
        naechster.setEnabled(true);
        add(naechster);

        handel = new JButton(Nuz.BILDSCHIRM_4_HANDEL);
        handel.addActionListener(e -> new Handel(6)
        );
        add(handel);

        entwicklung = new JButton(Nuz.BILDSCHIRM_ENTWICKLUNG);
        entwicklung.addActionListener(e -> new Entwicklungskarten());
        entwicklung.setEnabled(false);
        add(entwicklung);

        baukosten = new JPanel();
        baukosten.setLayout(new BoxLayout(baukosten, BoxLayout.PAGE_AXIS));
        baukosten.setBackground(new Color(0xaaaaaa));
        baukosten.add(baukostenTeil(Nuz.STRASSE, Inventar.strasse));
        baukosten.add(baukostenTeil(Nuz.SIEDLUNG, Inventar.siedlung));
        baukosten.add(baukostenTeil(Nuz.STADT, Inventar.stadt));
        baukosten.add(baukostenTeil(Nuz.ENTWICKLUNGSKARTE, Inventar.entwicklung));
        add(baukosten);

        if (!Main.lokal) {
            chat = new Chat();
            add(chat);
        }

        optionspanel = new Optionspanel();
        add(optionspanel);


        farbe.setBackground(Main.spieler().farbe);
        momentanSpielerLabel.setText(Nuz.BILDSCHIRM_MOM_SPIELER + Main.spieler().id);

        addAnderePanel();
        setBoundsZeug();
        //f.setVisible(true);
        new Timer(100 / 6, e -> repaint()).start();
    }

    private static JPanel baukostenTeil(String name, int[] array) {
        JPanel rechts = new JPanel();
        rechts.setLayout(new BoxLayout(rechts, BoxLayout.PAGE_AXIS));
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                JPanel rohstoff = new JPanel();
                JLabel rohstoffName = new JLabel(Nuz.rohstoffName(i, array[i] != 1));
                JLabel rohstoffZahl = new JLabel("" + array[i]);
                rohstoff.add(rohstoffZahl);
                rohstoff.add(rohstoffName);
                rohstoff.setBackground(new Color(0x888888));
                rechts.add(rohstoff);
            }
        }
        JLabel links = new JLabel(name);
        JPanel tmp = new JPanel();
        tmp.setLayout(new BorderLayout());
        tmp.add(links, BorderLayout.WEST);
        tmp.add(rechts, BorderLayout.EAST);
        return tmp;
    }

    static JButton getNaechster() {
        return naechster;
    }

    static Eckpanel getEckpanel() {
        return eckpanel;
    }

    static Spielerpanel getSpielerpanel() {
        return spielerpanel;
    }

    static void setFarbeFarbe(Color zielFarbe) {
        farbe.setBackground(zielFarbe);
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        try {
            for (int i = 0; i < Welt.felder.length; i++) {
                for (int j = 0; j < Welt.felder[i].length; j++) {
                /*System.out.println("welt: "+welt);
                System.out.println("welt.felder: "+welt.felder);
                System.out.println("welt.felder[j][entwicklungskarteAuspielen]"+welt.felder[j][entwicklungskarteAuspielen]);*/
                    Welt.felder[i][j].draw(g2);
                }
            }
            for (RectangleImage aGrafikobjekte : grafikobjekte) {
                aGrafikobjekte.draw(g2);
            }
        } catch (NullPointerException ex) {
            //ex.printStackTrace();
        }
    }

    void replaceGrafikObjekt(RectangleImage toReplace, RectangleImage replacement) {
        grafikobjekte.remove(toReplace);
        grafikobjekte.add(replacement);
    }

    static JFrame getF() {
        return f;
    }

    static void enableNaechster() {
        if (naechster != null)
            naechster.setEnabled(!Main.frueh && Main.dran() && (Main.strassenbauinprogress == 0) && !Bandit.amSetzen);
    }

    private void addAnderePanel() {
        anderePanel = new Anderepanel[Main.anzahlSpieler];
        for (int i = 0; i < Main.anzahlSpieler; i++) {
            anderePanel[i] = new Anderepanel(Main.spieler[i]);
            anderePanel[i].setVisible(true);
            add(anderePanel[i]);
        }
    }

    static void setSpielerpanelLabelText(String s) {
        spielerpanel.label.setText(s);
    }

    public static void setMomentanSpielerLabelText(String s) {
        momentanSpielerLabel.setText(s);
    }

    public static void clearEckpanel() {
        eckpanel.removeAll();
    }

    static void anderePanelAkt() {
        for (int i = 0; i < Main.anzahlSpieler; i++) {
            anderePanel[i].updateRohstoffe();
        }
    }
}