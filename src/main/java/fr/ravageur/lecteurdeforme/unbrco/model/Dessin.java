package fr.ravageur.lecteurdeforme.unbrco.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

@SuppressWarnings("serial")
public class Dessin extends JPanel 
{

    private static final int ESPACEMENT_LIGNES = 30;

    private List<Forme> formes;
    private int colonneCourante;

    /**
     * Permet de créer un dessin vide.
     */
    public Dessin() 
    {
        super();
        formes = new ArrayList<Forme>();
        setBackground(Color.white);
    }

    public List<Forme> getFormes()
    {
        return formes;
    }

    public void setColonneCourante(int colonneCourante)
    {
        this.colonneCourante = colonneCourante;
    }

    /**
     * Renvoie vrai si le dessin contient la forme donnée
     * @param f
     * @return boolean
     */
    public boolean contientLaForme(Forme f) 
    {
        for(Forme forme : formes)
        {
            if(f.equals(forme))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        dessinerLignesHorizontales(g);
        for (Forme forme : formes) 
        {
          forme.dessiner(g);
        }
    }

    private void dessinerLignesHorizontales(Graphics g) 
    {
        Color originale = g.getColor();
        g.setColor(new Color(227, 227, 227));
        for (int y = ESPACEMENT_LIGNES; y < getHeight(); y += ESPACEMENT_LIGNES) 
        {
            g.drawLine(0, y, getWidth(), y);
        }
        if (colonneCourante > 0 && colonneCourante < getWidth()) 
        {
            g.setColor(Color.RED);
            g.drawLine(colonneCourante, 0, colonneCourante, getHeight());
        }
        g.setColor(originale);
    }

    /**
     * Permet d'ajouter une forme au dessin.
     * @param forme
     */
    public void ajouterForme(Forme forme) 
    {
        if(forme != null)
        {
            formes.add(forme);
        }
    }

    /**
     * Permet de supprimer une forme du dessin.
     * @param forme
     */
    public void supprimerForme(Forme forme) 
    {
        if(forme != null)
        {
            formes.remove(forme);
        }
    }

    /**
     * Renvoie la première forme trouvée qui se trouve au point donné.
     * @param point
     * @return Forme
     */
    public Forme getPremiereFormeEn(Point point) 
    {
        for(Forme forme : formes) 
        {
            if(forme.contient(point))
            {
                return forme;
            } 
        }
        return null;
    }

    /**
     * Permet de trouver toutes les formes sur la colonne donnée. 
     * <p>Remarque: La liste peut être vide.</p>
     * @param colone
     * @return List<Forme>
     */
    public List<Forme> formesSurLaColonne(int colone) 
    {
        ArrayList<Forme> listeFormes = new ArrayList<>();
        for(Forme forme : formes)
        {
            if(forme.contientX(colone))
            {
                listeFormes.add(forme);
            }
        }
        return listeFormes;
    }
}
