import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("in.txt");
        Graph graph = new Graph(file);
        System.out.println();
    }
}
