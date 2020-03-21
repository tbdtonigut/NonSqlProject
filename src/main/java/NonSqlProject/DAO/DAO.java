/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NonSqlProject.DAO;

import NonSqlProject.exception.MyException;
import NonSqlProject.model.Employee;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;

public class DAO {

    final ArangoDB arangoDB = new ArangoDB.Builder().build();

    public boolean createDatabase() {
        String name = "mydb";
        try {
            arangoDB.createDatabase(name);
        } catch (ArangoDBException ex) {
            throw new MyException("Error: Database was not created sucesfully");
        }
    }

    public boolean createColletion() {
        String collectionName = "firstColletion";
        try {
            CollectionEntity myArangoCollection = arangoDB.db("mydb").createCollection(collectionName);
        } catch (ArangoDBException ex) {
            System.err.println("Fail to create the document:" + ex.getMessage());
        }
    }
   
    public void insertEmpleado(Employee e){
        BaseDocument myObject = new BaseDocument();
        
    }
}
