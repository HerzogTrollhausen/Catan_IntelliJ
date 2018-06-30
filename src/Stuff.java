import javax.swing.*;
import java.util.ArrayList;

public class Stuff
{
    public static void main(String[] args)
    {
        Game[] games = new Game[2];
        games[0] = new Game("Erstes Spiel", 0, 1, 4);
        games[1] = new Game("Zweites Spiel", 1, 2, 3);
        System.out.println(((Game)JOptionPane.showInputDialog(null, "message", "title",
                JOptionPane.QUESTION_MESSAGE, null, games, games[0])).getID());
    }
}

