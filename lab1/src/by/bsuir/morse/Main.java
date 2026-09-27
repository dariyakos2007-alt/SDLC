package by.bsuir.morse;

import by.bsuir.morse.controller.MorseController;
import by.bsuir.morse.model.MorseDecoderModel;
import by.bsuir.morse.view.MainView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;

public class Main {

    public static void main(String[] args) {
        UIManager.put("OptionPane.background", new Color(0xFFE4EC));
        UIManager.put("Panel.background", new Color(0xFFE4EC));
        UIManager.put("OptionPane.messageForeground", new Color(0xE91E63));
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.BOLD, 14));
        UIManager.put("Button.background", new Color(0xF06292));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 13));
        UIManager.put("OptionPane.errorIcon", null);

        SwingUtilities.invokeLater(() -> {
            MorseDecoderModel model = new MorseDecoderModel();
            MainView view = new MainView(model);
            MorseController controller = new MorseController(model, view);
            view.setController(controller);
            view.setVisible(true);
        });
    }
}