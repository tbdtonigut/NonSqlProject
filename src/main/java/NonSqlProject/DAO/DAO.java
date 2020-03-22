/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NonSqlProject.DAO;

import NonSqlProject.exception.MyException;
import NonSqlProject.model.Employee;
import NonSqlProject.model.Enum.EventType;
import NonSqlProject.model.Enum.Type;
import NonSqlProject.model.Incidence;
import NonSqlProject.model.Record;
import com.arangodb.*;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.util.MapBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
        myObject.setKey(null);
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

    public void insertRecord(Record record) throws MyException {
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(null);
        myObject.addAttribute("date", record.getDateTime());
        myObject.addAttribute("employee", record.getUserName());
        myObject.addAttribute("eventtype", record.getEventType());
        try {
            arangoDB.db(name).collection("record").insertDocument(myObject);
        } catch (ArangoDBException ex) {
            throw new MyException(MyException.documentoNotCreated);
        }
    }

    public boolean checkLogIn(String username, String password) throws MyException {
        BaseDocument myDocument = arangoDB.db(name).collection("employee").getDocument(username,
                BaseDocument.class);
        if (myDocument == null) {
            return false;
        } else {
            return password.equals(myDocument.getAttribute("pass"));
        }
    }

    public Collection<CollectionEntity> getAllCollections() {
        ArangoDatabase db = arangoDB.db(name);
        Collection<CollectionEntity> infos = db.getCollections();
        return infos;
    }

    public Employee getEmployeeByUsername(String username) {
        BaseDocument myDocument = arangoDB.db(name).collection("employee").getDocument(username,
                BaseDocument.class);
        Employee e = new Employee(myDocument.getKey(), (String) myDocument.getAttribute("pass"), (String) myDocument.getAttribute("firstName"), (String) myDocument.getAttribute("lastName"), (String) myDocument.getAttribute("phone"));
        return e;
    }

    public Incidence getIncidenceById(String id) {
        Incidence i = new Incidence();
        long month = 0, year = 0, day = 0, hour = 0, nano = 0, minute = 0, second = 0;
        BaseDocument myDocument = arangoDB.db(name).collection("incidence").getDocument(id,
                BaseDocument.class);

        Map<String, String> mapOrigin = (HashMap) myDocument.getAttribute("origin");
        String username = mapOrigin.get("username");
        Employee origin = getEmployeeByUsername(username);
        Map<String, String> mapRecipient = (HashMap) myDocument.getAttribute("recipient");
        String usernameRecipient = mapRecipient.get("username");
        Employee recipient = getEmployeeByUsername(usernameRecipient);

        Map<String, Map<String, Long>> allDates = new HashMap<String, Map<String, Long>>();
        allDates = (Map<String, Map<String, Long>>) myDocument.getAttribute("date");
        for (Map.Entry<String, Map<String, Long>> entry : allDates.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("date")) {
                Map<String, Long> map = entry.getValue();
                for (Map.Entry<String, Long> entry2 : map.entrySet()) {
                    if (entry2.getKey().equalsIgnoreCase("month")) {
                        month = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("year")) {
                        year = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("day")) {
                        day = entry2.getValue();
                    }
                }
            } else if (entry.getKey().equalsIgnoreCase("time")) {
                Map<String, Long> map = entry.getValue();
                for (Map.Entry<String, Long> entry2 : map.entrySet()) {
                    if (entry2.getKey().equalsIgnoreCase("hour")) {
                        hour = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("nano")) {
                        nano = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("minute")) {
                        minute = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("second")) {
                        second = entry2.getValue();
                    }
                }
            }
        }

        LocalDateTime fecha = LocalDateTime.of((int) year, (int) month, (int) day, (int) hour, (int) minute, (int) second, (int) nano);
        i = new Incidence(Integer.parseInt(myDocument.getKey()), fecha,
                origin, recipient, (String) myDocument.getAttribute("details"), Type.valueOf(myDocument.getAttribute("type").toString()));
        return i;
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

    public ArrayList<Incidence> getAllDocumentsIncidence() {
        ArrayList<Incidence> incidences = new ArrayList<>();
        String query = "FOR i IN incidence RETURN i";
        Map<String, Object> bindVars = new MapBuilder().get();
        ArangoCursor<BaseDocument> cursor = arangoDB.db(name).query(query, bindVars, null,
                BaseDocument.class);
        cursor.forEachRemaining(aDocument -> {
            Incidence i = getIncidenceById(aDocument.getKey());
            incidences.add(i);
        });
        return incidences;
    }

    public void deleteIncidence(Incidence i) throws MyException {
        ArangoDatabase db = arangoDB.db(name);
        ArangoCollection collection = db.collection("incidence");
        if (!collection.documentExists(String.valueOf(i.getId()))) {
            throw new MyException(MyException.documentDoesntExists);
        } else {
            collection.deleteDocument(String.valueOf(i.getId()));
        }
    }

    public void deleteEmployee(Employee e) throws MyException {
        ArangoDatabase db = arangoDB.db(name);
        ArangoCollection collection = db.collection("employee");
        if (!collection.documentExists(e.getUsername())) {
            throw new MyException(MyException.documentDoesntExists);
        } else {
            collection.deleteDocument(e.getUsername());
        }
    }

    public int getIncidencesCount() {
        ArangoCollection collection = arangoDB.db("mydb").collection("incidence");
        return collection.getIndexes().size();
    }

    public void updateIncidence(Incidence incidence) throws MyException {
        ArangoDatabase db = arangoDB.db(name);
        ArangoCollection collection = db.collection("incidence");
        try {
            collection.updateDocument(String.valueOf(incidence.getId()), incidence);
        } catch (ArangoDBException e) {
            throw new MyException(MyException.documentHaventBeenUpdated);
        }
    }

    public void updateEmployee(Employee employee) {

    }

    public ArrayList<Record> getAllDocumentsRecord() {
        ArrayList<Record> records = new ArrayList<>();
        String query = "FOR r IN record RETURN r";
        Map<String, Object> bindVars = new MapBuilder().get();
        ArangoCursor<BaseDocument> cursor = arangoDB.db(name).query(query, bindVars, null,
                BaseDocument.class);
        cursor.forEachRemaining(aDocument -> {
            Record r = getRecordById(aDocument.getKey());
            records.add(r);
        });
        return records;
    }

    private Record getRecordById(String key) {
        Record r = new Record();
        long month = 0, year = 0, day = 0, hour = 0, nano = 0, minute = 0, second = 0;
        BaseDocument myDocument = arangoDB.db(name).collection("record").getDocument(key,
                BaseDocument.class);

        Map<String, String> mapEmployee = (HashMap) myDocument.getAttribute("employee");
        String username = mapEmployee.get("username");
        Employee employee = getEmployeeByUsername(username);

        Map<String, Map<String, Long>> allDates = new HashMap<>();
        allDates = (Map<String, Map<String, Long>>) myDocument.getAttribute("date");
        for (Map.Entry<String, Map<String, Long>> entry : allDates.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("date")) {
                Map<String, Long> map = entry.getValue();
                for (Map.Entry<String, Long> entry2 : map.entrySet()) {
                    if (entry2.getKey().equalsIgnoreCase("month")) {
                        month = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("year")) {
                        year = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("day")) {
                        day = entry2.getValue();
                    }
                }
            } else if (entry.getKey().equalsIgnoreCase("time")) {
                Map<String, Long> map = entry.getValue();
                for (Map.Entry<String, Long> entry2 : map.entrySet()) {
                    if (entry2.getKey().equalsIgnoreCase("hour")) {
                        hour = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("nano")) {
                        nano = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("minute")) {
                        minute = entry2.getValue();
                    } else if (entry2.getKey().equalsIgnoreCase("second")) {
                        second = entry2.getValue();
                    }
                }
            }
        }
        LocalDateTime date = LocalDateTime.of((int) year, (int) month, (int) day, (int) hour, (int) minute, (int) second, (int) nano);
        r = new Record(Integer.parseInt(key), date, employee, EventType.valueOf((myDocument.getAttribute("eventtype").toString())));
        return r;
    }
}