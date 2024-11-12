package com.py;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class SyncClient {

    String uri = "mongodb://localhost:27017";

    private volatile static SyncClient instance;
    private final MongoClient mongoClient;

    public SyncClient() {
        mongoClient = MongoClients.create(uri);
    }

    public static SyncClient getInstance() {
        if (instance == null) {
            synchronized (SyncClient.class) {
                instance = new SyncClient();

            }
        }
        return instance;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
