import java.io.*;

public class main {
    public static void main(String[] args){

        if(args.length!=2){
            System.err.println("Program only runs on two arguments!!");
        } else {
            String filename = args[0];
            String url = args[1];

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
                sk.downloadHeader(host, path, port);
                String date = mis.findLastModified("header.txt");
                String newDate = mis.findLastModified("newheader.txt");
                if (date.equals(newDate)) {
                    long oldSize = mis.getFileSize(fileName + "incomp" + fileType);
                    int headerCount = mis.excludeHeader("newheader.txt", filename, "resume");
                    sk.resumableDownload(host, path, port, date, oldSize, filename, headerCount);
                } else {
                    System.out.println(date);
                    System.out.println(newDate);
                    int headerCount = mis.excludeHeader("newheader.txt", filename, "new");
                    sk.nonresumableDownload(host, path, port, date, filename, headerCount);
                }
            } else {
                int headerCount = mis.excludeHeader("header.txt", filename, "new");
                sk.downloadFile(host, path, port, filename, headerCount);
            }
        }
    }
}
