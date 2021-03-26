package fr.ravageur.lecteurdeforme.unbrco.ui.outils;

import javax.swing.JButton;
import javax.swing.JComponent;
import fr.ravageur.lecteurdeforme.unbrco.ui.EditeurDeFormes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutilModeSonAleatoire extends Outil
{

    public OutilModeSonAleatoire(EditeurDeFormes editeur, JComponent parent) 
    {
        super(editeur, parent);
    }

    @Override
    protected void creerBouton(JComponent parent) 
    {
        bouton = new JButton("Activer son aléatoire");
        bouton = customiserButton(bouton);
    }

    @Override
    protected void ajouterListener() 
    {
        bouton.addActionListener(new OutilModeSonAleatoireClicHandler());
    }
    
    private class OutilModeSonAleatoireClicHandler implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            boolean estActiver = editeur.getDessinCourant().activeOuDesactiverSonsAleatoires();
            if(estActiver)
            {
                bouton.setText("Désactiver son aléatoire");
            }
            else
            {
                bouton.setText("Activer son aléatoire");
            }
        }
    }
}
