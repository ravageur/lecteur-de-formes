package fr.ravageur.lecteurdeforme.unbrco.ui.outils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.ravageur.lecteurdeforme.unbrco.model.Forme;

public class ThreadFormes extends Thread
{
    private ArrayList<Forme> formes;
    private Random random = new Random();
    private boolean doitExecuter;

    public ThreadFormes(List<Forme> formes)
    {
        this.formes = new ArrayList<>(formes);
        doitExecuter = false;
    }

    @Override
    public void run()
    {
        while(true)
        {
            System.out.println("RUNNNNN !!!");
            if(doitExecuter)
            {
                System.out.println("Execution effectué");
                formeRandomSon();
            }
            else
            {
                eteindreMomentanement(Integer.MAX_VALUE / 1000);
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

    public boolean getEtatExecution()
    {
        return doitExecuter;
    }

    public void setEtatExecution(boolean doitExecuter)
    {
        this.doitExecuter = doitExecuter;
        if(doitExecuter)
        {
            interrupt();
        }
    }

    /**
     * Permet d'ajouter une forme
     * @param forme
     */
    public void ajouterForme(Forme forme)
    {
        formes.add(forme);
    }

    /**
     * Permet d'enlever une forme
     * @param forme
     */
    public void enleverForme(Forme forme)
    {
        formes.remove(forme);
    }

    /**
     * Permet de changer le son de toutes les formes de façon aléatoire.
     */
    public void formeRandomSon()
    {
        for(Forme forme : formes) 
        {
            forme.setInstrument(random.nextInt(100));
        }
    }
}
