package fudan.se.lab5;

import fudan.se.lab5.util.LogUtil;
import org.junit.Test;

public class TestLog {
    @Test
    public void testLog() {
        LogUtil.orderLogger.info("orderLogger test");
        LogUtil.sysLogger.error("sysLogger test");
    }
}
