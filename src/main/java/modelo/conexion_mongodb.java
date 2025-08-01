package modelo;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import javax.swing.JOptionPane;

public class conexion_mongodb {
    public MongoClient mongoClient;
    
    public MongoClient crearConexion() {
        String uri = "mongodb://admin:admin123@192.168.1.71:27017";
        try {
            mongoClient = MongoClients.create(uri);
            if (mongoClient == null) {
                throw new MongoException("Fallo al crear la conexion MongoDB");
            }
            System.out.println("Conexión a MongoDB establecida");
            return mongoClient;
        } catch (MongoException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión a MongoDB: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    public MongoDatabase getDatabaseWithCodec(String databaseName) throws Exception {
        if (mongoClient == null) {
            throw new Exception("La conexión a MongoDB no ha sido establecida.");
        }
        CodecRegistry pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );
        return mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
    }

    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    } 
}
