package by.bsuir.morse.view;

import by.bsuir.morse.util.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputDialog extends JDialog {

    private final JTextField txtInput = new JTextField(30);
    private final JButton btnOk = new JButton("ОК");

    private static final Color BG = new Color(0xFFE4EC);
    private static final Color CARD = new Color(0xFFD1DC);
    private static final Color TEXT = new Color(0x5A2A3A);
    private static final Color ACCENT = new Color(0xE91E63);
    private static final Color BUTTON_BG = new Color(0xF06292);
    private static final Color FIELD_BG = Color.WHITE;

    public InputDialog(JFrame parent) {
        super(parent, "Ввод кода Морзе", true);
        initView();
    }

    private void initView() {
        setSize(600, 320);
        setLocationRelativeTo(getParent());

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(BG);
        root.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblHint = new JLabel(
                "<html><div style='color:#8A5A6A;'>" +
                        "Введите код Морзе.<br>" +
                        "<b style='color:#5A2A3A;'>Пробел</b> — между буквами, " +
                        "<b style='color:#5A2A3A;'>/</b> — между словами.<br>" +
                        "Пример: <code style='color:#E91E63;'>.--. .-. .. .-- . -</code> → ПРИВЕТ" +
                        "</div></html>"
        );
        lblHint.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBackground(BG);

        JLabel lblInput = new JLabel("Код Морзе:");
        lblInput.setForeground(TEXT);
        lblInput.setFont(new Font("SansSerif", Font.BOLD, 14));

        txtInput.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtInput.setBackground(FIELD_BG);
        txtInput.setForeground(TEXT);
        txtInput.setCaretColor(ACCENT);
        txtInput.setPreferredSize(new Dimension(0, 38));
        txtInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xF8BBD0), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        txtInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char ch = e.getKeyChar();
                if (ch == '.') {
                    SoundPlayer.playDot();
                } else if (ch == '-') {
                    SoundPlayer.playDash();
                }
            }
        });

        center.add(lblInput, BorderLayout.WEST);
        center.add(txtInput, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(BG);

        btnOk.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnOk.setPreferredSize(new Dimension(150, 40));
        btnOk.setBackground(BUTTON_BG);
        btnOk.setForeground(Color.WHITE);
        btnOk.setFocusPainted(false);
        btnOk.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD81B60), 1, true),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        bottom.add(btnOk);

        root.add(lblHint, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);

        txtInput.addActionListener(e -> btnOk.doClick());
    }

    public String getInput() {
        return txtInput.getText();
    }

    public void setValues(String encoded) {
        if (encoded != null) {
            txtInput.setText(encoded);
            txtInput.selectAll();
        }
    }

    public void setOnSubmit(Runnable action) {
        for (java.awt.event.ActionListener al : btnOk.getActionListeners()) {
            btnOk.removeActionListener(al);
        }
        btnOk.addActionListener(e -> {
            SoundPlayer.playClick();
            action.run();
        });
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
        txtInput.requestFocus();
    }
}