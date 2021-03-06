import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OnlineInterpreter
{

    static ArrayList<String> log = new ArrayList<>();
    private static boolean saveToLog = false;

    /**
     * aHallo wie geht's denn so?: Chatnachricht "Hallo wie geht's denn so"
     * b00332421: Felderstapel
     * c26a5ab349: Zahlenstapel (mit Zahlen in Hexadezimal)
     * d0453: Straße von 0 bei Feld 4|5 Position 3 gebaut
     * e0453: Siedlung von 0 bei Feld 4|5 Position 3 gebaut
     * f0453: Stadt von 0 bei Feld 4|5 Position 3 gebaut
     * g323: Spieler 3 zieht Entwicklungskarte 23
     * h12: Spieler würfelt 12
     * i23: Spieler 2 spielt Entwicklungskarte vom Typ 3 aus
     * j56: Räuber auf Feld 5|6
     * k2: ID wird auf 2 gesetzt
     * l23104: Hafenstapel
     * m3: Starte das Spiel mit 3 Spielern
     * n20m0m0m12m3: Spieler 2 bezahlt 12 Weizen und 3 Erz
     * o20m0m0m12m3: Spieler 2 bekommt 12 Weizen und 3 Erz
     * p1Hallo: Chatnachricht an Spieler 1: Hallo
     * qGruß#Hallo: Popup an alle: Titel Gruß, nachricht Hallo
     * r1Gruß#Hallo: Popup an Spieler 1: Titel Gruß, Nachricht Hallo
     * s20m0m0m12m3: Das Inventar von Spieler 2 wird auf 0 0 0 12 3 gesetzt
     * t132m5m-3m0m0 : Handelsangebot von 1 zu 3, dass du dieses Inventar bekommst
     * u34Erstes Spiel#12Zweites Spiel: Im Spiel "Erstes Spiel" gibt es 3 von 4 Spielern, im Spiel "Zweites Spiel" gibt es 1 von 2 Spielern
     */
    static void interpret(String msg)
    {
        System.out.println(msg);
        if (saveToLog)
        {
            log.add(msg);
        }
        switch (msg.charAt(0))
        {
            case 'a'://aHallo wie geht's denn so?: Chatnachricht "Hallo wie geht's denn so"
            {
                Chat.displayMessage(msg.substring(1));
                break;
            }
            case 'b'://b00332421: Felderstapel
            {
                int[] a = new int[19];
                char[] c = msg.toCharArray();
                for (int i = 1; i < 20; i++)
                {
                    a[i - 1] = Integer.parseInt("" + c[i]);
                }
                Welt.felderArray = a;
                if (Main.starter)
                {
                    zahlenstapel(Welt.stapelZahlen(Stapel.StapelTypen.Zahl));
                }
                break;
            }
            case 'c'://c26a5ab349: Zahlenstapel (mit Zahlen in Hexadezimal)
            {
                int[] a = new int[18];
                char[] c = msg.toCharArray();
                for (int i = 1; i < 19; i++)
                {
                    a[i - 1] = Integer.parseInt("" + c[i], 16);
                }
                Welt.plaettchenArray = a;
                if (Main.starter)
                {
                    hafenStapel(Welt.stapelZahlen(Stapel.StapelTypen.Hafen));
                }
                break;
            }
            case 'd'://d0453: Straße von 0 bei Feld 4|5 Position 3 gebaut
            {
                Welt.addStrasse(Integer.parseInt("" + msg.charAt(2)), Integer.parseInt("" + msg.charAt(3)), Integer.parseInt("" + msg.charAt(4)), Integer.parseInt("" + msg.charAt(1)));
                break;
            }
            case 'e'://e0453: Siedlung von 0 bei Feld 4|5 Position 3 gebaut
            {
                Welt.addSiedlung(Integer.parseInt("" + msg.charAt(2)), Integer.parseInt("" + msg.charAt(3)), Integer.parseInt("" + msg.charAt(4)), Integer.parseInt("" + msg.charAt(1)));
                break;
            }
            case 'f'://f0453: Stadt von 0 bei Feld 4|5 Position 3 gebaut
            {
                Welt.zuStadt(Integer.parseInt("" + msg.charAt(2)), Integer.parseInt("" + msg.charAt(3)), Integer.parseInt("" + msg.charAt(4)), Integer.parseInt("" + msg.charAt(1)));
                break;
            }
            case 'g'://g323: Spieler 3 zieht Entwicklungskarte 23
            {
                Spieler spieler = Main.spieler[Integer.parseInt("" + msg.charAt(1))];
                String subString = msg.substring(2);
                int nrKarte = Integer.parseInt(subString);
                spieler.ziehEntwicklungskarte(nrKarte);
                break;//TODO
            }
            case 'h'://h12: Spieler würfelt 12
            {
                int tmp = Integer.parseInt(msg.substring(1));
                Main.nextPlayer(tmp);
                break;
            }
            case 'i'://i23: Spieler 2 spielt Entwicklungskarte vom Typ 3 aus
            {
                Main.entwicklungskarte(Integer.parseInt("" + msg.charAt(2)), Main.spieler[Integer.parseInt("" + msg.charAt(1))]);
                break;
            }
            case 'j'://j56: Räuber auf Feld 5|6
            {
                int x = Integer.parseInt("" + msg.charAt(1));//TODO: Ex machina angucken
                int y = Integer.parseInt("" + msg.charAt(2));
                if (Bandit.feld != null)
                {
                    Bandit.feld.besetzt = false;
                }
                Welt.felder[y][x].besetzt = true;
                Bandit.feld = Welt.felder[y][x];
                break;
            }
            case 'k'://k2: ID wird auf 2 gesetzt
            {
                Main.setIch(Integer.parseInt(msg.substring(1)));
                if (Main.ich == 0)
                {
                    Main.hauptmenue.add(Main.hauptmenue.starten);
                    Main.hauptmenue.revalidate();
                }
                break;
            }
            case 'l'://l23104: Hafenstapel
            {
                int[] a = new int[9];
                char[] c = msg.toCharArray();
                for (int i = 1; i < 10; i++)
                {
                    a[i - 1] = Integer.parseInt("" + c[i]);
                }
                Welt.haefenArray = a;
                Main.start();
                break;
            }
            case 'm'://m3: Starte das Spiel mit 3 Spielern
            {
                log.add(msg);
                saveToLog = true;
                Main.anzahlSpieler = (byte) Integer.parseInt(msg.charAt(1) + "");
                if (Main.starter)
                {
                    felderstapel(Welt.stapelZahlen(Stapel.StapelTypen.Feld));
                }
                break;
            }
            case 'n'://n20m0m0m12m3: Spieler 2 bezahlt 12 Weizen und 3 Erz
            {
                Spieler s = Main.spieler[Integer.parseInt("" + msg.charAt(1))];
                String[] sArray = msg.substring(2).split("m");
                int[] iArray = new int[5];
                for (int i = 0; i < 5; i++)
                {
                    iArray[i] = Integer.parseInt(sArray[i]);
                }
                s.inv.bezahl(iArray);
                break;
            }
            case 'o': //o20m0m0m12m3: Spieler 2 bekommt 12 Weizen und 3 Erz
            {
                Spieler s = Main.spieler[Integer.parseInt("" + msg.charAt(1))];
                String[] sArray = msg.substring(2).split("m");
                int[] iArray = new int[5];
                for (int i = 0; i < 5; i++)
                {
                    iArray[i] = Integer.parseInt(sArray[i]);
                    System.out.println(iArray[i]);
                }
                s.inv.hinzu(iArray);
                break;
            }
            case 'p':
            {
                if (Integer.parseInt("" + msg.charAt(1)) == Main.ich)
                {
                    Chat.displayMessage(msg.substring(1));
                }
                break;
            }
            case 'q':
            {
                String[] split = msg.substring(1).split("#");
                //new Fehler(split[0], split[1]);
                JOptionPane.showMessageDialog(Bildschirm.getF(), split[0], split[1], JOptionPane.PLAIN_MESSAGE);
                break;
            }
            case 'r':
            {
                if (Integer.parseInt("" + msg.charAt(1)) == Main.ich)
                {
                    String[] split = msg.substring(2).split("#");
                    //new Fehler(split[0], split[1]);
                    JOptionPane.showMessageDialog(Bildschirm.getF(), split[0], split[1], JOptionPane.PLAIN_MESSAGE);
                }
                break;
            }
            case 's': //s20m0m0m12m3: Das Inventar von Spieler 2 wird auf 0 0 0 12 3 gesetzt
            {
                Spieler s = Main.spieler[Integer.parseInt("" + msg.charAt(1))];
                String[] sArray = msg.substring(2).split("m");
                int[] iArray = new int[5];
                for (int i = 0; i < 5; i++)
                {
                    iArray[i] = Integer.parseInt(sArray[i]);
                }
                s.inv.rohstoffe = iArray;
                Bildschirm.anderePanelAkt();
                break;
            }
            case 't'://t132m5m-3m0m0 : Handelsangebot von 1 zu 3, dass du dieses Inventar bekommst
            {
                if (Integer.parseInt("" + msg.charAt(2)) == Main.ich)
                {
                    Spieler s1 = Main.spieler[Integer.parseInt("" + msg.charAt(1))];
                    Spieler s2 = Main.ich();
                    int[] inv = msgToInv(msg.substring(3));
                    StringBuilder optionMessage = new StringBuilder(Nuz.HANDEL_ANGEBOTPOPUP_FRAGE);
                    for (int i = 0; i < 5; i++)
                    {
                        if (inv[i] != 0)
                        {
                            optionMessage.append(inv[i]).append(Nuz.rohstoffName(i, inv[i] != 1)).append("\n");
                        }
                    }
                    int abfrage = JOptionPane.showOptionDialog(Bildschirm.getF(), optionMessage.toString(),
                            Nuz.HANDEL_ANGEBOTSPOPUP_TITEL + msg.charAt(1), JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null,
                            new String[]{Nuz.PLAYER_TRADE_AGREE, Nuz.PLAYER_TRADE_REFUSE}, Nuz.PLAYER_TRADE_AGREE);
                    System.out.println(abfrage);
                    if (abfrage == 0)
                    {
                        int[] antiInv = new int[5];
                        for (int i = 0; i < 5; i++)
                        {
                            antiInv[i] = inv[i] * -1;
                        }
                        OnlineInterpreter.bekommen(s1, antiInv);
                        OnlineInterpreter.bekommen(s2, inv);
                        break;
                    }
                }
                break;
            }
            case 'u'://u034Erstes Spiel#112Zweites Spiel: Im Spiel "Erstes Spiel" gibt es 3 von 4 Spielern, ID ist 0,
                // im Spiel "Zweites Spiel" gibt es 1 von 2 Spielern, ID ist 1
            {
                if (msg.length() == 1)
                {
                    String name = (String) JOptionPane.showInputDialog(Bildschirm.getF(),
                            Nuz.ERSTES_SPIEL_FRAGE,
                            Nuz.SPIEL_ERSTELLEN_TITEL, JOptionPane.QUESTION_MESSAGE, null, null, Nuz.DEFAULT_SPIEL_NAME);
                    mpSpielErstellenOderLaden(name);
                    break;
                }
                String[] sA = msg.substring(1).split("#");
                for (String s : sA)
                {
                    String name = s.substring(3);
                    int ID = Integer.parseInt("" + s.charAt(0));
                    int momSpieler = Integer.parseInt("" + s.charAt(1));
                    int maxSpieler = Integer.parseInt("" + s.charAt(2));
                    Game.addGame(new Game(name, ID, momSpieler, maxSpieler));
                }
                int anzahlSpiele = Game.games.size();
                int erstellenOderBeitreten = JOptionPane.showOptionDialog(Bildschirm.getF(),
                        Nuz.spielErstellenOderBeitretenFrage(anzahlSpiele), Nuz.SPIEL_ERSTELLEN_ODER_BEITRETEN_TITEL,
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        new String[]{Nuz.SPIEL_ERSTELLEN_TITEL, Nuz.SPIEL_ERSTELLEN_ODER_BEITRETEN_BEITRETEN},
                        Nuz.SPIEL_ERSTELLEN_ODER_BEITRETEN_BEITRETEN);
                if (erstellenOderBeitreten == 0)
                {
                    String name = (String) JOptionPane.showInputDialog(Bildschirm.getF(),
                            Nuz.SPIEL_ERSTELLEN_NAME,
                            Nuz.SPIEL_ERSTELLEN_TITEL, JOptionPane.QUESTION_MESSAGE, null, null, Nuz.DEFAULT_SPIEL_NAME);
                    String maxAnzahlSpielerString = (String) JOptionPane.showInputDialog(Bildschirm.getF(),
                            Nuz.SPIEL_ERSTELLEN_ZAHL,
                            Nuz.SPIEL_ERSTELLEN_TITEL, JOptionPane.QUESTION_MESSAGE, null, null, 4);
                    if (maxAnzahlSpielerString != null)
                    {
                        int maxAnzahlSpieler = Integer.parseInt(maxAnzahlSpielerString);
                        OnlineInterpreter.spielErstellen(name, maxAnzahlSpieler);
                    }
                    break;
                }
                Game auswahl = (Game) JOptionPane.showInputDialog(Bildschirm.getF(), Nuz.SPIEL_AUSWAEHLEN_FRAGE, Nuz.SPIEL_AUSWAEHLEN_TITEL,
                        JOptionPane.INFORMATION_MESSAGE, null, Game.games.toArray(), Game.games.get(0));
                spielBeitreten(auswahl.getID());
                break;
            }
            case 'v':
            {
                Main.beendeFrueh();
            }
            case '?':
            {
                if (!Main.lokal)
                {
                    throw new IllegalArgumentException(msg);
                }
                break;
            }

            default:
                throw new IllegalArgumentException("Unpassende Nachricht: " + msg);
        }
    }

    private static void senden(String msg)
    {
        Client.senden(msg);
    }

    private static void mpSpielErstellenOderLaden(String name)
    {
        int erstellenOderLadenResult = JOptionPane.showOptionDialog(Main.fenster, "Möchtest du ein neues Spiel" +
                        " erstellen oder ein altes laden?", "Erstellen oder laden?", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new String[]{"Neu erstellen", "Laden"}, "Neu erstellen");
        if (erstellenOderLadenResult == 0)
        {
            String maxAnzahlSpielerString = (String) JOptionPane.showInputDialog(Bildschirm.getF(),
                    Nuz.SPIEL_ERSTELLEN_ZAHL,
                    Nuz.SPIEL_ERSTELLEN_TITEL, JOptionPane.QUESTION_MESSAGE, null, null, 4);
            if (maxAnzahlSpielerString != null)
            {
                int maxAnzahlSpieler = Integer.parseInt(maxAnzahlSpielerString);
                OnlineInterpreter.spielErstellen(name, maxAnzahlSpieler);
            }
        }
        else if(erstellenOderLadenResult == 1)
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(Nuz.SAVEGAME_LOCATION));
            int fileSelecterReturn = chooser.showSaveDialog(Main.fenster);
            if(fileSelecterReturn == JFileChooser.APPROVE_OPTION)
            {
                List<String> list = readSaveFile(chooser.getSelectedFile());
                senden("?b4"+name);
                for(String aString : list)
                {
                    senden(aString);
                }
            }
        }
    }

    static List<String> readSaveFile(File file)
    {
        ArrayList<String> list = new ArrayList<>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String tmp;
            while((tmp = bufferedReader.readLine()) != null)
            {
                list.add(tmp);
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return list;
    }

    private static int[] msgToInv(String msg)
    {
        String[] sArray = msg.split("m");
        int[] iArray = new int[5];
        for (int i = 0; i < 5; i++)
        {
            iArray[i] = Integer.parseInt(sArray[i]);
        }
        return iArray;
    }

    private static String invToMsg(int[] inv)
    {
        if (inv.length != 5)
        {
            throw new IllegalArgumentException("Ungültige Länge für ein Inventar-Array: " + inv.length);
        }
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < 5; i++)
        {
            tmp.append(inv[i]).append("m");
        }
        return (tmp.toString());
    }

    static void chatNachricht(String msg)//a
    {
        senden("a" + msg);
    }

    private static void felderstapel(String felder)
    {
        senden("b" + felder);
    }

    private static void zahlenstapel(String zahlen)
    {
        senden("c" + zahlen);
    }

    static void strasseBauen(Spieler spieler, Kante kante)
    {
        senden("d" + spieler.id + "" + kante.getX() + "" + kante.getY() + "" + kante.pos);
    }

    static void siedlungBauen(Spieler spieler, Ecke ecke)
    {
        senden("e" + spieler.id + "" + ecke.getX() + "" + ecke.getY() + "" + ecke.pos);
    }

    static void stadtBauen(Spieler spieler, Ecke ecke)
    {
        senden("f" + spieler.id + "" + ecke.getX() + "" + ecke.getY() + "" + ecke.pos);
    }

    static void entwicklungskarteZiehen(Spieler spieler, int karte)
    {
        senden("g" + spieler.id + "" + karte);
    }

    static void wuerfel(int wurf)
    {
        if (wurf == 7)
        {
            Bandit.ausschicken();
            Main.siebenSteuer();
        }
        senden("h" + wurf);
    }


    static void entwicklungskarteAusspielen(Spieler spieler, int typ)
    {
        senden("i" + spieler.id + "" + typ);
    }

    static void raeuberVersetzen(Feld feld)
    {
        senden("j" + feld.getX() + "" + feld.getY());
    }

    private static void hafenStapel(String haefen)
    {
        senden("l" + haefen);
    }

    static void spielStarten()
    {
        if (Main.lokal)
        {
            senden("m" + JOptionPane.showInputDialog(Bildschirm.getF(), Nuz.LOKAL_SPIELERANZAHL_AUSWAEHLEN_FRAGE, "4"));
        } else
        {
            senden("?d");
        }
    }

    static void spielVerlassen()
    {
        senden("?e");
    }

    static void bezahlen(Spieler spieler, int[] inv)
    {
        if (inv.length != 5)
        {
            throw new IllegalArgumentException("Ungültige Länge für ein Inventar-Array: " + inv.length);
        }
        StringBuilder tmp = new StringBuilder("n" + spieler.id);
        for (int i = 0; i < 5; i++)
        {
            tmp.append(inv[i]).append("m");
        }
        senden(tmp.toString());
    }

    static void bekommen(Spieler spieler, int[] inv)
    {
        if (inv.length != 5)
        {
            throw new IllegalArgumentException("Ungültige Länge für ein Inventar-Array: " + inv.length);
        }
        StringBuilder tmp = new StringBuilder("o" + spieler.id);
        for (int i = 0; i < 5; i++)
        {
            tmp.append(inv[i]).append("m");
        }
        senden(tmp.toString());
    }

    @SuppressWarnings("unused")
    public static void privatChatNachricht(Spieler spieler, String msg)
    {
        senden("p" + spieler.id + msg);
    }

    @SuppressWarnings("unused")
    public static void publicPopup(String titel, String nachricht)
    {
        senden("q" + nachricht + "#" + titel);
    }

    static void privatPopup(Spieler spieler, @SuppressWarnings("SameParameterValue") String titel, String nachricht)
    {
        senden("r" + spieler.id + nachricht + "#" + titel);
    }

    static void setInventar(Spieler spieler, int[] inv)
    {
        if (inv.length != 5)
        {
            throw new IllegalArgumentException("Ungültige Länge für ein Inventar-Array: " + inv.length);
        }
        StringBuilder tmp = new StringBuilder("s" + spieler.id);
        for (int i = 0; i < 5; i++)
        {
            tmp.append(inv[i]).append("m");
        }
        senden(tmp.toString());
    }

    static void spielerHandel(Spieler s1, Spieler s2, int[] inv)
    {
        invToMsg(inv);
        senden("t" + s1 + "" + s2 + invToMsg(inv));
    }

    @SuppressWarnings("WeakerAccess")
    static void spielErstellen(String name, int maxAnzahlSpieler)
    {
        senden("?b" + maxAnzahlSpieler + name);
    }

    @SuppressWarnings("WeakerAccess")
    static void spielBeitreten(int ID)
    {
        senden("?c" + ID);
    }
}
