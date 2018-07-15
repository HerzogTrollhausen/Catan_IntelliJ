import java.awt.*;

public class Hafen extends Kante
{
    int typ; //0-4 entsprechendes Gut 2:1, 5 ist alles 3:1
    static Image[] haefen;

    Hafen(int typ)
    {
        super(haefen[typ]);
        this.typ = typ;
        hafen = true;
        //img = FileManager.createResizedCopy(img, 50, 50, true);
        setSize(40, 40);
    }

    @Override
    public void aktBild()
    {
        img = haefen[typ];
    }

    @Override
    public void buttonStuff()
    {
        super.buttonStuff();
    }
}
