package by.bsuir.morse.model;

public enum Alphabet {

    А(".-"),
    Б("-..."),
    В(".--"),
    Г("--."),
    Д("-.."),
    Е("."),
    Ж("...-"),
    З("--.."),
    И(".."),
    Й(".---"),
    К("-.-"),
    Л(".-.."),
    М("--"),
    Н("-."),
    О("---"),
    П(".--."),
    Р(".-."),
    С("..."),
    Т("-"),
    У("..-"),
    Ф("..-."),
    Х("...."),
    Ц("-.-."),
    Ч("---."),
    Ш("----"),
    Щ("--.-"),
    Ъ("--.--"),
    Ы("-.--"),
    Ь("-..-"),
    Э("..-.."),
    Ю("..--"),
    Я(".-.-");

    private final String code;

    Alphabet(String code) {
        this.code = code;
    }

    public static Alphabet findByCode(String code) throws InvalidMorseCodeException {
        for (Alphabet letter : values()) {
            if (letter.code.equals(code)) {
                return letter;
            }
        }
        throw new InvalidMorseCodeException("Неизвестный код");
    }
}