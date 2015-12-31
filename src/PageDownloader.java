import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageDownloader
{
    Document downloadDocument(String url)
    {
        try
        {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36")
//                    .referrer("http://www.funimation.com/")
                    .get();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
