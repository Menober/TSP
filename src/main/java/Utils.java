import java.util.Random;

public class Utils {

    public static int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static Double randomDouble(int min, Double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    public static int[] reverseIntTable(int[] citiesIds) {
        int[] temp = new int[citiesIds.length];
        int j = citiesIds.length - 1;
        for (int i : citiesIds) {
            temp[j--] = i;
        }
        return temp;
    }

    public static void reverseIntTableFromTo(int[] citiesIds, int x, int y) {
        int[] temp = new int[y - x + 1];
        for (int i = 0; i <= y - x; i++) {
            temp[i] = citiesIds[x + i];
        }

        for (int i = x; i <= y; i++) {
            citiesIds[i] = temp[y - i];
        }
    }

    private boolean doesTableContainValue(int value, int[] table) {
        for (int x : table) {
            if (x == value) {
                return true;
            }
        }
        return false;
    }
}
