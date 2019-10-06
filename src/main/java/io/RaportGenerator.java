package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RaportGenerator {
    public void generateRaport(String raportName, String data) throws IOException {
        FileWriter fileWriter = new FileWriter("output/" + raportName + ".csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(data);
        bufferedWriter.close();
    }
}
