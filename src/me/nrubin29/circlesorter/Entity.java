package me.nrubin29.circlesorter;

import java.awt.*;

public class Entity {

    private final Color color;
    private final GameImage img;
    private final int width, height;
    private int x, y;
    private final Orientation o;

    public Entity(Color color, GameImage img, int width, int height, Orientation o) {
        this.color = color;
        this.img = img;
        this.width = width;
        this.height = height;
        this.o = o;
    }

    public Color getColor() {
        return color;
    }

    public GameImage getImage() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    protected void setY(int y) {
        this.y = y;
    }

    public void modifyLocation(int x, int y) {
        if (getX() + x > 640) setX(640);
        else if (getX() + x < 0) setX(0);
        else setX(getX() + x);

        if (getY() + y > 480) setY(480);
        else if (getY() + y < 0) setY(0);
        else setY(getY() + y);
    }

    public Orientation getOrientation() {
        return o;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img.getImage(), getX(), getY(), getWidth(), getHeight(), null);
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}