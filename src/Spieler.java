import java.awt.Color;
import java.util.ArrayList;

public class Spieler
{
    Inventar inv;
    ArrayList<Siedlung> siedlungen = new ArrayList<>();
    Color farbe;
    Inventar letzteErnte;
    byte id;//0 == rot, 1 == blau, 2 == weiß, 3 == orange
    int anzahlSiedlungen, anzahlStaedte, anzahlStrassen, anzahlGelegteRitter;
    public int rittermachtSiegpunkte = 0;
    @SuppressWarnings("WeakerAccess")
    public int handelsstrasse = 0;

    public Spieler(byte id)
    {
        this.id = id;
        switch (id)
        {
            case 0:
                farbe = new Color(255, 0, 0);
                break;
            case 1:
                farbe = new Color(0, 0, 255);
                break;
            case 2:
                farbe = new Color(255, 255, 255);
                break;
            case 3:
                farbe = new Color(255, 140, 0);
        }
        inv = new Inventar(4);
    }

    private String farbeString()
    {
        switch (id)
        {
            case 0:
                return "Rot";
            case 1:
                return "Blau";
            case 2:
                return "Weiß";
            case 3:
                return "Orange";
            default:
                throw new IllegalArgumentException("Komische id: " + id);
        }
    }

    public void ernte(int wuerfel)
    {
        letzteErnte = new Inventar();
        for (Siedlung aSiedlungen : siedlungen)
        {
            int[] tmp = aSiedlungen.ernte(wuerfel);
            inv.hinzu(tmp);
            letzteErnte.hinzu(tmp);
        }
    }

    public int anzahlEntwicklungskarten(int typ)
    {
        return inv.anzahlEntwicklungskarten(typ);
    }

    static int randomRohstoff(int[] inv)
    {
        int rand = (int) (Math.random() * sumOfArray(inv));
        int untergrenze = 0;
        for (int j = 0; j < 5; j++)
        {
            if (rand >= untergrenze && rand <= untergrenze - 1 + inv[j])
            {
                return j;
            } else
            {
                untergrenze += inv[j];
            }
        }
        return -1;
    }

    private static int sumOfArray(int[] a)
    {
        int tmp = 0;
        for (int anA : a)
        {
            tmp += anA;
        }
        return tmp;
    }

    public void ziehEntwicklungskarte(int nr)
    {
        int typ = Entwicklungskarten.stapel.get(nr, true).intInhalt;
        inv.entwicklungskarten[typ]++;
    }


    public int siegPunkte()
    {
        return anzahlSiedlungen + 2 * anzahlStaedte + anzahlEntwicklungskarten(1) + rittermachtSiegpunkte + handelsstrasse;
    }

    public String toString()
    {
        return "Spieler " + id+ " ("+farbeString()+")";
    }
}