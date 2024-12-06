package it.epicode.dao;

import it.epicode.entity.Element;
import it.epicode.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserDAO {
    private EntityManager em;

    public void save(User oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createNamedQuery("Trova_tutto_User", User.class).getResultList();
    }

    public void update(User oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(User oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }

    public User findUserByTessere(int tess) {
        return em.createQuery("SELECT u FROM User u WHERE u.numeroDITessera = :numeroDITessera", User.class)
                .setParameter("numeroDITessera", tess)
                .getSingleResult();
    }

    public List<Element> findElementInPre(int tess) {
        User user = findUserByTessere(tess);
        long userID = user.getId();
        return em.createQuery("SELECT p.element FROM Prestito p WHERE p.user.id = :userId", Element.class)
                .setParameter("userId", userID)
                .getResultList();
    }

}
