package bookmark;

import studyit.StudyItLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class BookmarkStorage {
    private final File bookmarkFile;
    private final String filePath;

    public BookmarkStorage(String filePath) {
        String dirPath = "data";
        File fileDir = new File(dirPath);

        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        this.filePath = filePath;
        bookmarkFile = new File(filePath);
    }

    public ArrayList<BookmarkCategory> loadFile() {
        try {
            Scanner s = new Scanner(bookmarkFile);
            ArrayList<BookmarkCategory> bookmarkCategories = new ArrayList<>();
            int i = 0;
            while (s.hasNext()) {
                String[] parseCategory = s.nextLine().split("=");
                String categoryName = parseCategory[0];
                bookmarkCategories.add(new BookmarkCategory(categoryName));
                if (parseCategory.length < 2) {
                    i++;
                    continue;
                }
                String[] links = parseCategory[1].split(",");
                String title;
                int x = 0;
                for (String link : links) {
                    link = link.trim();
                    if (link.contains(" t->")) {
                        String[] array = link.split(" t->");
                        link = array[0].trim();
                        title = array[1].trim();
                    } else {
                        title = null;
                    }
                    assert i >= 0 : "Problem reading file";
                    if (link.contains("|STAR|")) {
                        bookmarkCategories.get(i).addLink(link.substring(6),title);
                        bookmarkCategories.get(i).markLinkAsStar(x);
                    } else {
                        bookmarkCategories.get(i).addLink(link,title);
                    }
                    x++;
                }
                i++;
            }
            return bookmarkCategories;
        } catch (FileNotFoundException e) {
            System.out.println("data/bookmark.txt is not found, creating a new file now!");
            ArrayList<BookmarkCategory> newBookmarkCategories = new ArrayList<>();
            newBookmarkCategories.add(new BookmarkCategory("NUS"));
            newBookmarkCategories.add(new BookmarkCategory("Zoom"));
            newBookmarkCategories.add(new BookmarkCategory("Internship"));
            newBookmarkCategories.add(new BookmarkCategory("Hackathon"));
            newBookmarkCategories.add(new BookmarkCategory("Career Talk"));
            saveLinksToFile(newBookmarkCategories);
            StudyItLog.logger.info(e + "\nflashcard storage file created");
            return newBookmarkCategories;
        }
    }

    public void saveLinksToFile(ArrayList<BookmarkCategory> categories) {
        try {
            FileWriter fw = new FileWriter(filePath, false); //true append, false overwrite
            for (BookmarkCategory category : categories) {
                fw.write(category.getName() + " =" + getCategoryLinks(category) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Something went wrong" + e.getMessage());
            StudyItLog.logger.warning("Problem writing to bookmark storage file\n" + e);
        }
    }

    private String getCategoryLinks(BookmarkCategory category) {
        String listOfLinks = "";
        for (BookmarkList link : category.getLinks()) {
            listOfLinks += link.getLink() + ",";
        }
        return listOfLinks;
    }
}
