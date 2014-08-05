package com.Client.Utils;
import javax.xml.namespace.NamespaceContext;
import java.util.*;

/**
 * Created by OJT4 on 8/5/14.
 */
public class NameSpace implements NamespaceContext {

    private Map map;

    public NameSpace() {
        map = new HashMap();
    }

    public void setMap(String prefix, String uri) {
        map.put(prefix,uri);
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return (String) map.get(prefix);
    }

    @Override
    public String getPrefix(String uri) {
        Set key = map.keySet();
        Iterator iterator= key.iterator();
        while (iterator.hasNext()) {
            String prefix = (String) iterator.next();
            String uri_temp = (String) map.get(prefix);
            if (uri.equals(uri_temp))
                return uri_temp;
        }
        return null;
    }

    @Override
    public Iterator getPrefixes(String uri) {
        List prefixes = new ArrayList();
        Set key = map.keySet();
        Iterator iterator = key.iterator();
        while (iterator.hasNext()) {
            String prefix = (String) iterator.next();
            String uri_temp = (String) map.get(prefix);
            prefixes.add(prefix);
        }
        return prefixes.iterator();
    }

}
