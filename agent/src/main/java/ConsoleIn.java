import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ConsoleIn implements Runnable {

    private final Reader reader;

    @Override
    public void run() {
        BufferedReader bfReader = new BufferedReader(reader);
        while (true){
            if(Thread.interrupted()){
                return;
            }
            try {
                System.out.println(bfReader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ConsoleIn(Reader reader) {
        this.reader = reader;
    }
}
