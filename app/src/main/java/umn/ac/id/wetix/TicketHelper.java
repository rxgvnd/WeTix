package umn.ac.id.wetix;

public class TicketHelper {
    String idMovie, waktu, bioskop, uid;
    long harga, jmlKursi;

    public TicketHelper(){}

    public TicketHelper(String idMovie, long harga, String waktu, String bioskop, String uid, long jmlKursi) {
        this.idMovie = idMovie;
        this.harga = harga;
        this.waktu = waktu;
        this.bioskop = bioskop;
        this.uid = uid;
        this.jmlKursi = jmlKursi;
    }

    public long getJmlKursi() {
        return jmlKursi;
    }

    public void setJmlKursi(long jmlKursi) {
        this.jmlKursi = jmlKursi;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getBioskop() {
        return bioskop;
    }

    public void setBioskop(String bioskop) {
        this.bioskop = bioskop;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
