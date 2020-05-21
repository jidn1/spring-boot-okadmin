package spring.org.nutz.castor.castor;

import java.util.Date;

import spring.org.nutz.castor.Castor;

public class Number2Datetime extends Castor<Number, Date> {

    @Override
    public Date cast(Number src, Class<?> toType, String... args) {
        return new Date(src.longValue());
    }

}
