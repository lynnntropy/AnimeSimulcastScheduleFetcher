import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorribleSubsProvider extends PageDownloader implements ScheduleProvider
{
    String URL = "http://horriblesubs.info/release-schedule/";

    private List<Element> weekdayTables = new ArrayList<Element>();
    private List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();

    @Override
    public List<ScheduleItem> getItems()
    {
        Document downloadedPage = super.downloadDocument(this.URL);

        for (Element weekdayTable : downloadedPage.select("table.schedule-today-table"))
        {
            this.weekdayTables.add(weekdayTable);
        }

        // remove the 'to be scheduled' table
        this.weekdayTables.remove(weekdayTables.size() - 1);

        int i = 1;
        for (Element table : this.weekdayTables)
        {
            Elements items = table.select("tr.schedule-page-item");

            for (Element item : items)
            {
                String id = item.select("a[href]").attr("href");
                String  title = item.select("a[href]").text();

                String rawTimeString = item.select("td.schedule-time").text();
                int pstHours = Integer.parseInt(rawTimeString.split(":")[0]);
                int pstMinutes = Integer.parseInt(rawTimeString.split(":")[1]);

                this.scheduleItems.add(
                        new ScheduleItem(DayOfWeek.of(i), title, id, LocalTime.of(pstHours, pstMinutes)));
            }

            i++;
        }

        return this.scheduleItems;
    }
}
