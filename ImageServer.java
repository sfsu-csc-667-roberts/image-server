import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageServer {
  private static final int PORT = 8080;
  public static void main(String[] args) {
    try {
    (new ImageServer()).start();
    } catch(Exception e) {
      System.err.println("Could not start server on port " + PORT);
    }
  }

  private ServerSocket socket;

  public ImageServer() throws IOException {
    socket = new ServerSocket(PORT);
  }

  public void start() {
    Socket client;

    while(true) {
      try {
        client = socket.accept();

        readRequest(client);
        sendResponse(client);
      } catch(IOException e) {
        System.err.println("IOException on socket.accept()");
        e.printStackTrace();
      }
    }
  }

  private void readRequest(Socket client) throws IOException {
    BufferedReader reader = new BufferedReader(
      new InputStreamReader(client.getInputStream())
    );

    String line = null;

    System.out.println("--- Request ---");
    while((line = reader.readLine()) != null && line.trim().length() != 0) {
      System.out.println("> " + line);
    }
  }

  private void sendResponse(Socket client) throws IOException {
    File file = new File("./Kenzie.png");

    OutputStream out = client.getOutputStream();
    PrintWriter printWriter = new PrintWriter(out);

    printWriter.write("HTTP/1.1 200 OK\r\n");
    printWriter.write("Content-Type: image/png\r\n");
    printWriter.write("Content-Length: " + file.length() + "\r\n");
    printWriter.write("\r\n");
    printWriter.flush();

    byte[] fileBytes = Files.readAllBytes(Paths.get("./Kenzie.png"));
    out.write(fileBytes);
    out.flush();
  }
}