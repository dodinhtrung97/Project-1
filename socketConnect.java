import java.io.*;
import java.net.Socket;

public class socketConnect{
    public void downloadFile(String host, String path, int port, String filename, int headCounter){

        String fileType = filename.substring(filename.lastIndexOf("."), filename.length());
        String fileName = filename.replace(fileType,"");
        downloadMethod method = new downloadMethod();
        InputStream input = null;
        BufferedWriter writer = null;

        try{
            Socket socket = new Socket(host,port); //Create new socket//catches ioe
            PrintWriter request = new PrintWriter(socket.getOutputStream()); //Create object request
            request.print(method.mkDownloadRequest(host,path));//download normally
            request.flush(); //Send request as string

            input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            writer = new BufferedWriter(new FileWriter(fileName+"incomp"+fileType));//incomplete output file incase of disconnection

            String data;
            int counter = 0;

            while ((data=reader.readLine()) != null){
                if (counter>headCounter) {
                    writer.append(data);
                    writer.newLine();
                } else {
                    counter += 1;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if (input != null) {//closing connection when finished//closing writer
                    input.close();
                    writer.close();
                    File finalOutput = new File(fileName+"incomp"+fileType);
                    finalOutput.renameTo(new File(fileName+fileType));//rename to user input name if things go well ie no disconnection
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void downloadHeader(String host, String path, int port){

        downloadMethod method = new downloadMethod();
        InputStream input = null;
        BufferedWriter writer = null;

        try{
            Socket socket = new Socket(host,port); //Create new socket//catches ioe
            PrintWriter request = new PrintWriter(socket.getOutputStream()); //Create object request
            request.print(method.mkHeadDownloadRequest(host,path));//download header
            request.flush(); //Send request as string

            input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            if (new File("header.txt").isFile()) {
                writer = new BufferedWriter(new FileWriter("newheader.txt"));
            } else {
                writer = new BufferedWriter(new FileWriter("header.txt"));
            }
            String data;
            while ((data=reader.readLine()) != null){
                writer.append(data);//write line if there is remaining data
                writer.newLine();//new line after every writen line
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if (input != null) {//closing connection when finished//closing writer
                    input.close();
                    writer.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void resumableDownload(String host, String path, int port, String date, long fileSize, String filename, int headCounter){

        downloadMethod method = new downloadMethod();
        InputStream input = null;
        BufferedWriter writer = null;
        String fileType = filename.substring(filename.lastIndexOf("."), filename.length());
        String fileName = filename.replace(fileType,"");

        try{
            Socket socket = new Socket(host,port); //Create new socket//catches ioe
            PrintWriter request = new PrintWriter(socket.getOutputStream()); //Create object request
            request.print(method.continueDownloadRequest(host,path,date,fileSize));
            request.flush(); //Send request as string

            input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new BufferedWriter(new FileWriter(fileName+"incomp"+fileType,true));//incomplete until connection is closed

            String data;

            int counter = 0;

            while ((data=reader.readLine()) != null){
                if (counter > headCounter) {
                    writer.append(data);
                    writer.newLine();
                } else {
                    counter += 1;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if (input != null) {//closing connection when finished//closing writer
                    input.close();
                    writer.close();
                    File finalOutput = new File(fileName+"incomp"+fileType);
                    finalOutput.renameTo(new File(fileName+fileType));//rename to user input name if things go well ie no disconnection
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void nonresumableDownload(String host, String path, int port, String date, String filename, int headCounter){

        System.err.println("File has been modified, start downloading anew!!");
        downloadMethod method = new downloadMethod();
        InputStream input = null;
        BufferedWriter writer = null;
        String fileType = filename.substring(filename.lastIndexOf("."), filename.length());
        String fileName = filename.replace(fileType,"");

        try{
            Socket socket = new Socket(host,port); //Create new socket//catches ioe
            PrintWriter request = new PrintWriter(socket.getOutputStream()); //Create object request
            request.print(method.mkNewDownloadRequest(host,path,date));
            request.flush(); //Send request as string

            input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            writer = new BufferedWriter(new FileWriter(fileName+"incomp"+fileType));

            String data;
            int counter = 0;
            while ((data=reader.readLine()) != null){
                if (counter > headCounter) {
                    writer.append(data);
                    writer.newLine();
                } else {
                    counter+=1;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if (input != null) {//closing connection when finished//closing writer
                    input.close();
                    writer.close();
                    File finalOutput = new File(fileName+"incomp"+fileType);
                    finalOutput.renameTo(new File(fileName+fileType));//rename to user input name if things go well ie no disconnection
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}