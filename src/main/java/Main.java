import enums.Crossover;
import enums.Mutation;
import enums.Selection;
import io.DataReader;
import io.RaportGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Main {
    static String[] filesNames = {"berlin11_modified.tsp", "berlin52.tsp", "kroA100.tsp", "kroA150.tsp", "kroA200.tsp", "fl417.tsp", "ali535.tsp", "gr666.tsp", "nrw1379.tsp", "pr2392.tsp"};
    static String[] filesNamesTest = {"berlin11_modified.tsp"};
    static String[] filesNamesEasy = {"berlin11_modified.tsp", "berlin52.tsp"};
    static String[] filesNamesMedium = {"kroA100.tsp", "kroA150.tsp", "kroA200.tsp"};
    static String[] kroA100 = {"kroA100.tsp"};
    static String[] kroA150 = {"kroA150.tsp"};
    static String[] kroA200 = {"kroA200.tsp"};
    static String[] fl417 = {"fl417.tsp"};
    static String[] ali535 = {"ali535.tsp"};
    static String[] gr666 = {"gr666.tsp"};
    static String[] filesNamesHard = {"fl417.tsp", "ali535.tsp", "gr666.tsp"};
    static int[] populationSizes = {100};
    static int[] generations = {500};
    static double[] crossoverRates = {10}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
    static double[] mutationRates = {60}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
    static int[] amountsOfChamps = {5};
    static String directory = "zajecia/";
    static String raportPrefix = "sw";
    static Selection selectionType = Selection.TOURNAMENT;
    static Crossover crossoverType = Crossover.OX;
    static Mutation mutationType = Mutation.INVERSION;

    public static void main(String[] args) throws IOException {
        //GA();
        tabu();
    }

    private static void tabu() throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        ArrayList<String> cities = dataReader.getCities(dataReader.readFile(fl417[0]));
        int dimension = cities.size();
        Double[][] matrix = new Double[dimension][dimension];
        parseCitiesToMatrix(cities, matrix);
        Population population = Population.generateNewRandomPopulation(1, dimension);
        calculateIndividualsFitness(population, matrix);
        Individual best = population.getBestIndividual();

        ArrayList<int[]> tabuList = new ArrayList<>();
        int tabuListMaxSize = 10;

        tabuList.add(best.getCitiesIds());

        System.out.println("INICJALIZACJA:" + best.getFitness());
        int i = 0;
        while (best.getFitness() > 11862 && i != 1000) {
            Population neighbourhood = neighbourhood(best, 1000);
            calculateIndividualsFitness(neighbourhood, matrix);
            Individual bestTabu = getBestTabu(neighbourhood, tabuList);
            if (bestTabu != null & bestTabu.getFitness() < best.getFitness()) {
                best = bestTabu;
                if (tabuList.size() > tabuListMaxSize) {
                    tabuList.remove(0);
                }
                tabuList.add(best.getCitiesIds());
                System.out.println("ZNALEZIONO LEPSZEGO:" + best.getFitness());
                i = 0;
            } else {
                i++;
            }

        }
        best.printData();
    }

    private static Individual getBestTabu(Population neighbourhood, ArrayList<int[]> tabuList) {
        neighbourhood.getPopulation().sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                return (int) (o1.getFitness() - o2.getFitness());
            }
        });
        for (Individual i : neighbourhood.getPopulation()) {
            if (!isTabu(i, tabuList)) {
                return i;
            }
        }

        return null;
    }

    private static boolean isTabu(Individual i, ArrayList<int[]> tabuList) {
        for (int[] x : tabuList) {
            if (areCitiesTheSame(i.getCitiesIds(), x))
                return true;
        }
        return false;
    }

    private static boolean areCitiesTheSame(int[] a, int[] b) {
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

    private static Population neighbourhood(Individual best, int k) {
        Population population = Population.generateNewEmptyPopulation(k, 0);
        for (int i = 0; i < k; i++) {
            Individual neighbour = best.copy();
            neighbour.mutateOnce();
            population.addIndividual(neighbour);
        }

        return population;
    }

    public static void GA() throws IOException {
        Date startDate = new Date();
        for (int counter = 0; counter < 1; counter++) {
            for (String file : filesNamesTest) {
                for (int populationSize : populationSizes) {
                    DataReader dataReader = new DataReader();
                    ArrayList<String> cities = dataReader.getCities(dataReader.readFile(file));
                    int dimension = cities.size();
                    Double[][] matrix = new Double[dimension][dimension];
                    parseCitiesToMatrix(cities, matrix);
                    for (int generationsCount : generations) {
                        if (generationsCount % 500 == 0) {
                            //    System.out.println(generationsCount);
                        }
                        for (double crossoverRate : crossoverRates) {
                            for (double mutationRate : mutationRates) {
                                for (int amountOfChamps : amountsOfChamps) {
                                    System.out.println("File: " + file + " Population: " + populationSize + " Generations: " + generationsCount + " Crossover rate: " + crossoverRate + " Mutation rate: " + mutationRate + " Amount of champs: " + amountOfChamps);
                                    String outputData = "";

                                    Population population = Population.generateNewRandomPopulation(populationSize, dimension);
                                    calculateIndividualsFitness(population, matrix);

                                    outputData += prepareCurrentPopulationData(population);
                                    for (int i = 0; i < generationsCount; i++) {
                                        population.bumpGenerationNumber();
                                        Population newPopulation = Population.generateNewEmptyPopulation(population.getPopulationSize(), population.getGeneration());

                                        newPopulation.crossover(population, crossoverRate, amountOfChamps);
                                        newPopulation.mutation(mutationRate);

                                        calculateIndividualsFitness(newPopulation, matrix);

                                        population = newPopulation;
                                        outputData += prepareCurrentPopulationData(population);
                                    }
                                    // System.out.println("Raport time: " + getSeconds(startDate, new Date()) + " [s]");
                                    RaportGenerator raport = new RaportGenerator();
                                    raport.generateRaport(buildRaportName(counter, file, populationSize, generationsCount, crossoverRate, mutationRate, amountOfChamps), outputData);
                                    System.out.println(population.getBestIndividual().getFitness() + "," + population.getWorstIndividual().getFitness() + "," + population.getAverageFitness());
                                }
                            }
                        }
                    }

                }
            }
        }

        //  System.out.println("Time: " + getSeconds(startDate, new Date()) + " [s]");
    }


    private static String buildRaportName(int counter, String file, int populationSize, int generationsCount, double crossoverRate, double mutationRate, int amountOfChamps) {
        String raportName = ""
                + directory
                + raportPrefix
                + file.split("\\.")[0]
                + "-POP;" + populationSize
                + "-GENS;" + generationsCount
                + "-PX;" + crossoverRate
                + "-PM;" + mutationRate
                + "-CHAMPS;" + amountOfChamps
                + "-SELECTION;" + selectionType
                + "-MUTATION;" + mutationType
                + "-CROSSOVER;" + crossoverType;
        return raportName;
    }

    private static int getSeconds(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / 1000);
    }

    private static String prepareCurrentPopulationData(Population population1) {
        String data = (population1.getGeneration() + 1) +
                "," + population1.getBestIndividual().getFitness() +
                "," + population1.getWorstIndividual().getFitness() +
                "," + population1.getAverageFitness() +
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
