package fr.ravageur.lecteurdeforme.unbrco.ui;

import fr.ravageur.lecteurdeforme.son.MidiSynth;
import fr.ravageur.lecteurdeforme.unbrco.model.Dessin;
import fr.ravageur.lecteurdeforme.unbrco.model.Forme;
import fr.ravageur.lecteurdeforme.unbrco.ui.outils.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class EditeurDeFormes extends JFrame 
{
    public static final int LONGUEUR = 1000;
    public static final int HAUTEUR = 700;

    private MidiSynth midiSynth;
    private List<Outil> outils;
    private Outil outilActif;
    private Dessin dessinCourant;

    public EditeurDeFormes() 
    {
        super("Editeur de formes");
        outilActif = null;
        dessinCourant = null;
        outils = new ArrayList<Outil>();
        initialiserGraphics();
        initialiserSon();
        initialiserInteraction();
    }

    private void initialiserInteraction() 
    {
        DessinSourisListener dml = new DessinSourisListener();
        addMouseListener(dml);
        addMouseMotionListener(dml);
    }

    private void initialiserSon() 
    {
        midiSynth = new MidiSynth();
        midiSynth.open();
    }

    private void initialiserGraphics() 
    {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(LONGUEUR, HAUTEUR));
        creerOutils();
        ajouterNouveauDessin();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void ajouterNouveauDessin() 
    {
        Dessin nouveauDessin = new Dessin();
        dessinCourant = nouveauDessin;
        add(nouveauDessin, BorderLayout.CENTER);
        validate();
    }

    /**
     * Permet d'ajouter une forme au dessin.
     * @param forme
     */
    public void ajouterAuDessin(Forme forme) 
    {
        if(forme != null)
        {
            dessinCourant.ajouterForme(forme);
        }
    }

    /**
     * Permet de supprimer la forme donné en paramètre dans le dessin si elle est trouvée.
     * @param forme
     */
    public void supprimerDuDessin(Forme forme) 
    {
        if(forme != null)
        {
            dessinCourant.supprimerForme(forme);
        }
    }

    private void handleSourisPressee(MouseEvent e) 
    {
        if(outilActif != null) 
        {
            outilActif.pressDansZoneDessin(e);
        }
        repaint();
    }

    private void handleSourisRelease(MouseEvent e) 
    {
        if(outilActif != null) 
        {
            outilActif.releaseDansZoneDessin(e);
        }
        repaint();
    }

    private void handleSourisClic(MouseEvent e) 
    {
        if(outilActif != null) 
        {
            outilActif.clicDansZoneDessin(e);
        }
        repaint();
    }

    private void handleSourisDrag(MouseEvent e) 
    {
        if(outilActif != null) 
        {
            outilActif.dragDansZoneDessin(e);
        }
        repaint();
    }

    public void setOutilActif(Outil outil) 
    {
        if(outilActif != null) 
        {
            outilActif.desactiver();
        }
        outil.activer();
        outilActif = outil;
    }

    public Forme getFormeEn(Point point) 
    {
        return dessinCourant.getPremiereFormeEn(point);
    }

    private void creerOutils() 
    {
        JPanel zoneOutils = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 2);
        zoneOutils.setLayout(gridLayout);
        zoneOutils.setSize(new Dimension(0, 0));
        add(zoneOutils, BorderLayout.SOUTH);

        outils.add(new OutilOvale(this, zoneOutils));

        OutilForme outilForme = new OutilForme(this, zoneOutils);
        outils.add(outilForme);

        OutilDeplacer outilDeplacer = new OutilDeplacer(this, zoneOutils);
        outils.add(outilDeplacer);

        OutilRedimensionner outilRedimensionner = new OutilRedimensionner(this, zoneOutils);
        outils.add(outilRedimensionner);

        OutilSupprimer outilSupprimer = new OutilSupprimer(this, zoneOutils);
        outils.add(outilSupprimer);

        OutilJouerForme outilJouerForme = new OutilJouerForme(this, zoneOutils);
        outils.add(outilJouerForme);

        OutilJouerDessin outilJouerDessin = new OutilJouerDessin(this, zoneOutils);
        outils.add(outilJouerDessin);

        outils.add(new OutilModeSonAleatoire(this, zoneOutils));

        outils.add(new OutilModeCouleurAleatoire(this, zoneOutils));

        setOutilActif(outilForme);
    }

    public MidiSynth getMidiSynth()
    {
        return midiSynth;
    }

    public Dessin getDessinCourant()
    {
        return dessinCourant;
    }

    public static void main(String args[]) 
    {
        new EditeurDeFormes();
    }

    private class DessinSourisListener extends MouseAdapter 
    {

        @Override
        public void mousePressed(MouseEvent e) 
        {
            handleSourisPressee(convertirEvt(e));
        }

        @Override
        public void mouseReleased(MouseEvent e) 
        {
            handleSourisRelease(convertirEvt(e));
        }

        @Override
        public void mouseClicked(MouseEvent e) 
        {
            handleSourisClic(convertirEvt(e));
        }

        @Override
        public void mouseDragged(MouseEvent e) 
        {
            handleSourisDrag(convertirEvt(e));
        }

        private MouseEvent convertirEvt(MouseEvent e) 
        {
            return SwingUtilities.convertMouseEvent(e.getComponent(), e, dessinCourant);
        }
  }
}
