
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

public class ICalendarExample {

	public static void main(String[] args) {

		// Initilize values
		String calFile = "test.ics";
		// 12 == 7 am
		// start time
		java.util.Calendar startCal = java.util.Calendar.getInstance();
		startCal.set(2017, 04, 23);

		// end time
		// start time is 7 am with difference in time
		java.util.Calendar endCal = java.util.Calendar.getInstance();
		endCal.set(2017, 04, 23);

		// CLASS NAME
		String subject = "Class - Compsci ";
		// CLASS ROOM #
		String location = "Room - 109";
		// TEACHER
		String description = "B Block";
		// EMAIL
		String hostEmail = "jack_engels@ryecountryday.org";

		// Creating a new calendar
		net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
		calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
		String strDate = sdFormat.format(startCal.getTime());
		System.out.println(startCal.getTime());
		System.out.println(endCal.getTime());
		System.out.println(strDate); 

		net.fortuna.ical4j.model.Date startDt = null;
		System.out.println(strDate);
		startDt = new net.fortuna.ical4j.model.Date(startCal.getTime());

		// DURATION OF CLASS
		int min = 90;

		Dur dur = new Dur(0, 0, min, 0);

		// Creating a meeting event
		VEvent meeting = new VEvent(startDt, dur, subject);

		meeting.getProperties().add(new Location(location));
		meeting.getProperties().add(new Description());
		System.out.println(meeting.getProperties());

		try {
			meeting.getProperties().getProperty(Property.DESCRIPTION).setValue(description);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			meeting.getProperties().add(new Organizer("MAILTO:" + hostEmail));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		calendar.getComponents().add(meeting);

		System.out.println(calendar.getComponents());

		// just printing

		FileOutputStream fout = null;

		try {
			fout = new FileOutputStream(calFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.setValidating(false);

		try {
			outputter.output(calendar, fout);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}

		System.out.println(meeting);
	}
}