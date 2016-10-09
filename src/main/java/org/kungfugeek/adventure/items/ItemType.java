package org.kungfugeek.adventure.items;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kungfugeek.adventure.modifiers.Modifier;
import org.kungfugeek.adventure.options.Option;
import org.springframework.data.annotation.Id;

/**
 * adventure
 * ItemType
 * @author Nate
 * Oct 9, 2016
 */
public class ItemType {

	@Id
	private String id;
	
	private String name;
	
}

