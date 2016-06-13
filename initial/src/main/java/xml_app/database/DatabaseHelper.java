package xml_app.database;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import xml_app.model.Akt;
import xml_app.model.Amandman;
import xml_app.model.Korisnik;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper {

    private DatabaseClient client;
    private XMLDocumentManager manager;
    private Configuration.ConnectionProperties props;
    public DatabaseHelper(){
        try {
            DatabaseClientFactory.getHandleRegistry().register(
                    JAXBHandle.newFactory(Korisnik.class, Amandman.class, Akt.class)
            );
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        props = Configuration.loadProperties();
            client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
            manager = client.newXMLDocumentManager();
    }

    public void release(){
        client.release();
    }

    public void write(String XMLPath, String collId, String docId){
        // Create an input stream handle to hold XML content.
        InputStreamHandle handle = null;
        try {
            handle = new InputStreamHandle(new FileInputStream(XMLPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(collId);
        // Write the document to the

        manager.write(docId, metadata, handle);

        System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + docId);
    }

    public void writeKorisnik(Korisnik k){

        String collId = "korisnici";
        String docId = "korisnici/" + k.getKorisnickoIme() + ".xml";

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(collId);

        // Write the document to the database
        manager.writeAs(docId, metadata, k);

        System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + Integer.toString(k.getId()));
    }

    public void writeAkt(Akt a){

        String collId = "akti";
        String docId = "akti/" + a.getId() + ".xml";

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(collId);

        // Write the document to the database
        manager.writeAs(docId, metadata, a);

        System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + a.getId());
    }

    public void writeAmandman(Amandman a){

        String collId = "amandmani";
        String docId = "amandmani/" + a.getId() + ".xml";

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(collId);

        // Write the document to the database
        manager.writeAs(docId, metadata, a);

        System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + a.getId());
    }

    public Korisnik findKorisnikById(String username) {
        String docId = "korisnici/" + username + ".xml";
        Korisnik k;
        try {
            k = manager.readAs(docId, Korisnik.class);
        } catch (Exception e) {
            k = null;
        }

        return k;
    }

    public Akt findAktById(String id) {
        String docId = "akti/" + id + ".xml";
        Akt a;
        try {
            a = manager.readAs(docId, Akt.class);
        } catch (Exception e) {
            a = null;
        }

        return a;
    }

    public List<Akt> getUsvojeniAkti(){
        QueryManager queryMgr = client.newQueryManager();

        StringQueryDefinition stringQry = queryMgr.newStringDefinition();
        stringQry.setCollections("akti");

        List<Akt> ret = new ArrayList<>();

        SearchHandle searchHandle = queryMgr.search(stringQry, new SearchHandle());
        for (MatchDocumentSummary docSum: searchHandle.getMatchResults()) {

            Akt a = manager.readAs(docSum.getUri(), Akt.class);
            if(a.getStatus().equals("Usvojen"))
                ret.add(a);
        }

        return ret;

    }

    public List<Akt> getAktiUsvojeniUNacelu(){
        QueryManager queryMgr = client.newQueryManager();

        StringQueryDefinition stringQry = queryMgr.newStringDefinition();
        stringQry.setCollections("akti");

        List<Akt> ret = new ArrayList<>();

        SearchHandle searchHandle = queryMgr.search(stringQry, new SearchHandle());
        for (MatchDocumentSummary docSum: searchHandle.getMatchResults()) {

            Akt a = manager.readAs(docSum.getUri(), Akt.class);
            if(a.getStatus().equals("U nacelu"))
                ret.add(a);
        }

        return ret;

    }

    public List<Akt> getAktiUProceduri(){
        QueryManager queryMgr = client.newQueryManager();

        StringQueryDefinition stringQry = queryMgr.newStringDefinition();
        stringQry.setCollections("akti");

        List<Akt> ret = new ArrayList<>();

        SearchHandle searchHandle = queryMgr.search(stringQry, new SearchHandle());
        for (MatchDocumentSummary docSum: searchHandle.getMatchResults()) {

            Akt a = manager.readAs(docSum.getUri(), Akt.class);
            if(a.getStatus().equals("U proceduri"))
                ret.add(a);
        }

        return ret;

    }

    public List<Amandman> getAmandmani(){
        QueryManager queryMgr = client.newQueryManager();

        StringQueryDefinition stringQry = queryMgr.newStringDefinition();
        stringQry.setCollections("amandmani");

        List<Amandman> ret = new ArrayList<>();

        SearchHandle searchHandle = queryMgr.search(stringQry, new SearchHandle());
        for (MatchDocumentSummary docSum: searchHandle.getMatchResults()) {

            Amandman a = manager.readAs(docSum.getUri(), Amandman.class);
            ret.add(a);
        }

        return ret;

    }

    public Amandman findAmandmanById(String id) {
        String docId = "amandmani/" + id + ".xml";
        Amandman a;
        try {
            a = manager.readAs(docId, Amandman.class);
        } catch (Exception e) {
            a = null;
        }

        return a;
    }
///////////////////////////////----------------------------STATE METHODS------------------------------////////////////////////////////
    public void initState(){
        String collId = "state";
        String docId = "stateVal";

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(collId);

        // Write the document to the database
        manager.writeAs(docId, metadata, "idle");
    }

    public String getState(){
        String docId = "stateVal";
        String state;

        try {
            state = manager.readAs(docId, String.class);
        } catch (Exception e) {
            state = null;
        }

        return state;
    }

    public void nextState(){
        String docId = "stateVal";
        String collId = "state";
        String state;

        try {
            state = manager.readAs(docId, String.class);
        } catch (Exception e) {
            state = null;
        }

        state = state.trim();

        switch(state){
            case "idle": {
                state = "predlaganjeAkata";

            } break;
            case "predlaganjeAkata": {
                state = "predlaganjeAmandmana";

            } break;
            case "predlaganjeAmandmana": {
                state = "glasanjeUNacelu";

            } break;
            case "glasanjeUNacelu": {
                state = "glasanjeZaAmandmane";

            } break;
            case "glasanjeZaAmandmane": {
                state = "glasanjeUCelosti";

            } break;
            case "glasanjeUCelosti": {
                state = "idle";

            } break;
        }

        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add(collId);

        // Write the document to the database
        manager.writeAs(docId, metadata, state);
    }
}
