package com.example.productapi;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserSettingsRepository settingsRepo;

    @Scheduled(fixedRate = 80000)
    public void checkProducts() {

        System.out.println("Controllo prodotti...");

        List<Product> products = productRepository.findAll();
        List<UserSettings> users = settingsRepo.findAll();

        // 🔥 QUI: controllo quanti dati hai
        System.out.println("Numero prodotti: " + products.size());
        System.out.println("Numero utenti: " + users.size());

        LocalDate today = LocalDate.now();

        for (UserSettings user : users) {

            System.out.println("Email utente: " + user.getEmail());

            for (Product p : products) {

                System.out.println("Prodotto: " + p.getNome());
                System.out.println("Scadenza: " + p.getDataScadenza());

                if (p.getDataScadenza() == null) continue;

                long giorni = today.until(p.getDataScadenza()).getDays();

                System.out.println("Giorni alla scadenza: " + giorni);
                System.out.println("Notifica già inviata: " + p.isNotificaInviata());

                if (giorni <= 2 && !p.isNotificaInviata()) {

                    System.out.println("⚠️ INVIO EMAIL...");

                    emailService.send(
                        user.getEmail(),
                        "Prodotto in scadenza",
                        "Il prodotto " + p.getNome() + " scade tra " + giorni + " giorni"
                    );

                    p.setNotificaInviata(true);
                    productRepository.save(p);
                }
            }
        }
    }

}


