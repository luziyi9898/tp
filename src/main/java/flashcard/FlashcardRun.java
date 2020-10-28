package flashcard;

import studyit.StudyItLog;

import java.io.IOException;
import java.util.Scanner;

public class FlashcardRun {
    public static final String TEST = "test";
    public static final String DELETE = "delete";
    public static final String FIND = "find";
    public FlashcardDeck flashcardDeck;
    public FlashcardStorage storage;
    public static final String ADD = "add";
    public static final String LIST = "list";
    public static final String EXIT = "exit";

    public FlashcardRun() {
        flashcardDeck = new FlashcardDeck();
        try {
            storage = new FlashcardStorage("data/flashcard.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.readFromFile(flashcardDeck);
        StudyItLog.logger.info("Flashcard mode initialized");
    }

    public void run(String command) {
        switch (command) {
        case ADD:
            flashcardDeck.addCards();
            break;
        case DELETE:
            flashcardDeck.deleteCard();
            break;
        case LIST:
            flashcardDeck.listCards();
            break;
        case TEST:
            flashcardDeck.testRandomCard();
            break;
        case FIND:
            flashcardDeck.findCard();
            break;
        default:
            System.out.println("Invalid command. Valid commands are 'add', 'delete', 'list', 'test' and 'find'.\n"
                    + "Use 'exit' to exit the flashcard mode.");
            StudyItLog.logger.warning("Invalid flashcard command: Command unidentifiable");
        }
        storage.writeToFile(flashcardDeck);
    }
}
