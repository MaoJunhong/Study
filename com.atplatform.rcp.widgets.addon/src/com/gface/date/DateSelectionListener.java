package com.gface.date;

import java.util.EventListener;

public interface DateSelectionListener extends EventListener {

	public void dateSelected(DateSelectedEvent e);
}
