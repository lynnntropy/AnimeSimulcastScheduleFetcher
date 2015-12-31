import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FunimationProviderTest
{

    @Test
    public void testGetItems() throws Exception
    {
        FunimationProvider funimationProvider = new FunimationProvider();
        List<ScheduleItem> items = funimationProvider.getItems();

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