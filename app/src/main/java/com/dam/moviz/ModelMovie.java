package com.dam.moviz;

public class ModelMovie {
    private String titre;
    private String titre_minuscule;
    private int annee;
    private String acteurs;
    private String affiche;
    private String sysnopsis;

    public String getActeurs() {
        return acteurs;
    }

    public void setActeurs(String acteurs) {
        this.acteurs = acteurs;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getSysnopsis() {
        return sysnopsis;
    }

    public void setSysnopsis(String sysnopsis) {
        this.sysnopsis = sysnopsis;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre_minuscule() {
        return titre_minuscule;
    }

    public void setTitre_minuscule(String titre_minuscule) {
        this.titre_minuscule = titre_minuscule;
    }

    public ModelMovie(String titre, int annee, String acteurs, String afficheUrl, String sysnopsis) {
        this.titre = titre;
        this.annee = annee;
        this.acteurs = acteurs;
        this.affiche = afficheUrl;
        this.sysnopsis = sysnopsis;
    }

    public ModelMovie() {
    }
}
