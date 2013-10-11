package amu.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionResponse {

    private final ActionResponseType type;
    private final String url;
    private Map<String, String> parameters;

    public ActionResponse(ActionResponseType type, String url) {
        this.type = type;

        if (this.type == ActionResponseType.FORWARD) {
            this.url = "/" + url + ".jsp";
        } else { // (this.type == ActionResponseType.REDIRECT)
            this.url = url + ".do";
        }

        this.parameters = null;
    }

    public ActionResponseType getType() {
        return type;
    }

    public String getURL() {
        return url;
    }

    public void addParameter(String name, String value) {
        if (parameters == null) {
            parameters = new HashMap<String, String>();
        }
        
        parameters.put(name, value);
    }
    
    public String getParameterString() {

        if (parameters == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder("?");

            Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> parameter = it.next();
                
                try {
                    sb.append(URLEncoder.encode(parameter.getKey(), "UTF-8"));
                    sb.append("=");
                    sb.append(URLEncoder.encode(parameter.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ActionResponse.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (it.hasNext()) {
                    sb.append("&");
                }
            }
            return sb.toString();
        }
    }
}
