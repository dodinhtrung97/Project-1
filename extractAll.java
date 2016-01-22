import java.net.MalformedURLException;
import java.net.URL;

public class extractAll {
    public String[] getAll(String url) {
        String host = null;
        String path = null;
        try {
            URL newURL = new URL(url);
            //Extract host and path from input url
            host = newURL.getHost();
            path = newURL.getPath();
            String query = newURL.getQuery(); //Get url action query if exists

            //Dealing with path's miscellanous
            if (path == null || path.length() == 0) {
                path = "/"; //adds as a stop when url contains no path
            }
            if (query != null && query.length() > 0) { //If query exists
                path += "?" + query; //Query begins with a ? after path name
            }

        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return new String[] {host,path};
    }

    public Integer getPort(String url) {
        int port=0;
        try {
            URL newURL = new URL(url);
            port = newURL.getPort();
            String protocol = newURL.getProtocol(); //http or https
            if (port == -1) {
                if (protocol.equals("http")) {
                    port = 80;
                } else {
                    System.out.println("Port not supported! Exiting");
                    return null;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return port;
    }
}
