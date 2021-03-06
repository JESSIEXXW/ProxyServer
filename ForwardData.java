import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class ForwardData extends Thread {
    private List<String> buff;
    private Socket source;
    private Socket destination;

    public ForwardData(List<String> buff, Socket sou, Socket des) {
        this.buff = buff;
        this.source = sou;
        this.destination = des;
    }

    @Override
    public void run() {
        try {
            OutputStream outStream = destination.getOutputStream();
            InputStream inStream  = source.getInputStream();

            if(buff != null && buff.size() > 0){
                for(String str : buff)
                {
                    outStream.write((str + "\r\n").getBytes());
                    outStream.flush();
                }
                outStream.write("\r\n".getBytes());
                outStream.flush();
            }

            byte[] bs = new byte[4096];
            int len;
            while ((len = inStream.read(bs)) != -1) {
                outStream.write(bs, 0, len);
                outStream.flush();
            }

            outStream.close();
            inStream.close();
        } catch (IOException ie) {
            System.err.println("http 数据转发异常 " + ie);
        }
    }
}
