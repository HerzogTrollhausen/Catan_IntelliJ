import java.awt.Image;
import javax.swing.JButton;
import java.io.IOException;

/**
 * Ecke 0 ist oben links, danach im Uhrzeigersinn
 * <p>
 * <p>
 * 1
 * / \
 * /   \
 * /     \
 * 0       2
 * |       |
 * |       |1.154
 * |       |
 * 5       3
 * \     /
 * \   /
 * \ /
 * 4
 * <p>
 * Felder gehen von links nach rechts
 */
public class Ecke extends RectangleImage
{
    Feld[] felder = new Feld[3];
    Kante[] kanten = new Kante[3];
    private static Image leer = Buz.ECKE_LEER;
    static private Image[][] siedlungen = new Image[4][2];
    private Siedlung siedlung;
    Feld feld;
    int pos;

    Ecke(Feld feld, int pos)
    {
        super(leer, 0, 0);
        this.pos = pos;
        this.feld = feld;
        if (siedlungen[0][0] == null)
        {
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 2; j++)
                {
                    //System.out.println("Bilder/Siedlungen/"+entwicklungskarteAuspielen+"_"+j+".gif");
                    try
                    {
                        siedlungen[i][j] = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Siedlungen/" + i + "_" + j + ".gif"),
                                Guz.eckeX(), Guz.eckeY(), false);
                    } catch (IOException e)
                    {
                        e.printStackTrace();//TODO: Das Ganze irgendwie auslagern nach Buz
                    }
                }
            }
        }

        addFelder(feld, pos);
        if ((felder[0] != null && felder[1] != null && felder[2] != null) && !(felder[0].art == 6 && felder[1].art == 6 && felder[2].art == 6))
        {
            double xr = 0;
            double yr = 0;
            if (pos == 1 || pos == 4)
            {
                xr = 0.5;
            } else if (pos == 2 || pos == 3)
            {
                xr = 1;
            }
            if (pos == 0 || pos == 2)
            {
                yr = 0.25 * 1.154;
            } else if (pos == 5 || pos == 3)
            {
                yr = 0.75 * 1.154;
            } else if (pos == 4)
            {
                yr = 1;
            }
            bewegen((feld.rechteck.getX() + xr * Guz.feldX()) - 0.5 * Guz.eckeX(),
                    (feld.rechteck.getY() + yr * Guz.feldY()) - 0.5 * Guz.eckeY());
            Bildschirm.grafikobjekte.add(this);

            JButton button = new JButton();
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            Main.bildschirm.add(button);
            button.setBounds((int) rechteck.getX(), (int) rechteck.getY(), (int) rechteck.getWidth(), (int) rechteck.getHeight());
            Ecke tmp = this;
            button.addActionListener(e ->
            {
                Welt.dunkel();
                for (int i = 0; i < 3; i++)
                {
                    if (felder[i] != null)
                    {
                        felder[i].hell = true;
                        felder[i].refreshBild();
                    }
                }
                Eckpanel eckpanel = Bildschirm.getEckpanel();
                eckpanel.leerEcke(tmp);
            });
        }

    }

    public void addSiedlung(Spieler spieler)
    {
        if (siedlung == null)
        {
            siedlung = new Siedlung(this, spieler);
        } else
        {
            throw new IllegalStateException("Hier wird versucht, eine Siedlung zu erstellen, wo es schon eine gibt");
        }
        aktBild();
    }

    public boolean keinNachbar()
    {
        for (int i = 0; i < 3; i++)
        {
            if (kanten[i] != null && kanten[i].gegen(this).siedlung != null)
            {
                return false;
            }
        }
        return true;
    }

    public Siedlung getSiedlung()
    {
        return siedlung;
    }

    public void addKanten(Kante kante)
    {
        for (int i = 0; i < 3; i++)
        {
            if (kanten[i] == null)
            {
                kanten[i] = kante;
                return;
            }
        }
        throw new IllegalArgumentException("Ecke.java; void addKanten(Kante kante): Alle Kanten voll!");
    }

    public void zuStadt()
    {
        siedlung.zuStadt();
        aktBild();
    }

    public int[] ernte(int wuerfel)
    {
        int[] tmp = new int[7];
        for (int i = 0; i < 3; i++)
        {
            if (felder[i].wert == wuerfel && !felder[i].besetzt)
            {
                tmp[felder[i].art]++;
            }
        }
        return tmp;
    }

    /**
     * @return Gibt den Besitzer der zugehörigen Siedlung zurück. Gibt null zurück, wenn es keine Siedlung gibt.
     */
    Spieler getSpieler()
    {
        if (siedlung != null)
        {
            return siedlung.spieler;
        } else
        {
            return null;
        }
    }

    private void addFeld(int x, int y, int stelle, int pos)
    {
        if (Welt.doesFeldExist(x, y))
        {
            felder[stelle] = Welt.getFeld(x, y);
            felder[stelle].setEcke(this, pos);
        }
    }

    public int getX()
    {
        return feld.getX();
    }

    public int getY()
    {
        return feld.getY();
    }

    private void addFelder(Feld feld, int pos)
    {
        if(pos == 0)
        {
            felder[2] = feld;
            addFeld(getX(), getY(), 1, 4);
            addFeld(getX()-1, getY(), 0, 2);
        }
        if(pos == 1)
        {
            felder[1] = feld;
            addFeld(getX()+1, getY()-1, 2, 5);
            addFeld(getX(), getY()-1, 0, 3);
        }
        if(pos == 2)
        {
            felder[0] = feld;
            addFeld(getX()+1, getY()-1, 1, 4);
            addFeld(getX()+1, getY(), 2, 0);
        }
        if(pos == 3)
        {
            felder[0] = feld;
            addFeld(getX(), getY()+1, 1, 1);
            addFeld(getX()+1, getY(), 2, 5);
        }
        if(pos == 4)
        {
            felder[1] = feld;
            addFeld(getX()-1, getY()+1, 0, 2);
            addFeld(getX(), getY()+1, 2, 0);
        }
        if(pos == 5)
        {
            felder[2] = feld;
            addFeld(getX()-1, getY()+1, 1, 1);
            addFeld(getX()-1, getY(), 0, 3);
        }
    }

    private void aktBild()
    {
        if (siedlung == null)
        {
            img = leer;
        } else
        {
            img = siedlungen[siedlung.spieler.id][siedlung.art];
        }
    }

    public boolean nachbar(Spieler spieler)
    {
        for (int i = 0; i < 3; i++)
        {
            if (kanten[i].spieler == spieler)
            {
                return true;
            }
        }
        return false;
    }

    int getKante(Kante kante)
    {
        for (int i = 0; i < 3; i++)
        {
            if (kanten[i] == kante)
            {
                return i;
            }
        }
        throw new IllegalArgumentException("Ecke.java; int getKante(Kante kante): kante nicht gefunden.");
    }
}