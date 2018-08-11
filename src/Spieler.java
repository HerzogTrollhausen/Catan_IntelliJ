import javax.swing.*;
import java.awt.Color;
import java.util.ArrayList;

public class Spieler {
    Inventar inv;
    ArrayList<Siedlung> siedlungen = new ArrayList<>();
    Color farbe;
    Inventar letzteErnte;
    byte id;//0 == rot, 1 == blau, 2 == weiß, 3 == orange
    int anzahlSiedlungen, anzahlStaedte, anzahlStrassen, anzahlGelegteRitter;
    int rittermachtSiegpunkte = 0;
    @SuppressWarnings("WeakerAccess")
    public int handelsstrasse = 0;
    ArrayList<Kante> strassen = new ArrayList<>();

    public Spieler(byte id) {
        this.id = id;
        switch (id) {
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
        inv = new Inventar(Main.testmodus ? Inventar.Inventararten.TEST : Inventar.Inventararten.START,
                this);
    }


    void ernte(int wuerfel) {
        letzteErnte = new Inventar();
        for (Siedlung aSiedlungen : siedlungen) {
            int[] tmp = aSiedlungen.ernte(wuerfel);
            inv.hinzu(tmp);
            letzteErnte.hinzu(tmp);
        }
    }

    int anzahlEntwicklungskarten(int typ) {
        return inv.anzahlEntwicklungskarten(typ);
    }

    static int randomRohstoff(int[] inv) {
        int rand = (int) (Math.random() * sumOfArray(inv));
        int untergrenze = 0;
        for (int j = 0; j < 5; j++) {
            if (rand >= untergrenze && rand <= untergrenze - 1 + inv[j]) {
                return j;
            } else {
                untergrenze += inv[j];
            }
        }
        return -1;
    }

    private static int sumOfArray(int[] a) {
        int tmp = 0;
        for (int anA : a) {
            tmp += anA;
        }
        return tmp;
    }

    void ziehEntwicklungskarte(int nr) {
        int typ = Entwicklungskarten.stapel.get(nr, true).intInhalt;
        inv.entwicklungskarten[typ]++;
    }


    int siegPunkte() {
        return anzahlSiedlungen + 2 * anzahlStaedte + anzahlEntwicklungskarten(1) + rittermachtSiegpunkte + handelsstrasse;
    }

    public void handelsStrasseErmitteln() {
        int tmp = 0;
        for (Kante aKante : strassen) {
            for (Kante bKante : strassen) {
                bKante.abgelaufen = false;
            }
            int tmpTiefe = aKante.tiefeFinden();
            System.out.println(tmpTiefe);
            tmp = Math.max(tmp, tmpTiefe);
        }
        JOptionPane.showMessageDialog(Bildschirm.getF(), tmp);
    }

    public String toString() {
        return Nuz.spielerToString(id);
    }
}