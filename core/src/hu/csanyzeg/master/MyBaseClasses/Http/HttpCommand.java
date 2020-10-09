package hu.csanyzeg.master.MyBaseClasses.Http;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by tuskeb on 2017. 02. 12..
 */

abstract public class HttpCommand extends HttpConnect {
    private volatile static boolean locked = false;
    private HashMap<String, String> send = new HashMap<String, String>();
    private HashMap<String, String> receive = new HashMap<String, String>();

    public HashMap<String, String> getReceive() {
        return receive;
    }

    public HashMap<String, String> getSend() {
        return send;
    }

    public HttpCommand(String Url) {
        super(Url);
    }

    public void sendCommand(){
        waitingWhileLocked();
        locked = true;
        System.out.println("Send start");
        httpRequest.setContent(HttpMapUtil.mapToString(send));
        System.out.println(httpRequest.getContent());
        Gdx.app.error("http", httpRequest.getContent());
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String s;
                System.out.println("Result:\n" + (s = httpResponse.getResultAsString()));
                Gdx.app.error("http", s);
                HttpCommand.this.response(HttpMapUtil.stringToMap(s));
                locked = false;
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("Send command failed: " + t.getMessage());
                Gdx.app.error("http", "Send command failed: " + t.getMessage());
                HttpCommand.this.failed(HttpErrors.timeout);
                locked = false;
            }

            @Override
            public void cancelled() {
                System.out.println("Send command cancelled");
                HttpCommand.this.failed(HttpErrors.cancelled);
                locked = false;
            }
        });
        System.out.println("Send done");
    }

    private void response(HashMap<String, String> map){


        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            System.out.println(mentry.getValue());
            //m.put(String.valueOf(mentry.getKey().toString().trim().substring(1)), mentry.getValue().toString());
        }
        //System.out.println("Response: " + m.get("message"));
        receive = map;
        responsed();
    }

    abstract protected void responsed();

    abstract protected void failed(HttpErrors httpErrors);


    protected void waitingWhileLocked() {
        while (isLocked()) {
            try {
                System.out.println("Waiting (Http req)");
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean isLocked() {
        return locked;
    }

}
