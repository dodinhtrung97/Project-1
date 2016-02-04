import java.io.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {
            String filename = args[0];
            String url = args[1];

            Miscellanous mis = new Miscellanous();
            extractAll extract = new extractAll();
            socketConnect sk = new socketConnect();
            Scanner scanner = new Scanner(System.in);

            if (new File(filename).exists()){
                    System.err.println("This file already exist, would you like to rename (y/n)");
                    String input = scanner.nextLine();
                    if (input.toLowerCase().equals("y")){
                        System.err.println("Please enter a new file name with file type extension");
                        String newName = scanner.nextLine();
                        filename = newName;
                    } else {
                        System.err.println("Would you like to delete the old file (y/n)");
                        String answer = scanner.nextLine();
                        if (answer.toLowerCase().equals("y")){
                            File oldFile = new File(filename);
                            oldFile.delete();
                        }
                    }
            }


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
                    sk.resumableDownload(host, path, port, date, oldSize, filename);
                } else {
                    System.err.println("File has been modified, start downloading anew!!\n" + "Would you like to rewrite this file (y/n)");
                    String answer = scanner.nextLine();
                    if (answer.toLowerCase().equals("y")){
                        sk.downloadFile(host, path, port, filename);
                    } else {
                        System.err.println("Terminate download, exiting!");
                        File header = new File("newheader.txt");
                        if (!header.delete()){
                            System.out.println("failed");
                        } else {
                            System.out.println("succeed");
                        }
                    }
                }
            } else {
                sk.downloadFile(host, path, port, filename);
            }
    }
}
