package fr.upmc.dar.sncf.util;

import java.util.Date;

/**
 * Class for human-readable, pretty date formatting
 * 
 * @author David San (adapted to French from Lea Verou version)
 */
public class PrettyDate {
	private Date date;

	public PrettyDate() {
		this(new Date());
	}

	public PrettyDate(Date date) {
		this.date = date;
	}

	public String toString() {
		long current = (new Date()).getTime(), timestamp = date.getTime(), diff = (current - timestamp) / 1000;
		int amount = 0;
		String what = "";

		/**
		 * Second counts 3600: hour 86400: day 604800: week 2592000: month
		 * 31536000: year
		 */

		if (diff > 31536000) {
			amount = (int) (diff / 31536000);
			what = "année";
		} else if (diff > 31536000) {
			amount = (int) (diff / 31536000);
			what = "mois";
		} else if (diff > 604800) {
			amount = (int) (diff / 604800);
			what = "semaine";
		} else if (diff > 86400) {
			amount = (int) (diff / 86400);
			what = "jour";
		} else if (diff > 3600) {
			amount = (int) (diff / 3600);
			what = "heure";
		} else if (diff > 60) {
			amount = (int) (diff / 60);
			what = "minute";
		} else {
			amount = (int) diff;
			what = "seconde";
			if (amount < 6) {
				return "maintenant";
			}
		}

		if (amount == 1) {
			if (what.equals("day")) {
				return "hier";
			} else if (what.equals("semaine")) {
				return "la semaine dernière";
			} else if (what.equals("mois")) {
				return "le mois dernier";
			} else if (what.equals("année")) {
				return "l'année dernière";
			}
		} else {
			if (what.equals("mois")) {

			} else if (what.equals("année")) {
				what = "ans";
			} else {

				what += "s";
			}
		}

		return "il y a " + amount + " " + what;
	}
}