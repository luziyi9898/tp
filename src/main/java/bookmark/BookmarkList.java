package bookmark;

public class BookmarkList {
    private String link;
    private Boolean star;
    private String title;

    public BookmarkList(String link, String title) {
        this.link = link;
        this.star = false;
        this.title = title;
    }

    public String getLink() {
        String returnLink = "";
        if (star) {
            returnLink += "|STAR|";
        }
        if (title != null) {
            return returnLink + link + " t->" + title;
        } else {
            return returnLink + link;
        }
    }


    public void markLinkAsStar() {
        if (!this.star) {
            this.star = true;
        } else {
            assert this.star : "star is unmarked incorrectly";
            this.star = false;
        }
    }

    private String getStarIcon() {
        return (star ? " (*)" : ""); //return tick symbols
    }

    public String toString() {
        if (title == "" || title == null) {
            return link + getStarIcon();
        }
        return title + ": " + link + getStarIcon();
    }

    public Boolean getStar() {
        return star;
    }
}
