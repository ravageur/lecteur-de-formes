package fr.ravageur.lecteurdeforme.unbrco.ui.outils;

import fr.ravageur.lecteurdeforme.unbrco.model.Forme;
import fr.ravageur.lecteurdeforme.unbrco.model.Ovale;
import fr.ravageur.lecteurdeforme.unbrco.ui.EditeurDeFormes;

import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class OutilOvale extends Outil 
{
    private Forme ovale;

    public OutilOvale(EditeurDeFormes editeur, JComponent parent) 
    {
        super(editeur, parent);
        ovale = null;
    }

    @Override
    protected void creerBouton(JComponent parent) 
    {
        bouton = new JButton("Forme ovale");
        bouton = customiserButton(bouton);
    }

    @Override
    protected void ajouterListener() 
    {
        bouton.addActionListener(new OutilFormeClicHandler());
    }

    @Override
    public void pressDansZoneDessin(MouseEvent e) 
    {
        ovale = new Ovale(e.getPoint(), editeur.getMidiSynth());
        ovale.selectionnerEtJouer();
        ovale.setLimites(e.getPoint());
        editeur.ajouterAuDessin(ovale);
    }

    @Override
    public void releaseDansZoneDessin(MouseEvent e) 
    {
        ovale.deselectionnerEtStopper();
        ovale = null;
    }

    @Override
    public void dragDansZoneDessin(MouseEvent e) 
    {
        ovale.setLimites(e.getPoint());
    }

    private class OutilFormeClicHandler implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            editeur.setOutilActif(OutilOvale.this);
        }
    }
}
