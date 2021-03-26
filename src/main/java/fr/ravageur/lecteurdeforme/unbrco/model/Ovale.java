package fr.ravageur.lecteurdeforme.unbrco.model;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;

import fr.ravageur.lecteurdeforme.son.MidiSynth;

public class Ovale extends Forme
{
    private static final Color COULEUR = new Color(13, 37, 255);

    public Ovale(Point hautGauche, MidiSynth midiSynth) 
    {
        super(hautGauche, midiSynth);
        instrument = 35;
    }

    @Override
    public void dessiner(Graphics g)
    {
        Color saveCouleur = g.getColor();
        if (estSelectionnee) 
        {
            g.setColor(COULEUR);
        } 
        else 
        {
            g.setColor(Color.white);
        }
        g.fillOval(x, y, longueur, hauteur);
        g.setColor(saveCouleur);
        g.drawOval(x, y, longueur, hauteur);

        if(colonneJouee > 0 && colonneJouee < longueur) 
        {
            g.setColor(Color.red);
            g.drawLine(x + colonneJouee, y, x + colonneJouee, y + hauteur);
            g.setColor(saveCouleur);
        }
    }
}
