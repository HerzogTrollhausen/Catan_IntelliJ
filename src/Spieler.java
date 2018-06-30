import java.awt.Color;
import java.util.ArrayList;

public class Spieler
{
    Inventar inv;
    ArrayList<Siedlung> siedlungen = new ArrayList<Siedlung>();
    Color farbe;
    Inventar letzteErnte;
    byte id;//0 == rot, 1 == blau, 2 == weiß, 3 == orange
    String name;
    int anzahlSiedlungen, anzahlStaedte, anzahlStrassen, anzahlGelegteRitter;
    public int rittermacht = 0;
    public int handelsstrasse;

    public Spieler(byte id)
    {
        this.id = id;
        name = ""+id;
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

    public void ernte(int wuerfel)
    {
        letzteErnte = new Inventar();
        for (int i = 0; i < siedlungen.size(); i++)
        {
            int[] tmp = siedlungen.get(i).ernte(wuerfel);
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
            }
            else
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
        return anzahlSiedlungen+2*anzahlStaedte+anzahlEntwicklungskarten(1)+rittermacht+handelsstrasse;
    }

    public String toString()
    {
        return "Spieler "+id;
    }
}