package fr.umontpellier.iut.graphes;

import fr.umontpellier.iut.rails.Route;

import java.util.Objects;

/**
 * Classe modélisant les arêtes. Pour simplifier, vous pouvez supposer le prérequis que i!=j
 */
public record Arete(int i, int j, Route route) {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arete arete = (Arete) o;
        return (i == arete.i && j == arete.j  || j == arete.i && i == arete.j )&& Objects.equals(route, arete.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j, route);
    }

    public Arete(int i, int j) {
        this(i, j, null);
    }

    public boolean incidenteA(int v) {
        return i == v || j == v;
    }

    public int getAutreSommet(int v) {
        return v == i ? j : i;
    }

    @Override
    public Route route() {
        return route;
    }

    public int longueurRoute() {
        if (route == null) {
            return 1;
        }
        return route.getLongueur();
    }
}