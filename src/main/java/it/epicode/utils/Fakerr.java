package it.epicode.utils;

import com.github.javafaker.Faker;
import it.epicode.classi_enum.Periodicita;
import it.epicode.dao.*;
import it.epicode.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Fakerr {
    public static void main(String[] args) {
        Faker faker = new Faker(new Locale("it"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();

        BookDAO bookDAO = new BookDAO(em);
        MegazineDAO megazineDAO = new MegazineDAO(em);
        UserDAO userDAO = new UserDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);
        ElementDAO elementDAO = new ElementDAO(em);

        // Creazione di 10 utenti
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setNome(faker.name().firstName());
            user.setCognome(faker.name().lastName());
            user.setDataDiNascita(faker.date().birthday(18, 65).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            user.setNumeroDITessera(faker.number().numberBetween(1, 999));

            userDAO.save(user); // Salvataggio dell'utente
        }

        // Creazione di 10 libri e riviste
        for (int i = 0; i < 10; i++) {
            // Creazione di un nuovo libro
            Book book = new Book();
            book.setIsbn(faker.number().numberBetween(100, 999));
            book.setTitolo(faker.book().title());
            book.setAnnoDiPublicazione(faker.number().numberBetween(2020, 2024));
            book.setNumeroDiPagine(faker.number().numberBetween(100, 1000));
            book.setAutore(faker.book().author());
            book.setGenere(faker.book().genre());
            bookDAO.save(book); // Salvataggio del libro

            // Creazione di una nuova rivista
            Megazine megazine = new Megazine();
            megazine.setIsbn(faker.number().numberBetween(100, 999));
            megazine.setTitolo(faker.book().title());
            megazine.setAnnoDiPublicazione(faker.number().numberBetween(2020, 2024));
            megazine.setNumeroDiPagine(faker.number().numberBetween(20, 100));
            Periodicita[] periodicitaValues = Periodicita.values();
            megazine.setPeriodicita(periodicitaValues[faker.random().nextInt(periodicitaValues.length)]);
            megazineDAO.save(megazine); // Salvataggio della rivista

            // Creazione di un prestito
            Prestito prestito = new Prestito();
            LocalDate startDate = LocalDate.of(2024, 10, 15);
            LocalDate dateStartPrest = startDate.plusDays(faker.number().numberBetween(0, 40));
            prestito.setDataInizioPrestito(dateStartPrest);

            // Recupero utente casuale
            User userRandom = userDAO.findById((long) faker.number().numberBetween(1, 10));
            prestito.setUser(userRandom);

            // Recupero elemento casuale
            Element elementRandom = elementDAO.findById((long) faker.number().numberBetween(1, 10));
            prestito.setElement(elementRandom);

            prestito.setDataRestituzionePrevista(dateStartPrest.plusDays(30));
            if (ThreadLocalRandom.current().nextBoolean()) {
                prestito.setDataRestituzioneEffettiva(null);
            } else {
                prestito.setDataRestituzioneEffettiva(dateStartPrest.plusDays(faker.number().numberBetween(0, 60)));
            }

            // Salvataggio del prestito
            prestitoDAO.save(prestito);

            userRandom.aggiungiPrestito(prestito);

        }

        em.close();
        emf.close();
    }
}
