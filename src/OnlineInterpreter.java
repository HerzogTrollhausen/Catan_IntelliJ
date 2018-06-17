public class OnlineInterpreter
{
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
     * m: Starte das Spiel
     * n20m0m0m12m3: Spieler 2 bezahlt 12 Weizen und 3 Erz
     * o20m0m0m12m3: Spieler 2 bekommt 12 Weizen und 3 Erz
     * p1Hallo: Chatnachricht an Spieler 1: Hallo
     * qGruß#Hallo: Popup an alle: Titel Gruß, nachricht Hallo
     * r1Gruß#Hallo: Popup an Spieler 1: Titel Gruß, Nachricht Hallo
     * s20m0m0m12m3: Das Inventar von Spieler 2 wird auf 0 0 0 12 3 gesetzt
     */
    public static void interpret(String msg)
    {
        System.out.println(msg);
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
                    zahlenstapel(Welt.stapelZahlen("Zahl"));
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
                    hafenStapel(Welt.stapelZahlen("Hafen"));
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
                int x = Integer.parseInt("" + msg.charAt(1));
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
                Main.ich = Integer.parseInt(msg.substring(1));
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
            case 'm'://m: Starte das Spiel
            {
                if (Main.starter)
                {
                    felderstapel(Welt.stapelZahlen("Feld"));
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
                new Fehler(split[0], split[1]);
                break;
            }
            case 'r':
            {
                if (Integer.parseInt("" + msg.charAt(1)) == Main.ich)
                {
                    String[] split = msg.substring(2).split("#");
                    new Fehler(split[0], split[1]);
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

            default:
                System.out.println("Unpassende Nachricht: " + msg);
        }
    }

    private static void senden(String msg)
    {
        Client.senden(msg);
    }

    public static void chatNachricht(String msg)//a
    {
        senden("a" + msg);
    }

    public static void felderstapel(String felder)
    {
        senden("b" + felder);
    }

    public static void zahlenstapel(String zahlen)
    {
        senden("c" + zahlen);
    }

    public static void strasseBauen(Spieler spieler, Kante kante)
    {
        senden("d" + spieler.id + "" + kante.getX() + "" + kante.getY() + "" + kante.pos);
    }

    public static void siedlungBauen(Spieler spieler, Ecke ecke)
    {
        senden("e" + spieler.id + "" + ecke.getX()+ "" + ecke.getY() + "" + ecke.pos);
    }

    public static void stadtBauen(Spieler spieler, Ecke ecke)
    {
        senden("f" + spieler.id + "" + ecke.getX() + "" + ecke.getY() + "" + ecke.pos);
    }

    public static void entwicklungskarteZiehen(Spieler spieler, int karte)
    {
        //TODO
    }

    public static void wuerfel(int wurf)
    {
        if(wurf == 7)
        {
            Main.siebenSteuer();
        }
        senden("h" + wurf);
    }


    public static void entwicklungskarteAusspielen(Spieler spieler, int typ)
    {
        senden("i" + spieler.id + "" + typ);
    }

    public static void raeuberVersetzen(Feld feld)
    {
        senden("j" + feld.getX() + "" + feld.getY());
    }

    public static void hafenStapel(String haefen)
    {
        senden("l" + haefen);
    }

    public static void spielStarten()
    {
        senden("m");
    }

    public static void bezahlen(Spieler spieler, int[] inv)
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

    public static void bekommen(Spieler spieler, int[] inv)
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

    public static void privatChatNachricht(Spieler spieler, String msg)
    {
        senden("p" + spieler.id + msg);
    }

    public static void publicPopup(String titel, String nachricht)
    {
        senden("q" + nachricht + "#" + titel);
    }

    public static void privatPopup(Spieler spieler, String titel, String nachricht)
    {
        senden("r" + spieler.id + nachricht + "#" + titel);
    }

    public static void setInventar(Spieler spieler, int[] inv)
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


}
