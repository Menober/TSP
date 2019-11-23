public class CityDistance implements Comparable<CityDistance> {
    int index;
    Double value;

    public CityDistance(int i, Double matrixValue) {
        this.index = i;
        this.value = matrixValue;
    }

    @Override
    public int compareTo(CityDistance o) {
        if (this.value > o.value)
            return 1;
        else if (this.value < o.value)
            return -1;
        return 0;
    }
}
