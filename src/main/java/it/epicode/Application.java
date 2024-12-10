package it.epicode;

import it.epicode.classi_enum.Periodicita;
import it.epicode.dao.*;
import it.epicode.entity.Book;
import it.epicode.entity.Megazine;
import it.epicode.entity.Prestito;
import it.epicode.entity.User;
import it.epicode.utils.Archivio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Application {

    static Scanner scanner = new Scanner(System.in);

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
    static EntityManager em = emf.createEntityManager();



    static UserDAO userDAO = new UserDAO(em);
    static PrestitoDAO prestitoDAO = new PrestitoDAO(em);
    static ElementDAO elementDAO = new ElementDAO(em);
    static Archivio archivio = new Archivio(em);


    public static void main(String[] args) {

        boolean continua = true;

        while (continua) {
            stampaMenu();
            System.out.println("inserisci input: ");
            int scelta = scanner.nextInt();

            switch (scelta) {
                case 1:
                    aggiungiLibro();
                    break;
                case 2:
                    aggiungiRivista();
                    break;
                case 3:
                    System.out.println(elementDAO.findAll());
                    break;
                case 4:
                    deletePerISBN();
                    break;
                case 5:
                    cercaAutore();
                    break;
                case 6:
                    cercaPerAnno();
                    break;
                case 7:
                    cercaPerISBN();
                    break;
                case 8:
                    cercaPerTitolo();
                    break;
                case 9:
                    creaPrestito();
                    break;
                case 10:
                    System.out.println("inserisci numero tessera");
                    int numero = scanner.nextInt();
                    System.out.println(userDAO.findElementInPre(numero));
                    break;
                case 11:
                    System.out.println(prestitoDAO.trovaPrestitiScaduti());
                    break;
                case 0:
                    continua = false;
                    System.out.println("Uscita dal programma. Arrivederci!");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }






    }

    public static void stampaMenu() {
        System.out.println("\n--- Menu Catalogo ---");
        System.out.println("1. Aggiungi un libro");
        System.out.println("2. Aggiungi una rivista");
        System.out.println("3. Visualizza catalogo");
        System.out.println("4, Elimina elemento per ISBN");
        System.out.println("5. Filtra elementi per autore");
        System.out.println("6. Filtra elementi per anno");
        System.out.println("7. Cerca elemento per (ISBN)");
        System.out.println("8. Cerca elemento per titolo o parte di esso");
        System.out.println("9. Prendi in prestito");
        System.out.println("10. trova prestiti in base tessera");
        System.out.println("11. trova prestiti scaduti");
        System.out.println("0. Esci");
    }

    private static void aggiungiLibro() {

        Book book = new Book();

        System.out.println("\n--- Aggiungi Libro ---");

        System.out.println("Inserisci ISBN: ");
        book.setIsbn(scanner.nextInt());


        System.out.println("Inserisci titolo: ");
        book.setTitolo(scanner.next());

        System.out.println("Inserisci anno di pubblicazione: ");
        book.setAnnoDiPublicazione(scanner.nextInt());

        System.out.println("Inserisci numero di pagine: ");
        book.setNumeroDiPagine(scanner.nextInt());

        System.out.println("Inserisci autore: ");
        book.setAutore(scanner.next());

        System.out.println("Inserisci genere: ");
        book.setGenere(scanner.next());


        BookDAO bookDAO = new BookDAO(em);

        bookDAO.save(book);
        System.out.println("Libro aggiunto con successo!");


    }

    private static void aggiungiRivista() {
        Megazine megazine = new Megazine();

        System.out.println("\n--- Aggiungi Rivista ---");

        System.out.println("Inserisci ISBN: ");
        megazine.setIsbn(scanner.nextInt());

        System.out.println("Inserisci titolo: ");
        megazine.setTitolo(scanner.next());

        System.out.println("Inserisci anno di pubblicazione: ");
        megazine.setAnnoDiPublicazione(scanner.nextInt());

        System.out.println("Inserisci numero di pagine: ");
        megazine.setNumeroDiPagine(scanner.nextInt());

        System.out.println("Inserisci periodicità (Es. Settimanale, Mensile, Annuale): ");
        String periodicitaInput = scanner.next();


        Periodicita periodicita = Periodicita.valueOf(periodicitaInput.toUpperCase());
        megazine.setPeriodicita(periodicita);

        MegazineDAO megazineDAO = new MegazineDAO(em);
        megazineDAO.save(megazine);

        System.out.println("Rivista aggiunta con successo!");
    }

    public static void cercaAutore() {
        System.out.println("inserisci nome Autore: ");
        String autore = scanner.next();
        System.out.println(archivio.getByElementAutore(autore));
    }

    public static void cercaPerAnno() {
        System.out.println("inserisci Anno: ");
        System.out.println(archivio.getByElementAnno(scanner.nextInt()));
    }

    public static void cercaPerISBN() {
        System.out.println("inserisci Anno: ");
        System.out.println(archivio.getByElementISBN(scanner.nextInt()));
    }

    public static void cercaPerTitolo() {
        System.out.println("inserisci titolo: ");
        String titolo = scanner.next();
        System.out.println(archivio.getByElementTitolo(titolo));
    }

    public static void deletePerISBN() {
        System.out.println("inserisci ISBN: ");
        System.out.println(archivio.deleteByElementISBN(scanner.nextInt()));
    }

    private static void creaPrestito() {
        Prestito prestito = new Prestito();


        System.out.println("\n--- Aggiungi Presito ---");

        System.out.println("Inserisci id User: ");
        Long id = scanner.nextLong();
        User userCorrente = userDAO.findById(id);
        prestito.setUser(userCorrente);

        System.out.println("Inserisci id elemento: ");
        Long idEl = scanner.nextLong();
        prestito.setElement(elementDAO.findById(idEl));


        prestito.setDataInizioPrestito(LocalDate.now());


        prestito.setDataRestituzionePrevista(LocalDate.now().plusDays(30));

        prestito.setDataRestituzioneEffettiva(null);




        prestitoDAO.save(prestito);

        userCorrente.aggiungiPrestito(prestito);

        System.out.println("Prestito aggiunto con successo!");
    }


}
