import java.awt.*;
import java.io.IOException;

public class Buz
{
    public static Image ECKE_LEER;
    public static Image KANTE_LEER;

    public static void init() throws IOException
    {
        ECKE_LEER = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Siedlungen/leer.gif"), Bildschirm.eckeb, Bildschirm.eckeh, false);
        KANTE_LEER = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Kanten/Weg.gif"), Bildschirm.kanteb, Bildschirm.kanteh, false);

    }
}
