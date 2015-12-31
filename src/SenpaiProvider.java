import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SenpaiProvider extends PageDownloader implements ScheduleProvider
{
    String URL = "http://www.senpai.moe/?sort=name&zone=Europe/London&mode=table&air=stream";

    private List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();

    @Override
    public List<ScheduleItem> getItems()
    {
        Document page = this.downloadDocument(this.URL);

        Elements itemRows = page.select("table#series > tbody > tr.series_instance");

        for (Element item : itemRows)
        {
            String name = item.select("span.seriesTitle").text().trim();
            String id = item.attr("data-malid");

            String weekday = item.select("td.weekday").text().trim();
            DayOfWeek dayOfWeek = this.dayAbbrToEnum(weekday);

            String timeString = item.select("td.time").text().trim();

            LocalTime time;

            try
            {
                time = LocalTime.of(
                        Integer.parseInt(timeString.split(":")[0]),
                        Integer.parseInt(timeString.split(":")[1])
                );
            }
            catch (NumberFormatException e)
            {
                break;
            }

            this.scheduleItems.add(new ScheduleItem(dayOfWeek, name, id, time));
        }

        return this.scheduleItems;
    }

    @Override
    Document downloadDocument(String url)
    {
        try
        {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36")
                    .cookie("user_timezone_triedget", "1")
                    .get();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    DayOfWeek dayAbbrToEnum(String abbr)
    {
        if (abbr.toLowerCase().equals("mon"))
        {
            return DayOfWeek.MONDAY;
        }
        else if (abbr.toLowerCase().equals("tue"))
        {
            return DayOfWeek.TUESDAY;
        }
        else if (abbr.toLowerCase().equals("wed"))
        {
            return DayOfWeek.WEDNESDAY;
        }
        else if (abbr.toLowerCase().equals("thu"))
        {
            return DayOfWeek.THURSDAY;
        }
        else if (abbr.toLowerCase().equals("fri"))
        {
            return DayOfWeek.FRIDAY;
        }
        else if (abbr.toLowerCase().equals("sat"))
        {
            return DayOfWeek.SATURDAY;
        }
        else if (abbr.toLowerCase().equals("sun"))
        {
            return DayOfWeek.SUNDAY;
        }
        else
        {
            return null;
        }
    }
}
