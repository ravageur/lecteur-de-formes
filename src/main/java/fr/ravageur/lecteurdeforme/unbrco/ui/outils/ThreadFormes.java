package fr.ravageur.lecteurdeforme.unbrco.ui.outils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.ravageur.lecteurdeforme.unbrco.model.Forme;

public class ThreadFormes extends Thread
{
    private ArrayList<Forme> formes;
    private Random random = new Random();
    private boolean doitExecuterLeSon;
    private boolean doitExecuterCouleurs;

    public ThreadFormes(List<Forme> formes)
    {
        this.formes = new ArrayList<>(formes);
        doitExecuterLeSon = false;
        doitExecuterCouleurs = false;
    }

    @Override
    public void run()
    {
        while(true)
        {
            if(doitExecuterLeSon && !doitExecuterCouleurs)
            {
                formesRandomSon();
            }
            else if(!doitExecuterLeSon && doitExecuterCouleurs)
            {
                formesRandomCouleur();
            }
            else if(doitExecuterLeSon && doitExecuterCouleurs)
            {
                formesRandomSon();
                formesRandomCouleur();
            }
            else
            {
                eteindreMomentanement(Integer.MAX_VALUE / 1000);
                continue;
            }
            eteindreMomentanement(1);
        }
    }

    /**
     * Permet de relancer le thread
     */
    public void restart()
    {
        interrupt();
    }

    /**
     * Permet de faire attendre le thread pour minimiser les ressources prises par le thread.
     * @param secondes
     */
    public void eteindreMomentanement(int secondes)
    {
        try 
        {
            sleep(secondes * 1000);
        } 
        catch(InterruptedException e) 
        {
            e.printStackTrace();
        }  
    }

    /**
     * Permet de savoir si la fonctionnalité du son aléatoire pour chaque forme est
     * activée.
     * @return <b>boolean</b>
     */
    public boolean getEtatExecutionDuSon()
    {
        return doitExecuterLeSon;
    }

    /**
     * Permet de dire si le thread doit activer la fonctionnalité du son aléatoire pour 
     * chaque forme.
     * @param doitExecuterLeSon
     */
    public void setEtatExecutionDuSon(boolean doitExecuterLeSon)
    {
        this.doitExecuterLeSon = doitExecuterLeSon;
        if(doitExecuterLeSon)
        {
            interrupt();
        }
        else
        {
            reinitialisationFormes(true, false);
        }
    }

    /**
     * Permet de savoir la fonctionnalité de la couleur aléatoire pour chaque forme est
     * activée.
     * @return <b>boolean</b>
     */
    public boolean getEtatExecutionCouleurs()
    {
        return doitExecuterCouleurs;
    }


    /**
     * Permet de dire si le thread doit activer la fonctionnalité de la couleur aléatoire
     * pour chaque forme.
     * @param doitExecuterCouleurs
     */
    public void setEtatExecutionCouleurs(boolean doitExecuterCouleurs)
    {
        this.doitExecuterCouleurs = doitExecuterCouleurs;
        if(doitExecuterCouleurs)
        {
            interrupt();
        }
        else
        {
            reinitialisationFormes(false, true);
        }
    }

    /**
     * Permet d'ajouter une forme.
     * @param forme
     */
    public void ajouterForme(Forme forme)
    {
        formes.add(forme);
    }

    /**
     * Permet d'enlever une forme.
     * @param forme
     */
    public void enleverForme(Forme forme)
    {
        formes.remove(forme);
    }

    /**
     * Permet de changer le son pour chaque forme de façon aléatoire.
     */
    public void formesRandomSon()
    {
        for(Forme forme : formes) 
        {
            forme.setInstrument(random.nextInt(100));
        }
    }

    /**
     * Permet de changer pour chaque forme sa couleur en une couleur choisi aléatoirement.
     */
    public void formesRandomCouleur()
    {
        for(Forme forme : formes)
        {
            forme.reDessiner();
        }
    }

    /**
     * Permet de réinitialiser l'anciennes couleur et (ou) du son pour chaque forme.
     * @param son
     * @param couleur
     */
    public void reinitialisationFormes(boolean son, boolean couleur)
    {
        for(Forme forme : formes)
        {
            forme.reinitialiser(son, couleur);
        }
    }
}
