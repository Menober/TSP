import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Individual {
    private int[] citiesIds;
    private Double fitness;

    public void fillWithRandomCities() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < citiesIds.length; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < citiesIds.length; i++) {
            citiesIds[i] = list.get(i);
        }
    }

    public void fillWithSubsequentCities() {
        for (int i = 0; i < citiesIds.length; i++) {
            citiesIds[i] = i;
        }
    }

    public void printData() {
        System.out.println(this + " cities are: ");
        for (int x : citiesIds) {
            System.out.print(x + " ");
        }
        System.out.println("\nThe fitness is: " + fitness);
    }

    public void printFitness() {
        System.out.println(this + " fitness is: " + fitness);
    }

    public Individual(int citiesCount) {
        this.citiesIds = new int[citiesCount];
    }

    public int[] getCitiesIds() {
        return citiesIds;
    }

    public void setCitiesIds(int[] citiesIds) {
        this.citiesIds = citiesIds;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public void swapRandomCities() {
        Random r = new Random();
        int cityOne = r.nextInt(citiesIds.length);
        int cityTwo = r.nextInt(citiesIds.length);
        while (cityOne == cityTwo) {
            cityTwo = r.nextInt(citiesIds.length);
        }
        int tempValue = citiesIds[cityOne];
        citiesIds[cityOne]=citiesIds[cityTwo];
        citiesIds[cityTwo]=tempValue;
    }
}
