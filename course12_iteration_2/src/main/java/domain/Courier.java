package domain;

public class Courier {
    private String name;
    private String listStreets;
    private float centerX;
    private float centerY;
    private float radius;

    public Courier(String name, String listStreets, float centerX, float centerY, float radius) {
        this.name = name;
        this.listStreets = listStreets;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListStreets() {
        return listStreets;
    }

    public void setListStreets(String listStreets) {
        this.listStreets = listStreets;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "name='" + name + '\'' +
                ", listStreets='" + listStreets + '\'' +
                ", centerX=" + centerX +
                ", centreY=" + centerY +
                ", radius=" + radius +
                '}';
    }
}
