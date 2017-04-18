package ru.gordinmitya.hallmap.models;

public class Table {
    public static final String TYPE_SQUARE = "square";
    public static final String TYPE_CIRCLE = "circle";

    public final int id;
    public final String name;
    public final int x, y;
    public final int width, height;
    public final int angle;
    public final String type;

    public Table(int id, String name, int x, int y, int weight, int height, int angle, String type) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = weight;
        this.height = height;
        this.angle = angle;
        this.type = type;
    }
}
