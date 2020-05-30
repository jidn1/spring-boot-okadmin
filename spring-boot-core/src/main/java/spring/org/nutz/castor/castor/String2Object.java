package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.json.Json;
import spring.org.nutz.lang.Mirror;
import spring.org.nutz.lang.Strings;

public class String2Object extends Castor<String, Object> {

    @Override
    public Object cast(String src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        if (Strings.isQuoteByIgnoreBlank(src, '{', '}'))
            return Json.fromJson(toType, src);
        return Mirror.me(toType).born(src);
    }

}
