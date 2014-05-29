package com.gface.date;

import java.util.Date;
import java.util.EventObject;

public class DateSelectedEvent extends EventObject {

	public final Date date ;
	
	public DateSelectedEvent(Date date) {
		super(date);
		this.date = date ;
	}

}
