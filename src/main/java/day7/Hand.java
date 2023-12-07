package main.java.day7;

import main.java.day6.Race;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


enum HandType {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
}

class Card implements Comparable<Card> {
    public final int val;
    public Card(char val, boolean useJokers) {
        if ('1' <= val && '9' >= val) {
            this.val = val - '0';
        } else {
            switch (val) {
                case 'T': {
                    this.val = 10;
                    break;
                }
                case 'J': {
                    if (useJokers) {
                        this.val = 0;
                    } else {
                        this.val = 11;
                    }
                    break;
                }
                case 'Q': {
                    this.val = 12;
                    break;
                }
                case 'K': {
                    this.val = 13;
                    break;
                }
                case 'A': {
                    this.val = 14;
                    break;
                }
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public int compareTo(Card o) {
        return Integer.compare(this.val, o.val);
    }
}

public class Hand implements Comparable<Hand> {

    private final int bid;
    private final ArrayList<Card> hand = new ArrayList<>();
    private final HashMap<Character, Integer> handMap = new HashMap<>();
    private final int jokers;

    public Hand(String handDef, boolean useJokers) {
        String[] handDefSplit = handDef.split("\\s+");
        this.bid = Integer.parseInt(handDefSplit[1]);
        this.handMap.put('J', 0);
        this.handMap.put('0', 0);
        for (int i = 0; i < handDefSplit[0].length(); i ++) {
            char cardVal = handDefSplit[0].charAt(i);
            this.handMap.put(cardVal, this.handMap.getOrDefault(cardVal, 0) + 1);
            this.hand.add(new Card(cardVal, useJokers));
        }
        if (useJokers) {
            this.jokers = this.handMap.remove('J');
        } else {
            this.jokers = 0;
        }
    }

    private HandType getType() {
        Collection<Integer> handMapKeys = new ArrayList<>(this.handMap.values());
        if (handMapKeys.remove(5 - this.jokers)) {
            return HandType.FIVE_OF_A_KIND;
        } else if (handMapKeys.remove(4 - this.jokers)) {
            return HandType.FOUR_OF_A_KIND;
        } else if (handMapKeys.remove(3 - this.jokers)) {
            if (handMapKeys.remove(2)) {
                return HandType.FULL_HOUSE;
            } else {
                return HandType.THREE_OF_A_KIND;
            }
        } else if (handMapKeys.remove(2 - this.jokers)) {
            if (handMapKeys.remove(2)) {
                return HandType.TWO_PAIR;
            } else {
                return HandType.ONE_PAIR;
            }
        } else {
            return HandType.HIGH_CARD;
        }
    }

    public static int getTotalWinnings(Iterable<String> handStrs, boolean useJokers) {
        int total = 0;
        ArrayList<Hand> hands = new ArrayList<>();
        for (String handDef: handStrs) {
            hands.add(new Hand(handDef, useJokers));
        }
        Collections.sort(hands);
        for (int i = 0; i < hands.size(); i++) {
            int rank = i + 1;
            total += hands.get(i).bid * rank;
        }
        return total;
    }

    @Override
    public int compareTo(Hand o) {
        int comparisonVal = this.getType().compareTo(o.getType());
        int i = 0;
        while (comparisonVal == 0 && i < this.hand.size()) {
            comparisonVal = this.hand.get(i).compareTo(o.hand.get(i));
            i++;
        }
        return comparisonVal;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("inputs/day7_input.txt");
        Scanner sc = new Scanner(input);
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        long result = Hand.getTotalWinnings(lines, true);
        System.out.println(result);
    }
}
