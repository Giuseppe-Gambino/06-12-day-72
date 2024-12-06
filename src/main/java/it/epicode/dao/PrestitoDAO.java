package it.epicode.dao;

import it.epicode.entity.Element;
import it.epicode.entity.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PrestitoDAO {
    private EntityManager em;

    public void save(Prestito oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Prestito findById(Long id) {
        return em.find(Prestito.class, id);
    }

    public List<Prestito> findAll() {
        return em.createNamedQuery("Trova_tutto_Prestito", Prestito.class).getResultList();
    }

    public void update(Prestito oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Prestito oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }

    public List<Prestito> trovaPrestitiScaduti() {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < CURRENT_DATE AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                .getResultList();
    }




}
