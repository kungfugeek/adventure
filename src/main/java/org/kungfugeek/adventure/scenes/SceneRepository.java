package org.kungfugeek.adventure.scenes;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * adventure
 * SceneRepository
 * @author Nate
 * Oct 9, 2016
 */
public interface SceneRepository extends MongoRepository<Scene, String> {

}

