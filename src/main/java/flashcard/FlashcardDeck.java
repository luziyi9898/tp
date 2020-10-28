package flashcard;

import userinterface.Ui;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FlashcardDeck {


    public ArrayList<Flashcard> flashcardDeck;

    public FlashcardDeck() {
        flashcardDeck = new ArrayList<Flashcard>();
    }

    public void addCards() {
        Ui.printDivider();
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter question: ");
        String question = in.nextLine();
        System.out.println("Please enter answer: ");
        String answer = in.nextLine();
        Ui.printDivider();
        flashcardDeck.add(new Flashcard(question, answer));
        System.out.println("You have successfully created the flashcard below: \n"
                + "Question: " + question + "\n"
                + "Answer: " + answer);
        Ui.printDivider();
    }

    public void listCards() {
        Ui.printDivider();
        System.out.println("Here is the list of flashcards you have: ");
        int cardIndex = 1;
        for (Flashcard flashcard: flashcardDeck) {
            System.out.println(cardIndex + ". " + flashcard.question + "|" + flashcard.answer);
            cardIndex++;
        }
        Ui.printDivider();
    }

    public void testRandomCard() {
        Scanner in = new Scanner(System.in);
        int score = 0;
        Ui.printDivider();
        System.out.println("You have entered the test mode.");
        Ui.printDivider();
        String attempt = "null";
        while (!attempt.equals("back")) {
            Random random = new Random();
            int randomIndex = random.nextInt(flashcardDeck.size());
            System.out.println("What is the answer to this question?");
            System.out.println(flashcardDeck.get(randomIndex).question);
            attempt = in.nextLine();
            while (!attempt.equals(flashcardDeck.get(randomIndex).answer) && !attempt.equals("back")) {
                System.out.println("Incorrect! Try again?");
                attempt = in.nextLine();
            }
            if (attempt.equals("back")) {
                Ui.printDivider();
                System.out.println("Exiting test mode...\n"
                        + "You are now back in flashcard main.");
            } else {
                score++;
                System.out.print("This is the right answer! ");
                if (score == 1) {
                    System.out.println("You now have " + score + " point.");
                } else {
                    System.out.println("You now have " + score + " points.");
                }
            }
            Ui.printDivider();
        }
    }

    public void deleteCard() {
        Scanner in = new Scanner(System.in);
        Ui.printDivider();
        System.out.println("Please enter the index of the card you wish to delete.");
        String userInput = in.nextLine();
        int cardIndex = 0;
        try {
            cardIndex = Integer.parseInt(userInput);
            if (cardIndex > flashcardDeck.size()) {
                Ui.printDivider();
                System.out.println("Sorry, you only have " + flashcardDeck.size() + " cards in your deck!\n"
                        + "Please enter a number within the range of 1-" + flashcardDeck.size() + ".");
                Ui.printDivider();
            } else {
                assert cardIndex <= flashcardDeck.size() : "card index inserted should be less than size of deck "
                        + "at this step";
                Ui.printDivider();
                System.out.println("Noted. I have removed this card: "
                        + flashcardDeck.get(cardIndex - 1).question + "|" + flashcardDeck.get(cardIndex - 1).answer
                        + "\n" + "Now you have " + (flashcardDeck.size() - 1) + " cards in the list.");
                Ui.printDivider();
                flashcardDeck.remove(cardIndex - 1);
            }
        } catch (NumberFormatException e) {
            Ui.printDivider();

            System.out.println("Please enter the index of the card as an integer!");
            Ui.printDivider();
        }
    }

    public void findCard() {
        int numberOfCardsFound = 0;
        Scanner in = new Scanner(System.in);
        Ui.printDivider();
        System.out.println("Please enter a relevant search term: ");
        String searchItem = in.nextLine();
        ArrayList<Flashcard> cardsFound = (ArrayList<Flashcard>) flashcardDeck.stream()
                .filter((flashcard) -> flashcard.question.contains(searchItem))
                .collect((Collectors.toList()));
        if (cardsFound.size() == 0) {
            Ui.printDivider();
            System.out.println("There are no matching cards in your list.");
            Ui.printDivider();
        } else {
            Ui.printDivider();
            System.out.println("Here are the matching cards in your list:");
            for (Flashcard flashcard: cardsFound) {
                numberOfCardsFound++;
                System.out.println(numberOfCardsFound + ". " + flashcard.question + "|" + flashcard.answer);
            }
            Ui.printDivider();
        }
    }
}
