package bookmark;

import bookmark.commands.AddLinkCommand;
import bookmark.commands.BackCommand;
import bookmark.commands.BookmarkCommand;
import bookmark.commands.ChangeModeCommand;
import bookmark.commands.ListCommand;
import bookmark.commands.RemoveLinkCommand;
import exceptions.InvalidCommandException;
import studyit.CommandParser;
import studyit.StudyItLog;

public class BookmarkParser extends CommandParser {
    public BookmarkParser() {
    }

    public BookmarkCommand evaluateInput(String command, int chosenCategory) throws InvalidCommandException {
        if (command == null) {
            StudyItLog.logger.finest("Empty command");
            throw new InvalidCommandException();
        }
        String commandModified = command.trim().toLowerCase();
        if (commandModified.startsWith("bm")) {
            return new ChangeModeCommand(command, chosenCategory);
        } else if (commandModified.startsWith("add")) {
            return new AddLinkCommand(command, chosenCategory);
        } else if (commandModified.startsWith("rm")) {
            return new RemoveLinkCommand(command, chosenCategory);
        } else if (commandModified.startsWith("list")) {
            return new ListCommand(chosenCategory);
        } else if (commandModified.startsWith("back")) {
            return new BackCommand(chosenCategory);
        } else {
            StudyItLog.logger.info("Cannot understand bookmark command");
            throw new InvalidCommandException();
        }
    }

}