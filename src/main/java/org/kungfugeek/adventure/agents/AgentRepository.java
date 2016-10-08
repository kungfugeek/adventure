package org.kungfugeek.adventure.agents;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * adventure
 * AgentRepository
 * @author Nate
 * Oct 8, 2016
 */
public interface AgentRepository extends MongoRepository<Agent, String> {

}

