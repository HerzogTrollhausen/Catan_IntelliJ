import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;

public class Main
{
    static Bildschirm bildschirm;
    static Spieler[] spieler;
    private static int spielernr = 0;
    static byte anzahlSpieler = 4;
    static boolean frueh = true;
    static int fruehvor = 1;
    static boolean fruehsiedlung = true;
    static Ecke letzteSiedlung;
    static int strassenbauinprogress;
    static int maxStrassen = 15;
    static int maxSiedlungen = 5;
    static int maxStaedte = 4;
    static Spieler rittermacht;
    public static int ich;
    static boolean lokal = true;
    static JFrame fenster;
    static Hauptmenue hauptmenue;
    static boolean starter = false;

    public static void main(String[] args)
    {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try
            {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        try
        {
            ImagesLaden();
            Buz.init();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        spieler = new Spieler[anzahlSpieler];
        for (byte i = 0; i < anzahlSpieler; i++)
        {
            spieler[i] = new Spieler(i);
        }
        fenster = new JFrame(Nuz.HAUPTTITEL);
        fenster.addWindowListener(new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {

            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                if(!lokal)
                {
                    OnlineInterpreter.spielVerlassen();
                }
                System.exit(-1);
            }

            @Override
            public void windowClosed(WindowEvent e)
            {

            }

            @Override
            public void windowIconified(WindowEvent e)
            {

            }

            @Override
            public void windowDeiconified(WindowEvent e)
            {

            }

            @Override
            public void windowActivated(WindowEvent e)
            {

            }

            @Override
            public void windowDeactivated(WindowEvent e)
            {

            }
        });
        fenster.setSize(1600, 900);
        fenster.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        hauptmenue = new Hauptmenue();
        fenster.add(new Hauptmenue());
        fenster.setContentPane(hauptmenue);
        fenster.setVisible(true);
        //mitServerVerbinden("localhost", 6677);
    }

    public static void strassenbau()
    {
        strassenbauinprogress += 2;
    }

    public static Spieler spieler()
    {
        return spieler[spielernr];
    }

    public static void setIch(int i)
    {
        ich = i;
        Bildschirm.enableNaechster();
    }

    public static void nextPlayer(int wurf)
    {
        if (!frueh)
        {
            if (Main.spieler().siegPunkte() >= 10)
            {
                JOptionPane.showMessageDialog(Bildschirm.getF(), Nuz.gewonnenNachricht(spieler() == ich(), spieler()));//TODO
            }
            spielernr = spielernr == anzahlSpieler - 1 ? 0 : spielernr + 1;
            ernte(wurf);
            Bildschirm.setSpielerpanelLabelText(Nuz.LETZTER_WURF + wurf);
        } else
        {
            spielernr = spielernr + fruehvor;
            if (spielernr == anzahlSpieler)
            {
                fruehvor = fruehvor * -1;
                nextPlayer(wurf);
            } else if (spielernr == -1)
            {
                //OnlineInterpreter.fruehBeenden();
                beendeFrueh();
            }
        }
        Bildschirm.setFarbeFarbe(spieler().farbe);
        Bildschirm.setMomentanSpielerLabelText(Nuz.BILDSCHIRM_MOM_SPIELER + spieler().id);
        Bildschirm.clearEckpanel();
        Welt.dunkel();
        Bildschirm.anderePanelAkt();
        if (lokal)
        {
            ich = spielernr;
        }
        if (ich != spielernr || frueh)
        {
            Bildschirm.getNaechster().setEnabled(false);
        } else if(wurf != 7)
        {
            Bildschirm.getNaechster().setEnabled(true);
        }
    }

    static boolean dran()
    {
        return ich == spielernr;
    }

    public static void beendeFrueh()
    {
        JOptionPane.showMessageDialog(fenster, "Fr\u00fchphase ist vorbei");
        spielernr = 0;
        frueh = false;
        Bildschirm.getNaechster().setEnabled(true);
        Bildschirm.entwicklung.setEnabled(true);
    }

    public static int wuerfel()
    {
        return (int) (Math.random() * 6) + (int) (Math.random() * 6) + 2;
    }

    private static void ernte(int wuerfel)
    {
        if (wuerfel != 7)
        {
            for (int i = 0; i < anzahlSpieler; i++)
            {
                spieler[i].ernte(wuerfel);
            }
        }
    }

    public static void siebenSteuer()
    {
        for (int i = 0; i < anzahlSpieler; i++)
        {
            if (spieler[i].inv.anzahlAnRohstoffen() > 7)
            {
                int[] tmp = spieler[i].inv.rohstoffe.clone();
                for (int j = 0; j < spieler[i].inv.anzahlAnRohstoffen() / 2; j++)
                {
                    if (Spieler.randomRohstoff(tmp) >= 0)
                    {
                        tmp[Spieler.randomRohstoff(tmp)]--;
                    } else
                    {
                        throw new IllegalArgumentException("" + Spieler.randomRohstoff(tmp));
                    }
                }
                OnlineInterpreter.setInventar(spieler[i], tmp);
            }
        }
    }

    private static void ImagesLaden() throws IOException
    {
        Kante.wege = new Image[4];
        for (int i = 0; i < 4; i++)
        {
            Kante.wege[i] = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Kanten/Weg" + i + ".gif"), Bildschirm.kanteb, Bildschirm.kanteh, false);
        }
        Hafen.haefen = new Image[6];
        for (int i = 0; i < 6; i++)
        {
            Hafen.haefen[i] = FileManager.bildLaden("Bilder/Kanten/Hafen" + i + ".png");
        }
    }

    private static void rittermacht(int anzahlGelegteRitter)
    {
        if (rittermacht == null && anzahlGelegteRitter >= 3)
        {
            rittermacht = spieler();
            spieler().rittermachtSiegpunkte = 2;
            //new Fehler("Du besitzt nun die Größte Rittermacht!", "Rittermacht");
            JOptionPane.showMessageDialog(Bildschirm.getF(), "Du besitzt nun die größte Rittermacht", "Rittermacht",
                    JOptionPane.INFORMATION_MESSAGE);
            Bildschirm.anderePanelAkt();
            return;
        }
        if (rittermacht != null && rittermacht.anzahlGelegteRitter < ich().anzahlGelegteRitter)
        {
            rittermacht.rittermachtSiegpunkte = 0;
            spieler().rittermachtSiegpunkte = 2;
            //new Fehler("Du hast die Größte Rittermacht von Spieler " + rittermachtSiegpunkte.id + " übernommen!");
            JOptionPane.showMessageDialog(Bildschirm.getF(), "Du hasst die größte Rittermacht von " +
                            rittermacht.id + " übernommen", "Rittermacht",
                    JOptionPane.INFORMATION_MESSAGE);
            rittermacht = spieler();
            Bildschirm.anderePanelAkt();
        }
    }

    public static void mitServerVerbinden(String Host, int port)
    {
        try
        {

            Socket s = new Socket(Host, port);//CONNECT TO THE SERVER

            Client client = new Client(s);//START NEW CLIENT OBJECT

            Thread t = new Thread(client);//INITIATE NEW THREAD
            t.start();//START THREAD

        } catch (Exception noServer)//IF DIDNT CONNECT PRINT THAT THEY DIDNT
        {
            JOptionPane.showMessageDialog(Bildschirm.getF(), Nuz.VERBINDUNG_FEHLGESCHLAGEN, Nuz.FEHLER, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void start()
    {
        bildschirm = new Bildschirm(fenster);
        //Client.senden("b"+Welt.stapelZahlen("Feld"));
        Welt.initWelt(3);
        bildschirm.setVisible(true);
    }

    public static Spieler ich()
    {
        return spieler[ich];
    }

    public static void entwicklungskarte(int typ, Spieler pSpieler)
    {
        pSpieler.inv.entwicklungskarten[typ]--;
        if (typ == 0)
        {
            rittermacht(++pSpieler.anzahlGelegteRitter);
        }
    }
}