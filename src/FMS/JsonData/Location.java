package JsonData;

public class Location {
    float latitude;
    float longitude;
    String city;
    String country;

    public Location(float latitude, float longitude, String city, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (obj.getClass() != this.getClass()){
            return false;
        }

        Location compareLocation = (Location)obj;
        if (this.city != compareLocation.getCity() ||
            this.country != compareLocation.getCountry() ||
            this.latitude != compareLocation.getLatitude() ||
            this.longitude != compareLocation.getLongitude()){
            return false;
        }
        return true;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
