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
    static String[] nrw1379 = {"nrw1379.tsp"};
    static int[] populationSizes = {100};
    static int[] generations = {500};
    static double[] crossoverRates = {10}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
    static double[] mutationRates = {60}; //100.0 = 100%; 50.0 = 50% 0.5 = 0.5%. Min: 0.001 Max: 100.0
    static int[] amountsOfChamps = {5};
    static String directory = "zajecia/";
    static String raportPrefix = "sw";
    static Selection selectionType = Selection.TOURNAMENT;
    static Crossover crossoverType = Crossover.OX;
    static Mutation mutationType = Mutation.SWAP;

    public static void main(String[] args) throws IOException {
        //GA();
        tabu();
    }

    private static void tabu() throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        ArrayList<String> cities = dataReader.getCities(dataReader.readFile(nrw1379[0]));
        int dimension = cities.size();
        Double[][] matrix = new Double[dimension][dimension];
        parseCitiesToMatrix(cities, matrix);

        //Population population = Population.generateNewRandomPopulation(1, dimension);
        String hardCodedIndividual = "871 841 875 890 911 928 929 947 930 964 986 984 976 996 993 1017 1064 1065 1055 1061 1092 1105 1110 1131 1120 1139 1182 1188 1208 1222 1158 1126 1114 1151 1125 1121 1108 1127 1150 1134 1149 1176 1201 1166 1165 1196 1202 1211 1232 1245 1229 1224 1198 1183 1143 1167 1192 1220 1235 1230 1244 1250 1264 1240 1231 1219 1177 1206 1200 1174 1157 1146 1100 1093 1112 1101 1057 1054 1058 1074 1089 1085 1071 1062 1027 1032 1029 1011 982 983 955 942 907 872 835 813 857 893 913 887 884 873 877 854 829 830 840 847 820 810 784 806 760 732 737 773 788 821 774 761 724 701 657 665 692 703 678 700 723 757 758 754 779 786 794 814 825 812 832 827 836 819 798 807 790 775 755 750 749 726 687 680 669 725 695 715 719 702 741 753 756 776 777 808 801 769 759 729 710 704 684 675 682 688 662 623 597 570 563 538 501 516 532 517 524 553 560 543 577 606 587 621 673 649 635 630 647 653 646 661 677 714 739 796 782 734 720 751 770 748 735 727 705 686 681 709 722 696 667 651 671 718 733 711 691 660 633 601 598 593 627 643 632 602 609 612 655 638 605 586 565 568 573 567 575 582 499 492 469 467 463 486 483 508 489 513 509 487 473 461 458 464 431 409 433 452 450 488 507 528 534 526 559 584 616 636 663 694 668 637 624 600 613 611 561 537 510 470 445 490 454 446 407 396 395 376 346 332 319 311 279 258 304 358 364 388 370 347 372 389 385 386 405 419 436 447 440 484 449 442 435 375 379 392 378 342 399 441 455 493 462 443 514 525 481 480 476 522 547 574 564 588 617 645 656 641 634 614 572 539 531 550 590 603 625 652 620 619 650 690 697 707 744 768 802 826 837 844 867 901 909 920 950 949 969 1000 974 958 1009 1016 1030 1107 1109 1132 1156 1102 1084 1056 1075 1053 1043 1018 1004 1035 1013 956 917 921 953 981 999 1021 1023 1038 1066 1077 1104 1119 1103 1122 1142 1162 1171 1159 1160 1190 1207 1218 1228 1239 1269 1265 1294 1284 1266 1275 1259 1271 1258 1336 1348 1332 1343 1357 1355 1350 1361 1367 1372 1360 1368 1376 1364 1358 1352 1334 1327 1349 1347 1346 1326 1319 1303 1321 1311 1293 1296 1315 1328 1341 1356 1359 1370 1371 1369 1374 1375 1377 1378 1373 1365 1366 1363 1354 1353 1333 1337 1322 1335 1316 1312 1285 1291 1282 1263 1251 1268 1255 1236 1261 1279 1280 1290 1304 1298 1320 1325 1307 1306 1295 1270 1257 1267 1203 1163 1141 1184 1168 1152 1129 1118 1140 1148 1179 1193 1209 1226 1221 1242 1243 1253 1276 1283 1297 1314 1313 1329 1342 1362 1351 1344 1345 1339 1340 1331 1308 1299 1301 1317 1324 1305 1310 1309 1318 1323 1338 1330 1302 1300 1288 1272 1237 1234 1217 1186 1194 1216 1205 1187 1170 1169 1195 1145 1124 1113 1088 1069 1095 1116 1154 1164 1135 1137 1155 1191 1197 1210 1214 1227 1249 1256 1281 1287 1274 1286 1289 1292 1277 1278 1273 1260 1248 1238 1246 1241 1252 1254 1262 1247 1225 1223 1199 1173 1172 1175 1138 1130 1115 1144 1133 1081 1052 1040 1020 1033 1048 1060 1083 1072 1097 1111 1147 1161 1185 1212 1215 1189 1181 1180 1204 1233 1213 1178 1153 1123 1106 1096 1068 1094 1078 1045 1026 1002 992 1007 954 934 912 902 868 845 818 800 762 792 789 767 747 730 698 712 783 772 740 731 746 781 799 803 833 856 852 817 811 838 869 858 878 897 896 885 922 959 988 941 931 906 880 874 888 865 843 848 828 809 816 791 742 765 771 738 736 717 716 721 685 664 607 576 551 502 448 456 494 529 546 540 557 562 581 583 579 644 594 626 628 659 654 689 670 666 706 683 679 676 631 622 548 558 497 521 544 520 496 426 437 495 465 410 359 302 241 305 333 297 235 201 219 214 218 177 173 122 93 139 180 133 107 61 26 40 47 71 46 28 11 6 19 14 33 70 56 75 57 55 81 98 120 153 162 116 115 112 167 168 198 206 231 288 303 318 341 351 352 338 334 301 280 253 274 287 273 300 275 262 237 186 157 129 119 89 91 105 102 94 136 150 154 179 189 211 217 195 226 230 199 176 138 155 194 182 148 160 208 239 245 204 216 254 269 270 293 308 299 330 369 367 393 394 423 434 475 468 472 485 511 527 549 555 589 523 519 512 482 428 418 381 384 343 345 357 329 326 391 420 438 429 425 411 387 353 339 323 314 296 290 276 248 227 209 187 181 132 126 104 108 109 86 64 59 52 43 68 80 90 101 84 100 141 123 65 54 35 22 9 2 1 18 24 27 44 29 30 37 51 69 45 67 66 117 130 118 99 78 60 88 63 38 23 10 5 0 13 16 7 17 21 31 15 12 4 3 8 25 32 62 53 36 34 20 42 82 50 77 85 103 111 127 158 184 163 151 125 97 96 87 83 73 39 41 79 106 113 128 114 124 171 185 159 169 145 166 191 192 220 215 247 261 286 313 328 281 265 266 252 213 233 223 190 174 144 164 152 178 193 205 210 236 246 264 251 238 257 325 321 310 316 356 365 398 413 415 401 417 430 471 491 500 505 479 451 460 466 478 533 554 571 591 595 604 580 566 542 536 530 545 608 642 672 640 610 585 578 541 498 535 515 506 477 457 439 412 414 403 368 340 355 382 400 422 404 361 362 374 380 363 336 317 298 292 307 324 285 263 256 250 224 203 196 175 156 146 140 135 165 197 212 200 222 243 295 282 260 259 244 242 272 294 255 249 229 225 202 183 172 188 149 137 121 110 92 74 49 48 72 58 76 95 134 147 142 161 143 131 170 221 228 267 278 277 312 348 335 322 271 240 207 234 232 291 306 331 360 371 427 421 366 349 354 315 320 289 283 268 284 309 327 337 373 350 344 383 397 444 453 459 424 402 377 406 390 408 416 432 474 504 503 518 552 556 569 592 596 615 648 629 599 618 639 658 674 708 713 699 693 728 745 743 764 766 780 793 815 823 805 785 752 763 778 795 824 831 846 822 804 787 797 834 855 850 842 839 851 853 849 861 870 881 895 889 894 940 933 910 898 905 882 876 864 859 862 863 860 866 892 899 903 919 927 926 923 946 963 973 991 1010 997 978 977 989 995 1014 1031 1039 1050 1067 1099 1136 1128 1117 1098 1086 1059 1087 1049 1042 1019 1012 987 971 957 948 932 916 918 937 891 883 924 939 951 979 1006 1024 1022 1070 1082 1051 1034 1003 1001 985 968 965 970 962 945 943 915 900 914 904 925 936 967 961 966 990 980 998 1008 1005 1036 1041 1073 1091 1079 1044 1046 1063 1076 1090 1080 1047 1037 1028 1025 1015 994 975 972 960 944 952 938 935 908 886 879";
        Population population = Population.generateNewEmptyPopulation(1, dimension);
        population.addIndividual(Individual.createIndividualFromString(hardCodedIndividual," "));

        calculateIndividualsFitness(population, matrix);

        Individual best = population.getBestIndividual();

        ArrayList<int[]> tabuList = new ArrayList<>();
        int tabuListMaxSize = 10;

        tabuList.add(best.getCitiesIds());

        System.out.println("INICJALIZACJA:" + best.getFitness());
        int i = 0;
        while (i != 1000) {
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
                if(i==500){
                    System.out.println("i=500:best");
                    best.printData();
                }
            }

        }
        best.printData();
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

    public static void GA() throws IOException {
        Date startDate = new Date();
        for (int counter = 0; counter < 1; counter++) {
            for (String file : gr666) {
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
