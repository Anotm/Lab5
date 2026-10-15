package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example")
@EntityScan(basePackages = "org.example")
public class AccessingDataJpaApplication {
    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

    /**
     * The database is in-memory (H2), so it is empty every time the app starts.
     * Without sample data, /addressbook/1 returns 404 and the app looks broken.
     * This creates one address book with a few buddies on startup if none exist.
     */
    @Bean
    public CommandLineRunner seedData(AddressBookRepository addressBookRepository) {
        return (args) -> {
            if (addressBookRepository.count() == 0) {
                AddressBook book = new AddressBook();
                book.addBuddy(new BuddyInfo("Rick", "613-555-0123"));
                book.addBuddy(new BuddyInfo("Mick", "613-666-4567"));
                book.addBuddy(new BuddyInfo("Slick", "613-777-8900"));
                addressBookRepository.save(book);
                log.info("Seeded sample address book: {}", book);
            }
        };
    }
}
