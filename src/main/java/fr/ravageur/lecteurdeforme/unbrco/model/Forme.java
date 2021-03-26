package fr.ravageur.lecteurdeforme.unbrco.model;

import fr.ravageur.lecteurdeforme.son.MidiSynth;
import java.awt.*;

public abstract class Forme 
{
    protected int x;
    protected int y;
    protected int longueur;
    protected int hauteur;
    protected boolean estSelectionnee;
    private MidiSynth midiSynth;
    protected int instrument;
    protected int colonneJouee;
    protected Graphics graphic;

    /**
     * Permet de créer la base d'une forme.
     * @param hautGauche
     * @param midiSynth
     */
    public Forme(Point hautGauche, MidiSynth midiSynth) 
    {
        this.x = (int)hautGauche.x;
        this.y = (int)hautGauche.y;
        longueur = 0;
        hauteur = 0;
        estSelectionnee = false;
        this.midiSynth = midiSynth;
        instrument = 0;
        colonneJouee = 0;
    }

    /**
     * Permet de définir la colonne jouée.
     * @param colonneJouee
     */
    public void setColonneJouee(int colonneJouee)
    {
        this.colonneJouee = colonneJouee;
    }

    /**
     * Permet de définir l'instrument joué, plus le nombre est grand plus le son sera grave.
     * @param instrument
     */
    public void setInstrument(int instrument)
    {
        this.instrument = instrument;
    }

    /**
     * Permet d'obtenir la coordonnée X.
     * @return <b>int</b>
     */
    public int getX()
    {
        return x;
    }

    /**
     * Permet d'obtenir la longueur.
     * Remarque: Elle peut être de valeur négative.
     * @return <b>int</b>
     */
    public int getLongueur()
    {
        return longueur;
    }

    /**
     * Permet d'obtenir la hauteur
     * Remarque: Elle peut être de valeur négative.
     * @return <b>int</b>
     */
    public int getHauteur()
    {
        return hauteur;
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

    public abstract void dessiner(Graphics g);


    /**
     * Permet de déplacer la forme vers un autre endroit.
     * @param dx
     * @param dy
     */
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

    /**
     * Permet de jouer la forme et de forcer la forme à être sélectionner.
     */
    public void selectionnerEtJouer() 
    {
        if(!estSelectionnee) 
        {
            estSelectionnee = true;
            jouer();
        }
    }

    /**
     * Permet d'arrêter de jouer la forme et de forcer de déselectioner de la forme.
     */
    public void deselectionnerEtStopper() 
    {
        if(estSelectionnee) 
        {
            estSelectionnee = false;
            stopper();
        }
    }

    /**
     * Permet de jouer la forme
     */
    private void jouer() 
    {
        int volume = convertirZoneVersVelocite(longueur * hauteur);
        midiSynth.play(instrument, convertirCoordVersNote(y), volume);
    }

    /**
     * Permet de stopper le son jouer par la forme.
     */
    private void stopper() 
    {
        midiSynth.stop(instrument, convertirCoordVersNote(y));
    }

    /**
     * Permet d'obtenir le volume d'une forme
     * @param zone
     * @return <b>int</b>
     */
    private int convertirZoneVersVelocite(int zone) 
    {
        return Math.max(60, Math.min(127, zone / 30));
    }

    /**
     * 
     * @param y
     * @return <b>int</b>
     */
    private int convertirCoordVersNote(int y) 
    {
        return 70 - y / 12;
    }
}
