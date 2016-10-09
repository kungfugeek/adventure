package org.kungfugeek.adventure.items;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * adventure
 * ItemRepository
 * @author Nate
 * Oct 9, 2016
 */
public interface ItemRepository extends MongoRepository<Item, String> {

}

