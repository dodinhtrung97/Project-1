import java.io.*;
import java.net.Socket;

public class socketConnect{
    public void downloadFile(String host, String path, int port, String filename) throws IOException {

        String fileType = filename.substring(filename.lastIndexOf("."), filename.length());
        String fileName = filename.replace(fileType, "");
        downloadMethod method = new downloadMethod();
        FileOutputStream writer = null;
        String test;
        InputStream input = null;

        try {
            Socket socket = new Socket(host, port); //Create new socket//catches ioe
            PrintWriter request = new PrintWriter(socket.getOutputStream()); //Create object request
            input = socket.getInputStream();
            request.print(method.mkDownloadRequest(host, path));//download normally
            request.flush(); //Send request as string

            writer = new FileOutputStream(fileName + "incomp" + fileType);//incomplete output file incase of disconnection
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] bytes = new byte[1024];
            int byteCount;
            int lenTest;
            boolean notHead = false;

            while ((byteCount = input.read(bytes)) != -1) {
                if (notHead==false) {
                    out.write(bytes, 0, byteCount);
                    byte[] response = out.toByteArray();
                    test = new String(response);
                    if (test.contains("\r\n\r\n")) {
                        String[] all = test.split("\r\n\r\n", 2);
                        lenTest = all[0].length();
                        writer.write(response, lenTest, byteCount - lenTest);
                        notHead = true;
                    }
                } else {
                        writer.write(bytes, 0, byteCount);
                }
            }
        } finally {
            if (input != null) {//closing connection when finished//closing writer
                input.close();
                writer.close();
                File finalOutput = new File(fileName + "incomp" + fileType);
                finalOutput.renameTo(new File(fileName + fileType));//rename to user input name if things go well ie no disconnection
                }
        }
    }


    public void downloadHeader(String host, String path, int port) throws IOException{

        downloadMethod method = new downloadMethod();
        FileOutputStream writer = null;
        InputStream input = null;

        try{
            Socket socket = new Socket(host, port); //Create new socket//catches ioe
            PrintWriter request = new PrintWriter(socket.getOutputStream()); //Create object request
            input = socket.getInputStream();
            request.print(method.mkHeadDownloadRequest(host,path));//download header
            request.flush(); //Send request as string
            long startTime = System.currentTimeMillis();
            long endTime = startTime + 4*1000;

            if (new File("header.txt").isFile()) {
                writer = new FileOutputStream("newheader.txt");
            } else if (new File("newheader.txt").isFile()) {
                File header = new File("newheader.txt");
                header.renameTo(new File("header.txt"));
                writer = new FileOutputStream("newheader.txt");
            } else {
                writer = new FileOutputStream("header.txt");
            }
            byte[] bytes = new byte[1024];
            int byteCount;

            while ((byteCount = input.read(bytes)) != -1) {
                if (System.currentTimeMillis() >= endTime) {
                    break;
                } else {
                    writer.write(bytes, 0, byteCount);//write line if there is remaining data
                }
            }
        } finally {
            if (input != null) {//closing connection when finished//closing writer
                input.close();
                writer.close();
                System.out.println("tes");
            }
        }
    }

    public void resumableDownload(String host, String path, int port, String date, long fileSize, String filename, int headCounter) throws IOException{

        downloadMethod method = new downloadMethod();
        InputStream input = null;
        FileOutputStream writer = null;
        String fileType = filename.substring(filename.lastIndexOf("."), filename.length());
        String fileName = filename.replace(fileType,"");
        String test;

        try{
            Socket socket = new Socket(host,port); //Create new socket//catches ioe
            PrintWriter request = new PrintWriter(socket.getOutputStream()); //Create object request
            request.print(method.continueDownloadRequest(host,path,date,fileSize));
            request.flush(); //Send request as string

            input = socket.getInputStream();
            writer = new FileOutputStream(fileName+"incomp"+fileType,true);//incomplete until connection is closed
            ByteArrayOutputStream out = new ByteArrayOutputStream();


            byte[] bytes = new byte[1024];
            int byteCount;
            int lenTest;
            boolean notHead = false;

            while ((byteCount = input.read(bytes)) != -1) {
                if (notHead==false) {
                    out.write(bytes, 0, byteCount);
                    byte[] response = out.toByteArray();
                    test = new String(response);
                    if (test.contains("\r\n\r\n")) {
                        String[] all = test.split("\r\n\r\n", 2);
                        lenTest = all[0].length();
                        writer.write(response, lenTest, byteCount - lenTest);
                        notHead = true;
                    }
                } else {
                    writer.write(bytes, 0, byteCount);
                }
            }
        } finally {
            if (input != null) {//closing connection when finished//closing writer
                input.close();
                writer.close();
                File finalOutput = new File(fileName+"incomp"+fileType);
                finalOutput.renameTo(new File(fileName+fileType));//rename to user input name if things go well ie no disconnection
            }
        }
    }
}