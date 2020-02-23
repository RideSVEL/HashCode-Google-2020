package hash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pizza {
    private int max;
    private int[] input;
    private List<Integer> output;
    private int score;
    private String name;

    public Pizza(final String name) {
        this.name = name;
    }

    public void start() throws IOException {
        inputData();
        algorithmSolve();
        writingToFile();
        System.out.println("For input file - " + name + ", the score = " + score);
    }

    private void algorithmSolve() {
        for (int j = input.length - 1; j >= 0; j--) {
            int sum = 0;
            List<Integer> buffer = new ArrayList<>();
            for (int i = j; i >= 0; i--) {
                int tempSum = sum + input[i];
                if (tempSum == max) {
                    sum = tempSum;
                    buffer.add(i);
                    break;
                } else if (tempSum < max) {
                    sum = tempSum;
                    buffer.add(i);
                }
            }
            if (score < sum) {
                score = sum;
                output = new ArrayList<>(buffer);
            }
        }
        Collections.reverse(output);
    }

    private void inputData() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("input\\" + name + ".in"), StandardCharsets.UTF_8);
        String[] variables = lines.get(0).split(" ");
        max = Integer.parseInt(variables[0]);
        input = new int[Integer.parseInt(variables[1])];
        String[] slices = lines.get(1).split(" ");
        for (int i = 0; i < input.length; i++) {
            input[i] = Integer.parseInt(slices[i]);
        }
    }

    private void writingToFile() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("output\\" + name + ".out");
        pw.println(output.size());
        for (int i = 0; i < output.size(); i++) {
            pw.print(output.get(i));
            if (i != output.size() - 1) {
                pw.print(" ");
            }
        }
        pw.close();
    }

    public static void main(final String[] args) throws IOException {
        String[] files = new String[]{"a_example", "b_small", "c_medium", "d_quite_big", "e_also_big"};
        for (String file : files) {
            new Pizza(file).start();
        }
    }
}
