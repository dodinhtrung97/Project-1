import java.io.*;
import java.net.Socket;
import java.net.URL;

public class main {
    public static void main(String[] args) throws IOException {
            String filename = "cat.txt";
            String url = "http://stackoverflow.com/questions/8654141/convert-byte-to-string-in-java";

            Miscellanous mis = new Miscellanous();
            extractAll extract = new extractAll();
            socketConnect sk = new socketConnect();

            String host = extract.getAll(url)[0];
            String path = extract.getAll(url)[1];
            int port = extract.getPort(url);

            String fileType = filename.substring(filename.lastIndexOf("."), filename.length());
            String fileName = filename.replace(fileType, "");

            sk.downloadHeader(host, path, port);
            if (new File(fileName + "incomp" + fileType).isFile()) {
                String date = mis.findLastModified("header.txt");
                String newDate = mis.findLastModified("newheader.txt");
                if (date.equals(newDate)) {
                    long oldSize = mis.getFileSize(fileName + "incomp" + fileType);
                    int headerCount = mis.excludeHeader("newheader.txt");
                    sk.resumableDownload(host, path, port, date, oldSize, filename, headerCount);
                } else {
                    System.err.println("File has been modified, start downloading anew!!");
                    sk.downloadFile(host, path, port, filename);
                }
            } else {
                //int contentLength = mis.getContentLength(url);
                //int headerCount = mis.excludeHeader("header.txt");
                //System.out.println(headerCount);
                //long headerSize = mis.getFileSize("header.txt");
                sk.downloadFile(host, path, port, filename);
            }
        /*extractAll data = new extractAll();
        socketConnect sk = new socketConnect();
        Miscellanous mis = new Miscellanous();
        String[] allData = data.getAll("https://pbs.twimg.com/profile_images/2370446440/6e2jwf7ztbr5t1yjq4c5.jpeg");
        int port = data.getPort("https://pbs.twimg.com/profile_images/2370446440/6e2jwf7ztbr5t1yjq4c5.jpeg");
        String date = mis.findLastModified("header.txt");
        long size = mis.getContentLength("https://pbs.twimg.com/profile_images/2370446440/6e2jwf7ztbr5t1yjq4c5.jpeg");
        long a = mis.getFileSize("outputincomp.txt");

        //System.out.println(date);
        //sk.resumableDownload(allData[0],allData[1],port,date,a,size,"output.txt");
        sk.downloadFile(allData[0],allData[1],port, "output.txt",headerCount);
        //sk.nonresumableDownload(allData[0],allData[1],port,"Fri, 10 Jan 2016 07:37:59 GMT","output.txt");
        //sk.downloadHeader(allData[0],allData[1],port);

        //System.out.println(mis.findLastModified("header.txt"));
        //System.out.println(sk.getContentLength("http://www.mahidol.ac.th/en"));
        try {
            URL url = new URL("http://i.ytimg.com/vi/JNIxFZGqP-E/maxresdefault.jpg");
            InputStream in = new BufferedInputStream(url.openStream());
            OutputStream out = new BufferedOutputStream(new FileOutputStream("Image-Porkeri_001.jpg"));

            for (int i; (i = in.read()) != -1; ) {
                out.write(i);
            }
            in.close();
            out.close();
        }catch(IOException e){
            e.printStackTrace();*/

    }
}
