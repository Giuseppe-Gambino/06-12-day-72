package it.epicode.dao;

import it.epicode.entity.Megazine;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MegazineDAO {
    private EntityManager em;

    public void save(Megazine oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Megazine findById(Long id) {
        return em.find(Megazine.class, id);
    }

    public List<Megazine> findAll() {
        return em.createNamedQuery("Trova_tutto_Megazine", Megazine.class).getResultList();
    }

    public void update(Megazine oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Megazine oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }


}
