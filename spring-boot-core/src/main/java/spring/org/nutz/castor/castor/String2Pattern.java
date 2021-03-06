package spring.org.nutz.castor.castor;

import java.util.regex.Pattern;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.lang.Lang;

public class String2Pattern extends Castor<String, Pattern> {

    @Override
    public Pattern cast(String src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        try {
            return Pattern.compile(src);
        }
        catch (Exception e) {
            throw new FailToCastObjectException("Error regex: " + src, Lang.unwrapThrow(e));
        }
    }

}
