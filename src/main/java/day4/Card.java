package main.java.day4;

import main.java.day3.Schematic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Card {

    private static final HashMap<Integer, Card> cards = new HashMap<>();

    private  final int cardNum;
    private final HashSet<Integer> winningNums = new HashSet<>();
    private final HashSet<Integer> presentNums = new HashSet<>();
    private int winningNumsPresent = 0;
    public final int value;
    private Integer totalCards = null;

    public Card(String data) {
        Pattern stringPattern = Pattern.compile("Card\\s+(\\d+):\\s+(\\d+(?>\\s+\\d+)*)\\s+\\|\\s+(\\d+(?>\\s+\\d+)*)");
        Matcher m = stringPattern.matcher(data);
        if (!m.find()) {
            throw new IllegalArgumentException();
        } else {
            this.cardNum = Integer.parseInt(m.group(1));
            String presentNumsStr = m.group(3);
            for (String num: presentNumsStr.split("\\s+")) {
                this.presentNums.add(Integer.parseInt(num));
            }
            String winningNumsStr = m.group(2);
            for (String num: winningNumsStr.split("\\s+")) {
                int winningNum = Integer.parseInt(num);
                this.winningNums.add(winningNum);
                if (this.presentNums.contains(winningNum)) {
                    this.winningNumsPresent++;
                }
            }
            int val;
            if (this.winningNumsPresent > 0) {
                val = 1;
                for (int i = 1; i < this.winningNumsPresent; i++) {
                    val *= 2;
                }
            } else {
                val = 0;
            }
            this.value = val;
            Card.cards.put(this.cardNum, this);
        }
    }

    public int getCards() {
        if (this.totalCards == null) {
            this.totalCards = 1;
            for (int i = this.cardNum + 1; i < this.cardNum + 1 + this.winningNumsPresent; i++) {
                this.totalCards += Card.cards.get(i).getCards();
            }
        }
        return this.totalCards;
    }

    public static int getTotalCards(Iterable<String> inputs) {
        for (String input: inputs) {
            new Card(input);
        }
        int total = 0;
        for (int cardNum: Card.cards.keySet()) {
            total += Card.cards.get(cardNum).getCards();
        }
        return total;
    }

    public static int getTotalValues(Iterable<String> inputs) {
        int total = 0;
        for (String input: inputs) {
            Card card = new Card(input);
            total += card.value;
        }
        return total;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("inputs/day4_input.txt");
        Scanner sc = new Scanner(input);
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        System.out.println(Card.getTotalCards(lines));
    }
}
