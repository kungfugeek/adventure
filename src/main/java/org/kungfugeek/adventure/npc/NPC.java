package org.kungfugeek.adventure.npc;

import org.springframework.data.annotation.Id;

/**
 * @author Nate
 *
 */
public class NPC {
	@Id
	private String id;
	
	private String name;
	private String description;
	
	public NPC(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
}
