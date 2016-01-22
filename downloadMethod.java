import java.io.*;
import java.net.Socket;

public class downloadMethod {
    public String mkDownloadRequest(String host, String path){
        return "GET " + path + " HTTP/1.1\r\n" + "Host: " + host + "\r\n\r\n";
    }
    public String mkHeadDownloadRequest(String host, String path){
        return "HEAD " + path + " HTTP/1.1\r\n" + "Host: " + host + "\r\n\r\n";
    }
    public String mkNewDownloadRequest(String host, String path, String date){
        return "GET " + path + " HTTP/1.1\r\n" + "Host: " + host + "\r\n" + "If-Modified-Since: " + date + "\r\n\r\n";
    }
    public String continueDownloadRequest(String host, String path, String date, long lastStop){
        return "GET " + path + " HTTP/1.1\r\n" + "Host: " + host + "\r\n" + "If-Range: " + date + "Range: " + "bytes=" + lastStop + "-" + "\r\n\r\n";
    }
}
