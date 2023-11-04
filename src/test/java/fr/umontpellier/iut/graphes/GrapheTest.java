package fr.umontpellier.iut.graphes;


import fr.umontpellier.iut.rails.Route;
import fr.umontpellier.iut.rails.RouteMaritime;
import fr.umontpellier.iut.rails.data.Couleur;
import fr.umontpellier.iut.rails.data.Ville;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.net.Inet4Address;
import java.util.*;

import static java.lang.Integer.sum;
import static org.junit.jupiter.api.Assertions.*;





public class GrapheTest {

    private Graphe grapheAbstrait;
    private List<Arete> listeAretesGrapheAbstrait;

    @BeforeEach
        void setUp() {
            grapheAbstrait = new Graphe(5);
            listeAretesGrapheAbstrait = Arrays.asList(new Arete(0, 1),
                    new Arete(0, 4),
                    new Arete(1, 2),
                    new Arete(1, 4),
                    new Arete(2, 4),
                    new Arete(2, 5),
                    new Arete(3, 4),
                    new Arete(5, 1),
                    new Arete(5, 3));
            listeAretesGrapheAbstrait.forEach(a -> grapheAbstrait.ajouterArete(a));
        }

    @Test
    void testNbAretes() {
        listeAretesGrapheAbstrait = Arrays.asList(new Arete(0, 1),
                new Arete(0, 4),
                new Arete(1, 2),
                new Arete(1, 4),
                new Arete(2, 4),
                new Arete(2, 5),
                new Arete(3, 4),
                new Arete(5, 1),
                new Arete(5, 3));

        grapheAbstrait = new Graphe(listeAretesGrapheAbstrait);

        assertTrue(grapheAbstrait.existeArete(new Arete(0, 1)));
        assertTrue(grapheAbstrait.existeArete(new Arete(0, 4)));
        assertTrue(grapheAbstrait.existeArete(new Arete(1, 2)));
        assertTrue(grapheAbstrait.existeArete(new Arete(1, 4)));
        assertTrue(grapheAbstrait.existeArete(new Arete(2, 4)));
        assertTrue(grapheAbstrait.existeArete(new Arete(2, 5)));
        assertTrue(grapheAbstrait.existeArete(new Arete(3, 4)));
        assertTrue(grapheAbstrait.existeArete(new Arete(5, 1)));
        assertTrue(grapheAbstrait.existeArete(new Arete(5, 3)));
        assertFalse(grapheAbstrait.existeArete(new Arete(0, 2)));
    }

        private boolean collectionsDansLeMemeOrdre(Iterable<Integer> listeAttendue, Iterable<Integer> listeObtenue) {
            Iterator<Integer> it1 = listeAttendue.iterator();
            Iterator<Integer> it2 = listeObtenue.iterator();
            while (it1.hasNext() && it2.hasNext()) {
                int elem1 = it1.next();
                int elem2 = it2.next();
                if (elem1 != elem2) {
                    return false;
                }
            }
            return !it1.hasNext() && !it2.hasNext();
        }

        @Test
        public void test_parcoursSansRepetition_sous_liste() {
            List<Integer> sousListe = Arrays.asList(0, 2, 3);
            List<Integer> parcoursAttendu1 = Arrays.asList(0, 1, 2, 4, 3);
            List<Integer> parcoursAttendu2 = Arrays.asList(0, 1, 2, 5, 3);
            List<Integer> parcoursAttendu3 = Arrays.asList(0, 4, 2, 5, 3);

            List<Integer> resultat = grapheAbstrait.parcoursSansRepetition(sousListe);
            System.out.println(resultat);

            assertTrue(collectionsDansLeMemeOrdre(resultat, parcoursAttendu1) ||
                    collectionsDansLeMemeOrdre(resultat, parcoursAttendu2) ||
                    collectionsDansLeMemeOrdre(resultat, parcoursAttendu3)
            );
        }

        @Test
        public void test_parcoursSansRepetition_simple() {
            List<Integer> parcoursAttenduCorrecte = Arrays.asList(0, 1, 2);
            List<Integer> parcoursAttenduCorrecte1 = Arrays.asList(0, 4, 2);
            List<Integer> parcoursAttenduIncorrecte1 = Arrays.asList(0, 4, 1, 2);
            List<Integer> parcoursAttenduIncorrecte2 = Arrays.asList(0, 1, 4, 2);
            List<Integer> parcoursAttenduIncorrecte3 = Arrays.asList(2, 1, 0);

            List<Integer> resultat = grapheAbstrait.parcoursSansRepetition(0, 2, false);
            assertTrue(collectionsDansLeMemeOrdre(parcoursAttenduCorrecte, resultat) ||
                    collectionsDansLeMemeOrdre(parcoursAttenduCorrecte1, resultat));
            assertFalse(collectionsDansLeMemeOrdre(parcoursAttenduIncorrecte1, resultat) &&
                    collectionsDansLeMemeOrdre(parcoursAttenduIncorrecte2, resultat) &&
                    collectionsDansLeMemeOrdre(parcoursAttenduIncorrecte3, resultat));
        }

        // tout les test a la suite ne sont pas sur d'etre juste, ce sont les tets de la version précédente que j'ai souhaité conservé

    @Test
    void testnbAretes() {
        grapheAbstrait = new Graphe(4);
        grapheAbstrait.ajouterArete(new Arete(0, 2));
        grapheAbstrait.ajouterArete(new Arete(0, 3));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(1, 3));
        grapheAbstrait.ajouterArete(new Arete(2, 3));


        System.out.println(grapheAbstrait.nbAretes());
        System.out.println(grapheAbstrait.getMapAretes());

        assertTrue(grapheAbstrait.existeArete(new Arete(0, 2)) &&
                grapheAbstrait.existeArete(new Arete(0, 3)) &&
                grapheAbstrait.existeArete(new Arete(1, 2)) &&
                grapheAbstrait.existeArete(new Arete(1, 3)) &&
                grapheAbstrait.existeArete(new Arete(2, 3)));
        assertEquals( 10, grapheAbstrait.nbAretes());
    }

    @Test

    void nbSommets() {
        assertEquals(5, grapheAbstrait.nbSommets());
    }


    @Test
    void testnbSommets() {
        Graphe graphe2 = new Graphe(grapheAbstrait, Set.of(0, 1, 2, 3));
        assertEquals(4, graphe2.nbSommets());
        graphe2.ajouterSommet(8);
        assertEquals(5, graphe2.nbSommets());
    }
    @Test
    void testContientsommet() {
        assertTrue(grapheAbstrait.contientSommet(0));
        assertFalse(grapheAbstrait.contientSommet(7));
    }

    @Test
    void testAjouterSommet2() {
        int nbSommets = grapheAbstrait.nbSommets();
        grapheAbstrait.ajouterSommet(59);
        assertTrue(grapheAbstrait.contientSommet(59));
        assertEquals(nbSommets + 1, grapheAbstrait.nbSommets());
        grapheAbstrait.ajouterSommet(59);
        assertEquals(nbSommets + 1, grapheAbstrait.nbSommets());
    }


    @Test
    void testContientSommet() {
        assertTrue(grapheAbstrait.contientSommet(0));
        assertTrue(grapheAbstrait.contientSommet(1));
        assertTrue(grapheAbstrait.contientSommet(2));
        assertTrue(grapheAbstrait.contientSommet(3));
        grapheAbstrait.ajouterSommet(8);
        assertTrue(grapheAbstrait.contientSommet(8));
        assertFalse(grapheAbstrait.contientSommet(42));
        assertFalse(grapheAbstrait.contientSommet(7));
    }

    @Test
    void testAjouterSommet() {
        int nbSommets = grapheAbstrait.nbSommets();
        grapheAbstrait.ajouterSommet(59);
        assertTrue(grapheAbstrait.contientSommet(59));
        assertEquals(nbSommets + 1, grapheAbstrait.nbSommets());
        grapheAbstrait.ajouterSommet(59);
        assertEquals(nbSommets + 1, grapheAbstrait.nbSommets());
    }

    /*listeAretesGrapheAbstrait = Arrays.asList(new Arete(0, 1),
                    new Arete(0, 4),
                    new Arete(1, 2),
                    new Arete(1, 4),
                    new Arete(2, 4),
                    new Arete(2, 5),
                    new Arete(3, 4),
                    new Arete(5, 1),
                    new Arete(5, 3));
            listeAretesGrapheAbstrait.forEach(a -> grapheAbstrait.ajouterArete(a));*/

    @Test
    void testAjouterArete() {
        int nbAretes = grapheAbstrait.nbAretes();
        grapheAbstrait.ajouterArete(new Arete(0, 3));
        assertEquals(nbAretes + 1, grapheAbstrait.nbAretes());
        //System.out.println(grapheAbstrait.getMapAretes());
        grapheAbstrait.ajouterArete(new Arete(9, 439, null));
        System.out.println(grapheAbstrait.nbAretes());
        assertEquals(nbAretes + 2, grapheAbstrait.nbAretes());
        grapheAbstrait.ajouterArete(new Arete(0, 3, new RouteMaritime(new Ville("Athina", true), new Ville("Marseille", true), Couleur.ROUGE, 2) {
        }));
        assertEquals(nbAretes + 2, grapheAbstrait.nbAretes());
    }

    @Test
    void testajouterarrete2() {
        grapheAbstrait = new Graphe(6);
        grapheAbstrait.ajouterArete(new Arete(0,1));
        grapheAbstrait.ajouterArete(new Arete(0,2));
        grapheAbstrait.ajouterArete(new Arete(0,3));
        grapheAbstrait.ajouterArete(new Arete(0,4));
        assertEquals(4, grapheAbstrait.nbAretes());
        grapheAbstrait.ajouterArete(new Arete(0,4));
        assertEquals(4, grapheAbstrait.nbAretes());
        grapheAbstrait.ajouterArete(new Arete(0,5));
        assertEquals(5, grapheAbstrait.nbAretes());
    }

    @Test
    public void testAjouterArrete3() {
        int nbAretes = grapheAbstrait.nbAretes();
        grapheAbstrait.ajouterArete(new Arete(0,3));
        grapheAbstrait.ajouterArete(new Arete(3, 0));
        System.out.println(grapheAbstrait.nbAretes());
        assertTrue(nbAretes + 2 == grapheAbstrait.nbAretes());
    }

    @Test
    void testSupprimerArete() {
        int nbAretes = grapheAbstrait.nbAretes();
        grapheAbstrait.supprimerArete(new Arete(0, 1));
        assertEquals(nbAretes - 1, grapheAbstrait.nbAretes());
        grapheAbstrait.supprimerArete(new Arete(0, 1));
        assertEquals(nbAretes - 1, grapheAbstrait.nbAretes());
    }

    @Test
    public void supprimerArete() {
        grapheAbstrait = new Graphe(7);
        grapheAbstrait.ajouterArete(new Arete(0,1));
        grapheAbstrait.ajouterArete(new Arete(1,2));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        grapheAbstrait.ajouterArete(new Arete(4,5));
        grapheAbstrait.ajouterArete(new Arete(4,6));
        grapheAbstrait.ajouterArete(new Arete(6, 7));

        assertTrue(grapheAbstrait.estConnexe());
        grapheAbstrait.supprimerArete(new Arete(0,1));
        assertEquals(6, grapheAbstrait.nbAretes());
    assertFalse(grapheAbstrait.estConnexe());

    }

    @Test
    void testdegréSommet() {
        grapheAbstrait = new Graphe(6);

        grapheAbstrait.ajouterArete(new Arete(0,2));
        grapheAbstrait.ajouterArete(new Arete(0,3));
        grapheAbstrait.ajouterArete(new Arete(0,4));
        grapheAbstrait.ajouterArete(new Arete(0,5));
        grapheAbstrait.ajouterArete(new Arete(1,2));
        grapheAbstrait.ajouterArete(new Arete(1,4));
        grapheAbstrait.ajouterArete(new Arete(1,5));
        grapheAbstrait.ajouterArete(new Arete(2,3));
        grapheAbstrait.ajouterArete(new Arete(2,4));
        grapheAbstrait.ajouterArete(new Arete(2,5));
        grapheAbstrait.ajouterArete(new Arete(3,4));
        grapheAbstrait.ajouterArete(new Arete(3,5));


        assertEquals(4, grapheAbstrait.degre(0));
        assertEquals(3, grapheAbstrait.degre(1));
        assertEquals(5, grapheAbstrait.degre(2));
        assertEquals(4, grapheAbstrait.degre(3));
        assertEquals(4, grapheAbstrait.degre(4));
        assertEquals(4, grapheAbstrait.degre(5));

    }

    @Test
    void testSupprimerSommet() {
        int nbSommets = grapheAbstrait.nbSommets();
        int nbAretes = grapheAbstrait.nbAretes();
        grapheAbstrait.supprimerSommet(42);
        assertEquals(nbSommets, grapheAbstrait.nbSommets());
        assertEquals(nbAretes, grapheAbstrait.nbAretes());
        grapheAbstrait.supprimerSommet(2);
        assertEquals(nbSommets - 1, grapheAbstrait.nbSommets());
        assertEquals(nbAretes - 3, grapheAbstrait.nbAretes());
    }
    @Test

    void supprimerSommetTest2() {
        int nbSommets = grapheAbstrait.nbSommets();
        int nbAretes = grapheAbstrait.nbAretes();
        System.out.println(grapheAbstrait.nbAretes());
        grapheAbstrait.supprimerSommet(0);
        System.out.println(grapheAbstrait.nbAretes());
        assertEquals(nbSommets - 1, grapheAbstrait.nbSommets());
        assertEquals(nbAretes - 2, grapheAbstrait.nbAretes());
    }

    @Test
    void grapheSimpleTest3() {
        grapheAbstrait = new Graphe(1);
       listeAretesGrapheAbstrait = Arrays.asList(new Arete(0, 0));
        assertFalse(grapheAbstrait.estSimple());
    }

    @Test
    void grapheSimpleTest2() {
        grapheAbstrait = new Graphe(10);
        listeAretesGrapheAbstrait = Arrays.asList(new Arete(0, 1),
                new Arete(0, 2));
        assertFalse(grapheAbstrait.estSimple());
    }

    @Test
    void grapheSimpleTest() {
        grapheAbstrait = new Graphe(10);
        listeAretesGrapheAbstrait = Arrays.asList(new Arete(0, 1),
                new Arete(0, 2));
        assertFalse(grapheAbstrait.estSimple());
    }

    /*listeAretesGrapheAbstrait = Arrays.asList(new Arete(0, 1),
                    new Arete(0, 4),
                    new Arete(1, 2),
                    new Arete(1, 4),
                    new Arete(2, 4),
                    new Arete(2, 5),
                    new Arete(3, 4),
                    new Arete(5, 1),
                    new Arete(5, 3));
            listeAretesGrapheAbstrait.forEach(a -> grapheAbstrait.ajouterArete(a));*/

    @Test
    void testExisteArete() {
        grapheAbstrait = new Graphe(2);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        assertTrue(grapheAbstrait.existeArete(new Arete(0, 1)));
        assertFalse(grapheAbstrait.existeArete(new Arete(1, 0)));
        assertFalse(grapheAbstrait.existeArete(new Arete(0, 0)));
        assertFalse(grapheAbstrait.existeArete(new Arete(1, 1)));
    }


    @Test
    void estPasCompletBase() {
        grapheAbstrait = new Graphe(1);
        grapheAbstrait.ajouterArete(new Arete(0, 0));
        assertFalse(grapheAbstrait.estComplet());
    }

    @Test

    void estCompletBase() {
        grapheAbstrait = new Graphe(1);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        assertTrue(grapheAbstrait.estComplet());
    }

    @Test
    void estPasComplet() {
        grapheAbstrait = new Graphe(2);
        grapheAbstrait.ajouterArete(new Arete(0, 0));
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        assertFalse(grapheAbstrait.estComplet());
    }

    @Test
    void grapheEstPAsComplet() {
        grapheAbstrait = new Graphe(10);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(0, 2));
        grapheAbstrait.ajouterArete(new Arete(0, 3));
        grapheAbstrait.ajouterArete(new Arete(0, 4));
        grapheAbstrait.ajouterArete(new Arete(0, 5));
        grapheAbstrait.ajouterArete(new Arete(0, 6));
        grapheAbstrait.ajouterArete(new Arete(0, 7));
        grapheAbstrait.ajouterArete(new Arete(0, 8));
        grapheAbstrait.ajouterArete(new Arete(0, 9));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(1, 3));
        grapheAbstrait.ajouterArete(new Arete(1, 4));
        grapheAbstrait.ajouterArete(new Arete(1, 5));
        assertFalse(grapheAbstrait.estComplet());
    }

    @Test
    void estComplet2() {
        grapheAbstrait = new Graphe(10);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(0, 2));
        grapheAbstrait.ajouterArete(new Arete(0, 3));
        grapheAbstrait.ajouterArete(new Arete(0, 4));
        grapheAbstrait.ajouterArete(new Arete(0, 5));
        grapheAbstrait.ajouterArete(new Arete(0, 6));
        grapheAbstrait.ajouterArete(new Arete(0, 7));
        grapheAbstrait.ajouterArete(new Arete(0, 8));
        grapheAbstrait.ajouterArete(new Arete(0, 9));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(1, 3));
        grapheAbstrait.ajouterArete(new Arete(1, 4));
        grapheAbstrait.ajouterArete(new Arete(1, 5));
        grapheAbstrait.ajouterArete(new Arete(1, 6));
        grapheAbstrait.ajouterArete(new Arete(1, 7));
        grapheAbstrait.ajouterArete(new Arete(1, 8));
        grapheAbstrait.ajouterArete(new Arete(1, 9));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(2, 4));
        grapheAbstrait.ajouterArete(new Arete(2, 5));
        grapheAbstrait.ajouterArete(new Arete(2, 6));
        grapheAbstrait.ajouterArete(new Arete(2, 7));
        grapheAbstrait.ajouterArete(new Arete(2, 8));
        grapheAbstrait.ajouterArete(new Arete(2, 9));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        grapheAbstrait.ajouterArete(new Arete(3, 5));
        grapheAbstrait.ajouterArete(new Arete(3, 6));
        grapheAbstrait.ajouterArete(new Arete(3, 7));
        grapheAbstrait.ajouterArete(new Arete(3, 8));
        grapheAbstrait.ajouterArete(new Arete(3, 9));
        grapheAbstrait.ajouterArete(new Arete(4, 5));
        grapheAbstrait.ajouterArete(new Arete(4, 6));
        grapheAbstrait.ajouterArete(new Arete(4, 7));
        grapheAbstrait.ajouterArete(new Arete(4, 8));
        grapheAbstrait.ajouterArete(new Arete(4, 9));
        grapheAbstrait.ajouterArete(new Arete(5, 6));
        grapheAbstrait.ajouterArete(new Arete(5, 7));
        grapheAbstrait.ajouterArete(new Arete(5, 8));
        grapheAbstrait.ajouterArete(new Arete(5, 9));
        grapheAbstrait.ajouterArete(new Arete(6, 7));
        grapheAbstrait.ajouterArete(new Arete(6, 8));
        grapheAbstrait.ajouterArete(new Arete(6, 9));
        grapheAbstrait.ajouterArete(new Arete(7, 8));
        grapheAbstrait.ajouterArete(new Arete(7, 9));
        grapheAbstrait.ajouterArete(new Arete(8, 9));

        assertTrue(grapheAbstrait.estComplet());
    }

    @Test
    public void estComplet3() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(0, 2));
        grapheAbstrait.ajouterArete(new Arete(0, 3));
        grapheAbstrait.ajouterArete(new Arete(0, 4));

        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(1, 3));
        grapheAbstrait.ajouterArete(new Arete(1, 4));

        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(2, 4));

        grapheAbstrait.ajouterArete(new Arete(3, 4));

        assertTrue(grapheAbstrait.estComplet());
    }
    @Test
    public void estPasComplet3() {
        grapheAbstrait = new Graphe(3);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(0, 2));
        assertFalse(grapheAbstrait.estComplet());
    }

    @Test
    public void estUneChaine() {
        grapheAbstrait = new Graphe(3);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        assertTrue(grapheAbstrait.estUneChaine());
    }

    @Test
    public void estUneChaine2() {
        grapheAbstrait = new Graphe(10);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        grapheAbstrait.ajouterArete(new Arete(4, 5));
        grapheAbstrait.ajouterArete(new Arete(5, 6));
        grapheAbstrait.ajouterArete(new Arete(6, 7));
        grapheAbstrait.ajouterArete(new Arete(7, 8));
        grapheAbstrait.ajouterArete(new Arete(8, 9));
        assertTrue(grapheAbstrait.estUneChaine());
    }

    @Test
    public void estConexe() {
        // fait gaffe a mettre le bon nombre de sommet mdr
        grapheAbstrait = new Graphe(3);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 1));


        assertTrue(grapheAbstrait.estConnexe());
    }
    // test qui n'est pas une chaine
    @Test
    public void estConexe2() {
        grapheAbstrait = new Graphe(4);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 1));
        grapheAbstrait.ajouterArete(new Arete(3, 1));
        assertTrue(grapheAbstrait.estConnexe());
    }

    @Test
    public void testEstConexe() {
        grapheAbstrait = new Graphe(7);
        grapheAbstrait.ajouterArete(new Arete(6, 7));
        grapheAbstrait.ajouterArete(new Arete(0,1));
        grapheAbstrait.ajouterArete(new Arete(1,2));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        grapheAbstrait.ajouterArete(new Arete(4,5));
        grapheAbstrait.ajouterArete(new Arete(4,6));

        assertTrue(grapheAbstrait.estConnexe());
        grapheAbstrait.supprimerArete(new Arete(0,1));
        System.out.println(grapheAbstrait.existeArete(new Arete(6, 7)));
        grapheAbstrait.supprimerArete(new Arete(6, 7));
        assertEquals(5, grapheAbstrait.nbAretes());
        assertFalse(grapheAbstrait.estConnexe());
    }

    @Test
    public void estPasConexe() {
        grapheAbstrait = new Graphe(4);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 3));
        grapheAbstrait.ajouterArete(new Arete(2, 2));
        assertFalse(grapheAbstrait.estConnexe());
    }

    // pas une chaine mais un cycle
    public void estPasChaine() {
        grapheAbstrait = new Graphe(3);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 0));
        assertFalse(grapheAbstrait.estUneChaine());
    }

    // est un cycle

    @Test
    public void estPasCycle() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        assertFalse(grapheAbstrait.estUnCycle());
    }

    @Test
    public void estUnCycle() {
        grapheAbstrait = new Graphe(3);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 0));

        System.out.println(grapheAbstrait.degre(0));
        System.out.println(grapheAbstrait.degre(1));
        System.out.println(grapheAbstrait.degre(2));


        assertTrue(grapheAbstrait.estUnCycle());
    }

    @Test
    public void estUnCycle2() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        grapheAbstrait.ajouterArete(new Arete(4, 0));
        assertTrue(grapheAbstrait.estUnCycle());
    }

    //n'est pas un cycle

    @Test
    public void testNonConnexe() {
        Graphe g = new Graphe(5); // graphe à 5 sommets
        g.ajouterArete(new Arete(0, 1)); // première composante connexe
        g.ajouterArete(new Arete(2, 3)); // deuxième composante connexe
        g.ajouterArete(new Arete(3, 4)); // deuxième composante connexe
        assertFalse(g.estUnCycle());
    }

    @Test
    public void testGrapheOriente() {
        Graphe g = new Graphe(5); // graphe orienté à 5 sommets
        g.ajouterArete(new Arete(0, 1));
        g.ajouterArete(new Arete(1, 2));
        g.ajouterArete(new Arete(2, 3));
        g.ajouterArete(new Arete(3, 4));
        assertFalse(g.estUnCycle());
    }

    @Test
    public void testGrapheNonSimple() {
        Graphe g = new Graphe(5); // graphe à 5 sommets
        g.ajouterArete(new Arete(0, 1));
        g.ajouterArete(new Arete(0, 1)); // deuxième arête entre 0 et 1
        g.ajouterArete(new Arete(1, 2));
        g.ajouterArete(new Arete(2, 3));
        g.ajouterArete(new Arete(3, 3)); // boucle sur 3
        assertFalse(g.estUnCycle());
    }

    @Test
    public void testGrapheAvecSommetsIsoles() {
        Graphe g = new Graphe(5); // graphe à 5 sommets
        g.ajouterArete(new Arete(0, 1));
        g.ajouterArete(new Arete(1, 2));
        g.ajouterArete(new Arete(2, 3));

        assertFalse(g.estUnCycle());
    }

    @Test
    public void testGrapheAvecDegresImpairs() {
        Graphe g = new Graphe(5); // graphe à 5 sommets
        g.ajouterArete(new Arete(0, 1));
        g.ajouterArete(new Arete(1, 2));
        g.ajouterArete(new Arete(2, 3));
        g.ajouterArete(new Arete(2, 4)); // 4 a un degré impair
        assertFalse(g.estUnCycle());
    }

    @Test
    // vérifie si le grpahe et une foret
    public void estsUneForet() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));

        for (Integer sommet : grapheAbstrait.getMapAretes().keySet()) {
            Graphe graphe_actuel= new Graphe(grapheAbstrait, grapheAbstrait.getClasseConnexite(sommet));
            System.out.println(graphe_actuel.estConnexe());
        }

        assertFalse(grapheAbstrait.contientCycle(0, 0, new HashMap<>()));
        assertTrue(grapheAbstrait.estConnexe());
        assertTrue(grapheAbstrait.estUneForet());
    }

    @Test
    public void estPasUneForêt() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 0));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        assertFalse(grapheAbstrait.estUneForet());
    }

    @Test
    void test_estUneForet(){
        Arete a1 = new Arete(1,2);
        Arete a2 = new Arete(1,4);
        Arete a3 = new Arete(4,5);
        Arete a4 = new Arete(2,6);

        Arete a5 = new Arete(7,8);
        Arete a6 = new Arete(7,9);
        Arete a7 = new Arete(9,14);

        Arete a8 = new Arete(10,11);
        Arete a9 = new Arete(11,12);
        Arete a10 = new Arete(11,13);

        Graphe graphe = new Graphe(Arrays.asList(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10));
        assertEquals(true, graphe.estUneForet());
    }

    @Test
    void test_estConxeee() {
        Arete a1 = new Arete(1,2);
        Arete a2 = new Arete(1,4);
        Arete a3 = new Arete(4,5);
        Arete a4 = new Arete(2,6);

        Graphe graphe = new Graphe(Arrays.asList(a1,a2,a3,a4));
        assertEquals(true, graphe.estConnexe());
    }
    @Test
    void test_estForetVide(){
        Graphe graphe = new Graphe();
        assertEquals(true,graphe.estUneForet());
    }

    @Test
    void test_estUneForet3(){
        Graphe graphe = new Graphe(11);

        Arete a1 = new Arete(1,2);
        Arete a2 = new Arete(1,4);
        Arete a3 = new Arete(4,5);
        Arete a4 = new Arete(2,6);

        Arete a5 = new Arete(7,8);
        Arete a6 = new Arete(7,9);

        graphe.ajouterArete(a1);
        graphe.ajouterArete(a2);
        graphe.ajouterArete(a3);
        graphe.ajouterArete(a4);
        graphe.ajouterArete(a5);
        graphe.ajouterArete(a6);

        assertEquals(true,graphe.estUneForet());

    }
    @Test
    public void estUneForêt() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        assertTrue(grapheAbstrait.estUneForet());
    }

    @Test
    public void estForetCaMere() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(5, 4));

        assertFalse(grapheAbstrait.estUneForet());
    }

    @Test
    public void sequenceGraphe() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 0));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));

        ArrayList<Integer> sequence = new ArrayList<>();
        sequence.add(2);
        sequence.add(2);
        sequence.add(3);
        sequence.add(2);
        sequence.add(1);

        System.out.println(grapheAbstrait.getMapAretes());

        System.out.println(grapheAbstrait.degre(0));
        System.out.println(grapheAbstrait.degre(1));
        System.out.println(grapheAbstrait.degre(2));
        System.out.println(grapheAbstrait.degre(3));
        System.out.println(grapheAbstrait.degre(4));


        System.out.println(grapheAbstrait.sequenceGraphe(grapheAbstrait));

        assertTrue(grapheAbstrait.sequenceGraphe(grapheAbstrait).containsAll(sequence));
    }

    @Test
    public void voisins() {
        grapheAbstrait = new Graphe(2);
        grapheAbstrait.ajouterArete(new Arete(0, 1));

        List<Integer> voisins = new ArrayList<>();
        voisins.add(1);

        System.out.println(grapheAbstrait.getVoisins(0));
        assertTrue(grapheAbstrait.getVoisins(0).containsAll(voisins));
    }

    @Test
    public void voisinCaMere() {
        grapheAbstrait = new Graphe(4);
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 0));
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));

        List<Integer> voisins_0 = new ArrayList<>();
        voisins_0.add(1);
        voisins_0.add(3);

        List<Integer> voisins_1 = new ArrayList<>();
        voisins_1.add(0);
        voisins_1.add(2);




        System.out.println(grapheAbstrait.getVoisins(1));

        System.out.println(grapheAbstrait.getVoisins(0));
        assertTrue(grapheAbstrait.getVoisins(0).containsAll(voisins_0));
        assertTrue(grapheAbstrait.getVoisins(1).containsAll(voisins_1));
    }


    @Test
    public void contientArete() {
        grapheAbstrait = new Graphe(5);

        grapheAbstrait.ajouterArete(new Arete(0, 1));
        assertTrue(grapheAbstrait.existeArete(new Arete(0, 1)));

        grapheAbstrait.ajouterArete(new Arete(1, 2));

        assertTrue(grapheAbstrait.existeArete(new Arete(1, 2)));
        grapheAbstrait.ajouterArete(new Arete(2, 0));
        assertTrue(grapheAbstrait.existeArete(new Arete(2, 0)));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        assertTrue(grapheAbstrait.existeArete(new Arete(2, 3)));

    }


    @Test
    public void classeConexité() {
        grapheAbstrait = new Graphe(6);
        grapheAbstrait.ajouterArete(new Arete(0,1));
        grapheAbstrait.ajouterArete(new Arete(0,2));
        grapheAbstrait.ajouterArete(new Arete(2,3));
        grapheAbstrait.ajouterArete(new Arete(1,3));
        grapheAbstrait.ajouterArete(new Arete(4,5));

        List<Integer> classe_0 = new ArrayList<>();
        classe_0.add(0);
        classe_0.add(1);
        classe_0.add(2);
        classe_0.add(3);


        List<Integer> classe_4 = new ArrayList<>();
        classe_4.add(4);
        classe_4.add(5);

        List<Integer> classe_5 = new ArrayList<>();
        classe_5.add(5);
        classe_5.add(4);

        System.out.println(grapheAbstrait.getVoisins(4));

        assertTrue(grapheAbstrait.getClasseConnexite(0).containsAll(classe_0)
        && grapheAbstrait.getClasseConnexite(1).containsAll(classe_0) &&
                grapheAbstrait.getClasseConnexite(2).containsAll(classe_0) &&
                grapheAbstrait.getClasseConnexite(3).containsAll(classe_0) &&
                grapheAbstrait.getClasseConnexite(4).containsAll(classe_4) &&
                grapheAbstrait.getClasseConnexite(5).containsAll(classe_5));
    }

    @Test
    public void classeConexitésommet() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0,1));
        grapheAbstrait.ajouterArete(new Arete(0,2));
        grapheAbstrait.ajouterArete(new Arete(4,3));
        grapheAbstrait.ajouterArete(new Arete(2, 3));

        System.out.println(grapheAbstrait.getClasseConnexite(0));

    }

    @Test
    public void parcoursansRépétition() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 3));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        grapheAbstrait.ajouterArete(new Arete(1,3));
        grapheAbstrait.ajouterArete(new Arete(0,4));

        List<Integer> parcours = new ArrayList<>();
        parcours.add(0);
        parcours.add(4);

        System.out.println(grapheAbstrait.parcoursSansRepetition(0, 4, false));

        assertTrue(parcours.containsAll(grapheAbstrait.parcoursSansRepetition(0, 4, false)));
    }

    @Test
    public void constructeruListArete() {
        Arete a1 = new Arete(1,2);
        Arete a2 = new Arete(1,4);

        Graphe graphe = new Graphe(Arrays.asList(a1,a2));
        assertTrue(graphe.existeArete(a1) && graphe.existeArete(a2));
    }

    @Test
    public void parcoursansRépétitionHard() {
        Arete a1 = new Arete(1,2);
        Arete a2 = new Arete(1,4);
        Arete a3 = new Arete(4,5);
        Arete a4 = new Arete(2,6);
        Arete a5 = new Arete(7,8);
        Arete a6 = new Arete(8,9);
        Arete a7 = new Arete(9,10);
        Arete a8 = new Arete(10,7);
        Arete a9 = new Arete(6,7);
        Arete a10 = new Arete(6,3);

        Graphe graphe = new Graphe(Arrays.asList(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10));
        System.out.println(graphe.getMapAretes());

        List<Integer> parcours = new ArrayList<>();
        parcours.add(1);
        parcours.add(2);
        parcours.add(6);
        parcours.add(7);

        System.out.println(graphe.existeArete(new Arete(1, 6)));

        System.out.println(graphe.parcoursSansRepetition(1, 7, false));

        assertTrue(graphe.parcoursSansRepetition(1, 7, false).containsAll(parcours));

        //assertTrue(parcours.containsAll(graphe.parcoursSansRepetition(1, 7, false)));
    }

    @Test
    public void parcoursSansRépétiton() {
        grapheAbstrait = new Graphe(8);
        grapheAbstrait.ajouterArete(new Arete(0,1));
        grapheAbstrait.ajouterArete(new Arete(1,2));
        grapheAbstrait.ajouterArete(new Arete(2,3));
        grapheAbstrait.ajouterArete(new Arete(3,4));
        grapheAbstrait.ajouterArete(new Arete(4,0));
        grapheAbstrait.ajouterArete(new Arete(4,5));
        grapheAbstrait.ajouterArete(new Arete(5,6));
        grapheAbstrait.ajouterArete(new Arete(6,7));
        grapheAbstrait.ajouterArete(new Arete(7,0));

        List<Integer> parcours = new ArrayList<>();
        parcours.add(0);
        parcours.add(7);


        System.out.println(grapheAbstrait.parcoursSansRepetition(0, 7, false));

        assertTrue(parcours.containsAll(grapheAbstrait.parcoursSansRepetition(0, 7, false)));
    }

    @Test
    public void parcoursSansRépétiton2() {
        grapheAbstrait = new Graphe(8);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(0, 6));
        grapheAbstrait.ajouterArete(new Arete(1, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 5));
        grapheAbstrait.ajouterArete(new Arete(6, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 5));
        grapheAbstrait.ajouterArete(new Arete(5, 7));
        grapheAbstrait.ajouterArete(new Arete(7, 0));

        List<Integer> parcours = new ArrayList<>();
        parcours.add(0);
        parcours.add(6);

        System.out.println(grapheAbstrait.parcoursSansRepetition(0, 6, false));

        assertTrue(grapheAbstrait.parcoursSansRepetition(0, 6, false).containsAll(parcours));
    }

    @Test
    public void parcoursSansRépétitonHard() {
        grapheAbstrait = new Graphe(11);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(0, 2));
        grapheAbstrait.ajouterArete(new Arete(1, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 10));

        grapheAbstrait.ajouterArete(new Arete(4,8));
        grapheAbstrait.ajouterArete(new Arete(5,9));
        grapheAbstrait.ajouterArete(new Arete(9, 7));
        grapheAbstrait.ajouterArete(new Arete(1, 4));
        grapheAbstrait.ajouterArete(new Arete(4, 6));
        grapheAbstrait.ajouterArete(new Arete(5,6));
        grapheAbstrait.ajouterArete(new Arete(6, 8));
        grapheAbstrait.ajouterArete(new Arete(9, 10));

        System.out.println(grapheAbstrait.getMapAretes());

        //System.out.println(grapheAbstrait.getMapAretes());

        List<Integer> parcours = new ArrayList<>();
        parcours.add(1);
        parcours.add(3);
        parcours.add(10);


        System.out.println(grapheAbstrait.existeArete(new Arete(3, 10)));


        System.out.println(grapheAbstrait.parcoursSansRepetition(1,10, false));

        assertTrue(grapheAbstrait.parcoursSansRepetition(1,10, false).containsAll(parcours));
    }

    @Test
    public void testParcoursSansRepetitionGraphe() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1, new RouteMaritime(new Ville("Athina", true), new Ville("Roma", true), Couleur.ROUGE, 1)));

        grapheAbstrait.ajouterArete(new Arete(0, 2, new RouteMaritime(new Ville("Athina", true), new Ville("Roma", true), Couleur.ROUGE, 2)));

        grapheAbstrait.ajouterArete(new Arete(1, 2, new RouteMaritime(new Ville("Athina", true), new Ville("Roma", true), Couleur.ROUGE, 1)));

        grapheAbstrait.ajouterArete(new Arete(1, 3, new RouteMaritime(new Ville("Athina", true), new Ville("Roma", true), Couleur.ROUGE, 4)));
        grapheAbstrait.ajouterArete(new Arete(2, 3, new RouteMaritime(new Ville("Athina", true), new Ville("Roma", true), Couleur.ROUGE, 2)));
        grapheAbstrait.ajouterArete(new Arete(3, 4, new RouteMaritime(new Ville("Athina", true), new Ville("Roma", true), Couleur.ROUGE, 1)));
        grapheAbstrait.ajouterArete(new Arete(2, 4, new RouteMaritime(new Ville("Athina", true), new Ville("Roma", true), Couleur.ROUGE, 8)));

        List<Integer> parcours = new ArrayList<>();
        parcours.add(0);
        parcours.add(2);
        parcours.add(3);
        parcours.add(4);

        System.out.println(grapheAbstrait.parcoursSansRepetition(0, 4, true));

        assertTrue(grapheAbstrait.parcoursSansRepetition(0, 4, true).containsAll(parcours));
    }


    @Test
    public void testParcoursSasnRepetitionHardV3() {
        grapheAbstrait = new Graphe(10);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(0, 3));


        List<Integer> parcours = new ArrayList<>();
        parcours.add(0);
        parcours.add(3);

        System.out.println(grapheAbstrait.parcoursSansRepetition(0, 3, false));

        assertTrue(grapheAbstrait.parcoursSansRepetition(0, 3, false).containsAll(parcours));
    }





    @Test
    public void algoDijtrat() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(0, 2));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(1, 3));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(2, 4));
        grapheAbstrait.ajouterArete(new Arete(3, 4));

        List<Integer> parcours = new ArrayList<>();
        parcours.add(0);
        parcours.add(1);


        System.out.println(grapheAbstrait.parcoursSansRepetition(0, 1, false));

        assertTrue(parcours.containsAll(grapheAbstrait.parcoursSansRepetition(0, 1, false)));
    }

    @Test
    public void algoDijtrat2() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(0, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 4));

        List<Integer> parcours = new ArrayList<>();
        parcours.add(0);
        parcours.add(2);
        parcours.add(4);

        System.out.println(grapheAbstrait.getVoisins(0));

        System.out.println(grapheAbstrait.parcoursSansRepetition(0, 4, false));
        assertTrue(grapheAbstrait.parcoursSansRepetition(0, 4, false).containsAll(parcours));
    }

    public static boolean sequenceEstGraphe(List<Integer> sequence) {
        // somme de sequence
        //Triez la séquence de degrés dans l'ordre décroissant.
        //
        //Vérifiez si la somme des degrés est paire. Si ce n'est pas le cas, le graphe ne peut pas être construit.
        //
        //Tant que la séquence de degrés n'est pas vide, répétez les étapes suivantes :
        //
        //Sélectionnez le plus grand degré restant dans la séquence et appelez-le d.
        //Si d est plus grand que le nombre de degrés restants, ou s'il reste moins de d degrés dans la séquence, le graphe ne peut pas être construit.
        //Supprimez d degrés de la séquence.
        //Décrémentez d de 1 pour chaque degré restant dans la séquence.
        //Si toutes les étapes précédentes sont complétées avec succès, c'est-à-dire que tous les degrés ont été utilisés et réduits à zéro,
        // alors le graphe peut être construit à partir de la séquence de degrés donnée. Sinon, le graphe ne peut pas être construit.

        if (sequence.stream().mapToInt(Integer::intValue).sum() % 2 == 0) {
            return true;
        }
        return false;
    }

    @Test
    public void testSequenceestGraphe() {
        grapheAbstrait = new Graphe(5);

        List<Integer> sequence = new ArrayList<>();
        sequence.add(0);
        sequence.add(1);
        sequence.add(2);
        sequence.add(3);
        assertTrue(sequenceEstGraphe(sequence));
    }

    @Test
    public void testSequenceestPasGraphe() {
        grapheAbstrait = new Graphe(5);

        List<Integer> sequence = new ArrayList<>();
        sequence.add(0);
        sequence.add(1);
        sequence.add(2);
        sequence.add(3);
        sequence.add(5);
        assertFalse(sequenceEstGraphe(sequence));
    }

    @Test
        public void testClasseConexité() {
            grapheAbstrait = new Graphe(5);
            grapheAbstrait.ajouterArete(new Arete(0, 1));
            grapheAbstrait.ajouterArete(new Arete(0, 2));
            grapheAbstrait.ajouterArete(new Arete(2, 4));

            List<Integer> classeConexe = new ArrayList<>();
            classeConexe.add(0);
            classeConexe.add(1);
            classeConexe.add(2);


        System.out.println(grapheAbstrait.getClasseConnexite(0));
    }

    @Test

    public void testIsomorphes() {
        List<Arete> areteList = List.of(
                new Arete(1, 3),
                new Arete(2, 3),
                new Arete(3, 4),
                new Arete(3, 5)
        );

        Graphe grapheAbstrait = new Graphe(areteList);

        List<Arete> aretes = List.of(
                new Arete(1, 3),
                new Arete(1, 8),
                new Arete(1,6),
                new Arete(1,5)
        );

        Graphe graphe = new Graphe(aretes);

        assertTrue(Graphe.sontIsomorphes(grapheAbstrait, graphe));
    }

    @Test
    public void testClasseConexité4() {
        grapheAbstrait = new Graphe(5);
        grapheAbstrait.ajouterArete(new Arete(0, 1));
        grapheAbstrait.ajouterArete(new Arete(0, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 4));

        List<Integer> classeConexe = new ArrayList<>();
        classeConexe.add(0);
        classeConexe.add(1);
        classeConexe.add(2);
        classeConexe.add(4);

        assertTrue(classeConexe.containsAll(grapheAbstrait.getClasseConnexite(0)));
    }

    @Test
    public void testAretePareil() {
        Arete arete = new Arete(1, 3);
        Arete arete2 = new Arete(3, 1);

        assertTrue(Objects.equals(arete, arete2));
    }
}
