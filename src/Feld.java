import java.awt.Image;
import java.util.ArrayList;

/**
 * Ecke 0 ist oben links, danach im Uhrzeigersinn
 * Entsprechende Kante ist immer im Uhrzeigersinn der Ecke.
 * Felder gehen von links nach rechts
 */
public class Feld extends RectangleImage
{
    boolean besetzt;
    Ecke[] ecken;
    Kante[] kanten;
    int wert;
    private int x;
    private int y;
    int art = 6; // 0 Holz, 1 Lehm, 2 Schaf, 3 Weizen, 4 Erz, 5 Wueste, 6 Meer
    boolean hell;

    public Feld(double x, int y)
    {
        super(FileManager.createResizedCopy(Buz.FELD_HOLZ, Bildschirm.feldb, Bildschirm.feldh, false), Bildschirm.ox + Bildschirm.feldb * x + y * 0.5 * Bildschirm.feldb, Bildschirm.oy + y * (int) (Bildschirm.feldh * 0.75));
        //super(lade(art), Bildschirm.ox+Bildschirm.feldb*x+y*0.5*Bildschirm.feldb, Bildschirm.oy+y*(int)(Bildschirm.feldh*0.75));
        this.x = (int) x;
        this.y = y;
        ecken = new Ecke[6];
        kanten = new Kante[6];
        refreshBild();
    }

    public void refreshBild()
    {
        img = geladen();
    }

    public void setEcke(Ecke ecke, int pos)
    {
        ecken[pos] = ecke;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    private Image geladen()
    {
        switch (art)
        {
            case 0:
                return hell ? Buz.FELD_HOLZ_HELL : Buz.FELD_HOLZ;
            case 6:
                return hell ? Buz.FELD_MEER_HELL : Buz.FELD_MEER;
            case 5:
                return hell ? Buz.FELD_WUESTE_HELL : Buz.FELD_WUESTE;
            case 2:
                return hell ? Buz.FELD_WEIZEN_HELL : Buz.FELD_WEIZEN;
            case 1:
                return hell ? Buz.FELD_LEHM_HELL : Buz.FELD_LEHM;
            case 4:
                return hell ? Buz.FELD_ERZ_HELL : Buz.FELD_ERZ;
            case 3:
                return hell ? Buz.FELD_SCHAF_HELL : Buz.FELD_SCHAF;
            default:
                throw new IllegalArgumentException("Im Switchzeug is was falsch: " + art+""+hell);
        }
    }


    public void ecken()
    {
        for (int i = 0; i < 6; i++)
        {
            if (ecken[i] == null)
            {
                ecken[i] = new Ecke(this, i);
            }
        }
    }

    public void kanten()
    {
        for (int i = 0; i < 6; i++)
        {
            if (kanten[i] == null)
            {
                kanten[i] = new Kante(Buz.KANTE_LEER, this, i);
            }
        }
    }

    public void land()
    {
        art = Welt.felderArray[Welt.felderI];
        Welt.felderI++;
        if (art != 6 && art != 5)
        {
            wert = Welt.plaettchenArray[Welt.plaettchenI];
            Welt.plaettchenI++;
            Bildschirm.grafikobjekte.add(new Zahlplatte(this, wert, (rechteck.x + (rechteck.width / 2)) - (int) (0.5 * (Bildschirm.feldb / Bildschirm.plattenr)), rechteck.y + rechteck.height / 2 - (int) (0.5 * Bildschirm.feldh / Bildschirm.plattenr)));
        }
        refreshBild();
    }

    public static int weiter(int pos, int hoch)
    {
        for (int i = 0; i < hoch; i++)
        {
            if (pos < 5)
            {
                pos++;
            } else
            {
                pos = 0;
            }
        }
        return pos;
    }

    /**
     * Gibt den Index einer Kante im Kantenarray aus
     */
    int getKante(Kante kante)
    {
        for (int i = 0; i < 6; i++)
        {
            if (kanten[i] == kante)
            {
                return i;
            }
        }
        System.err.println("Feld.java; int getKante(Kante kante): kante nicht gefunden.");
        throw new IllegalArgumentException("Feld.java; int getKante(Kante kante): kante nicht gefunden.");
    }

    void ausrauben(Spieler spieler)
    {
        int rand = (int) (Math.random() * spieler.inv.anzahlAnRohstoffen());
        int[] r = spieler.inv.rohstoffe;
        int untergrenze = 0;
        for (int j = 0; j < 5; j++)
        {
            if (rand >= untergrenze && rand <= untergrenze - 1 + r[j])
            {
                //r[j]--;
                int[] beute = new int[5];
                beute[j] = 1;
                OnlineInterpreter.bezahlen(spieler, beute);
                OnlineInterpreter.bekommen(Main.spieler(), beute);
                OnlineInterpreter.privatPopup(Main.spieler(), Nuz.RAUB_NOTIFICATION_TITLE,
                        Nuz.RAUB_ATTACKER_NOTIFICATION_MESSAGE_1 + Inventar.name(j) + Nuz.RAUB_ATTACKER_NOTIFICATION_MESSAGE_2);
                OnlineInterpreter.privatPopup(spieler, Nuz.RAUB_NOTIFICATION_TITLE,
                        Nuz.RAUB_DEFENDER_NOTIFICATION_MESSAGE_1 + Inventar.name(j) + Nuz.RAUB_DEFENDER_NOTIFICATION_MESSAGE_2);
                Bildschirm.anderePanelAkt();
                return;
            } else
            {
                untergrenze += r[j];
            }
        }


    }

    public Spieler[] angrenzendeSpieler()
    {
        /*int[] tmp = new int[Main.anzahlSpieler];
        for (int i = 0; i < 6; i++)
        {
            if (ecken[i].getSpieler() != null)
            {
                tmp[ecken[i].getSpieler().id]++;
            }
        }
        int anzahlSpieler = 0;
        for (int i = 0; i < tmp.length; i++)
        {
            if (tmp[i] != 0)
            {
                anzahlSpieler++;
            }
        }
        int[] endlich = new int[anzahlSpieler];
        int j = 0;
        for (int i = 0; i < tmp.length; i++)
        {
            if (tmp[i] != 0)
            {
                endlich[j] = i;
                j++;
            }
        }
        return endlich;*/
        ArrayList<Spieler> aL = new ArrayList<>();
        for(int i = 0; i < 6; i++)
        {
            Spieler tmp = ecken[i].getSpieler();
            if(tmp != null && !aL.contains(tmp))
            {
                aL.add(tmp);
            }
        }
        return aL.toArray(new Spieler[0]);
    }
}