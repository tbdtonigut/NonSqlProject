/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NonSqlProject.DAO;

import NonSqlProject.exception.MyException;
import NonSqlProject.model.Employee;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;

import java.util.Collection;

public class DAO {

    final String name = "mydb";

    final ArangoDB arangoDB = new ArangoDB.Builder()
            .user("root")
            .password("admin")
            .build();

    public void createDatabase() throws MyException {
        Collection<String> names = arangoDB.getDatabases();
        if (names.size() == 1) {
            try {
                arangoDB.createDatabase(name);
            } catch (ArangoDBException ex) {
                throw new MyException(MyException.databaseNotCreated);
            }
        }

    }

    public void createColletion(String nombre) throws MyException {
        String collectionName = nombre;
        Collection<CollectionEntity> colecciones = getAllCollections();

        if (colecciones.size() < 13) {
            try {
                CollectionEntity myArangoCollection = arangoDB.db(name).createCollection(collectionName);
            } catch (ArangoDBException ex) {
                throw new MyException(MyException.collectionNotCreated);
            }
        }

    }

    public void insertEmpleado(Employee e) throws MyException {
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(e.getUsername());
        myObject.addAttribute("pass", e.getPass());
        myObject.addAttribute("firstName", e.getFirstName());
        myObject.addAttribute("lastName", e.getLastName());
        myObject.addAttribute("phone", e.getPhone());
        try {
            arangoDB.db(name).collection("employee").insertDocument(myObject);
        } catch (ArangoDBException ex) {
            throw new MyException(MyException.documentoNotCreated);
        }
    }

    public boolean checkLogIn(String username, String password) throws MyException {
        BaseDocument myDocument = arangoDB.db(name).collection("employee").getDocument(username,
                BaseDocument.class);
        if (myDocument == null) {
            throw new MyException(MyException.wrongUsername);
        } else if (!password.equals(myDocument.getAttribute("pass"))) {
            throw new MyException(MyException.wrongPass);
        }
        return true;
    }

    public Collection<CollectionEntity> getAllCollections() {
        ArangoDatabase db = arangoDB.db(name);
        Collection<CollectionEntity> infos = db.getCollections();
        return infos;
    }



}
