import io.DataReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

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
        int x = 0;
        int y = 0;
        for (int i = 0; i < 10; i++) {
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

    @Test
    public void countDistance() {
        String cities = "468 469 474 475 478 483 486 489 490 491 492 493 497 498 500 501 506 508 509 512 513 517 518 519 520 523 524 537 538 541 542 543 546 547 548 549 550 552 554 556 562 564 565 566 567 578 580 582 584 585 587 588 589 591 592 593 595 596 599 600 601 602 604 608 612 614 615 618 620 621 623 624 625 626 627 628 629 630 631 632 633 640 642 645 646 650 651 652 653 654 655 656 659 660 663 666 667 671 672 676 678 679 680 682 683 684 688 690 691 693 694 697 698 699 700 702 705 706 708 709 713 714 720 722 723 725 726 727 728 730 732 733 734 735 738 739 744 745 747 748 749 752 754 755 757 758 759 760 766 768 769 770 773 775 776 781 782 784 785 786 787 788 791 793 794 795 796 797 800 801 805 811 812 815 816 819 821 822 824 825 826 827 834 836 837 838 839 841 842 846 851 853 854 855 857 858 859 860 861 863 865 866 867 868 869 872 873 874 876 878 879 880 881 884 885 893 895 896 898 908 910 911 916 920 922 926 927 928 929 931 932 934 935 936 938 939 940 941 943 944 946 947 948 951 952 960 964 966 967 970 971 972 973 975 976 980 983 985 986 987 989 990 993 994 997 999 1000 1001 1002 1003 1004 1005 1008 1010 1012 1015 1017 1018 1019 1022 1023 1027 1028 1029 1031 1032 1037 1038 1039 1040 1041 1042 1045 1050 1051 1053 1056 1057 1059 1061 1066 1067 1070 1071 1074 1075 1076 1078 1079 1080 1081 1082 1085 1087 1095 1096 1098 1099 1101 1103 1105 1110 1111 1112 1114 1115 1117 1119 1120 1124 1127 1128 1131 1134 1136 1142 1143 1144 1146 1148 1156 1157 1158 1159 1160 1167 1168 1176 1177 1178 1179 1181 1182 1183 1185 1190 1193 1196 1200 1203 1205 1206 1208 1209 1213 1215 1219 1221 1223 1224 1225 1229 1230 1234 1235 1237 1239 1240 1241 1242 1248 1249 1250 1256 1258 1259 1261 1264 1266 1268 1269 1270 1271 1273 1275 1278 1279 1280 1281 1282 1284 1286 1287 1288 1291 1295 1299 1300 1303 1304 1308 1311 1314 1315 1316 1318 1319 1320 1321 1325 1331 1332 1333 1334 1336 1339 1341 1343 1348 1350 1352 1353 1356 1357 1361 1364 1365 1368 1369 1371 1374 1375 1376 1378 1377 1373 1372 1370 1367 1366 1363 1362 1360 1359 1358 1355 1354 1351 1349 1347 1346 1345 1344 1342 1340 1338 1337 1335 1330 1329 1328 1327 1326 1324 1323 1322 1317 1313 1312 1310 1309 1307 1306 1305 1302 1301 1298 1297 1296 1294 1293 1292 1290 1289 1285 1283 1277 1276 1274 1272 1267 1265 1263 1262 1260 1257 1255 1254 1253 1252 1251 1247 1246 1245 1244 1243 1238 1236 1233 1232 1231 1228 1227 1226 1222 1220 1218 1217 1216 1214 1212 1211 1210 1207 1204 1202 1201 1199 1198 1197 1195 1194 1192 1191 1189 1188 1187 1186 1184 1180 1175 1174 1173 1172 1171 1170 1169 1166 1165 1164 1163 1162 1161 1155 1154 1153 1152 1151 1150 1149 1147 1145 1141 1140 1139 1138 1137 1135 1133 1132 1130 1129 1126 1125 1123 1122 1121 1118 1116 1113 1109 1108 1107 1106 1104 1102 1100 1097 1094 1093 1092 1091 1090 1089 1088 1086 1084 1083 1077 1073 1072 1069 1068 1065 1064 1063 1062 1060 1058 1055 1054 1052 1049 1048 1047 1046 1044 1043 1036 1035 1034 1033 1030 1026 1025 1024 1021 1020 1016 1014 1013 1011 1009 1007 1006 998 996 995 992 991 988 984 982 981 979 978 977 974 969 968 965 963 962 961 959 958 957 956 955 954 953 950 949 945 942 937 933 930 925 924 923 921 919 918 917 915 914 913 912 909 907 906 905 904 903 902 901 900 899 897 894 892 891 890 889 888 887 886 883 882 877 875 871 870 864 862 856 852 850 849 848 847 845 844 843 840 835 833 832 831 830 829 828 823 820 818 817 814 813 810 809 808 807 806 804 803 802 799 798 792 790 789 783 780 779 778 777 774 772 771 767 765 764 763 762 761 756 753 751 750 746 743 742 741 740 737 736 731 729 724 721 719 718 717 716 715 712 711 710 707 704 703 701 696 695 692 689 687 686 685 681 677 675 674 673 670 669 668 665 664 662 661 658 657 649 648 647 644 643 641 639 638 637 636 635 634 622 619 617 616 613 611 610 609 607 606 605 603 598 597 594 590 586 583 581 579 577 576 575 574 573 572 571 570 569 568 563 561 560 559 558 557 555 553 551 545 544 540 539 536 535 534 533 532 531 530 529 528 527 526 525 522 521 516 515 514 511 510 507 505 504 503 502 499 496 495 494 488 487 485 484 482 481 480 479 477 476 473 472 471 470 467 466 465 464 463 462 460 458 456 452 449 447 446 443 442 441 440 438 437 435 434 433 428 426 425 423 421 420 419 417 415 413 412 411 410 407 402 401 400 399 395 393 392 389 388 386 384 382 381 378 377 376 375 372 371 370 369 366 363 362 359 358 355 351 350 349 347 346 343 342 339 338 336 330 329 328 326 325 323 316 315 314 310 308 305 303 302 300 298 297 296 295 294 293 292 291 290 288 279 278 275 274 273 272 268 267 266 265 264 262 261 260 259 258 257 253 252 251 245 243 241 239 237 236 234 233 226 224 221 220 219 214 211 208 205 204 203 201 198 194 191 188 187 186 184 182 180 179 177 173 172 171 169 168 167 166 164 162 161 160 159 157 155 154 153 151 150 148 147 146 145 144 141 140 139 138 137 136 135 134 133 132 130 129 122 121 120 119 116 115 114 110 109 108 107 106 104 102 98 93 81 80 78 74 73 69 68 65 64 61 59 56 54 51 49 47 42 38 37 32 27 26 25 23 22 20 18 14 13 11 4 3 2 0 1 5 6 7 8 9 10 12 15 16 17 19 21 24 28 29 30 31 33 34 35 36 39 40 41 43 44 45 46 48 50 52 53 55 57 58 60 62 63 66 67 70 71 72 75 76 77 79 82 83 84 85 86 87 88 89 90 91 92 94 95 96 97 99 100 101 103 105 111 112 113 117 118 123 124 125 126 127 128 131 142 143 149 152 156 158 163 165 170 174 175 176 178 181 183 185 189 190 192 193 195 196 197 199 200 202 206 207 209 210 212 213 215 216 217 218 222 223 225 227 228 229 230 231 232 235 238 240 242 244 246 247 248 249 250 254 255 256 263 269 270 271 276 277 280 281 282 283 284 285 286 287 289 299 301 304 306 307 309 311 312 313 317 318 319 320 321 322 324 327 331 332 333 334 335 337 340 341 344 345 348 352 353 354 356 357 360 361 364 365 367 368 373 374 379 380 383 385 387 390 391 394 396 397 398 403 404 405 406 408 409 414 416 418 422 424 427 429 430 431 432 436 439 444 445 448 450 451 453 454 455 457 459 461";
        ArrayList<Integer> city = new ArrayList<>();
        for (String x : cities.split(" ")) {
            city.add(Integer.valueOf(x));
        }
        for (Integer x : city) {
            System.out.print(x + ", ");
        }

    }

    @Test
    public void sss() {
        Double[][] matrix = new Double[][]{{
                0.0, 666.0, 281.0, 395.0, 291.0, 326.0, 640.0, 426.0, 600.0, 561.0, 1040.0,},
                {
                        666.0, 0.0, 649.0, 1047.0, 945.0, 978.0, 45.0, 956.0, 1134.0, 1132.0, 1638.0,},
                {
                        281.0, 649.0, 0.0, 603.0, 508.0, 542.0, 610.0, 308.0, 485.0, 487.0, 1266.0,},
                {
                        395.0, 1047.0, 603.0, 0.0, 104.0, 69.0, 1026.0, 525.0, 611.0, 533.0, 663.0,},
                {
                        291.0, 945.0, 508.0, 104.0, 0.0, 35.0, 923.0, 470.0, 583.0, 513.0, 760.0,},
                {
                        326.0, 978.0, 542.0, 69.0, 35.0, 0.0, 957.0, 491.0, 596.0, 523.0, 726.0,},
                {
                        640.0, 45.0, 610.0, 1026.0, 923.0, 957.0, 0.0, 918.0, 1095.0, 1095.0, 1627.0,},
                {
                        426.0, 956.0, 308.0, 525.0, 470.0, 491.0, 918.0, 0.0, 183.0, 180.0, 1144.0,},
                {
                        600.0, 1134.0, 485.0, 611.0, 583.0, 596.0, 1095.0, 183.0, 0.0, 83.0, 1165.0,},
                {
                        561.0, 1132.0, 487.0, 533.0, 513.0, 523.0, 1095.0, 180.0, 83.0, 0.0, 1082.0,},
                {
                        1040.0, 1638.0, 1266.0, 663.0, 760.0, 726.0, 1627.0, 1144.0, 1165.0, 1082.0, 0.0,},};
        System.out.println(getClosestCity(1, matrix));
        System.out.println(getClosestCity(2, matrix));
        System.out.println(getClosestCity(3, matrix));
        System.out.println(getClosestCity(4, matrix));
        System.out.println(getClosestCity(5, matrix));
        System.out.println(getClosestCity(6, matrix));
        System.out.println(getClosestCity(9, matrix));
        System.out.println(getClosestCity(10, matrix));
    }


    private int getClosestCity(int closestToIndex, Double[][] matrix) {
        ArrayList<CityDistance> cities = new ArrayList<>();
        for (int i = 0; i < matrix[closestToIndex].length; i++) {
            cities.add(new CityDistance(i, matrix[closestToIndex][i]));
        }
        Collections.sort(cities);
        return cities.get(1).index;
    }
}
