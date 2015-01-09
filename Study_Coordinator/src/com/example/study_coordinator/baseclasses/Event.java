package com.example.study_coordinator.baseclasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements HasId {
	public final int id;
	public final int locationId;
	public final int groupId;
	public final String name;
	public final Date date;
	public final String description;
	public final Scope scope;

	public Event(int id, int locationId2, int groupId, String name, String date, String description,
			String scope) throws ParseException {
		super();
		this.id = id;
		this.locationId = locationId2;
		this.groupId = groupId;
		this.name = name;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		this.date = df.parse(date);
		this.description = description;
		this.scope = Scope.get(scope);
	}

	@Override
	public int getId() {
		return id;
	}
}

enum Scope {
	PRIVATE, PROTECTED, PUBLIC;
	;;;
	public static Scope get(String name) {
		if (name == "private") {
			return PRIVATE;
		} else if (name == "protected") {
			return PROTECTED;
		} else if (name == "public") {
			return PUBLIC;
		} else {
			return null;
		}
	}
}
