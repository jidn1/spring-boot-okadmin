package spring.org.nutz.castor.castor;

import spring.org.nutz.lang.Times;

public class SqlDate2String extends DateTimeCastor<java.sql.Date, String> {

    @Override
    public String cast(java.sql.Date src, Class<?> toType, String... args) {
        return Times.sD(src);
    }
}
