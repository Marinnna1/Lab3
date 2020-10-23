package model;


import java.util.Objects;

public class Dot {
    private String x;
    private String r;
    private String y;
    private String result;
    private String color;


    public Dot() {
    }

    public Dot(String x, String y, String r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Dot(String x, String y, String r, String result, String color) {
        this(x, y, r);
        this.result = result;
        this.color = color;
    }


    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Dot other = (Dot) otherObject;
        return Objects.equals(x, other.getX())
                && Objects.equals(y, other.getY())
                && Objects.equals(r, other.getR())
                && Objects.equals(result, other.getResult())
                && Objects.equals(color, other.getColor());
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, result);
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }

    public void setXYR(String x, String y, String r)
    {
        this.x=x;
        this.y=y;
        this.r=r;
    }
}



