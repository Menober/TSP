import io.DataReader;
import io.RaportGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    static String[] filesNames = {"berlin11_modified.tsp", "berlin52.tsp", "kroA100.tsp", "kroA150.tsp", "kroA200.tsp", "fl417.tsp", "ali535.tsp", "gr666.tsp", "nrw1379.tsp", "pr2392.tsp"};
    static String[] filesNamesEasy = {"berlin11_modified.tsp", "berlin52.tsp"};
    static String[] filesNamesMedium = {"kroA100.tsp", "kroA150.tsp", "kroA200.tsp"};
    static String[] filesNamesHard = {"fl417.tsp", "ali535.tsp", "gr666.tsp"};
    static int[] populationSizes = {10, 50, 100};
    static int[] generations = {100, 1000};

    public static void main(String[] args) throws IOException {
        Date startDate = new Date();

        for (String file : filesNamesEasy) {
            for (int populationSize : populationSizes) {
                DataReader dataReader = new DataReader();
                ArrayList<String> cities = dataReader.getCities(dataReader.readFile(file));
                int dimension = cities.size();
                Double[][] matrix = new Double[dimension][dimension];
                parseCitiesToMatrix(cities, matrix);
                for (int generationsCount : generations) {
                    System.out.println("File: " + file + " Population: " + populationSize + " Generations: " + generationsCount);
                    String outputData = "";

                    Population population = Population.generateNewRandomPopulation(populationSize, dimension);
                    calculateIndividualsFitness(population, matrix);

                    outputData += prepareCurrentPopulationData(population);
                    for (int i = 0; i < generationsCount; i++) {
                        population.bumpGenerationNumber();
                        // population.selection();
                        // population.crossover();
                        // population.mutation();
                        calculateIndividualsFitness(population, matrix);

                        outputData += prepareCurrentPopulationData(population);
                    }
                    RaportGenerator raport = new RaportGenerator();
                    raport.generateRaport(file.split("\\.")[0] + "-POP" + populationSize + "-GENS" + generationsCount, outputData);
                }

            }
        }

        System.out.println("Time: " + getSeconds(startDate, new Date()) + " [s]");
    }

    private static int getSeconds(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / 1000);
    }

    private static String prepareCurrentPopulationData(Population population1) {
        String data = population1.getGeneration() +
                "," + population1.getBestIndividual().getFitness() +
                "," + population1.getWorstIndividual().getFitness() +
                "," + population1.getAverageFitness() +
                "," +
                "\n";
        return data;
    }

    private static void calculateIndividualsFitness(Population population, Double[][] matrix) {
        for (Individual c : population.getPopulation()) {
            c.setFitness(calculateIndividualFitness(c, matrix));
        }
    }

    public static Double calculateIndividualFitness(Individual c, Double[][] matrix) {
        Double distance = (double) 0;
        for (int i = 0; i < c.getCitiesIds().length - 1; i++) {
            distance += matrix[c.getCitiesIds()[i]][c.getCitiesIds()[i + 1]];
        }
        return distance;
    }

    private static void printInfoAboutPopulation(Population population) {

        population.printIndividualsFitness();
        System.out.println("Best individual:");
        population.getBestIndividual().printFitness();
        System.out.println("Worst individual:");
        population.getWorstIndividual().printFitness();
    }


    private static void parseCitiesToMatrix(ArrayList<String> berlin11, Double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = calculateDistance(berlin11, i, j);
            }
        }
    }

    private static Double calculateDistance(ArrayList<String> berlin11, int i, int j) {
        Double x1 = Double.valueOf(berlin11.get(i).split("\\s+")[1]);
        Double x2 = Double.valueOf(berlin11.get(j).split("\\s+")[1]);
        Double y1 = Double.valueOf(berlin11.get(i).split("\\s+")[2]);
        Double y2 = Double.valueOf(berlin11.get(j).split("\\s+")[2]);

        Double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        return distance;
    }

    private static void printMatrix(Double[][] matrix) {
        for (Double[] x : matrix) {
            for (Double y : x) {
                System.out.printf("%8s", String.valueOf(Math.floor(y) + " "));
            }
            System.out.println();
        }
    }
}
