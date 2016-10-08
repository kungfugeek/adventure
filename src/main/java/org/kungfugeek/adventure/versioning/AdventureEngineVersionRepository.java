package org.kungfugeek.adventure.versioning;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdventureEngineVersionRepository extends MongoRepository<AdventureEngineVersion, String> {}
