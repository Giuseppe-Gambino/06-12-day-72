package it.epicode.dao;

import it.epicode.entity.Element;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ElementDAO {
    private EntityManager em;

    public void save(Element oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Element findById(Long id) {
        return em.find(Element.class, id);
    }

    public List<Element> findAll() {
        return em.createNamedQuery("Trova_tutto_Element", Element.class).getResultList();
    }

    public void update(Element oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Element oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }


}
