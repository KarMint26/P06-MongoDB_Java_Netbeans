/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package produk;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.UpdateResult;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author karel
 */
public class TestDB {

    public static void main(String[] args) {
        try {
            // koneksi ke database MongoDB
            MongoDatabase database = koneksi.konekMongoDB();

            // melihat daftar koleksi (table)
            System.out.println("==========================");
            System.out.println("Daftar Tabel dalam Database");
            MongoIterable<String> tables = database.listCollectionNames();

            for (String name : tables) {
                System.out.println(name);
            }

            // Menambah data
            System.out.println("==========================");
            System.out.println("Menambahkan data");
            MongoCollection<Document> col = database.getCollection("produk");
            Document doc = new Document();
            doc.put("nama", "Printer Inkjet");
            doc.put("nama", 1204000);
            doc.put("tanggal", new Date());
            col.insertOne(doc);
            System.out.println("Data telah disimpan dalam koleksi");

            // Mendapatkan _id dari dokumen yang baru saja diinsert
            ObjectId id = new ObjectId(doc.get("_id").toString());

            // Melihat atau menampilkan data
            System.out.println("==========================");
            System.out.println("Data dalam koleksi produk");
            MongoCursor<Document> cursor = col.find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }

            // Mencari dokumen berdasarkan id
            Document myDoc = col.find(eq("_id", id)).first();
            System.out.println("==========================");
            System.out.println("Pencarian data berdasarkan id: " + id);
            System.out.println(myDoc.toJson());

            // Mengedit data
            Document docs = new Document();
            docs.put("nama", "Canon");
            Document doc_edit = new Document("$set", docs);
            UpdateResult hasil_edit = col.updateOne(eq("_id", id), doc_edit);
            System.out.println(hasil_edit.getModifiedCount());
            
            // Melihat atau menampilkan data
            System.out.println("==========================");
            System.out.println("Data dalam koleksi produk");
            cursor = col.find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
            
            // Menghapus data
            col.deleteOne(eq("_id", id));
            System.out.println("==========================");
            System.out.println("Data dalam koleksi produk");
            cursor = col.find().iterator();
            while(cursor.hasNext()){
                System.out.println(cursor.next().toJson());
            }
            
            // Menambah data
            System.out.println("==========================");
            System.out.println("Menambahkan data");
            col.insertOne(doc);
            System.out.println("Data telah disimpan dalam koleksi");
            
            // Melihat atau menampilkan data
            System.out.println("==========================");
            System.out.println("Data dalam koleksi produk");
            cursor = col.find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
