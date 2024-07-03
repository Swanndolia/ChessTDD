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
                put('a', 0);
                put('b', 1);
                put('c', 2);
                put('d', 3);
                put('e', 4);
                put('f', 5);
                put('g', 6);
                put('h', 7);
            }};
            try {
                move[0] = columnToIntMap.get(userInput.charAt(0));
                move[1] = Integer.parseInt(String.valueOf(userInput.charAt(1) - 1));
                move[2] = columnToIntMap.get(userInput.charAt(2));
                move[3] = Integer.parseInt(String.valueOf(userInput.charAt(3) - 1));
                if (move[1] > 7 || move[1] < 0 || move[3] < 0 || move[3] > 7) {
                    return null;
                }
                return move;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
