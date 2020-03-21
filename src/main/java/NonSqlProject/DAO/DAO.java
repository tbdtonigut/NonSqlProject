/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NonSqlProject.DAO;

import NonSqlProject.exception.MyException;
import NonSqlProject.model.Employee;
import NonSqlProject.model.Incidence;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.util.MapBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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

    public void insertEmployee(Employee e) throws MyException {
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

    public void insertIncidence(Incidence i) throws MyException {
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(String.valueOf(getIncidencesCount() + 1));
        myObject.addAttribute("date", i.getDateTime());
        myObject.addAttribute("origin", i.getOrigin());
        myObject.addAttribute("recipient", i.getDestination());
        myObject.addAttribute("details", i.getDetails());
        myObject.addAttribute("type", i.getType());
        try {
            arangoDB.db(name).collection("incidence").insertDocument(myObject);
        } catch (ArangoDBException ex) {
            throw new MyException(MyException.documentoNotCreated);
        }
    }

    public boolean checkLogIn(String username, String password) throws MyException {
        BaseDocument myDocument = arangoDB.db(name).collection("employee").getDocument(username,
                BaseDocument.class);
        if (myDocument == null) {
            return false;
        } else if (!password.equals(myDocument.getAttribute("pass"))) {
            return false;
        }
        return true;
    }

    public Collection<CollectionEntity> getAllCollections() {
        ArangoDatabase db = arangoDB.db(name);
        Collection<CollectionEntity> infos = db.getCollections();
        return infos;
    }

    public Employee getEmployeeByUsername(String username) {
        BaseDocument myDocument = arangoDB.db(name).collection("employee").getDocument(username,
                BaseDocument.class);
        Employee e = new Employee((String) myDocument.getId(), (String) myDocument.getAttribute("pass"), (String) myDocument.getAttribute("firstName"), (String) myDocument.getAttribute("lastName"), (String) myDocument.getAttribute("phone"));
        return e;
    }

    public ArrayList<Employee> getAllDocumentsEmployee() {
        ArrayList<Employee> employees = new ArrayList<>();
        String query = "FOR e IN employee RETURN e";
        Map<String, Object> bindVars = new MapBuilder().get();
        ArangoCursor<BaseDocument> cursor = arangoDB.db(name).query(query, bindVars, null,
                BaseDocument.class);
        cursor.forEachRemaining(aDocument -> {
            Employee e = getEmployeeByUsername(aDocument.getKey());
            employees.add(e);
        });
        return employees;
    }

    public int getIncidencesCount() {
        ArangoCollection collection = arangoDB.db("mydb").collection("incidence");
        return collection.getIndexes().size();
    }
}
