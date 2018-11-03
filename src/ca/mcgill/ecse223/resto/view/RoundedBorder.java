package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;


class RoundedBorder implements Border {

    private int radius;
    private Color borderColour;


    RoundedBorder(int radius, Color colour) {
        this.radius = radius;
        this.borderColour = colour;
    }

    RoundedBorder(int radius) {
        this(radius, Color.BLACK);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(borderColour);
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
