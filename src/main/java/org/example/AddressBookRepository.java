package org.example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// path is pinned so the URL is always /api/addressBooks
@RepositoryRestResource(path = "addressBooks", collectionResourceRel = "addressBooks")
public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {
}
