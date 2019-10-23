import enums.Crossover;
import enums.Mutation;
import enums.Selection;
import io.DataReader;
import io.RaportGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    static String[] filesNames = {"berlin11_modified.tsp", "berlin52.tsp", "kroA100.tsp", "kroA150.tsp", "kroA200.tsp", "fl417.tsp", "ali535.tsp", "gr666.tsp", "nrw1379.tsp", "pr2392.tsp"};
    static String[] filesNamesTest = {"berlin11_modified.tsp"};
    static String[] filesNamesEasy = {"berlin11_modified.tsp", "berlin52.tsp"};
    static String[] filesNamesMedium = {"kroA100.tsp", "kroA150.tsp", "kroA200.tsp"};
    static String[] kroA100 = {"kroA100.tsp"};
    static String[] kroA150 = {"kroA150.tsp"};
    static String[] kroA200 = {"kroA200.tsp"};
    static String[] filesNamesHard = {"fl417.tsp", "ali535.tsp", "gr666.tsp"};
    static int[] populationSizes = {100};
    static int[] generations = {500};
    static double[] crossoverRates = {95.0}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
    static double[] mutationRates = {0.005}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
    static int[] amountsOfChamps = {5};
    static String raportPrefix = "dev/dev-";
    static Selection selectionType = Selection.TOURNAMENT;
    static Crossover crossoverType = Crossover.OX;
    static Mutation mutationType = Mutation.SWAP;

    public static void main(String[] args) throws IOException {
        Date startDate = new Date();

        for (String file : filesNamesTest) {
            for (int populationSize : populationSizes) {
                DataReader dataReader = new DataReader();
                ArrayList<String> cities = dataReader.getCities(dataReader.readFile(file));
                int dimension = cities.size();
                Double[][] matrix = new Double[dimension][dimension];
                parseCitiesToMatrix(cities, matrix);
                for (int generationsCount : generations) {
                    if (generationsCount % 500 == 0) {
                        System.out.println(generationsCount);
                    }
                    for (double crossoverRate : crossoverRates) {
                        for (double mutationRate : mutationRates) {
                            for (int amountOfChamps : amountsOfChamps) {
                                System.out.println("File: " + file + " Population: " + populationSize + " Generations: " + generationsCount + " enums.Crossover rate: " + crossoverRate + " Mutation rate: " + mutationRate + " Amount of champs: " + amountOfChamps);
                                String outputData = "";

                                Population population = Population.generateNewRandomPopulation(populationSize, dimension);
                                calculateIndividualsFitness(population, matrix);

                                outputData += prepareCurrentPopulationData(population);
                                for (int i = 0; i < generationsCount; i++) {
                                    population.bumpGenerationNumber();
                                    Population newPopulation = Population.generateNewEmptyPopulation(population.getPopulationSize(), population.getGeneration());

                                    newPopulation.crossover(population, crossoverRate, amountOfChamps);
//                                    if (i % 400 == 0) {
//                                        newPopulation.mutation(mutationRate * 200);
//                                    } else {
//                                        newPopulation.mutation(mutationRate);
//                                    }
                                    calculateIndividualsFitness(newPopulation, matrix);

                                    population = newPopulation;
                                    outputData += prepareCurrentPopulationData(population);
                                }
                                System.out.println("Raport time: " + getSeconds(startDate, new Date()) + " [s]");
                                RaportGenerator raport = new RaportGenerator();
                                raport.generateRaport(buildRaportName(file, populationSize, generationsCount, crossoverRate, mutationRate, amountOfChamps), outputData);
                            }
                        }
                    }
                }

            }
        }

        System.out.println("Time: " + getSeconds(startDate, new Date()) + " [s]");
    }

    private static String buildRaportName(String file, int populationSize, int generationsCount, double crossoverRate, double mutationRate, int amountOfChamps) {
        String raportName = ""
                + raportPrefix
                + file.split("\\.")[0]
                + "-POP" + populationSize
                + "-GENS" + generationsCount
                + "-PX" + crossoverRate
                + "-PM" + mutationRate
                + "-CHAMPS" + amountOfChamps
                + "-SELECTION" + selectionType
                + "-MUTATION" + mutationType
                + "-CROSSOVER" + crossoverType;
        return raportName;
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
        distance += matrix[c.getCitiesIds()[c.getCitiesIds().length - 1]][c.getCitiesIds()[0]];
        return distance;
    }

    private static void printInfoAboutPopulation(Population population) {

        population.printIndividualsFitness();
        System.out.println("Best individual:");
        population.getBestIndividual().printFitness();
        System.out.println("Worst individual:");
        population.getWorstIndividual().printFitness();
    }


    public static void parseCitiesToMatrix(ArrayList<String> berlin11, Double[][] matrix) {
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
