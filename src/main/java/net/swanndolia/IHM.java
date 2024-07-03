package net.swanndolia;

import net.swanndolia.moves.ComputeMove;

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
            return ComputeMove.convertMoveCoordinate(userInput.toLowerCase());
        }
        return null;
    }
}
