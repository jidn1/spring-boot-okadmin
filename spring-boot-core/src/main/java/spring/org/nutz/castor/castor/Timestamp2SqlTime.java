package spring.org.nutz.castor.castor;

import java.sql.Time;
import java.sql.Timestamp;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;

public class Timestamp2SqlTime extends Castor<Timestamp, Time> {

    @Override
    public Time cast(Timestamp src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return new Time(src.getTime());
    }

}
