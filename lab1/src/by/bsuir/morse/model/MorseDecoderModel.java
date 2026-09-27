package by.bsuir.morse.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MorseDecoderModel {

    public static final String PROPERTY_DECODED_TEXT = "decodedText";

    private String lastEncodedText = "";

    private String decodedText = "";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public String getLastEncodedText() {
        return lastEncodedText;
    }

    public String getDecodedText() {
        return decodedText;
    }

    public void decode(String encoded) throws InvalidMorseCodeException {
        if (encoded == null || encoded.trim().isEmpty()) {
            throw new InvalidMorseCodeException("Введите данные");
        }

        validateCharacters(encoded);

        String normalized = encoded.trim().replaceAll("\\s+", " ");

        String[] words = normalized.split("/");
        StringBuilder result = new StringBuilder();
        boolean firstWord = true;

        for (String word : words) {
            String trimmedWord = word.trim();
            if (trimmedWord.isEmpty()) {
                continue;
            }

            if (!firstWord) {
                result.append(' ');
            }
            firstWord = false;

            String[] codes = trimmedWord.split(" ");
            for (String code : codes) {
                if (code.isEmpty()) {
                    continue;
                }
                Alphabet letter = Alphabet.findByCode(code);
                result.append(letter.name());
            }
        }

        if (result.length() == 0) {
            throw new InvalidMorseCodeException("Введите данные");
        }

        String oldValue = this.decodedText;
        this.lastEncodedText = encoded.trim();
        this.decodedText = result.toString();
        support.firePropertyChange(PROPERTY_DECODED_TEXT, oldValue, this.decodedText);
    }

    private void validateCharacters(String encoded) throws InvalidMorseCodeException {
        for (int i = 0; i < encoded.length(); i++) {
            char ch = encoded.charAt(i);

            if (ch == '.' || ch == '-' || ch == ' ' || ch == '/'
                    || ch == '\t' || ch == '\n' || ch == '\r') {
                continue;
            }

            if (Character.isLetter(ch)) {
                throw new InvalidMorseCodeException(
                        "Буквы вводить нельзя: '" + ch + "'");
            }

            if (Character.isDigit(ch)) {
                throw new InvalidMorseCodeException(
                        "Цифры вводить нельзя: '" + ch + "'");
            }

            throw new InvalidMorseCodeException(
                    "Недопустимый символ: '" + ch + "'");
        }
    }
}