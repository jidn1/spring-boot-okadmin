package spring.org.nutz.castor.castor;

import java.util.Calendar;
import java.util.Date;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;

public class Datetime2Calendar extends Castor<Date, Calendar> {

    @Override
    public Calendar cast(Date src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        Calendar c = Calendar.getInstance();
        c.setTime(src);
        return c;
    }

}
