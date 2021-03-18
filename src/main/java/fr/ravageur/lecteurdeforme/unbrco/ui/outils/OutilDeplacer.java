package fr.ravageur.lecteurdeforme.unbrco.ui.outils;

import fr.ravageur.lecteurdeforme.unbrco.model.Forme;
import fr.ravageur.lecteurdeforme.unbrco.ui.EditeurDeFormes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class OutilDeplacer extends Outil 
{
    private Forme formeADeplacer;
    private Point debut;

    public OutilDeplacer(EditeurDeFormes editeur, JComponent parent) 
    {
        super(editeur, parent);
        formeADeplacer = null;
        debut = null;
    }

    @Override
    protected void creerBouton(JComponent parent) 
    {
        bouton = new JButton("Déplacer");
        ajouterAuParent(parent);
    }

    @Override
    protected void ajouterListener() 
    {
        bouton.addActionListener(new OutilDeplacerClicHandler());
    }

    /**
     * Permet de sélectionner et de jouer une forme si il y en a une au point cliqué.
     * @param event
     */
    @Override
    public void pressDansZoneDessin(MouseEvent event) 
    {
        formeADeplacer = super.editeur.getFormeEn(event.getPoint());
        if(formeADeplacer != null)
        {
            debut = event.getPoint();
            formeADeplacer.selectionnerEtJouer();
        }
    }

    @Override
    public void releaseDansZoneDessin(MouseEvent e) 
    {
        if(formeADeplacer != null) 
        {
            formeADeplacer.deselectionnerEtStopper();
            formeADeplacer = null;
        }
    }

    @Override
    public void dragDansZoneDessin(MouseEvent e) 
    {
        if(formeADeplacer != null) 
        {
            int dx = (int) (e.getPoint().getX() - debut.getX());
            int dy = (int) (e.getPoint().getY() - debut.getY());
            debut = e.getPoint();
            formeADeplacer.deplacer(dx, dy);
        }
    }

    private class OutilDeplacerClicHandler implements ActionListener 
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            editeur.setOutilActif(OutilDeplacer.this);
        }
    }
}
