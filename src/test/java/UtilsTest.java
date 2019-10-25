import io.DataReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UtilsTest {

    @Test
    public void testCreate() {
        for (int i = 0; i < 100; i++) {
            int x = Utils.randomInt(0, 10);
            System.out.println(x);
            Assert.assertTrue(x <= 10 && x >= 0);
        }
    }

    @Test
    public void matrixTest() throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        ArrayList<String> cities = dataReader.getCities(dataReader.readFile("berlin11_modified.tsp"));
        int dimension = cities.size();
        Double[][] matrix = new Double[dimension][dimension];
        Main.parseCitiesToMatrix(cities, matrix);

        Individual c = new Individual(11);
        c.fillWithRandomCities();
        int a = c.getCitiesIds()[c.getCitiesIds().length - 1];
        int b = c.getCitiesIds()[0];
        System.out.println(matrix[a][b]);
    }

    @Test
    public void cloningTest() {
        Individual parentA = new Individual(7);
        Individual parentB = new Individual(7);

        parentB.setCitiesIds(new int[]{5, 7, 1, 3, 6, 4, 2});
        parentA.setCitiesIds(new int[]{4, 6, 2, 7, 3, 1, 5});
        Individual childA = parentA.copy();

        // int indexOfCut = Utils.randomInt(0, parentA.getCitiesIds().length - 1);
        int indexOfCut = 3;
        for (int i = 0; i < indexOfCut; i++) {
            int index = Population.findIndex(parentA.getCitiesIds(), parentB.getCitiesIds()[i]);
            Population.swap(i, index, childA);
        }
        childA.printData();


    }

    @Test
    public void cloningDoubleTest() {
        Double sumOfPopulationFitness = 100.0;
        Double pointer = 0.0;
        Double[] wheel = new Double[10];

        for (int i = 0; i < 10; i++) {
            pointer += 1;
            wheel[i] = pointer;
        }
        for (Double d : wheel) {
            System.out.println(d);
        }
    }

    @Test
    public void reverseTest() {
        int[] citiesIds = {1, 2, 3, 4};

        citiesIds = reverse(citiesIds);

        for (int i : citiesIds)
            System.out.print(i + ", ");
    }

    private int[] reverse(int[] citiesIds) {
        int[] temp = new int[citiesIds.length];
        int j = citiesIds.length - 1;
        for (int i : citiesIds) {
            temp[j--] = i;
        }
        return temp;
    }

    @Test
    public void OX() {
        Individual parent1 = new Individual(0);
        Individual parent2 = new Individual(0);
//        parent1.setCitiesIds(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
//        parent2.setCitiesIds(new int[]{5, 7, 4, 9, 1, 3, 6, 2, 8});
        parent1.setCitiesIds(new int[]{1, 2, 3, 4});
        parent2.setCitiesIds(new int[]{4, 3, 2, 1});

        for (int i = 0; i < 50; i++) {
            Individual child = OXf(parent1, parent2);
            for (int x : child.getCitiesIds()) {
                System.out.print(x + ", ");
            }
            System.out.println();
        }
    }

    private Individual OXf(Individual parent1, Individual parent2) {
        Individual child = new Individual(parent1.getCitiesIds().length);
        int firstCut = Utils.randomInt(0, parent1.getCitiesIds().length - 2);
        int secondCut = Utils.randomInt(firstCut + 1, parent1.getCitiesIds().length - 1);
        int[] insertedCities = new int[secondCut - firstCut]; //length of substring
        for (int i = 0; i < secondCut - firstCut; i++) { //write substring of first parent to the child
            child.getCitiesIds()[firstCut + i] = parent1.getCitiesIds()[firstCut + i];
            insertedCities[i] = parent1.getCitiesIds()[firstCut + i];
        }

        int i = 0;
        for (int a : parent2.getCitiesIds()) {
            if (!doesTableContainValue(a, insertedCities)) { //place cities from second parent without already insterted cities
                while (child.getCitiesIds()[i] != 0) { //if child has a city at 'i' index - skip this position
                    i++;
                }
                child.getCitiesIds()[i] = a;
            }
        }

        return child;
    }

    private boolean doesTableContainValue(int value, int[] table) {
        for (int x : table) {
            if (x == value) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void inversionTest() {
        Individual parent1 = new Individual(4);
        parent1.setCitiesIds(new int[]{1, 2, 3, 4, 5, 6});
        int x=0;
        int y=0;
        for(int i=0;i<10;i++) {
            x = Utils.randomInt(0, parent1.getCitiesIds().length - 2);
            y = Utils.randomInt(x + 1, parent1.getCitiesIds().length - 1);
            System.out.println(x + "_" + y);
        }
        Utils.reverseIntTableFromTo(parent1.getCitiesIds(), x, y);
        System.out.println();
        for (int a : parent1.getCitiesIds()) {
            System.out.print(a + ",");
        }
    }

    private boolean areTheSame(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}
