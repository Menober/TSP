import enums.Crossover;
import enums.Mutation;
import enums.Selection;
import io.DataReader;
import io.RaportGenerator;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
    static String[] nrw1379 = {"nrw1379.tsp"};
    static String[] pr2392 = {"pr2392.tsp"};
    static int[] populationSizes = {1000};
    static int[] generations = {1000};
    static double[] crossoverRates = {90}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
    static double[] mutationRates = {25}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
    static int[] amountsOfChamps = {5};
    static String directory = "zajecia/";
    static String raportPrefix = "sw";
    static Selection selectionType = Selection.TOURNAMENT;
    static Crossover crossoverType = Crossover.OX;
    static Mutation mutationType = Mutation.INVERSION;
    static List<Integer> xData = new ArrayList<>();
    static List<Double> yData = new ArrayList<>();
    static List<Integer> xData2 = new ArrayList<>();
    static List<Double> yData2 = new ArrayList<>();
    static List<Integer> xData3 = new ArrayList<>();
    static List<Double> yData3 = new ArrayList<>();
    static List<Integer> xData4 = new ArrayList<>();
    static List<Double> yData4 = new ArrayList<>();


    static int tabuIterationsLimit = 10000;
    static int tabuNoNeighbours = 10000;
    static int tabuNeighbourhoodSize = 100;
    static int tabuListMaxSize = 10;

    static Double temperatureSA = 5000.0;
    static Double temperatureStepSA = 0.99996;
    static Double minTemp = 1.0;
    static int saNeighbourhoodSize = 5;
    static String currentFile = fl417[0];
    static Double sred = 0.0;

    public static void main(String[] args) throws IOException {

        DataReader dataReader = new DataReader();
        ArrayList<String> cities = dataReader.getCities(dataReader.readFile(currentFile));
        int dimension = cities.size();
        Double[][] matrix = new Double[dimension][dimension];
        parseCitiesToMatrix(cities, matrix);

        Individual best;
        ArrayList<Double> randomFitnesses = new ArrayList<>();
        ArrayList<Double> greedyFitnesses = new ArrayList<>();
        ArrayList<Double> eaFitnesses = new ArrayList<>();
        ArrayList<Double> saFitnesses = new ArrayList<>();
        ArrayList<Double> tabuFitnesses = new ArrayList<>();
        Long[] time = {0L, 0L, 0L, 0L, 0L};
        Population population;
        Long tmp = 0L;
        for (int i = 0; i < 10; i++) {
//            RANDOM
//            tmp = System.currentTimeMillis();
//            population = Population.generateNewRandomPopulation(populationSizes[0] * 100, dimension);
//            calculateIndividualsFitness(population, matrix);
//            best = population.getBestIndividual();
//            randomFitnesses.add(best.getFitness());
//            time[0] += System.currentTimeMillis() - tmp;

            //GREEDY
//            tmp = System.currentTimeMillis();
//            population = Population.generateGreedyPopulation(populationSizes[0] * 100, dimension, matrix);
//            calculateIndividualsFitness(population, matrix);
//            best = population.getBestIndividual();
//            greedyFitnesses.add(best.getFitness());
//            time[1] += System.currentTimeMillis() - tmp;

            //EA
            tmp = System.currentTimeMillis();
//            population = GAconf(matrix, new Configuration[]{new Configuration(populationSizes[0]*10, generations[0], crossoverRates[0], mutationRates[0], amountsOfChamps[0])}).get(0);
//            population = GAconf(matrix, new Configuration[]{new Configuration(100, 100, 80, 30, 5)}).get(0);
            population = GA();
//            GAs();
            best = population.getBestIndividual();
            eaFitnesses.add(best.getFitness());
            time[2] += System.currentTimeMillis() - tmp;

            //SA
            tmp = System.currentTimeMillis();
            Individual bestbest = sa(Population.generateNewRandomPopulation(populationSizes[0], dimension), matrix);
//            System.out.println("SA| BEST:" + bestbest.getFitness());
            saFitnesses.add(bestbest.getFitness());
            time[3] += System.currentTimeMillis() - tmp;

            //TABU
            tmp = System.currentTimeMillis();
            best = tabu(Population.generateNewRandomPopulation(populationSizes[0], dimension), matrix);
            tabuFitnesses.add(best.getFitness());
            time[4] += System.currentTimeMillis() - tmp;
        }


        for (Long l : time) {
            System.out.print(l + ";");
        }
        System.out.println();
        System.out.println("Random;Greedy;EA;SA;Tabu");
        for (int i = 0; i < 10; i++) {
            System.out.print(eaFitnesses.get(i) + ";");
            System.out.print(tabuFitnesses.get(i) + ";");
            System.out.print(saFitnesses.get(i));
            System.out.println();
//            System.out.println(randomFitnesses.get(i) + ";" + greedyFitnesses.get(i) + ";" + eaFitnesses.get(i) + ";" + saFitnesses.get(i) + ";" + tabuFitnesses.get(i));
        }

//
//        String x="437804.58147198457;433505.7469293433;440755.2590202786;434326.237405836;433534.09232452506;436582.0493542268;434268.8426996727;440535.20397386496;433025.53507728945;439399.07637163287;";
//        randomFitnesses=parser(x);
//        x="13802.375026246133;13802.375026246133;13802.375026246133;13802.375026246133;13802.375026246133;13802.375026246133;13802.375026246133;13802.375026246133;13802.375026246133;13802.375026246133;";
////        greedyFitnesses=parser(x);
//        x="61592.4347944083;60759.657431719665;66083.51373046516;62141.72283399696;67091.40590717674;62446.95350682957;68849.89159662377;63787.78658100518;64133.97007618776;64391.81635050889;";
//        eaFitnesses=parser(x);
//        x="12495.794838318698;12725.894878159888;13036.32589133382;13034.077019036486;12662.322479222597;13015.213599608758;12884.348735978809;12605.119743200885;12757.125286138975;13312.770107069584;";
//        saFitnesses=parser(x);
//        x="19509.23266393756;20684.37625537842;20438.807088928337;20153.51753484788;18973.358974925533;19077.726416024216;19061.766421800483;18754.303573165893;19034.9514135695;21416.08872096999;";
//        tabuFitnesses=parser(x);


        System.out.println(getDeviation(randomFitnesses));
        System.out.println(getDeviation(greedyFitnesses));
        System.out.println(getDeviation(eaFitnesses));
        System.out.println(getDeviation(saFitnesses));
        System.out.println(getDeviation(tabuFitnesses));

    }

    public static ArrayList<Double> parser(String text) {
        ArrayList<Double> n = new ArrayList<>();
        for (String x : text.split(";"))
            n.add(Double.valueOf(x));

        return n;
    }


    public static double getDeviation(ArrayList<Double> src) {
        double avg = getAvg(src);
        double deviation = 0.0;
        for (double x : src) {
            deviation += Math.pow(x - avg, 2);
        }
        return Math.sqrt(deviation / src.size());
    }

    public static double getAvg(ArrayList<Double> src) {
        double avg = 0.0;
        for (double d : src) {
            avg += d;
        }
        return avg / src.size();
    }


    private static void drawChart(String title) {

        XYChart chart = new XYChartBuilder().width(1800).height(900).title(title).build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setMarkerSize(3);

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        chart.getStyler().setYAxisDecimalPattern("#,###.##");
        chart.getStyler().setXAxisDecimalPattern("#,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        if (!xData.isEmpty()) chart.addSeries("Local best", xData, yData);
        if (!xData2.isEmpty()) chart.addSeries("Worst", xData2, yData2);
        if (!xData3.isEmpty()) chart.addSeries("Best ever", xData3, yData3);
        if (!xData4.isEmpty()) chart.addSeries("temp", xData4, yData4);
        new SwingWrapper(chart).displayChart();
    }


    private static Individual sa(Population population, Double[][] matrix) throws IOException {

        calculateIndividualsFitness(population, matrix);
        Individual best = population.getBestIndividual();
        Individual bestBest = population.getBestIndividual();

        // System.out.println(best.getFitness());
        int time = 1;
        int counter = 0;
        Double temp = temperatureSA;
        Individual bestSA = best;
        while (temp > minTemp && counter++ < generations[0] * 1000) {
            Population neighbourhood = neighbourhood(best, saNeighbourhoodSize);
            calculateIndividualsFitness(neighbourhood, matrix);
            bestSA = getBestSA(neighbourhood);
            Double random = Utils.randomDouble(0, 1.0);
            Double powWk = (bestSA.getFitness() - best.getFitness()) / temp;
            Double pow = Math.pow(2.718281828459045, powWk);
            Double exp = 1.0 / (1.0 + pow);

            if (bestSA.getFitness() < best.getFitness()) {
                best = bestSA;
            } else if (random < exp) {
                best = bestSA;
            }
            xData.add(xData.size() + 1);
            yData.add(best.getFitness());
            sred += best.getFitness();

            if (bestBest.getFitness() > best.getFitness()) {
                bestBest = best;
            }

            xData3.add(xData3.size() + 1);
            yData3.add(bestBest.getFitness());


            temp = calculateTemp(temp, ++time);

        }
        //   bestBest.printData();
        return bestBest;
    }

    private static Double calculateTemp(Double temp, int time) {
        // temp - 0.001;
        return temp * temperatureStepSA;
//        Double x = (temperatureStepSA * temp);
//        Double y = Double.valueOf((time / 3.0));
//        return temp - x / y;
    }

    private static Individual getBestSA(Population neighbourhood) {
        return neighbourhood.getBestIndividual();
    }

    private static Individual tabu(Population population, Double[][] matrix) throws FileNotFoundException {
        calculateIndividualsFitness(population, matrix);
        Individual best = population.getBestIndividual();
        Individual bestTabu = best;
        ArrayList<int[]> tabuList = new ArrayList<>();

        tabuList.add(best.getCitiesIds());

        // System.out.println("INICJALIZACJA:" + best.getFitness());
        int i = 0;
        int k = 1;
        while (i != tabuNoNeighbours && k < tabuIterationsLimit) {
            Population neighbourhood = neighbourhood(bestTabu, tabuNeighbourhoodSize);
            calculateIndividualsFitness(neighbourhood, matrix);
            bestTabu = getBestTabu(neighbourhood, tabuList);
            xData.add(xData.size() + 1);
            yData.add(bestTabu.getFitness());
            xData2.add(xData2.size() + 1);
            yData2.add(neighbourhood.getWorstIndividual().getFitness());
            if (bestTabu != null && bestTabu.getFitness() < best.getFitness()) {
                best = bestTabu;

                if (tabuList.size() > tabuListMaxSize) {
                    tabuList.remove(0);
                }
                tabuList.add(best.getCitiesIds());
                // System.out.println("ZNALEZIONO LEPSZEGO:" + best.getFitness());
                i = 0;
            } else {
                i++;
                if (i == tabuNoNeighbours / 2) {
                    //   System.out.println("halfTabu:best");
                    //    best.printData();
                }
            }
            xData3.add(k++);
            yData3.add(best.getFitness());

        }
        //  best.printData();
        return best;
    }

    private static Individual getBestTabu(Population neighbourhood, ArrayList<int[]> tabuList) {
        try {
            neighbourhood.getPopulation().sort(new Comparator<Individual>() {
                @Override
                public int compare(Individual o1, Individual o2) {
                    int o = 1;

                    o = (int) (o1.getFitness() - o2.getFitness());
                    return o;
                }
            });
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

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


    public static Population GAIndiv(Population population, Double[][] matrix) throws IOException {
        calculateIndividualsFitness(population, matrix);

        for (int i = 0; i < generations[0]; i++) {
            population.bumpGenerationNumber();
            Population newPopulation = Population.generateNewEmptyPopulation(populationSizes[0], population.getGeneration());
            newPopulation.crossover(population, crossoverRates[0], amountsOfChamps[0]);
            newPopulation.mutation(mutationRates[0]);
            calculateIndividualsFitness(newPopulation, matrix);
            population = newPopulation;
            xData.add(xData.size() + 1);
            yData.add(population.getBestIndividual().getFitness());
            xData2.add(xData2.size() + 1);
            yData2.add(population.getWorstIndividual().getFitness());
        }
        return population;
    }

    public static ArrayList<Population> GAconf(Double[][] matrix, Configuration[] cf) throws IOException {
        Date startDate = new Date();
        ArrayList<Population> populations = new ArrayList<>();
        for (Configuration c : cf) {
            // System.out.println("File: " + currentFile + " Population: " + populationSize + " Generations: " + generationsCount + " Crossover rate: " + crossoverRate + " Mutation rate: " + mutationRate + " Amount of champs: " + amountOfChamps);
            String outputData = "";

            Population population = Population.generateNewRandomPopulation(c.populationSize, matrix.length);
            calculateIndividualsFitness(population, matrix);

            outputData += prepareCurrentPopulationData(population);
            for (int i = 0; i < c.generations; i++) {
                population.bumpGenerationNumber();
                Population newPopulation = Population.generateNewEmptyPopulation(population.getPopulationSize(), population.getGeneration());

                newPopulation.crossover(population, c.crossover, c.amount);
                newPopulation.mutation(c.mutation);

                calculateIndividualsFitness(newPopulation, matrix);

                population = newPopulation;
                outputData += prepareCurrentPopulationData(population);
            }
            // System.out.println("Raport time: " + getSeconds(startDate, new Date()) + " [s]");
//                                RaportGenerator raport = new RaportGenerator();
//                                raport.generateRaport(buildRaportName(counter, currentFile, populationSize, generationsCount, crossoverRate, mutationRate, amountOfChamps), outputData);
//                                System.out.println(population.getBestIndividual().getFitness() + "," + population.getWorstIndividual().getFitness() + "," + population.getAverageFitness());

            populations.add(new Population(population));

        }
        return populations;
        //  System.out.println("Time: " + getSeconds(startDate, new Date()) + " [s]");
    }

    public static Population GA() throws IOException {
        Date startDate = new Date();
        for (int counter = 0; counter < 1; counter++) {
            for (int populationSize : populationSizes) {
                DataReader dataReader = new DataReader();
                ArrayList<String> cities = dataReader.getCities(dataReader.readFile(currentFile));
                int dimension = cities.size();
                Double[][] matrix = new Double[dimension][dimension];
                parseCitiesToMatrix(cities, matrix);
                for (int generationsCount : generations) {
                    generationsCount/=3;
                    for (double crossoverRate : crossoverRates) {
                        for (double mutationRate : mutationRates) {
                            for (int amountOfChamps : amountsOfChamps) {
//                                System.out.println("File: " + currentFile + " Population: " + populationSize + " Generations: " + generationsCount + " Crossover rate: " + crossoverRate + " Mutation rate: " + mutationRate + " Amount of champs: " + amountOfChamps);
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
//                                    RaportGenerator raport = new RaportGenerator();
//                                    raport.generateRaport(buildRaportName(counter, file, populationSize, generationsCount, crossoverRate, mutationRate, amountOfChamps), outputData);
//                                System.out.println(population.getBestIndividual().getFitness() + "," + population.getWorstIndividual().getFitness() + "," + population.getAverageFitness());
                                //TODO TO DELETE
                                return population;
                            }
                        }
                    }
                }

            }

        }
        return new Population();
        //  System.out.println("Time: " + getSeconds(startDate, new Date()) + " [s]");
    }

    public static void GAs() throws IOException {
        Date startDate = new Date();
        for (int counter = 0; counter < 1; counter++) {
            for (int populationSize : populationSizes) {
                DataReader dataReader = new DataReader();
                ArrayList<String> cities = dataReader.getCities(dataReader.readFile(currentFile));
                int dimension = cities.size();
                Double[][] matrix = new Double[dimension][dimension];
                parseCitiesToMatrix(cities, matrix);
                for (int generationsCount : generations) {
                    for (double crossoverRate : crossoverRates) {
                        for (double mutationRate : mutationRates) {
                            for (int amountOfChamps : amountsOfChamps) {
                                System.out.println("File: " + currentFile + " Population: " + populationSize + " Generations: " + generationsCount + " Crossover rate: " + crossoverRate + " Mutation rate: " + mutationRate + " Amount of champs: " + amountOfChamps);
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
//                                    RaportGenerator raport = new RaportGenerator();
//                                    raport.generateRaport(buildRaportName(counter, file, populationSize, generationsCount, crossoverRate, mutationRate, amountOfChamps), outputData);
                                System.out.println(population.getBestIndividual().getFitness() + "," + population.getWorstIndividual().getFitness() + "," + population.getAverageFitness());
                                //TODO TO DELETE
//                                return population;
                            }
                        }
                    }
                }

            }

        }
//        return new Population();
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
        Double distance = 0.0;
        int i = 0;
        Double dis = 0.0;
        for (i = 0; i < c.getCitiesIds().length - 1; i++) {
            dis = matrix[c.getCitiesIds()[i]][c.getCitiesIds()[i + 1]];
            distance += dis;
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
        Double x1 = Double.valueOf(berlin11.get(i).split("\\s+")[1]) + 200;
        Double x2 = Double.valueOf(berlin11.get(j).split("\\s+")[1]) + 200;
        Double y1 = Double.valueOf(berlin11.get(i).split("\\s+")[2]) + 200;
        Double y2 = Double.valueOf(berlin11.get(j).split("\\s+")[2]) + 200;

        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private static void printMatrix(Double[][] matrix) {
        for (Double[] x : matrix) {
            System.out.println("{");
            for (Double y : x) {
                System.out.printf("%8s", String.valueOf(Math.floor(y) + ", "));
            }
            System.out.println("},");
        }
    }

    private static void clearChartData() {
        xData = new ArrayList<>();
        yData = new ArrayList<>();
        xData2 = new ArrayList<>();
        yData2 = new ArrayList<>();
        xData3 = new ArrayList<>();
        yData3 = new ArrayList<>();
        xData4 = new ArrayList<>();
        yData4 = new ArrayList<>();
    }
}
