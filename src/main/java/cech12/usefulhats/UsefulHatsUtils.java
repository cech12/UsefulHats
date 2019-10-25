package cech12.usefulhats;

public class UsefulHatsUtils {

    private UsefulHatsUtils() {}

    public static String getRomanNumber(int number, boolean withOne) {
        String romanNumber = "?";
        switch (number) {
            case 1 : if (withOne) romanNumber = "I"; else return ""; break;
            case 2 : romanNumber = "II"; break;
            case 3 : romanNumber = "III"; break;
            case 4 : romanNumber = "IV"; break;
            case 5 : romanNumber = "V"; break;
            case 6 : romanNumber = "VI"; break;
            case 7 : romanNumber = "VII"; break;
            case 8 : romanNumber = "VIII"; break;
            case 9 : romanNumber = "IX"; break;
            case 10 : romanNumber = "X"; break;
        }
        return " " + romanNumber;
    }

}
