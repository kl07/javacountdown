package org.adoptopenjdk.javacountdown;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class ImportGeoData {
	
	private static final int PORT 		= 27017;
	private static final String HOST 	= "localhost";
	
	private static final String COLLECTION_NAME = "geoposition";
	private static final String DATABASE_NAME 	= "jcountdown";

	private static final String CSV_FILE_LOCATION 		= "geoposition.csv";
	private static final String CSV_TEST_FILE_LOCATION 	= "geoposition_snapshot.csv";

	
	public static void main(String... args) {
		ImportGeoData importGeoData = new ImportGeoData();
		importGeoData.execute();
	}

	
	
	/**
	 * This method performs the import into the Mongo database.
	 */
	public void execute() {

		String csvFile 		= CSV_FILE_LOCATION;
		BufferedReader br 	= null;
		String line 		= "";
		String delimiter 	= ",";
		
		JSONUtility jsonUtility = new JSONUtility();
		DBCollection coll 		= createDBConnection();

		try {
			
			File fileDir = new File(csvFile);
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			
			while ((line = br.readLine()) != null) {

				String[] country = line.split(delimiter);
				//PrintStream out = new PrintStream(System.out, true, "UTF-8");
				//out.println();
				BasicDBObject document = jsonUtility.constructJSONDocument(country);						
				
				//System.out.println(document);
				coll.insert(document);
			}
			
			System.out.println("Number of documents inserted: " + coll.count());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}

	
	
	/**
	 * @return a connection to the Mongo database
	 */
	private DBCollection createDBConnection() {
		MongoClient mongo;
		DBCollection coll = null;
		try {
			mongo = new MongoClient(HOST, PORT);			
			DB dbTest = mongo.getDB(DATABASE_NAME);
			coll = dbTest.getCollection(COLLECTION_NAME);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return coll;
	}
	
	
	
	/**
	 * Encodes a String in UTF-8.
	 * 
	 * @param original
	 * @return String 
	 */
	public String encodeUTF8(String original){

		String roundTrip = "";
		try {
		    byte[] utf8Bytes = original.getBytes("UTF8");
		    roundTrip = new String(utf8Bytes, "UTF8");
		} 
		catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		return roundTrip;
	
	}
	
	


}
