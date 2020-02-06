package NonSqlProject.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Class to ask input to user.
 * 
 * @author mfontana
 */
public class InputAsker {

    /**
     * Request String
     *
     * @param message
     * @return String
     */
    public static String askString(String message) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        do {
            try {
                System.out.println(message);
                answer = br.readLine();
                if (answer.equals("")) {
                    System.out.println("You must write something.");
                }
            } catch (IOException ex) {
                System.out.println("Error input / output.");
            }
        } while (answer.equals(""));
        return answer;
    }

    /**
     * Request String, it can only be optionA or optionB
     *
     * @param message String
     * @param optionA String
     * @param optionB String
     * @param optionC String
     * @return String (optionA or optionB or optionC)
     */
    public static String askString(String message, String optionA, String optionB, String optionC) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        do {
            answer = askString(message);
            if (!answer.equalsIgnoreCase(optionA) && !answer.equalsIgnoreCase(optionB) && !answer.equalsIgnoreCase(optionC)) {
                System.out.println("Wrong answer! Write " + optionA + " , " + optionB + " or " + optionC + " please");
            }
        } while (!answer.equalsIgnoreCase(optionA) && !answer.equalsIgnoreCase(optionB) && !answer.equalsIgnoreCase(optionC));
        return answer;
    }

    /**
     * Request String from a ArrayList of String accepteds
     *
     * @param message String
     * @param wordsAccepted ArrayList of String
     * @return String
     */
    public static String askString(String message, ArrayList<String> wordsAccepted) {
        String answer;
        boolean wordOk;
        do {
            for (String word : wordsAccepted) {
                System.out.println(word);
            }
            answer = InputAsker.askString(message);
            wordOk = wordIsOk(answer, wordsAccepted);
            if (!wordOk) {
                System.out.println("Wrong answer!");
            }
        } while (!wordOk);
        return answer;
    }

    /**
     * Returns true if the word exists in wordsAccepted.
     * 
     * @param word 
     * @param wordsAccepted
     * @return boolean
     */
    private static boolean wordIsOk(String word, ArrayList<String> wordsAccepted) {
        for (String w : wordsAccepted) {
            if (w.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Request int
     *
     * @param message
     * @return int
     */
    public static int askInt(String message) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = 0;
        boolean error;
        do {
            try {
                System.out.println(message);
                num = Integer.parseInt(br.readLine());
                error = false;
            } catch (IOException ex) {
                System.out.println("Error input / output.");
                error = true;
            } catch (NumberFormatException ex) {
                System.out.println("Please, write integer number.");
                error = true;
            }
        } while (error);
        return num;
    }

    /**
     * Request int in the interval between min and max (inclusives)
     *
     * @param message
     * @param min
     * @param max
     * @return int
     */
    public static int askInt(String message, int min, int max) {
        int num;
        do {
            num = askInt(message);
            if (num < min || num > max) {
                System.out.println("Error, the number must be between " + min + " and " + max);
            }
        } while (num < min || num > max);
        return num;
    }

}
