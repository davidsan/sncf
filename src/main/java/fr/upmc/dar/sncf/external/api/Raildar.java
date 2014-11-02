package fr.upmc.dar.sncf.external.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Raildar {

	public static final String RAILDAR_API_URL = "http://www.raildar.fr/json/";
	public static final String RAILDAR_API_GARES_URL = RAILDAR_API_URL
			+ "gares";
	public static final String RAILDAR_API_TRAIN_URL = RAILDAR_API_URL
			+ "get_train";

	public static void main(String[] args) {
		try {
			System.out.println(fetchTrainName("881677"));
			System.out.println(fetchGareName("123"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String fetchTrainName(String trainId) throws IOException {
		// via http://raildar.fr/json/get_train?num=trainId&date=yyyy-mm-dd
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		String date = dateFormat.format(new Date());
		String query = String.format("num=%s&date=%s", trainId, date);
		JsonNode res = fetchJson(RAILDAR_API_TRAIN_URL, query);
		if (res.size() == 0) {
			throw new JsonParseException("Failed : No station with id "
					+ trainId, null);
		}

		for (int i = 0; i < res.size(); i++) {
			if (res.get(i).get("id_mission").get(0) != null) {
				return res.get(i).get("brand").asText().replace("OCE", "")
						+ " " + trainId;
			}
		}
		return res.get(0).get("brand").asText().replace("OCE", "") + " "
				+ trainId;
	}

	public static String fetchGareName(String gareId) throws IOException {
		String query = String.format("id_gare=%s", gareId);
		JsonNode res = fetchJson(RAILDAR_API_GARES_URL, query);
		if (res.size() == 0) {
			throw new JsonParseException("Failed : No station with id "
					+ gareId, null);
		}
		return res.get(0).get("name_gare").asText();
	}

	public static JsonNode fetchJson(String url, String query)
			throws IOException {
		URLConnection connection = new URL(url + "?" + query).openConnection();
		connection.setRequestProperty("Accept", "application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(connection.getInputStream())));

		String output;
		StringBuilder sb = new StringBuilder();
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		JsonParser jp = factory.createParser(sb.toString());
		JsonNode actualObj = mapper.readTree(jp);
		return actualObj;
	}
}