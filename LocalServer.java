import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalServer {
    private static int   LPort   ;  // 监听端口
    private ServerSocket loSocket;  // 本地套接字
    public LocalServer() throws IOException {
        loSocket = new ServerSocket(LPort);
    }
    public void start()
    {
        try {
            System.out.println("run on " + LPort);
            while(true)
            {
                Socket ct = loSocket.accept();
                new HandleRequest(ct).start(); // 新建线程处理客户端请求
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        LPort = 8080;
        try {
            new LocalServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
