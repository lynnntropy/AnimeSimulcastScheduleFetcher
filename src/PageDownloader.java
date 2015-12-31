import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageDownloader
{
    Document downloadDocument(String url)
    {
        try
        {
            return Jsoup.connect(url).get();
        }
        catch (IOException e)
        {
            return null;
        }
    }
}
