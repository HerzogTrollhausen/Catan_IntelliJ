public class Welt
{
    static Feld[][] felder;
    static private int yn;
    static private int xn;
    static int[] haefenArray, felderArray, plaettchenArray;
    static int plaettchenI, felderI;

    public static void initWelt(int basis)
    {
        yn = basis * 2 + 1;
        xn = yn;
        felder = new Feld[yn][xn];
        for (int i = 0; i < yn; i++)
        {
            for (int j = 0; j < xn; j++)
            {
                felder[i][j] = new Feld(j, i);
            }
        }
        insel(basis);
        for (int i = 0; i < yn; i++)
        {
            for (int j = 0; j < xn; j++)
            {
                felder[i][j].ecken();
            }
        }
        for (int i = 0; i < yn; i++)
        {
            for (int j = 0; j < xn; j++)
            {
                felder[i][j].kanten();
            }
        }
        //Client.senden(hafenStapelZahlen());
        haefen(haefenArray);
    }

    public static boolean doesFeldExist(int x, int y)
    {
        return !(x < 0 || y < 0 || x >= felder[0].length || y >= felder.length);
    }

    public static Feld getFeld(int x, int y)
    {
        return felder[y][x];
    }

    public static void dunkel()
    {
        for (int i = 0; i < yn; i++)
        {
            for (int j = 0; j < xn; j++)
            {
                felder[i][j].hell = false;
                felder[i][j].refreshBild();
            }
        }
    }

    private static void insel(int basis)
    {
        for (int i = 0; i < basis; i++)
        {
            for (int j = basis - i; j < 2 * basis; j++)
            {
                felder[i + 1][j].land();
            }
        }
        for (int i = 0; i < basis - 1; i++)
        {
            for (int j = 1; j < 2 * (basis - 1) - i + 1; j++)
            {
                felder[basis + i + 1][j].land();
            }
        }
    }

    public static String stapelZahlen(Stapel.StapelTypen typ)
    {
        StringBuilder tmp = new StringBuilder();
        Stapel stapel = new Stapel(typ);
        while (stapel.oben != null)
        {
            if (!typ.equals(Stapel.StapelTypen.Zahl))
            {
                tmp.append(stapel.randomZahl());
            } else
            {
                tmp.append(Integer.toHexString(stapel.randomZahl()));
            }
        }
        return tmp.toString();
    }

    private static void haefen(int[] a)
    {
        kZH(4, 0, 4, a[0]);
        kZH(5, 1, 2, a[1]);
        kZH(5, 3, 3, a[2]);
        kZH(3, 5, 4, a[3]);
        kZH(0, 5, 2, a[4]);
        kZH(1, 2, 3, a[5]);
        kZH(2, 1, 3, a[6]);
        kZH(5, 2, 2, a[7]);
        kZH(2, 5, 4, a[8]);
    }

    private static void kZH(int x, int y, int pos, int typ)
    {
        felder[y][x].kanten[pos].zuHafen(typ);
    }

    static void addStrasse(int x, int y, int pos, int spieler)
    {
        Kante kante = felder[y][x].kanten[pos];
        kante.spieler = Main.spieler[spieler];
        Main.spieler[spieler].anzahlStrassen++;
        Main.spieler[spieler].strassen.add(kante);
        Main.fruehsiedlung = true;
        kante.aktBild();
        Bildschirm.getEckpanel().weg(kante);
        //Main.spieler[spieler].handelsStrasseErmitteln();//TODO
        if (Main.frueh)
        {
            Spielerpanel.akt();
        }
    }

    static void addSiedlung(int x, int y, int pos, int spieler)
    {
        Ecke ecke = felder[y][x].ecken[pos];
        ecke.addSiedlung(Main.spieler[spieler]);
        Main.spieler[spieler].anzahlSiedlungen++;
        if (Main.frueh)
        {
            Bildschirm.anderePanelAkt();
        }
    }

    static void zuStadt(int x, int y, int pos, int spieler)
    {
        Ecke ecke = felder[y][x].ecken[pos];
        Main.spieler[spieler].anzahlSiedlungen--;
        Main.spieler[spieler].anzahlStaedte++;
        ecke.zuStadt();
    }
}