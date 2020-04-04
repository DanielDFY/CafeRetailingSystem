package fudan.se.lab5.util;

import fudan.se.lab5.constant.FileConstant;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogUtil {
    public final static Logger orderLogger = Logger.getLogger("orderLogger");
    public final static Logger sysLogger = Logger.getLogger("sysLogger");

    LogUtil(){
        PropertyConfigurator.configure(FileConstant.log4Config);
    }
}
