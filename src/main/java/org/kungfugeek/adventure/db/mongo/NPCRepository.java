package org.kungfugeek.adventure.db.mongo;

import org.kungfugeek.adventure.npc.NPC;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NPCRepository extends MongoRepository<NPC, String>{

}
