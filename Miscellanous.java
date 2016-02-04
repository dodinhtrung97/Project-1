import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Miscellanous {
    public long getFileSize(String file){
        File newFile = new File(file);
        return newFile.length(); //File size
    }

    public int getContentLength(String url){
        try{
            URL newUrl = new URL(url);
            URLConnection con = newUrl.openConnection();
            return con.getContentLength(); //get content length
        } catch (MalformedURLException e){ //url exception
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int excludeHeader(String header){
        File headerFile = new File(header);
        BufferedReader header_;
        int counter = 0;

        try {
            header_ = new BufferedReader(new FileReader(headerFile));//buffer header file for later read

            while (header_.readLine() != null){
                counter += 1;//count number of header lines
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } return counter;
    }

    public String findLastModified(String header){
        File headerFile = new File(header);
        BufferedReader header_;
        String headerLine;
        try{
            header_ = new BufferedReader(new FileReader(headerFile));

            while((headerLine=header_.readLine())!=null){
                if (headerLine.toLowerCase().contains("Last-Modified".toLowerCase())){
                    String finalOutput = headerLine.replace("Last-Modified: ","");
                    return finalOutput;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return "No Date";
    }
}

