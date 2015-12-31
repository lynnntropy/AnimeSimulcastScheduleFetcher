import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CrunchyrollProviderTest
{
    @Test
    public void testGetItems() throws Exception
    {
        CrunchyrollProvider crunchyrollProvider = new CrunchyrollProvider();
        List<ScheduleItem> items = crunchyrollProvider.getItems();

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