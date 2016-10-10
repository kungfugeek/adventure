package org.kungfugeek.adventure.scenes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

/**
 * adventure
 * ScenePack
 * @author Nate
 * Oct 9, 2016
 */
public class ScenePack {

	@Id
	private String id;

	private String title;
	private Map<String, Scene> scenes;
	private Scene openingScene;

	private ScenePack() {}

	private ScenePack(Builder builder) {
		this.title = builder.title;
		this.scenes = Collections.unmodifiableMap(builder.scenes);
		this.openingScene = builder.openingScene;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the openingScene
	 */
	public Scene getOpeningScene() {
		return openingScene;
	}

	public Scene getScene(String scene) {
		return scenes.get(scene);
	}

	public static class Builder {
		private String title;
		private Map<String, Scene> scenes;
		private Scene openingScene;

		public Builder() {
			scenes = new HashMap<String, Scene>(3);
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder scene(Scene scene) {
			this.scenes.put(scene.getTitle(), scene);
			return this;
		}

		public Builder openingScene(Scene openingScene) {
			this.openingScene = openingScene;
			return this;
		}

		public ScenePack build() {
			return new ScenePack(this);
		}
	}

}
