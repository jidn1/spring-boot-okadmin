package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;

public class Boolean2String extends Castor<Boolean, String> {

    @Override
    public String cast(Boolean src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return String.valueOf(src);
    }

}
