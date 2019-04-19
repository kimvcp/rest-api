package rest_api;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

	public static String PATH = "https://rest-ssd.herokuapp.com/api/candidates";

	public static void main(String[] args) throws Exception {
//        list();
//        show(2);
//		create("X", "Y", 99);
//		update(37, "XXX", "YYY", 99);
		delete(37);
	}

	public static void list() throws Exception {
		URL url = new URL(PATH);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		printResult(con);
	}

	public static void show(int id) throws Exception {
		URL url = new URL(PATH + "/" + id);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		printResult(con);
	}
	
	public static void delete(int id) throws Exception {
		URL url = new URL(PATH + "/" + id);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("DELETE");
		printResult(con);
	}

	public static void create(String name, String partyName, int age) throws Exception {
		URL url = new URL(PATH);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setRequestProperty("Content-type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		OutputStream os = con.getOutputStream();
		os.write(json(name, partyName, age).getBytes("UTF-8"));
		os.close();

		printResult(con);
	}

	private static void update(int id, String name, String partyName, int age) throws Exception {
		URL url = new URL(PATH + "/" + id);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("PUT");
		con.setDoOutput(true);
		con.setRequestProperty("Content-type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		OutputStream os = con.getOutputStream();
		os.write(json(name, partyName, age).getBytes("UTF-8"));
		os.close();

		printResult(con);
	}


	private static String json(String name, String partyName, int age) {
		return String.format("{\"name\":\"%s\", \"party_name\":\"%s\", \"age\": %d}", name, partyName, age);
	}

	private static void printResult(HttpURLConnection con) throws Exception {
		Scanner scanner = null;
		if (con.getResponseCode() == 200) {
			scanner = new Scanner(con.getInputStream()).useDelimiter("\\A");
		} else {
			scanner = new Scanner(con.getErrorStream()).useDelimiter("\\A");
		}

		String response = scanner.hasNext() ? scanner.next() : "";

		System.out.println("Status: " + con.getResponseCode());
		System.out.println("Body: " + response);
	}

}