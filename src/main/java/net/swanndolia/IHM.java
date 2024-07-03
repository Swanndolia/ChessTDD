package net.swanndolia;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IHM {
    private static final Scanner scanner = new Scanner(System.in);

    public static String askUserInput() {
        return scanner.nextLine();
    }

    public static void sendMessageToUser(String message) {
        System.out.println(message);
    }

    public static int[] parseUserInput(String userInput) {
        if (userInput.length() == 4) {
            int[] move = new int[4];
            userInput = userInput.toLowerCase();
            Map<Character, Integer> columnToIntMap = new HashMap<Character, Integer>() {{
                put('a', 1);
                put('b', 2);
                put('c', 3);
                put('d', 4);
                put('e', 5);
                put('f', 6);
                put('g', 7);
                put('h', 8);
            }};
            try {
                move[0] = Integer.parseInt(String.valueOf(userInput.charAt(1))) - 1;
                move[1] = columnToIntMap.get(userInput.charAt(0)) - 1;
                move[2] = Integer.parseInt(String.valueOf(userInput.charAt(3))) - 1;
                move[3] = columnToIntMap.get(userInput.charAt(2)) - 1;
                for (int coordinate : move) {
                    sendMessageToUser(String.valueOf(coordinate));
                    if (coordinate < 0 || coordinate > 7) {
                        sendMessageToUser("OutOfBonds");
                        return null;
                    }
                }
                return move;
            } catch (Exception e) {
                sendMessageToUser(e.getMessage());
                return null;
            }
        }
        return null;
    }
}
