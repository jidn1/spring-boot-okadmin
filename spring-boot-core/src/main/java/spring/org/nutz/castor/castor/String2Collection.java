package spring.org.nutz.castor.castor;

import java.util.Collection;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.json.Json;
import spring.org.nutz.lang.Lang;

@SuppressWarnings({"rawtypes"})
public class String2Collection extends Castor<String, Collection> {

    @Override
    public Collection cast(String src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return (Collection) Json.fromJson(toType, Lang.inr(src));
    }

}
