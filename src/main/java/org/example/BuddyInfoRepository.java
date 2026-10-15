package org.example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

// path is pinned so the URL is always /api/buddyInfos
@RepositoryRestResource(path = "buddyInfos", collectionResourceRel = "buddyInfos")
public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long> {
    List<BuddyInfo> findByName(String name);
}
