package fr.ravageur.lecteurdeforme.unbrco.model;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;

import fr.ravageur.lecteurdeforme.son.MidiSynth;

public class Rectangle extends Forme
{
    private static final Color COULEUR = new Color(230, 158, 60);

    public Rectangle(Point hautGauche, MidiSynth midiSynth) 
    {
        super(hautGauche, midiSynth);
        instrument = 0;
    }

    @Override
    public void dessiner(Graphics g)
    {
        Color saveCouleur = g.getColor();
        if (super.estSelectionnee) 
        {
            g.setColor(COULEUR);
        } 
        else 
        {
            g.setColor(Color.white);
        }
        g.setColor(COULEUR);
        g.fillRect(x, y, longueur, hauteur);
        g.setColor(saveCouleur);
        g.drawRect(x, y, longueur, hauteur);
    }
}
