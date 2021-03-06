package spring.org.nutz.castor.castor;

import java.sql.Timestamp;
import spring.org.nutz.lang.Strings;

public class String2Timestamp extends DateTimeCastor<String, Timestamp> {

    @Override
    public Timestamp cast(String src, Class<?> toType, String... args) {
        if (Strings.isBlank(src))
            return null;

        return new Timestamp(toDate(src).getTime());

    }

}
