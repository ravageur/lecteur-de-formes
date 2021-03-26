package fr.ravageur.lecteurdeforme.unbrco.ui.outils;

import javax.swing.JButton;
import javax.swing.JComponent;
import fr.ravageur.lecteurdeforme.unbrco.ui.EditeurDeFormes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutilModeCouleurAleatoire extends Outil
{
    public OutilModeCouleurAleatoire(EditeurDeFormes editeur, JComponent parent) 
    {
        super(editeur, parent);
    }

    @Override
    protected void creerBouton(JComponent parent) 
    {
        bouton = new JButton("Activer couleurs aléatoire");
        bouton = customiserButton(bouton);
    }

    @Override
    protected void ajouterListener() 
    {
        bouton.addActionListener(new OutilModeCouleurAleatoireClicHandler());
    }
    
    private class OutilModeCouleurAleatoireClicHandler implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            boolean estActiver = editeur.getDessinCourant().activeOuDesactiverCouleursAleatoires();
            if(estActiver)
            {
                bouton.setText("Désactiver couleurs aléatoire");
            }
            else
            {
                bouton.setText("Activer couleurs aléatoire");
            }
        }
    }
}
