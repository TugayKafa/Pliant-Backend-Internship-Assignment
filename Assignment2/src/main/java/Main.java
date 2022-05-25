import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String jsonString = scanner.nextLine();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Input input = objectMapper.readValue(jsonString, Input.class);
            output(input.getN(), input.getPnt(), input.getPwe());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void output(int n, int[] pnt, int[] pwe) throws IOException {

        Map<String, String> result = new LinkedHashMap<>();

        Gson gson = new Gson();
        String out;

        if (n < 2) {
            result.put("error", "n is less than 2");
            out = gson.toJson(result, LinkedHashMap.class);
            System.out.println(out);
            return;
        } else if (pnt.length < n) {
            result.put("error", String.format("length of pnt is less than %d", n));
            out = gson.toJson(result, LinkedHashMap.class);
            System.out.println(out);
            return;
        } else if (pwe.length < n) {
            result.put("error", String.format("length of pnt is less than %d", n));
            out = gson.toJson(result, LinkedHashMap.class);
            System.out.println(out);
            return;
        }

        int dMin = Integer.MAX_VALUE;
        findMinimumDifference(n, pnt, pwe, result, dMin);

        out = gson.toJson(result, LinkedHashMap.class);
        System.out.println(out);
    }

    private static void findMinimumDifference(int n, int[] pnt, int[] pwe, Map<String, String> result, int dMin) {
        for (int i = 0; i <= pnt.length - n; i++) {
            for (int j = 0; j <= pwe.length - n; j++) {

                int sum = 0;
                for (int k = 0; k < n; k++)
                    sum = sum + Math.abs(pnt[i + k] - pwe[j + k]);

                if (sum < dMin) {
                    dMin = sum;
                    result.put("d.min", sum + "");
                    result.put("s.pnt", i + "");
                    result.put("s.pwe", j + "");
                }
            }
        }
    }
}