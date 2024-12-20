import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class JsoupTest {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        String pageTitle = doc.title();
        System.out.println(pageTitle);
    }
}
