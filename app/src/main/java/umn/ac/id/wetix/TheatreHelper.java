package umn.ac.id.wetix;

public class TheatreHelper {
    String itsName;
    double longitude, latitude;

    public TheatreHelper(){}

    public TheatreHelper(String itsName, double longitude, double latitude) {
        this.itsName = itsName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getItsName() {
        return itsName;
    }

    public void setItsName(String itsName) {
        this.itsName = itsName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
