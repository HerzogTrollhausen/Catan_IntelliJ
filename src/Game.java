import java.util.ArrayList;

public class Game
{
    private String title;
    private int ID;
    private int momSpieler;
    private int maxSpieler;
    public static ArrayList<Game> games = new ArrayList<>();

    Game(String title, int ID, int momSpieler, int maxSpieler)
    {
        this.title = title;
        this.ID = ID;
        this.momSpieler = momSpieler;
        this.maxSpieler = maxSpieler;
    }

    public String toString()
    {
        return title + " ("+momSpieler+"/"+maxSpieler+")";
    }

    public int getID()
    {
        return ID;
    }

    public static void addGame(Game game)
    {
        games.add(game);
    }
}
