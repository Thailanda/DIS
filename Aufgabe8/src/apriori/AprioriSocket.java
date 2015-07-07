package apriori;

import com.google.inject.Inject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-07-07
 */
@ClientEndpoint
@ServerEndpoint(value="/apriori", configurator = AprioriConfigurator.class)
public class AprioriSocket {

    @Inject
    Apriori apriori;

    @OnOpen
    public void onWebSocketConnect(Session sess)
    {
        System.out.println("Socket Connected: " + sess);
    }

    @OnMessage
    public void onWebSocketText(Session sess, String incoming)
    {
        BasicDBObject incJSON = (BasicDBObject) JSON.parse(incoming);
        String file = (String) incJSON.get("file");
        double minSup = Double.parseDouble((String) incJSON.get("min_sup"));
        try {
            apriori.execute(new FileReader(Apriori.class.getResource(file).getFile()), minSup, (size, itemSets) -> {
                try {
                    BasicDBObject object = new BasicDBObject();
                    object.put("size", size);
                    object.put("item_sets", new BasicDBList());
                    for (FrequentItemSet itemSet : itemSets) {
                        BasicDBList itemSetsObj = (BasicDBList) object.get("item_sets");
                        final BasicDBObject itemSetObj = new BasicDBObject();
                        final BasicDBList setList = new BasicDBList();

                        for (int i : itemSet.getSet()) {
                            setList.add(i);
                        }

                        itemSetObj.put("set", setList);
                        itemSetObj.put("confidence", itemSet.getConfidence());
                        itemSetObj.put("occurrences", itemSet.getOccurrences());
                        itemSetsObj.add(itemSetObj);
                    }
                    sess.getBasicRemote().sendText(JSON.serialize(object));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("Socket Closed: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }
}
