package domain;

public class Courier {
    private String name;
    private String listOfStreets;
    private float centreX;
    private float centreY;
    private float radius;

    public Courier(String name, String listOfStreets, float centreX, float centreY, float radius) {
        this.name = name;
        this.listOfStreets = listOfStreets;
        this.centreX = centreX;
        this.centreY = centreY;
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListOfStreets() {
        return listOfStreets;
    }

    public void setListOfStreets(String listOfStreets) {
        this.listOfStreets = listOfStreets;
    }

    public float getCentreX() {
        return centreX;
    }

    public void setCentreX(float centreX) {
        this.centreX = centreX;
    }

    public float getCentreY() {
        return centreY;
    }

    public void setCentreY(float centreY) {
        this.centreY = centreY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Courier: " + name + ", streets: [" + listOfStreets + "], x:" + centreX + ", y:" + centreY + ", radius: " + radius;
    }
}
