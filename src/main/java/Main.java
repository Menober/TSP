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
    static int[] populationSizes = {100};
    static int[] generations = {100};
    static double[] crossoverRates = {30}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
    static double[] mutationRates = {15}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
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
    static int tabuNeighbourhoodSize = 30;
    static int tabuListMaxSize = 10;

    static Double temperatureSA = 100.0;
    static Double temperatureStepSA = 0.99996;
    static Double minTemp = 1.0;
    static int saNeighbourhoodSize = 5;
    static String currentFile = gr666[0];
    static Double sred = 0.0;

    public static void main(String[] args) throws IOException {

        DataReader dataReader = new DataReader();
        ArrayList<String> cities = dataReader.getCities(dataReader.readFile(currentFile));
        int dimension = cities.size();
        Double[][] matrix = new Double[dimension][dimension];
        parseCitiesToMatrix(cities, matrix);

        Individual best = Individual.createIndividualFromString("1;465;464;463;462;451;450;449;448;452;453;454;456;457;458;459;460;520;461;521;522;525;526;515;524;516;523;517;518;519;493;492;491;490;494;495;496;497;498;436;500;499;502;501;156;155;158;157;159;161;162;163;186;185;184;149;183;182;171;172;173;167;139;168;169;170;174;175;176;177;178;179;180;181;187;188;190;189;193;191;192;196;194;195;207;208;217;218;219;220;221;229;228;227;225;226;216;223;224;222;212;213;211;210;209;197;198;199;200;201;202;204;205;206;214;215;230;231;233;232;234;203;165;166;508;509;506;507;164;160;504;505;503;514;513;512;511;510;527;528;529;530;536;537;538;539;548;551;554;555;557;558;559;556;552;549;553;550;547;562;563;564;566;565;574;568;567;569;570;571;572;573;575;578;576;577;579;580;581;582;583;584;585;587;588;589;590;591;586;640;648;641;642;643;644;645;646;647;639;638;657;658;659;636;637;594;593;592;595;596;597;598;600;601;609;599;608;610;611;612;635;634;633;623;632;631;630;628;629;627;626;625;624;485;483;484;482;620;486;622;621;619;618;614;616;617;615;613;604;603;605;606;607;602;561;560;546;545;544;540;541;543;542;535;534;533;532;531;473;472;471;470;474;469;466;467;468;475;476;477;478;481;480;479;2;3;4;5;6;19;20;8;7;10;9;12;11;30;31;32;33;39;40;41;36;35;34;27;25;21;22;23;24;26;28;29;53;54;55;56;57;58;62;59;60;61;63;64;65;66;67;68;69;70;71;93;665;664;662;663;661;660;654;653;655;656;652;651;650;649;666;108;109;111;110;107;106;116;115;114;113;112;119;120;121;122;123;124;125;128;129;130;131;132;133;134;135;136;84;85;86;137;138;127;126;118;117;105;104;103;102;101;100;99;98;97;95;96;94;92;91;90;72;89;88;83;87;82;81;80;79;78;77;76;75;52;74;73;51;50;45;44;38;37;42;13;43;49;48;47;46;14;15;16;17;18;235;141;140;142;143;144;145;146;239;240;241;242;238;236;237;250;248;247;246;249;254;255;253;245;252;251;244;243;147;148;150;380;259;376;372;371;258;257;256;263;262;261;260;267;266;265;264;285;303;304;305;306;307;298;297;299;300;296;295;294;293;292;291;288;289;286;290;287;268;269;270;277;278;276;279;280;281;283;282;284;333;345;344;343;342;341;340;339;338;337;275;274;352;272;271;361;362;363;365;364;273;357;358;354;353;355;356;347;346;334;329;330;331;327;326;328;322;332;390;336;335;348;349;350;351;402;401;359;360;366;367;368;370;420;418;419;375;374;373;377;378;379;381;382;383;384;385;386;387;389;388;151;152;153;154;435;488;434;433;431;432;428;427;425;426;424;421;422;423;411;410;409;407;405;369;404;403;398;399;394;397;393;392;391;442;396;395;400;445;406;408;412;414;415;416;417;429;430;487;489;455;447;413;446;444;443;441;440;316;439;438;437;311;312;313;314;315;324;325;323;321;320;317;318;319;301;302;308;309;310",";");
        best.setFitness(calculateIndividualFitness(best, matrix));
        ArrayList<Double> randomFitnesses = new ArrayList<>();
        ArrayList<Double> greedyFitnesses = new ArrayList<>();
        ArrayList<Double> eaFitnesses = new ArrayList<>();
        ArrayList<Double> saFitnesses = new ArrayList<>();
        ArrayList<Double> tabuFitnesses = new ArrayList<>();
        Long[] time = {0L, 0L, 0L, 0L, 0L};
        Population population;
        Long tmp = 0L;
        for (int i = 0; i < 1; i++) {
            //RANDOM
//            tmp = System.currentTimeMillis();
//            population = Population.generateNewRandomPopulation(populationSizes[0]*1000 , dimension);
//            calculateIndividualsFitness(population, matrix);
//            best = population.getBestIndividual();
//            randomFitnesses.add(best.getFitness());
//            time[0] += System.currentTimeMillis() - tmp;
//            System.out.println("RANDOM| BEST:" + best.getFitness());

            //GREEDY
//            tmp = System.currentTimeMillis();
//            population = Population.generateGreedyPopulation(populationSizes[0], dimension, matrix);
//            calculateIndividualsFitness(population, matrix);
//            best = population.getBestIndividual();
//            greedyFitnesses.add(best.getFitness());
//            time[1] += System.currentTimeMillis() - tmp;
//            System.out.println("GREEDY| BEST:" + best.getFitness());

            //EA
            tmp = System.currentTimeMillis();
//            population = GAconf(matrix, new Configuration[]{new Configuration(populationSizes[0]*10, generations[0], crossoverRates[0], mutationRates[0], amountsOfChamps[0])}).get(0);
            population = GAconf(matrix, new Configuration[]{new Configuration(100, 100, 75, 30, 5)}).get(0);
            best = population.getBestIndividual();
            eaFitnesses.add(best.getFitness());
            time[2] += System.currentTimeMillis() - tmp;
            System.out.println("EA| BEST:" + best.getFitness());

//            //SA
//            tmp = System.currentTimeMillis();
//            Individual bestbest = sa(Population.generateNewRandomPopulation(populationSizes[0], dimension), matrix);
////            System.out.println("SA| BEST:" + bestbest.getFitness());
//            saFitnesses.add(bestbest.getFitness());
//            time[3] += System.currentTimeMillis() - tmp;

//            //TABU
//            tmp = System.currentTimeMillis();
//            best = tabu(Population.generateNewRandomPopulation(populationSizes[0], dimension), matrix);
//            tabuFitnesses.add(best.getFitness());
//            time[4] += System.currentTimeMillis() - tmp;
//            System.out.println("TABU| BEST:" + best.getFitness());
        }

//        System.out.println("RAND | GREED | EA | SA | TS");
//        for (Long l : time) {
//            System.out.print(l + " | ");
//        }
//        System.out.println();
//        for (Double d : randomFitnesses) {
//            System.out.print(d + ";");
//        }
//        System.out.println();
//        for (Double d : greedyFitnesses) {
//            System.out.println(d);
//        }
//        System.out.println();
//        for (Double d : eaFitnesses) {
//            System.out.print(d + ";");
//        }
//        System.out.println();
//        for (Double d : saFitnesses) {
//            System.out.print(d + ";");
//        }
//        System.out.println();
//        for (Double d : tabuFitnesses) {
//            System.out.print(d + ";");
//        }

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


//        System.out.println(getDeviation(randomFitnesses));
//        System.out.println(getDeviation(greedyFitnesses));
//        System.out.println(getDeviation(eaFitnesses));
//        System.out.println(getDeviation(saFitnesses));
//        System.out.println(getDeviation(tabuFitnesses));

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
        Double temp = temperatureSA;
        Individual bestSA = best;
        while (temp > minTemp) {
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
        Double x1 = Double.valueOf(berlin11.get(i).split("\\s+")[1]);
        Double x2 = Double.valueOf(berlin11.get(j).split("\\s+")[1]);
        Double y1 = Double.valueOf(berlin11.get(i).split("\\s+")[2]);
        Double y2 = Double.valueOf(berlin11.get(j).split("\\s+")[2]);

        Double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        return distance;
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
