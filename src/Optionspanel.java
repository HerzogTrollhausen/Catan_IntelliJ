import java.awt.Color;
import java.io.File;
import javax.swing.*;

public class Optionspanel extends JPanel
{
    Optionspanel()
    {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JButton speichern = new JButton(Nuz.SPEICHERN_BUTTON);
        speichern.addActionListener(e ->
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(Nuz.SAVEGAME_LOCATION));
            int fileSelecterReturn = chooser.showSaveDialog(Main.fenster);
            if(fileSelecterReturn == JFileChooser.APPROVE_OPTION)
            {
                File file = chooser.getSelectedFile();
                String path = file.getPath()+".catan";
                FileManager.writeToFile(path, OnlineInterpreter.log);
            }
        });
        add(speichern);
        setVisible(true);
    }
}
