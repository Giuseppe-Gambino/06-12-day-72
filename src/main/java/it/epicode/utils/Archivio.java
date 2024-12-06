package it.epicode.utils;

import it.epicode.entity.Book;
import it.epicode.entity.Element;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Archivio {

    private EntityManager em;

    public Archivio(EntityManager em) {
        this.em = em;
    }

    public void addElement(Element element) {
        em.getTransaction().begin();
        em.persist(element);
        em.getTransaction().commit();
    }

    public Element getByElementISBN(int isbn) {
        try {
            return em.createQuery("SELECT e FROM Element e WHERE e.isbn = :setIsbn", Element.class)
                    .setParameter("setIsbn", isbn)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Elemento non trovato");
            return null;
        }
    }

    public boolean deleteByElementISBN(int isbn) {

        em.getTransaction().begin();
        try {
            em.createQuery("DELETE FROM Element e WHERE e.isbn = :setIsbn")
                .setParameter("setIsbn", isbn)
                .executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (NoResultException e) {
            em.getTransaction().rollback();
            System.out.println("Errore durante l'eliminazione dell'elemento: " + e.getMessage());
            return false;
        }
    }

    public List<Element> getByElementAnno(int date) {
        try {
            return em.createQuery("SELECT e FROM Element e WHERE e.annoDiPublicazione = :setAnnoDiPublicazione", Element.class)
                    .setParameter("setAnnoDiPublicazione", date)
                    .getResultList();
        } catch (NoResultException e) {
            System.out.println("Elementi non trovati");
            return null;
        }
    }


    public List<Element> getByElementAutore(String autore) {
        try {
            return em.createQuery("SELECT e FROM Element e WHERE e.autore LIKE :autore", Element.class)
                    .setParameter("autore", "%" + autore + "%") // I caratteri % sono inclusi nel parametro
                    .getResultList();
        } catch (NoResultException e) {
            System.out.println("Elementi non trovati");
            return Collections.emptyList();
        }
    }



    public List<Element> getByElementTitolo(String titolo) {
        try {
            return em.createQuery("SELECT e FROM Element e WHERE e.titolo LIKE :titolo", Element.class)
                    .setParameter("titolo", "%" + titolo + "%")
                    .getResultList();
        } catch (NoResultException e) {
            System.out.println("Elementi non trovati");
            return null;
        }
    }




}
