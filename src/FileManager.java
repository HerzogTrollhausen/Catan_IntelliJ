import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.*;

public class FileManager implements java.io.Serializable
{

    public static BufferedImage bildLaden(String path) throws IOException
    {
        return ImageIO.read(new File(path));
    }


    public static BufferedImage createResizedCopy(Image originalImage,
                                                  int scaledWidth, int scaledHeight,
                                                  boolean preserveAlpha)
    {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha)
        {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    public static void writeToFile(String path, ArrayList<String> text)
    {
        try
        {
            Path file = Paths.get(path);
            Files.write(file, text, StandardCharsets.US_ASCII, StandardOpenOption.CREATE);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}