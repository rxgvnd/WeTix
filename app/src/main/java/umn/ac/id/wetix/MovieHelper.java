package umn.ac.id.wetix;

public class MovieHelper {
    String namemovie, duration, genre, poster;
    long year;

    public MovieHelper(){}

    public MovieHelper(String namemovie, String duration, String genre, long year, String poster) {
        this.namemovie = namemovie;
        this.duration = duration;
        this.genre = genre;
        this.year = year;
        this.poster = poster;
    }

    public String getNamemovie() {
        return namemovie;
    }

    public void setNamemovie(String namemovie) {
        this.namemovie = namemovie;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}


