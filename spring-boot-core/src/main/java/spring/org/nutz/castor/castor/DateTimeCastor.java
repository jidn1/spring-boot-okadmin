package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.lang.Times;

public abstract class DateTimeCastor<FROM, TO> extends Castor<FROM, TO> {

    protected java.util.Date toDate(String src) {
        try {
            return Times.D(src);
        }
        catch (Throwable e) {
            throw new FailToCastObjectException(e,
                                                "'%s' to %s",
                                                src,
                                                java.util.Date.class.getName());
        }
    }

}
