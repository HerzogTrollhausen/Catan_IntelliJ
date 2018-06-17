import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class Main
{
    static Bildschirm bildschirm;
    static Spieler[] spieler;
    private static int spielernr = 0;
    static final byte anzahlSpieler = 2;
    static boolean frueh = true;
    static int fruehvor = 1;
    static boolean fruehsiedlung = true;
    static Ecke letzteSiedlung;
    static int strassenbauinprogress;
    static int maxStrassen = 15;
    static int maxSiedlungen = 5;
    static int maxStaedte = 4;
    static Spieler rittermacht;
    static int ich;
    static boolean lokal = true;
    static JFrame fenster;
    static Hauptmenue hauptmenue;
    static boolean starter = false;

    public static void main(String[] args)
    {
        try
        {
            ImagesLaden();
            Buz.init();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        spieler = new Spieler[anzahlSpieler];
        for (byte i = 0; i < anzahlSpieler; i++)
        {
            spieler[i] = new Spieler(i);
        }
        fenster = new JFrame("Die Siedler von Catan");
        fenster.setSize(1600, 900);
        fenster.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    public static void nextPlayer(int wurf)
    {
        if (!frueh)
        {
            if (Main.spieler().siegPunkte() >= 10)
            {
                //new Fehler("Spieler " + Main.spieler().id + " hat gewonnen!", "Glückwunsch!"); TODO
            }
            spielernr = spielernr == anzahlSpieler - 1 ? 0 : spielernr + 1;
            ernte(wurf);
            Bildschirm.setSpielerpanelLabelText("Letzter Wurf: " + wurf);
        } else
        {
            spielernr = spielernr + fruehvor;
            if (spielernr == anzahlSpieler)
            {
                fruehvor = fruehvor * -1;
                //Client.senden("h"+wurf);
                nextPlayer(wurf);
            } else if (spielernr == -1)
            {
                spielernr = 0;
                frueh = false;
                Bildschirm.getNaechster().setEnabled(true);
            }
        }
        Bildschirm.setFarbeFarbe(spieler().farbe);
        Bildschirm.setMomentanSpielerLabelText("Momentaner Spieler: " + spieler().id);
        Bildschirm.clearEckpanel();
        Welt.dunkel();
        Bildschirm.anderePanelAkt();
        if (lokal)
        {
            ich = spielernr;
        }
        JButton naechster = Bildschirm.getNaechster();
        if (ich != spielernr || frueh)
        {
            naechster.setEnabled(false);
        } else
        {
            naechster.setEnabled(true);
        }
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
        for(int i = 0; i < anzahlSpieler; i++)
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

    public static void rittermacht(int anzahlGelegteRitter)
    {
        if (rittermacht == null && anzahlGelegteRitter >= 3)
        {
            rittermacht = spieler();
            spieler().rittermacht = 2;
            new Fehler("Du besitzt nun die Größte Rittermacht!", "Rittermacht");
            Bildschirm.anderePanelAkt();
            return;
        }
        if (rittermacht != null && rittermacht.anzahlGelegteRitter < spieler().anzahlGelegteRitter)
        {
            rittermacht.rittermacht = 0;
            spieler().rittermacht = 2;
            new Fehler("Du hast die Größte Rittermacht von Spieler " + rittermacht.id + " übernommen!");
            rittermacht = spieler();
            Bildschirm.anderePanelAkt();
        }
    }

    public static void mitServerVerbinden(String Host, int port)
    {
        try
        {

            Socket s = new Socket(Host, port);//CONNECT TO THE SERVER

            System.out.println("You connected to " + Host);//IF CONNECTED THEN PRINT IT OUT

            Client client = new Client(s);//START NEW CLIENT OBJECT

            Thread t = new Thread(client);//INITIATE NEW THREAD
            t.start();//START THREAD

        } catch (Exception noServer)//IF DIDNT CONNECT PRINT THAT THEY DIDNT
        {
            System.out.println("The server might not be up at this time.");
            System.out.println("Please try again later.");
        }
    }

    public static void start()
    {
        bildschirm = new Bildschirm(fenster);
        //Client.senden("b"+Welt.stapelZahlen("Feld"));
        Welt.initWelt(3);
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