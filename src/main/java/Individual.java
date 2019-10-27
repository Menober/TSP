import enums.Mutation;

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
        int cityOne = r.nextInt(citiesIds.length - 1);
        int cityTwo = r.nextInt(citiesIds.length - 1);
        while (cityOne == cityTwo) {
            cityTwo = r.nextInt(citiesIds.length - 1);
        }
        int tempValue = citiesIds[cityOne];
        citiesIds[cityOne] = citiesIds[cityTwo];
        citiesIds[cityTwo] = tempValue;
    }

    public Individual copy() {
        Individual newIndividual = new Individual(this.citiesIds.length);
        newIndividual.setFitness(fitness);
        newIndividual.setCitiesIds(citiesIds.clone());
        return newIndividual;
    }

    public void mutate(double pm) {
        if (Main.mutationType == Mutation.SWAP) {
            for (int i = 0; i < citiesIds.length; i++) {
                if (Utils.randomInt(0, 100000) <= pm * 1000) {
                    swapCityWithRandomCity(i);
                }
            }
        } else if (Main.mutationType == Mutation.INVERSION) {
            if (Utils.randomInt(0, 100000) <= pm * 1000) {
                reversePartOfGenome();
            }
        }
    }

    private void swapCityWithRandomCity(int index) {
        int randomCity = Utils.randomInt(0, citiesIds.length - 1);
        while (citiesIds[randomCity] == citiesIds[index]) {
            randomCity = Utils.randomInt(0, citiesIds.length - 1);
        }
        int tempValue = citiesIds[randomCity];
        citiesIds[randomCity] = citiesIds[index];
        citiesIds[index] = tempValue;
    }

    public void mutateOnce() {
        if (Main.mutationType == Mutation.SWAP) {
            swapCityWithRandomCity(Utils.randomInt(0, citiesIds.length - 1));
        } else if (Main.mutationType == Mutation.INVERSION) {
            reversePartOfGenome();
        }
    }

    private void reversePartOfGenome() {
        int x = Utils.randomInt(0, citiesIds.length - 2);
        int y = Utils.randomInt(x + 1, citiesIds.length - 1);
        Utils.reverseIntTableFromTo(citiesIds, x, y);
    }
}
