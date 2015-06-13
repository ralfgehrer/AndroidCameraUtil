package de.ecotastic.android.camerautil.lib;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A helper class to conveniently parse date information.
 * 
 * @author Ralf Gehrer <ralf@ecotastic.de>
 */
@SuppressLint("SimpleDateFormat")
public class DateParser {
	//ISO 8601 international standard date format
	public final static String dateFormat = "yyyy-MM-dd HH:mm:ss.SSSZ";
	
	public final static TimeZone utc = TimeZone.getTimeZone("UTC");
	
	/**
	 * Converts a Date object to a string representation.
	 * @param date
	 * @return date as String
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		} else {
			DateFormat df = new SimpleDateFormat(dateFormat);
			df.setTimeZone(utc);
			return df.format(date);	
		}
	}
	
	/**
	 * Converts a string representation of a date to its respective Date object.
	 * @param dateAsString
	 * @return Date
	 */
	public static Date stringToDate(String dateAsString) {
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			df.setTimeZone(utc);
			return df.parse(dateAsString);
		} catch (ParseException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
}