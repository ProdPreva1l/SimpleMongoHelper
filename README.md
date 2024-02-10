# A Way too simple mongo helper

> ### Usage

```java
import javax.print.Doc;

public class example {

    /*
      Connect to a database
     */
    private SimpleMongoHelper simpleMongoHelper = new SimpleMongoHelper("127.0.0.1", 27017, "dbUser", "dbPass", "dbName", "admin");
    // With URI 
    // private SimpleMongoHelper simpleMongoHelper = new SimpleMongoHelper("mongo://dbUser:dbPass@127.0.0.1:27017/?authSource=admin", "dbName");

    private CacheHandler cacheHandler = new CacheHandler(simpleMongoHelper);
    public CollectionHelper collectionHelper = new CollectionHelper(simpleMongoHelper, cacheHandler);


    /*
      Create and store data in a collection
     */
    public void createStoreExample() {
        // Create the collection
        collectionHelper.createCollection("example_collection");

        // Create the Document
        Document document = new Document("example_field_1", "hello").append("exampleField2", 65).append("customField", collectionHelper);

        collectionHelper.insertDocument("example_collection", document);
    }

    /*
      Get a document
     */
    public Document getDocument() {
        // Get the collection
        MongoCollection<Document> collection = collectionHelper.getCollection("example_collection");

        // Filter for a Key and Value, or multiple 
        Document filter = new Document("exampleField2", 65);
        
        // Find and return the document based off the filter
        return collection.find().filter(filter).first();
    }

    /*
      Modify a document
     */
    public Document modifyDocument() {
        // Reuse the function from earlier that gets the document
        Document document = getDocument();
        
        // Create a set of updates to be applied
        Bson updates = Updates.combine(
                Updates.set("example_field_1", "world!"),
                Updates.set("customField", cacheHandler)
        );

        // Finally update the document in the collection
        collectionHelper.updateDocument("example_collection", document, updates);
    }
}
```