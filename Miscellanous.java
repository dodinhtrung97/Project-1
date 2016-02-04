import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Miscellanous {
    public void deleteHeaderFile(){
        File header = new File("header.txt");
        File newHeader = new File("newheader.txt");
        if (newHeader.exists()){
            newHeader.delete();
        }
        header.delete();
    }
    public long getFileSize(String file){
        File newFile = new File(file);
        return newFile.length(); //File size
    }

    public int getContentLength(String url) throws IOException{
        URL newUrl = new URL(url);
        URLConnection con = newUrl.openConnection();
        return con.getContentLength(); //get content length
    }

    public int excludeHeader(String header) throws IOException{
        File headerFile = new File(header);
        BufferedReader header_;
        int counter = 0;

        header_ = new BufferedReader(new FileReader(headerFile));//buffer header file for later read

        while (header_.readLine() != null){
            counter += 1;//count number of header lines
        }
        return counter;
    }

    public String findLastModified(String header) throws  IOException{
        File headerFile = new File(header);
        BufferedReader header_;
        String headerLine;
        header_ = new BufferedReader(new FileReader(headerFile));
        while((headerLine=header_.readLine())!=null){
            if (headerLine.toLowerCase().contains("Last-Modified".toLowerCase())){
                String finalOutput = headerLine.replace("Last-Modified: ","");
                return finalOutput;
            }
        }
        header_.close();
        return "No Date";
    }
}

