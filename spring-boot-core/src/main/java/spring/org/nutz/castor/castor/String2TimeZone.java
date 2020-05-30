package spring.org.nutz.castor.castor;

import java.util.TimeZone;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.lang.Strings;

public class String2TimeZone extends Castor<String, TimeZone> {

    @Override
    public TimeZone cast(String src, Class<?> toType, String... args) {
        if (Strings.isBlank(src))
            return null;
        return TimeZone.getTimeZone(src);
    }

}
