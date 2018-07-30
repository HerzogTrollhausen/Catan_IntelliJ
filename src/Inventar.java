public class Inventar
{
    int[] rohstoffe;//0 holz, 1 lehm, 2 schaf, 3 weizen, 4 erz
    int[] entwicklungskarten; // 0 Ritter, 1 Siegpunkt, 2 Straßenbau, 3 Erfindung, 4 Monopol

    static int[] strasse = {1, 1, 0, 0, 0};
    static int[] siedlung = {1, 1, 1, 1, 0};
    static int[] stadt = {0, 0, 0, 1, 1};
    static int[] entwicklung = {0, 0, 1, 1, 1};
    private static int[] test = {10, 10, 10, 10, 10};
    private static int[] start = {1, 1, 1, 1, 1};

    Spieler spieler;

    public enum Inventararten
    {
        START, TEST, STRASSE, SIEDLUNG, STADT, ENTWICKLUNG
    }

    Inventar()
    {
        rohstoffe = new int[5];
        entwicklungskarten = new int[5];//0 holz, 1 lehm, 2 schaf, 3 weizen, 4 erz
    }


    Inventar(Inventararten arg, Spieler spieler)
    {
        this(arg);
        this.spieler = spieler;
    }

    Inventar(Inventararten arg)//0 Straße, 1 Siedlung, 2 Stadt, 3 Entwicklung, 4 test
    {
        rohstoffe = new int[5];
        entwicklungskarten = new int[5];
        switch (arg)
        {
            case SIEDLUNG:
                rohstoffe = siedlung.clone();
                break;
            case STRASSE:
                rohstoffe = strasse.clone();
                break;
            case STADT:
                rohstoffe = stadt.clone();
                break;
            case ENTWICKLUNG:
                rohstoffe = entwicklung.clone();
                break;
            case TEST:
                rohstoffe = test.clone();
                entwicklungskarten = test.clone();
                break;
            case START:
                rohstoffe = start.clone();
                break;
        }
    }

    public boolean bezahlbar(Inventar ab)
    {
        for (int i = 0; i < 5; i++)
        {
            if (rohstoffe[i] < ab.rohstoffe[i])
            {
                return false;
            }
        }
        return true;
    }

    public void bezahl(int[] ab)
    {
        for (int i = 0; i < 5; i++)
        {
            rohstoffe[i] = rohstoffe[i] - ab[i];
        }
        Bildschirm.anderePanelAkt();
    }

    public int anzahlAnRohstoffen()
    {
        int tmp = 0;
        for (int i = 0; i < 5; i++)
        {
            tmp += rohstoffe[i];
        }
        return tmp;
    }

    public void hinzu(int[] zu) throws IllegalArgumentException
    {
        for (int i = 0; i < 5; i++)
        {
            if (rohstoffe[i] + zu[i] >= 0)
            {
                rohstoffe[i] = rohstoffe[i] + zu[i];
            } else
            {
                throw new IllegalArgumentException("Rohstoffe gehen unter null");
        }
        }
        Bildschirm.anderePanelAkt();
    }

    public int anzahlEntwicklungskarten(int typ)
    {
        return entwicklungskarten[typ];
    }
}