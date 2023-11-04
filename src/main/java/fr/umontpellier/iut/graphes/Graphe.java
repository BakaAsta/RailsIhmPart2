package fr.umontpellier.iut.graphes;

import com.sun.jdi.ArrayReference;
import fr.umontpellier.iut.rails.Route;
import org.junit.jupiter.api.Test;

import javax.lang.model.type.ArrayType;
import javax.swing.*;
import java.net.Inet4Address;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * (Multi) Graphe non-orienté pondéré. Le poids de chaque arête correspond à la longueur de la route correspondante.
 * Pour une paire de sommets fixée {i,j}, il est possible d'avoir plusieurs arêtes
 * d'extrémités i et j et de longueur identique, du moment que leurs routes sont différentes.
 * Par exemple, il est possible d'avoir les deux arêtes suivantes dans le graphe :
 * Arete a1 = new Arete(i,j,new RouteTerrestre(villes.get("Lima"), villes.get("Valparaiso"), Couleur.GRIS, 2))
 * et
 * Arete a2 = new Arete(i,j,new RouteTerrestre(villes.get("Lima"), villes.get("Valparaiso"), Couleur.GRIS, 2))
 * Dans cet exemple (issus du jeu), a1 et a2 sont deux arêtes différentes, même si leurs routes sont très similaires
 * (seul l'attribut nom est différent).
 */
public class Graphe {

    /**
     * Liste d'incidences :
     * mapAretes.get(1) donne l'ensemble d'arêtes incidentes au sommet dont l'identifiant est 1
     * Si mapAretes.get(u) contient l'arête {u,v} alors, mapAretes.get(v) contient aussi cette arête
     */
    private Map<Integer, HashSet<Arete>> mapAretes;


    /**
     * Construit un graphe à n sommets 0..n-1 sans arêtes
     */
    public Graphe(int n) {
        mapAretes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            mapAretes.put(i, new HashSet<>());
        }
    }



    /**
     * Construit un graphe vide
     */
    public Graphe() {
        mapAretes = new HashMap<>();
    }

    /**
     * Construit un graphe à partir d'une collection d'arêtes.
     *
     * @param aretes la collection d'arêtes
     */
    public Graphe(Collection<Arete> aretes) {
        mapAretes = new HashMap<>();
        for (Arete arete : aretes) {
            mapAretes.putIfAbsent(arete.i(), new HashSet<>());
            mapAretes.get(arete.i()).add(arete);
            mapAretes.putIfAbsent(arete.j(), new HashSet<>());
            mapAretes.get(arete.j()).add(arete);
        }
    }

    public Map<Integer, HashSet<Arete>> getMapAretes() {
        return mapAretes;
    }

    /**
     * À partir d'un graphe donné, construit un sous-graphe induit
     * par un ensemble de sommets, sans modifier le graphe donné
     *
     * @param graphe le graphe à partir duquel on construit le sous-graphe
     * @param X      l'ensemble de sommets qui définissent le sous-graphe
     *               prérequis : X inclus dans V()
     */
    public Graphe(Graphe graphe, Set<Integer> X) {
        mapAretes = new HashMap<>();
        for (Integer sommet : X) {
            mapAretes.put(sommet, new HashSet<>());
            for (Arete arete : graphe.mapAretes.get(sommet)) {
                if (X.contains(arete.i()) && X.contains(arete.j())) {
                    mapAretes.get(sommet).add(arete);
                }
            }
        }
        //throw new RuntimeException("Méthode non implémentée");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graphe graphe = (Graphe) o;
        if (graphe.nbSommets() != this.nbSommets()) return false;
        if (graphe.nbAretes() != this.nbAretes()) return false;
        for (Integer sommet : mapAretes.keySet()) {
            for (Arete arete : mapAretes.get(sommet)) {
                if (!graphe.existeArete(arete)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapAretes);
    }

    /**
     * @return l'ensemble de sommets du graphe
     */
    public Set<Integer> ensembleSommets() {
        return mapAretes.keySet();
    }

    /**
     * @return l'ordre du graphe (le nombre de sommets)
     */
    public int nbSommets() {
        Set<Integer> sommet_deja_vu = new HashSet<>();
       for (Integer sommet : mapAretes.keySet()) {
           if (!sommet_deja_vu.contains(sommet)) {
               sommet_deja_vu.add(sommet);
           }
       }
        //System.out.println(sommet_deja_vu);
       return sommet_deja_vu.size();
    }

    /**
     * @return le nombre d'arêtes du graphe (ne pas oublier que this est un multigraphe : si plusieurs arêtes sont présentes entre un même coupe de sommets {i,j}, il faut
     * toutes les compter)
     */
    public int nbAretes() {
        return mapAretes.values().stream().mapToInt(HashSet::size).sum() / 2;
    }


    public boolean contientSommet(Integer v) {
        return mapAretes.containsKey(v);
        //throw new RuntimeException("Méthode non implémentée");
    }

    /**
     * Ajoute un sommet au graphe s'il n'est pas déjà présent
     *
     * @param v le sommet à ajouter
     */
    public void ajouterSommet(Integer v) {
        if (!mapAretes.containsKey(v)) {
            mapAretes.put(v, new HashSet<>());
        }
        //throw new RuntimeException("Méthode non implémentée");
    }

    /**
     * Ajoute une arête au graphe si elle n'est pas déjà présente
     *
     * @param a l'arête à ajouter. Si les 2 sommets {i,j} de a ne sont pas dans l'ensemble,
     *          alors les sommets sont automatiquement ajoutés à l'ensemble de sommets du graphe
     */
    public void ajouterArete(Arete a) {
        // problème presque résolu mais quand on ajt un truc dans le meme il supprime l'ancien wtf

        if (!existeArete(a)) {
            if (!mapAretes.containsKey(a.i())) {
                mapAretes.put(a.i(), new HashSet<>());
                mapAretes.get(a.i()).add(a);
            }
            else {
                mapAretes.get(a.i()).add(a);
            }
             if (!mapAretes.containsKey(a.j())) {
                mapAretes.put(a.j(), new HashSet<>());
                 mapAretes.get(a.j()).add(a);
            }
             else {
                mapAretes.get(a.j()).add(a);
            }
        }
    }


    /**
     * Supprime une arête du graphe si elle est présente, sinon ne fait rien
     *
     * @param a arête à supprimer
     *
     */
    public void supprimerArete(Arete a) {
        if (existeArete(a)) {
            if(mapAretes.get(a.i()) != null) {
                mapAretes.get(a.i()).remove(a);
            }
            if (mapAretes.get(a.j()) != null) {
                mapAretes.get(a.j()).remove(a);
            }
        }
    }

    /**
     * @param a l'arête dont on veut tester l'existence
     * @return true si a est présente dans le graphe
     */
    public boolean existeArete(Arete a) {
        // mais c faux ca
            for (Integer sommet : mapAretes.keySet()) {
                for (Arete arete : mapAretes.get(sommet)) {
                    if (arete.equals(a)) {
                        return true;
                    }
                }
            }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer v : mapAretes.keySet()) {
            sb.append("sommet").append(v).append(" : ").append(mapAretes.get(v)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Retourne l'ensemble des sommets voisins d'un sommet donné.
     * Si le sommet n'existe pas, l'ensemble retourné est vide.
     *
     * @param v l'identifiant du sommet dont on veut le voisinage
     */
    public Set<Integer> getVoisins(int v) {
        Set<Integer> voisins = new HashSet<>();
        if (contientSommet(v)) {
            for (Integer sommet : mapAretes.keySet()) {
               for (Arete arete : mapAretes.get(sommet)) {
                   if (arete.incidenteA(v)) {
                       if (arete.i() == v) voisins.add(arete.j());
                       else
                       voisins.add(arete.i());
                   }
                   else if (arete.i() == v && arete.j() == v) voisins.add(arete.i());

               }
            }
        }
        return voisins;
    }

    /**
     * Supprime un sommet du graphe, ainsi que toutes les arêtes incidentes à ce sommet
     *
     * @param v le sommet à supprimer
     */
    public void supprimerSommet(int v) {
        if (mapAretes.containsKey(v)) {
            mapAretes.remove(v);
        }
        for (Integer sommet : mapAretes.keySet()) {
            mapAretes.get(sommet).removeIf(arete -> arete.i() == v || arete.j() == v);
        }
    }

    public int degre(int v) {
        return getVoisins(v).size();
    }

    /**
     *
     * @return le degré max, et Integer.Min_VALUE si le graphe est vide
     */
    public int degreMax(){
        int max = 0;
        for (Integer sommet : mapAretes.keySet()) {
            if (degre(sommet) > max) {
                max = degre(sommet);
            }
        }
        return max;
    }

    public boolean estSimple() {
        for (int i = 0; i < nbSommets(); i++) { // pour chaque sommet
                if (getVoisins(i).isEmpty()) { // si le sommet n'a pas de voisin
                    return false;
                }
                for (int j : getVoisins(i)) { // pour chaque voisin du sommet
                    if (i == j || degre(i) != degre(j)) { // si le sommet est son propre voisin ou s'il y a des arêtes en double
                        return false;
                    }
                }
            }
            return true; // si toutes les arêtes sont simples, le graphe est simple
        }




    /**
     * @return ture ssi pour tous sommets i,j de this avec (i!=j), alors this contient une arête {i,j}
     *
     */
    public boolean estComplet() {
        for (Integer sommet : mapAretes.keySet()) {
            if(mapAretes.get(sommet).size() != nbSommets() - 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true ssi this est une chaîne. Attention, être une chaîne
     * implique en particulier que l'on a une seule arête (et pas plusieurs en parallèle) entre
     * les sommets successifs de la chaîne. On considère que le graphe vide est une chaîne.
     */
    public boolean estUneChaine() {
        if (mapAretes.isEmpty()) {
            return true;
        }
        return estConnexe();
    }


    /**
     * @return true ssi this est un cycle. Attention, être un cycle implique
     * en particulier que l'on a une seule arête (et pas plusieurs en parallèle) entre
     * les sommets successifs du cycle.
     * On considère que dans le cas où G n'a que 2 sommets {i,j}, et 2 arêtes parallèles {i,j}, alors G n'est PAS un cycle.
     * On considère que le graphe vide est un cycle.
     */
    public boolean estUnCycle() {
        if (mapAretes.isEmpty()) {
            return false;
        }
        for (Integer sommet : mapAretes.keySet()) {
            if (degre(sommet) != 2) {
                return false;
            }
        }
        return true;
    }


    // vérifier si le grpahe possède un cycle et non si il et un cycle

    // ne marche pas a revoir

    /*Parcours en profondeur (DFS) :

    Commencez par choisir un nœud de départ dans le graphe.
    Visitez le nœud et marquez-le comme visité.
    Pour chaque voisin non visité du nœud actuel, effectuez une recherche en profondeur récursive.
    Si vous rencontrez un nœud qui a déjà été visité, cela signifie qu'il existe un cycle dans le graphe.
    Si vous avez terminé le parcours de tous les nœuds sans rencontrer de cycles, alors le graphe ne contient pas de cycle.*/

    public boolean contientCycle(Integer depart, Integer actual, Map<Integer, Boolean> visited) {
        if (estUnCycle()) {
            return false;
        }
        else {
            for (Integer sommet : getVoisins(actual)) {
                visited.put(actual, true);
                System.out.println(visited);
                if (!visited.containsKey(sommet) || !visited.get(sommet)) {
                    visited.put(sommet, true);
                    if (contientCycle(depart, sommet, visited)) {
                        return true;
                    }
                }

                else if (depart.equals(actual)) {
                    System.out.println(depart + " " + sommet);
                    return true;
                }
            }
        }
        return false;
    }

    // permet de savoir si un grpahe est conexe ou non
/**
     * @return true ssi this est une forêt. Attention, être une forêt implique
     * en particulier que chaque composante connexe est un arbre.
     * On considère que le graphe vide est une forêt.
     */


        /*Un graphe est une forêt s'il ne contient pas de cycle et qu'il est composé de plusieurs arbres. Ainsi, pour vérifier si un graphe est une forêt,
              on peut appliquer les étapes suivantes :

            Vérifier si le graphe est acyclique : pour cela, on peut utiliser un algorithme de détection de cycle, comme celui que nous avons vu précédemment.
             Si le graphe contient un cycle, il ne peut pas être une forêt.
            Vérifier si le graphe est connexe : pour cela, on peut utiliser un algorithme de parcours en profondeur ou en largeur à partir d'un nœud quelconque du graphe.
            Si tous les nœuds du graphe sont visités, alors il est connexe.
            Vérifier si le graphe contient plusieurs arbres : si le graphe est connexe et acyclique, il ne peut contenir qu'un seul arbre.
            Si le graphe est connexe et contient plusieurs arbres, il peut être une forêt.

            Ainsi, pour résumer, un graphe est une forêt si et seulement si :

            Il ne contient pas de cycle.
            Il est connexe ou composé de plusieurs composantes connexes qui sont des arbres.*/



//permte de savoir si un graphe est conexe ou non



    public boolean estConnexe() {
        if (estUnCycle()) {
            System.out.println("cycle");
        return true;
    }
        else if (degreMax() == nbSommets() - 1) {
            System.out.println("complet");
        return true;
        }
        //System.out.println(degreMax());

        if (mapAretes.isEmpty() && nbSommets() == 1) {
            System.out.println("vide");
            return true;
        }
        for (Integer sommet : mapAretes.keySet()) {
            if (degre(sommet) == 0) {
                System.out.println("degre 0");
                return false;
            }
        }
        for (Integer  sommet :  mapAretes.keySet()) {
            if (getClasseConnexite(sommet).size() == nbSommets()) {
                return true;
            }
        }
    return false;
}


    // je souhaite savoir si a partir des classe de conexité si le graphe est conexe

    // a refaire pour vérifier que toute les composantes du grpahe sont connexe
    public boolean estConnexeForet() {
        return estUnCycle() || getEnsembleClassesConnexite().size() == 1;
    }





    // apremment ce serait simplement un graphe qui ne contient pas de cylce et qui est connexe

    // surement arevoir car spécificité


    // renvoie tout les arbres qui font partit d'un graphe
    public boolean arbre() {
        return estConnexeForet() && nbAretes() == nbSommets() -1;
    }


    public boolean estUneForet() {
        if (contientCycle(0, 0, new HashMap<>())) {
            System.out.println("caca");
            return false;
        }
        for (Integer sommet : mapAretes.keySet()) {
            Graphe graphe_actuel= new Graphe(this, getClasseConnexite(sommet));
            if (!graphe_actuel.estConnexe()) {
                return false;
            }
        }
        return true;
    }


    // il serait plus intéligent de faire un algo de parcour de sommet qui se baserait sur le fait que. On cherche a trover la plus grande chaine partant du sommet v
    // pour cela on génère les voisins du sommet v et tant que ces sommet ont des voisins on les génère également.
    // Si jamais le sommet actuelle n'a plus de voisin on vavérifier que son sommet précédent n'a lui même pas de voisin avec une List qui stock et le voisin précédent
    // et une liste qui permet de contenir la longueur max de la liste

    public Set<Integer> getClasseConnexite(int v) {
    Set<Integer> sommet_rouge = new HashSet<>(v);

    Set<Integer> sommet_bleu = new HashSet<>(getVoisins(v));
    sommet_bleu.addAll(getVoisins(v));
        System.out.println(getVoisins(v));
    // problème car il aime pas le fait que j'update la liste en meme temps que je parcour ces sommets

        while (!sommet_bleu.isEmpty()) {
            // on va update la liste comme ca
            List<Integer> update_sommet_bleu = new ArrayList<>(sommet_bleu);
            for (Integer sommet : update_sommet_bleu) {
                for (Integer voisins : getVoisins(sommet)) {
                    if (!sommet_rouge.contains(voisins)) {
                        sommet_bleu.add(voisins);
                    }
                }
                sommet_rouge.add(sommet);
                sommet_bleu.remove(sommet);
            }
        }
    return sommet_rouge;
}

public Set<Set<Integer>> getEnsembleClassesConnexite() {
    Set<Set<Integer>> ensembleConexite = new HashSet<>();
    for (Integer sommet : mapAretes.keySet()) {
        ensembleConexite.add(getClasseConnexite(sommet));
    }
    return ensembleConexite;
}

    public Set<Set<Integer>> getEnsembleClassesConnexiteGraphe(List<Integer> sommets) {
        Set<Set<Integer>> ensembleConexite = new HashSet<>();
        for (Integer sommet : sommets) {
            ensembleConexite.add(getClasseConnexite(sommet));
        }
        return ensembleConexite;
    }

    /**
     * @return true si et seulement si l'arête passée en paramètre est un isthme dans le graphe.
     */
    public boolean estUnIsthme(Arete a) {
        Graphe graphesansArete = this;
        graphesansArete.supprimerArete(a);
        if (graphesansArete.getEnsembleClassesConnexite().size() > this.getEnsembleClassesConnexite().size()) {
            return true;
        }
        return false;
    }

    public boolean sontAdjacents(int i, int j) {
        return this.getVoisins(i).contains(j) || this.getVoisins(j).contains(i);
    }

    /**
     * Fusionne les deux sommets passés en paramètre.
     * Toutes les arêtes reliant i à j doivent être supprimées (pas de création de boucle).
     * L'entier correspondant au sommet nouvellement créé sera le min{i,j}. Le voisinage du nouveau sommet
     * est l'union des voisinages des deux sommets fusionnés.
     * Si un des sommets n'est pas présent dans le graphe, alors cette fonction ne fait rien.
     */
    public void fusionnerSommets(int i, int j) {
        if (this.contientSommet(i) || this.contientSommet(j)) {
            this.ajouterSommet(Math.min(i, j));
            this.supprimerArete(new Arete(i, j));
            this.supprimerArete(new Arete(j, i));
            for (Integer sommet : this.getVoisins(i)) {
                this.ajouterArete(new Arete(Math.min(i, j), sommet));
            }
            for (Integer sommet : this.getVoisins(j)) {
                this.ajouterArete(new Arete(Math.max(i, j), sommet));
            }
        }
    }

    /**
     * @return true si et seulement si la séquence d'entiers passée en paramètre correspond à un graphe simple valide.
     * La pondération des arêtes devrait être ignorée.
     */

    // vérifier les conditon de sortie du while, vérifier les condition de bonne séquence,essayer de faire un algo tu type générateFils qui va donner tt les fils possible
    // pour savoir si la séquence est bonne

    // a refaire inportant
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
        if (sequence.contains(sequence.size() - 1) && sequence.contains(0)) {
            return false;
        }
        if (sequence.stream().mapToInt(Integer::intValue).sum() % 2 != 0) {
            return true;
        }
        return false;
    }

    /**
     * @return true si et seulement si la séquence d'entiers passée en paramètre correspond à un graphe valide.
     * La pondération des arêtes devrait être ignorée.
     */
    public List<Integer> sequenceGraphe(Graphe g1) {
        List<Integer> sequence = new ArrayList<>();
        for (Integer sommet : g1.mapAretes.keySet()) {
            sequence.add(g1.degre(sommet));
        }
        return sequence;
    }


    public Graphe permutation(int[] tableau) {
        Graphe g1 = new Graphe();

        Map<Integer, Integer> map = new HashMap<>();
        int indice = 0;
        for (Integer sommet : this.mapAretes.keySet()) {
            map.put(sommet, tableau[indice]);
            indice++;
        }
        for (Map.Entry<Integer, HashSet<Arete>> entry : this.mapAretes.entrySet()) {
            for (Arete arete : entry.getValue()) {
                g1.ajouterArete(new Arete(map.get(entry.getKey()), map.get(arete.getAutreSommet(entry.getKey()))));
            }
        }
        return  g1;
    }

    public boolean checkPermutation(Graphe g1, int[] tableau, int indice, Set<Integer> possibilites) {
        if (indice == tableau.length) {
            //System.out.println(Arrays.toString(tableau));
            Graphe graphe = g1.permutation(tableau);
            //System.out.println(graphe.mapAretes);
            if (Arrays.equals(tableau, new int[]{3, 1, 2, 4, 5})) {
                System.out.println(Arrays.toString(tableau));
                System.out.println(g1.mapAretes);
                System.out.println(graphe.mapAretes);
                System.out.println(this.mapAretes);
            }
            return this.equals(graphe);
        }
        for (Integer sommet : possibilites) {
            tableau[indice] = sommet;
            Set<Integer> possibilites2 = new HashSet<>(possibilites);
            possibilites2.remove(sommet);
            if (checkPermutation(g1, tableau, indice + 1, possibilites2)) {
                return true;
            }
        }
        return false;
    }


    // Cependant, il est important de noter que le fait que deux graphes aient le même nombre de sommets,
    // le même nombre d'arêtes et les mêmes arêtes ne suffit pas à garantir qu'ils sont isomorphes.
    // Il est possible que deux graphes différents aient les mêmes propriétés.
    // Pour être certain qu'ils sont isomorphes, il faut vérifier qu'il existe une correspondance bijective
    // entre les sommets des deux graphes,
    // c'est-à-dire une fonction qui associe chaque sommet du premier graphe à un sommet unique du second graphe,
    // de sorte que les arêtes soient conservées.
    public static boolean sontIsomorphes(Graphe g1, Graphe g2) {
        // tout est en refaire en gros aveec un algo plus complexe qui va associé les sommet entre eux
        // En associant un sommet à un autre on va vérifier si les sommet on le meme nombre de voisin et si les aretes correspondrait bien

        if (g1.nbSommets() != g2.nbSommets()) {
            return false;
        }
        if (g1.nbAretes() != g2.nbAretes()) {
            return false;
        }
        return g1.checkPermutation(g2, new int[g1.nbSommets()], 0, g1.mapAretes.keySet());
    }


    // test de sont isomorphes

/*
    @Test
    public static void main(String[] args) {
       Graphe grapheAbstrait = new Graphe(6);
        grapheAbstrait.ajouterArete(new Arete(6, 0));
        grapheAbstrait.ajouterArete(new Arete(6, 5));
        grapheAbstrait.ajouterArete(new Arete(1, 2));
        grapheAbstrait.ajouterArete(new Arete(2, 3));
        grapheAbstrait.ajouterArete(new Arete(3, 4));
        grapheAbstrait.ajouterArete(new Arete(4, 5));

        Graphe grapheAbstrait2 = new Graphe(6);
        grapheAbstrait2.ajouterArete(new Arete(6, 1));
        grapheAbstrait2.ajouterArete(new Arete(6, 5));
        grapheAbstrait2.ajouterArete(new Arete(1, 2));
        grapheAbstrait2.ajouterArete(new Arete(2, 3));
        grapheAbstrait2.ajouterArete(new Arete(3, 4));
        grapheAbstrait2.ajouterArete(new Arete(4, 5));



        System.out.println(sontIsomorphes(grapheAbstrait, grapheAbstrait2));
    }*/


    /**
     * Retourne un plus court chemin entre 2 sommets suivant l'algorithme de Dijkstra.
     * Les poids des arêtes sont les longueurs des routes correspondantes.
     * @param depart le sommet de départ
     * @param arrivee le sommet d'arrivée
     * @param pondere true si les arêtes sont pondérées (pas les longueurs des routes correspondantes dans le jeu)
     *                false si toutes les arêtes ont un poids de 1 (utile lorsque les routes associées sont complètement omises)
     *
     */

    public List<Integer> algoDikstra(int depart, int arrivee, boolean pondere) {

        Set<Integer> sommet_rouge = new HashSet<>(Arrays.asList(depart));

        Set<Integer> sommet_bleu = new HashSet<>(getVoisins(depart));

        // problème car il aime pas le fait que j'update la liste en meme temps que je parcour ces sommets

        while (!sommet_bleu.isEmpty()) {
            // on va update la liste comme ca
            List<Integer> update_sommet_bleu = new ArrayList<>(sommet_bleu);
            //System.out.println(sommet_bleu);
            for (Integer sommet : update_sommet_bleu) {

                for (Integer voisins : getVoisins(sommet).stream().filter(x -> !sommet_rouge.contains(x)).collect(Collectors.toList())) {

                    if (voisins == arrivee) {
                        sommet_rouge.add(voisins);
                        return new ArrayList<>(sommet_rouge);
                    }
                    else if (!sommet_rouge.contains(voisins)) {
                        sommet_bleu.add(voisins);
                    }
                }
                sommet_rouge.add(sommet);
                sommet_bleu.remove(sommet);
            }
        }
        return new ArrayList<>(sommet_rouge);
    }
    /**
     * Retourne un plus court chemin entre 2 sommets sans répétition de sommets
     * @param depart le sommet de départ
     * @param arrivee le sommet d'arrivée
     * @param pondere true si les arêtes sont pondérées (pas les longueurs des routes correspondantes dans le jeu)
     *                false si toutes les arêtes ont un poids de 1 (utile lorsque les routes associées sont complètement omises)
     */

    public List<Integer> parcoursSansRepetition(int depart, int arrivee, boolean pondere) {
        int[] distance = new int[nbSommets()];
        for (int i = 0; i < distance.length; i++) {
            if (i == arrivee) {
                distance[i] = 0;
            }
            else {
                distance[i] = Integer.MAX_VALUE;
            }
        }
        int[] precedent = new int[nbSommets()];

        ArrayList<Integer> frontiere = new ArrayList<>();
        frontiere.add(arrivee);

        if (!getClasseConnexite(depart).contains(arrivee)) {
            return new ArrayList<>();
        }
        while (!frontiere.isEmpty()) {
            Integer sommet_min_distance = frontiere.stream().min(Comparator.comparingInt(a -> distance[a])).get();
            frontiere.remove(sommet_min_distance);
            for (Arete arete : mapAretes.get(sommet_min_distance)) {
                if (distance[sommet_min_distance] + (pondere && arete.route() != null ? arete.route().getLongueur() : 1) < distance[arete.getAutreSommet(sommet_min_distance)]) {
                    distance[arete.getAutreSommet(sommet_min_distance)] = distance[sommet_min_distance] + (pondere && arete.route() != null ? arete.route().getLongueur() : 1);
                    precedent[arete.getAutreSommet(sommet_min_distance)] = sommet_min_distance;
                    frontiere.add(arete.getAutreSommet(sommet_min_distance));
                }
            }
        }
        List<Integer> parcours = new ArrayList<>(List.of(depart));
        int sommet_actuel = depart;
        while (sommet_actuel != arrivee) {
            parcours.add(precedent[sommet_actuel]);
            sommet_actuel = precedent[sommet_actuel];
        }
        return parcours;
    }

    // cette méthode est à comprendre pcq je suis duper la
    public List<Integer> parcoursSansRepetition(List<Integer> sousListe) {
        ArrayList<Integer> parcours = new ArrayList<>();
        for (int i = 0; i < sousListe.size() - 1; i++) {
            if (i != 0) {
                parcours.addAll(parcoursSansRepetition(sousListe.get(i), sousListe.get(i + 1), true).subList(1, parcoursSansRepetition(sousListe.get(i), sousListe.get(i + 1), true).size()));
            }
            else {
                parcours.addAll(parcoursSansRepetition(sousListe.get(i), sousListe.get(i + 1), true));
            }
        }
        System.out.println(parcours);
        return parcours;
    }

    public Route getRoute(Integer integer, Integer integer1) {
        return mapAretes.get(integer).stream().filter(x -> x.getAutreSommet(integer) == integer1).findFirst().get().route();
    }
}