import io.DataReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
        int a = c.getCitiesIds()[c.getCitiesIds().length-1];
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
}
