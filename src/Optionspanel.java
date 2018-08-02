import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class Optionspanel extends JPanel
{
    Optionspanel()
    {
        setSize(200, 400);
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JButton speichern = new JButton(Nuz.SPEICHERN_BUTTON);
        speichern.addActionListener(e ->
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(Nuz.SAVEGAME_LOCATION));
            int fileSelecterReturn = chooser.showOpenDialog(Main.fenster);
            if(fileSelecterReturn == JFileChooser.APPROVE_OPTION)
            {
                File file = chooser.getSelectedFile();
                String path = file.getPath();
                FileManager.writeToFile(path, OnlineInterpreter.log);
            }
        });
        add(speichern);
        setVisible(true);
    }
}
