import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FunimationProvider  extends PageDownloader implements ScheduleProvider
{
    String URL = "http://www.funimation.com/videos/simulcasts";

    private List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();

    @Override
    public List<ScheduleItem> getItems()
    {
        Document downloadedPage = super.downloadDocument(this.URL);

//        Elements weekdayTables = downloadedPage.select("article.simulcast_schedule div.item-cell.two");
        Elements weekdayTables = downloadedPage.select("div.item-cell.two");

        int i = 1;
        for (Element table : weekdayTables)
        {
            Elements entries = table.select("div.left > p");

            for (Element entry : entries)
            {
                String name = entry.select("a.item-title").text();
                String id = entry.select("a.item-title").attr("href");

                String timeString = entry.select("span").text();
                String rawTime = timeString.split(" ")[0];
                boolean pm = timeString.split(" ")[1].equals("pm");

                int hours = Integer.parseInt(rawTime.split(":")[0]);
                int minutes = Integer.parseInt(rawTime.split(":")[1]);

                if (pm)
                {
                    hours += 12;
                }

                this.scheduleItems.add(
                        new ScheduleItem(DayOfWeek.of(i), name, id, LocalTime.of(hours, minutes))
                );
            }

            i++;
        }


        return this.scheduleItems;
    }
}
