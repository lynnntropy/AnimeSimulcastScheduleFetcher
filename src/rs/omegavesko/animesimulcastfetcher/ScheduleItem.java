package rs.omegavesko.animesimulcastfetcher;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ScheduleItem
{
    public DayOfWeek weekday;
    public String seriesName;
    public String seriesId;

    public LocalTime time;

    public ScheduleItem(DayOfWeek weekday, String seriesName, String seriesId, LocalTime time)
    {
        this.weekday = weekday;
        this.seriesName = seriesName;
        this.seriesId = seriesId;
        this.time = time;
    }

    @Override
    public String toString()
    {
        return String.format("%s | %d:%d | %s | %s", weekday, time.getHour(), time.getMinute(), this.seriesId, this.seriesName);
    }
}
