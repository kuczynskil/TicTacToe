package pl.coderslab;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        display(POSSIBLE_POSITIONS);
        while (hasTheGameBeenWon().equals("no")) {
            chooseWhereToPutX();
            if (EXCLUDED_POSITIONS.size() < 9) {
                randomPositionToPutO();
            }
            display(ACTUAL_POSITIONS);
            if (hasTheGameBeenWon().equals("X")) {
                System.out.println("Congratulations, you won!");
                break;
            }
            if (hasTheGameBeenWon().equals("O")) {
                System.out.println("Unfortunately this time the computer turned out to be the winner.");
                break;
            }
            if (EXCLUDED_POSITIONS.size() == 9) {
                System.out.println("No winner has been determined.");
            }
        }
    }

    private static final List<String> POSSIBLE_POSITIONS =
            new LinkedList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
    private static final List<String> ACTUAL_POSITIONS = Arrays.asList(" ", " ", " ", " ", " ", " ", " ", " ", " ");
    private static final List<Integer> EXCLUDED_POSITIONS = new ArrayList<>();

    private static void display(List<String> positions) {
        for (int i = 0; i < positions.size(); i++) {
            if (i % 3 != 2) {
                System.out.printf("%s || ", positions.get(i));
            } else System.out.print(positions.get(i) + "\n");
        }
    }

    private static void chooseWhereToPutX() {
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        int choiceInt = 0;
        while (!(choiceInt > 0 && choiceInt < 10 && StringUtils.isNumeric(choice)) || EXCLUDED_POSITIONS.contains(choiceInt)) {
            System.out.print("Choose a position that you want to put 'X' at\n0 to display possible options: ");
            choice = scanner.nextLine();
            if (StringUtils.isNumeric(choice)) {
                choiceInt = Integer.parseInt(choice);
            }
            if (choiceInt == 0) {
                display(POSSIBLE_POSITIONS);
            }
        }
        ACTUAL_POSITIONS.set(choiceInt - 1, "X");
        POSSIBLE_POSITIONS.set(choiceInt - 1, " ");
        EXCLUDED_POSITIONS.add(choiceInt);
    }

    private static void randomPositionToPutO() {
        Random random = new Random();
        int randomNumber = random.nextInt(9) + 1;
        while (EXCLUDED_POSITIONS.contains(randomNumber)) {
            randomNumber = random.nextInt(9) + 1;
        }
        ACTUAL_POSITIONS.set(randomNumber - 1, "O");
        POSSIBLE_POSITIONS.set(randomNumber - 1, " ");
        EXCLUDED_POSITIONS.add(randomNumber);
    }

    private static String hasTheGameBeenWon() {
        if (horizontalWin().equals("X") || verticalWin().equals("X") || diagonalWin().equals("X")) {
            return "X";
        } else if (horizontalWin().equals("O") || verticalWin().equals("O") || diagonalWin().equals("O")) {
            return "O";
        }
        return "no";
    }

    private static String horizontalWin() {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < ACTUAL_POSITIONS.size(); i += 3) {
            rows.add(ACTUAL_POSITIONS.subList(i, i + 3));
        }
        return checkValuesInLines(rows);
    }

    private static String verticalWin() {
        List<List<String>> columns = new ArrayList<>();
        int columnStartIndex = 0;
        while (columnStartIndex < Math.sqrt(ACTUAL_POSITIONS.size())) {
            List<String> column = new ArrayList<>();
            for (int i = columnStartIndex; i < ACTUAL_POSITIONS.size(); i += 3) {
                column.add(ACTUAL_POSITIONS.get(i));
            }
            columns.add(column);
            columnStartIndex++;
        }
        return checkValuesInLines(columns);
    }

    private static String diagonalWin() {
        List<List<String>> diagonals = new ArrayList<>();
        List<String> diagonal1 = new ArrayList<>();
        List<String> diagonal2 = new ArrayList<>();
        for (int i = 0; i < 9; i += 4) {
            diagonal1.add(ACTUAL_POSITIONS.get(i));
        }
        for (int i = 2; i < 7; i += 2) {
            diagonal2.add(ACTUAL_POSITIONS.get(i));
        }
        diagonals.add(diagonal1);
        diagonals.add(diagonal2);
        return checkValuesInLines(diagonals);
    }

    private static String checkValuesInLines(List<List<String>> line) {
        for (List<String> column : line) {
            if (column.stream().allMatch(el -> el.equals("X"))) {
                return "X";
            }
            if (column.stream().allMatch(el -> el.equals("O"))) {
                return "O";
            }
        }
        return "";
    }
}
