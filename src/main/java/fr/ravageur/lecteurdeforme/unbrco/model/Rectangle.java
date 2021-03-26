package fr.ravageur.lecteurdeforme.unbrco.model;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;

import fr.ravageur.lecteurdeforme.son.MidiSynth;

public class Rectangle extends Forme
{
    private static final Color couleurBackup = new Color(230, 158, 60);
    private static final int instrumentBackup = 0;

    public Rectangle(Point hautGauche, MidiSynth midiSynth) 
    {
        super(hautGauche, midiSynth);
        instrument = 0;
        couleur = new Color(230, 158, 60);
    }

    @Override
    public void dessiner(Graphics g)
    {
        Color saveCouleur = g.getColor();
        if (super.estSelectionnee) 
        {
            g.setColor(couleur);
        } 
        else 
        {
            g.setColor(Color.white);
        }
        g.fillRect(x, y, longueur, hauteur);
        g.setColor(saveCouleur);
        g.drawRect(x, y, longueur, hauteur);

        if(colonneJouee > 0 && colonneJouee < longueur) 
        {
            g.setColor(Color.red);
            g.drawLine(x + colonneJouee, y, x + colonneJouee, y + hauteur);
            g.setColor(saveCouleur);
        }
        graphic = g;
    }

    @Override
    public void reinitialiser(boolean son, boolean couleur) 
    {
        if(son)
        {
            instrument = instrumentBackup;
        }
        if(couleur)
        {
            this.couleur = couleurBackup;
        }
    }
}
