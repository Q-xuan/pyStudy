package com.py;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SyncClient instance = SyncClient.getInstance();
        MongoClient client = instance.getMongoClient();
        MongoDatabase database = client.getDatabase("pyStudy");
        MongoCollection<Document> movies = database.getCollection("movies");
        movies.find().forEach(System.out::println);
    }
}
