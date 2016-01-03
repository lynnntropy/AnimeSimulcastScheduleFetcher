package rs.omegavesko.animesimulcastfetcher;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CrunchyrollProvider extends PageDownloader implements ScheduleProvider
{
    String URL = "http://www.crunchyroll.com/simulcastcalendar";

    private List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();

    private static List<ScheduleItem> cachedSchedule = null;
    private static LocalTime cacheTime = null;

    @Override
    public List<ScheduleItem> getItems()
    {
        if (cachedSchedule != null &&
            ChronoUnit.MINUTES.between(cacheTime, LocalTime.now()) < 60)
        {
            return cachedSchedule;
        }
        else
        {
            Document downloadedPage = super.downloadDocument(this.URL);

            Elements weekdayTables = downloadedPage.select("li.day");

            int i = 1;
            for (Element table : weekdayTables)
            {
                Elements entries = table.select("ol.releases > li");

                for (Element entry : entries)
                {
                    String name = entry.select("a.js-season-name-link > cite").text();
                    String id = entry.select("article.release").attr("data-slug");

                    String timeString = entry.select("time.available-time").text();
                    timeString = timeString.substring(0, timeString.length() - 2);

                    int hours = Integer.parseInt(timeString.split(":")[0]);
                    int minutes = Integer.parseInt(timeString.split(":")[1]);

                    if (!entry.select("time.available-time").text().contains("12:") &&
                            entry.select("time.available-time").text().contains("pm"))
                    {
                        hours += 12;
                    }

                    this.scheduleItems.add(
                            new ScheduleItem(DayOfWeek.of(i), name, id, LocalTime.of(hours, minutes))
                    );
                }

                i++;
            }

            cachedSchedule = this.scheduleItems;
            cacheTime = LocalTime.now();

            return this.scheduleItems;
        }
    }
}
