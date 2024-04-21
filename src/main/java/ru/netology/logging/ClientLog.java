package ru.netology.logging;

import java.io.*;

public class ClientLog {

    private File log;

    public File getLog() {
        return log;
    }

    public void log(int productNum, int amount) {
        log = new File("log.txt");
        try (FileWriter out = new FileWriter(log, true)) {
            out.write((productNum + 1) + "," + amount + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportAsCSV(File txtFile) {
        try (BufferedReader in = new BufferedReader(new FileReader(txtFile));
             FileWriter outCsv = new FileWriter("log.csv")) {
            try {
                outCsv.write("productNum,amount" + "\n");
                String input;
                while ((input = in.readLine()) != null) {
                    outCsv.write(input + "\n");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
