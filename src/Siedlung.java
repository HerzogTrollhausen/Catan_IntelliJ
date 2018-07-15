public class Siedlung
{
    Spieler spieler;
    private Ecke ecke;
    byte art;//0 Siedlung, 1 Stadt, 2 Metropole, 3 Fort, 4 Burg, 5 Kloster, 6 Kathedrale
    Siedlung(Ecke ecke, Spieler spieler)
    {
        this.ecke = ecke;
        this.spieler = spieler;
        spieler.siedlungen.add(this);
    }

    public void zuStadt()
    {
        art = 1;
    }

    public int[] ernte(int wuerfel)
    {
        int[] tmp = ecke.ernte(wuerfel);
        int[] ergebnis = new int[5];
        for(int i = 0; i < 5; i++)
        {
            ergebnis[i] = art == 1 ? tmp[i]*2 : tmp[i];
        }
        return ergebnis;
    }
}