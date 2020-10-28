package bookmark.commands;

import bookmark.BookmarkCategory;
import bookmark.BookmarkStorage;
import bookmark.BookmarkUi;
import exceptions.InvalidBookmarkException;
import exceptions.EmptyBookmarkException;

import java.util.ArrayList;

public class AddLinkCommand extends BookmarkCommand {
    public static final int ADD_LENGTH = 3;
    private String line;
    private int categoryNumber;
    private String link;
    private String title;

    public AddLinkCommand(String line, int categoryNumber) {
        this.categoryNumber = categoryNumber;
        this.line = line.trim();
        assert line.toLowerCase().startsWith("add") : "Add link command is called when line does not start with add";
        assert categoryNumber >= 0 : "Missing category number";
    }

    public void executeCommand(BookmarkUi ui, ArrayList<BookmarkCategory> categories, BookmarkStorage storage) {
        try {
            if (categoryNumber == 0) {
                ui.printChooseCategoryMessage();
            } else {
                assert categoryNumber > 0 : "Category number is not chosen";
                evaluateLink();
                categories.get(categoryNumber - 1).addLink(link,title);
                ui.showBookmarkLinkList(categories.get(categoryNumber - 1).getLinks());
                storage.saveLinksToFile(categories);
            }
        } catch (EmptyBookmarkException e) {
            ui.showEmptyLinkError();
        } catch (InvalidBookmarkException e) {
            ui.showInvalidLinkError();
        }
    }

    private void evaluateLink() throws EmptyBookmarkException, InvalidBookmarkException {
        if (line.length() <= ADD_LENGTH) {
            throw new EmptyBookmarkException();
        }
        assert line.length() > 0 : "Link should not be empty";
        link = line.substring(ADD_LENGTH).trim();
        if (link.contains(" t->")) {
            String[] array = link.split(" t->");
            if (array.length < 2) {
                throw new EmptyBookmarkException();
            }
            link = array[0].trim();
            title = array[1].trim();
        } else {
            title = null;
        }
        if (!link.contains(".") || link.contains(" ")) {
            throw new InvalidBookmarkException();
        }
        assert link.contains(".") && !link.contains(" ") : "Invalid link";
    }

    public int getCategoryNumber() {
        return categoryNumber;
    }
}
