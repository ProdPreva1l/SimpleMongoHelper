package info.preva1l;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class CacheHandler {
    private final Map<String, MongoCollection<Document>> collectionCache = new HashMap<>();
    private static SimpleMongoHelper db;

    public CacheHandler(SimpleMongoHelper db) {
        CacheHandler.db = db;
    }

    public void updateCache(String collectionName) {
        MongoDatabase database = db.getDatabase();
        collectionCache.put(collectionName, database.getCollection(collectionName));
    }

    public void removeFromCache(String collectionName) {
        MongoCollection<Document> cachedCollection = collectionCache.get(collectionName);
        if (cachedCollection != null) {
            MongoDatabase database = db.getDatabase();
            collectionCache.remove(collectionName, database.getCollection(collectionName));
        }
    }

    public MongoCollection<Document> getCachedCollection(String collectionName) {
        return collectionCache.get(collectionName);
    }
}