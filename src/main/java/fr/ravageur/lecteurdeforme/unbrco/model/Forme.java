package fr.ravageur.lecteurdeforme.unbrco.model;

import fr.ravageur.lecteurdeforme.son.MidiSynth;
import java.awt.*;

public class Forme 
{
    private static final Color COULEUR_LIGNE_JOUEE = new Color(230, 158, 60);

    public int x;
    public int y;
    private int longueur;
    private int hauteur;
    private boolean estSelectionnee;
    private MidiSynth midiSynth;
    private int instrument;
    private int colonneJouee;

    public Forme(Point hautGauche, MidiSynth midiSynth) 
    {
        this.x = (int)hautGauche.x;
        this.y = (int)hautGauche.y;
        longueur = 0;
        hauteur = 0;
        //this((int) hautGauche.getX(), (int) hautGauche.getY(), 0, 0);
        estSelectionnee = false;
        this.midiSynth = midiSynth;
        instrument = 0;
        colonneJouee = 0;
    }

    /**
     * Permet de définir la colonne jouée
     * @param colonneJouee
     */
    public void setColonneJouee(int colonneJouee)
    {
        this.colonneJouee = colonneJouee;
    }

    public int getX()
    {
        return x;
    }

    public int getLongueur()
    {
        return longueur;
    }

    /**
     * Renvoie vrai si le x donnée est dans l'espace horizontal de la forme.
     * <p>Sinon le x donnée renvoie faux</p>
     * @param x
     * @return boolean
     */
    public boolean contientX(int x) 
    {
        if(x >= this.x && x <= this.x + longueur)
        {
            return true;
        }
        else if(x <= this.x && x >= this.x + longueur)
        {
            return true;
        }
        return false;
    }

    /**
     * Renvoie vrai si le y donnée est dans l'espace vertical de la forme.
     * <p>Sinon le y donnée renvoie faux</p>
     * @param y
     * @return boolean
     */
    public boolean contientY(int y) 
    {
        if(y <= this.y + hauteur && y >= this.y)
        {
            return true;
        }
        else if(y >= this.y + hauteur && y <= this.y)
        {
            return true;
        }
        return false;
    }

    /**
     * Renvoie vrai si le point donnée est dans l'espace occupé par la forme.
     * <p>Sinon renvoie faux</p>
     * @param point
     * @return boolean
     */
    public boolean contient(Point point) 
    {
        if(contientX(point.x) && contientY(point.y))
        {
            return true;
        }
        return false;
    }

    /**
     * Permet de définir la limite d'une forme.
     * @param basDroite
     */
    public void setLimites(Point basDroite) 
    {
        longueur = basDroite.x - x;
        hauteur = basDroite.y - y;
    }

    public void dessiner(Graphics g) 
    {
        Color saveCouleur = g.getColor();
        if (estSelectionnee) 
        {
            g.setColor(COULEUR_LIGNE_JOUEE);
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
    }

    public void deplacer(int dx, int dy)
    {
        boolean changementNote;
        changementNote = (convertirCoordVersNote(y) != convertirCoordVersNote(y + dy));
        if (changementNote) 
        {
          stopper();
        }
        x += dx;
        y += dy;
        if (changementNote) 
        {
          jouer();
        }
    }

    public void selectionnerEtJouer() 
    {
        if(!estSelectionnee) 
        {
            estSelectionnee = true;
            jouer();
        }
    }

    public void deselectionnerEtStopper() 
    {
        if(estSelectionnee) 
        {
            estSelectionnee = false;
            stopper();
        }
    }

    private void jouer() 
    {
        int volume = convertirZoneVersVelocite(longueur * hauteur);
        midiSynth.play(instrument, convertirCoordVersNote(y), volume);
    }

    private void stopper() 
    {
        midiSynth.stop(instrument, convertirCoordVersNote(y));
    }

    private int convertirZoneVersVelocite(int zone) 
    {
        return Math.max(60, Math.min(127, zone / 30));
    }

    private int convertirCoordVersNote(int y) 
    {
        return 70 - y / 12;
    }
}
