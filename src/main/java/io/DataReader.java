package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class DataReader {
    static String resourcesPath = "src/main/resources/";

    public ArrayList<String> readFile(String fileName) throws FileNotFoundException {
        ArrayList<String> fileLines = new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(resourcesPath + fileName));
        bufferedReader.lines().forEach(fileLines::add);
        return fileLines;
    }

    public ArrayList<String> getCities(ArrayList<String> fileLines) {
        ArrayList<String> cities = new ArrayList<>();
        boolean flag = false;
        for (String line : fileLines) {
            if (!flag && line.contains("NODE_COORD_SECTION")) {
                flag = true;
                continue;
            }
            if (flag && line.contains("EOF")) {
                break;
            }
            if (flag) {
                cities.add(line);
            }
        }
        return cities;
    }
}
