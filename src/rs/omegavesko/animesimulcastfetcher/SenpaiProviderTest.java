package rs.omegavesko.animesimulcastfetcher;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SenpaiProviderTest
{
    @Test
    public void testGetItems() throws Exception
    {
        SenpaiProvider senpaiProvider = new SenpaiProvider();
        List<ScheduleItem> items = senpaiProvider.getItems();

        assertNotNull(items);
        assertTrue(items.size() > 0);

        for(ScheduleItem item : items)
        {
            assertNotNull(item.weekday);
            assertNotNull(item.seriesId);
            assertNotNull(item.seriesName);
            assertNotNull(item.time);

            System.out.println(item.toString());
        }
    }
}