package spring.org.nutz.castor.castor;

import java.sql.Date;
import java.sql.Timestamp;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;

public class SqlDate2Timestamp extends Castor<Date, Timestamp> {

    @Override
    public Timestamp cast(Date src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return new Timestamp(src.getTime());
    }

}
