package de.cech12.usefulhats;

public class UsefulHatsUtils {

    private UsefulHatsUtils() {}

    public static String getRomanNumber(int number, boolean withOne) {
        String romanNumber;
        switch (number) {
            case 1 -> {
                if (!withOne) return "";
                romanNumber = "I";
            }
            case 2 -> romanNumber = "II";
            case 3 -> romanNumber = "III";
            case 4 -> romanNumber = "IV";
            case 5 -> romanNumber = "V";
            case 6 -> romanNumber = "VI";
            case 7 -> romanNumber = "VII";
            case 8 -> romanNumber = "VIII";
            case 9 -> romanNumber = "IX";
            case 10 -> romanNumber = "X";
            default -> romanNumber = "?";
        }
        return " " + romanNumber;
    }

}
