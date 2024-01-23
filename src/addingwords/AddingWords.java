package addingwords;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class AddingWords {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String command;
        Map<String, Integer> variablesMap = new HashMap<>();
        Map<Integer, String> reversedVariablesMap = new HashMap<>();
        outer:
        while ((command = in.readLine()) != null) {
            if (command.startsWith("def ")) {
                String[] parts = command.substring(4).split(" ");
                reversedVariablesMap.remove(variablesMap.get(parts[0]));
                variablesMap.put(parts[0], Integer.valueOf(parts[1]));
                reversedVariablesMap.put(Integer.valueOf(parts[1]), parts[0]);
            } else if (command.startsWith("calc ")) {
                String calcParams = command.substring(5);
                String[] calcParamsParts = calcParams.substring(0, calcParams.length() - 2).split(" ");
                boolean plusSign = true;
                int sum = 0;
                for (String part : calcParamsParts) {
                    if (part.equals("+")) {
                        plusSign = true;
                    } else if (part.equals("-")) {
                        plusSign = false;
                    } else {
                        if (!variablesMap.containsKey(part)) {
                            out.println(calcParams + " unknown");
                            continue outer;
                        }
                        if (plusSign) {
                            sum += variablesMap.get(part);
                        } else {
                            sum -= variablesMap.get(part);
                        }
                    }
                }
                out.println(calcParams + " " +
                        (reversedVariablesMap.getOrDefault(sum, "unknown")));
            } else {
                variablesMap.clear();
                reversedVariablesMap.clear();
            }
        }

        out.close();
    }
}
