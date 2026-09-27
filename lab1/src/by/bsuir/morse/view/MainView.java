package by.bsuir.morse.view;

import by.bsuir.morse.controller.MorseController;
import by.bsuir.morse.util.SoundPlayer;
import by.bsuir.morse.model.MorseDecoderModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainView extends JFrame implements PropertyChangeListener {

    private MorseController controller;

    private final JLabel lblInput = new JLabel("Код Морзе не задан");
    private final JLabel lblResult = new JLabel("Результат: -");
    private final JButton btnInput = new JButton("Ввести данные");

    private static final Color BG = new Color(0xFFE4EC);
    private static final Color CARD = new Color(0xFFD1DC);
    private static final Color TEXT = new Color(0x5A2A3A);
    private static final Color ACCENT = new Color(0xE91E63);
    private static final Color BUTTON_BG = new Color(0xF06292);

    public MainView(MorseDecoderModel model) {
        model.addPropertyChangeListener(this);
        initView();
    }

    public void setController(MorseController controller) {
        this.controller = controller;
    }

    private void initView() {
        setTitle("Morse Decoder (MVC Active Model)");
        setSize(560, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(BG);
        root.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xF8BBD0), 1, true),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)
        ));

        lblInput.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblInput.setForeground(TEXT);
        lblInput.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblResult.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblResult.setForeground(ACCENT);
        lblResult.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblInput);
        card.add(Box.createVerticalStrut(10));
        card.add(lblResult);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BG);

        btnInput.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnInput.setPreferredSize(new Dimension(220, 42));
        btnInput.setBackground(BUTTON_BG);
        btnInput.setForeground(Color.WHITE);
        btnInput.setFocusPainted(false);
        btnInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD81B60), 1, true),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        btnInput.addActionListener(e -> {
            SoundPlayer.playClick();
            if (controller != null) {
                controller.openInputDialog(this);
            }
        });

        buttonPanel.add(btnInput);

        root.add(card, BorderLayout.CENTER);
        root.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(root);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (MorseDecoderModel.PROPERTY_DECODED_TEXT.equals(evt.getPropertyName())) {
            setResult((String) evt.getNewValue());
        }
    }

    public void setLastInput(String encoded) {
        if (encoded == null || encoded.isEmpty()) {
            lblInput.setText("Код Морзе не задан");
        } else {
            lblInput.setText("Последний ввод: " + encoded);
        }
    }

    public void setResult(String result) {
        if (result == null || result.isEmpty()) {
            lblResult.setText("Результат: -");
        } else {
            lblResult.setText("Результат: " + result);
        }
    }

}