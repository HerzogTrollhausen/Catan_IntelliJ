import javax.swing.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Bildschirm extends JPanel
{
    private static JFrame f;
    static ArrayList<RectangleImage> grafikobjekte;
    static int ox = 0;
    static int oy = 0;
    static int feldb = 150;
    static int feldh = 150;
    static int eckeb = 30;
    static int eckeh = 30;
    static int kanteb = 20;
    static int kanteh = 20;
    static double plattenr = 3;
    private static Eckpanel eckpanel;
    private static Spielerpanel spielerpanel;
    private static Anderepanel[] anderePanel;
    private static JPanel farbe;
    private static JButton naechster;
    private static JLabel momentanSpielerLabel;

    public Bildschirm(JFrame frame)
    {

        f = frame;
        f.setContentPane(this);
        setVisible(false);

        setLayout(null);
        setBackground(new Color(4417163));
        grafikobjekte = new ArrayList<>();

        eckpanel = new Eckpanel();
        eckpanel.setBounds(f.getWidth() - 600, 0, 600, 200);
        add(eckpanel);

        spielerpanel = new Spielerpanel();
        spielerpanel.setBounds(0, 0, 300, 300);
        add(spielerpanel);

        momentanSpielerLabel = new JLabel();

        farbe = new JPanel();
        farbe.setBounds(f.getWidth() - 200, f.getHeight() - 170, 200, 170);
        farbe.add(momentanSpielerLabel);
        add(farbe);

        naechster = new JButton(Nuz.NAECHSTER_DEFAULT);
        naechster.setBounds(f.getWidth() - 350, f.getHeight() - 170, 150, 170);
        naechster.addActionListener(ev ->
                OnlineInterpreter.wuerfel(Main.wuerfel())
        );
        enableNaechster(false);
        add(naechster);

        JButton handel = new JButton(Nuz.BILDSCHIRM_4_HANDEL);
        handel.setBounds(f.getWidth() / 2 - 100, 0, 200, 100);
        handel.addActionListener(e -> new Handel(6)
        );
        add(handel);

        JButton entwicklung = new JButton(Nuz.BILDSCHIRM_ENTWICKLUNG);
        {
            entwicklung.setBounds(0, 300, 200, 100);
        }
        entwicklung.addActionListener(e -> new Entwicklungskarten());
        add(entwicklung);

        if (!Main.lokal)
        {
            Chat chat = new Chat();
            chat.setBounds(0, 600, 350, f.getHeight() - 600 - 50);
            add(chat);
        }


        farbe.setBackground(Main.spieler().farbe);
        momentanSpielerLabel.setText(Nuz.BILDSCHIRM_MOM_SPIELER + Main.spieler().id);

        addAnderePanel();
        //f.setVisible(true);
        new Timer(100 / 6, e -> repaint()).start();
    }

    static JButton getNaechster()
    {
        return naechster;
    }

    static Eckpanel getEckpanel()
    {
        return eckpanel;
    }

    static Spielerpanel getSpielerpanel()
    {
        return spielerpanel;
    }

    static void setFarbeFarbe(Color zielFarbe)
    {
        farbe.setBackground(zielFarbe);
    }


    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        try
        {
            for (int i = 0; i < Welt.felder.length; i++)
            {
                for (int j = 0; j < Welt.felder[i].length; j++)
                {
                /*System.out.println("welt: "+welt);
                System.out.println("welt.felder: "+welt.felder);
                System.out.println("welt.felder[j][entwicklungskarteAuspielen]"+welt.felder[j][entwicklungskarteAuspielen]);*/
                    Welt.felder[i][j].draw(g2);
                }
            }
            for (RectangleImage aGrafikobjekte : grafikobjekte)
            {
                aGrafikobjekte.draw(g2);
            }
        } catch (NullPointerException ex)
        {
            //ex.printStackTrace();
        }
    }

    void replaceGrafikObjekt(RectangleImage toReplace, RectangleImage replacement)
    {
        grafikobjekte.remove(toReplace);
        grafikobjekte.add(replacement);
    }

    static JFrame getF()
    {
        return f;
    }

    static void enableNaechster(boolean b)
    {
        naechster.setEnabled(b);
    }

    private void addAnderePanel()
    {
        anderePanel = new Anderepanel[Main.anzahlSpieler];
        for (int i = 0; i < Main.anzahlSpieler; i++)
        {
            anderePanel[i] = new Anderepanel(Main.spieler[i]);
            anderePanel[i].setBounds((int) (f.getWidth() / 2 - Main.anzahlSpieler * 0.5 * Anderepanel.width + Anderepanel.width * i), f.getHeight() - Anderepanel.height, Anderepanel.width, Anderepanel.height);
            anderePanel[i].setVisible(true);
            add(anderePanel[i]);
        }
    }

    public static void setSpielerpanelLabelText(String s)
    {
        spielerpanel.label.setText(s);
    }

    public static void setMomentanSpielerLabelText(String s)
    {
        momentanSpielerLabel.setText(s);
    }

    public static void clearEckpanel()
    {
        eckpanel.removeAll();
    }

    public static void anderePanelAkt()
    {
        for (int i = 0; i < Main.anzahlSpieler; i++)
        {
            anderePanel[i].updateRohstoffe();
        }
    }
}