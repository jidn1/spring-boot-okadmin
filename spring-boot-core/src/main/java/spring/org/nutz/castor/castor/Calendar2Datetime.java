package spring.org.nutz.castor.castor;

import java.util.Calendar;
import java.util.Date;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;

public class Calendar2Datetime extends Castor<Calendar, Date> {

    @Override
    public Date cast(Calendar src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return src.getTime();
    }

}
