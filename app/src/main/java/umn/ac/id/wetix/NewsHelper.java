package umn.ac.id.wetix;

public class NewsHelper {
    String headline, content, Pict;

    public NewsHelper(){}

    public NewsHelper(String headline, String content, String pict){
        this.content = content;
        this.headline = headline;
        this.Pict = pict;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPict() {
        return Pict;
    }

    public void setPict(String pict) {
        this.Pict = pict;
    }
}
