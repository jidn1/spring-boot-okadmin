package spring.org.nutz.castor.castor;

import java.util.Collection;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.json.Json;
import spring.org.nutz.json.JsonFormat;

@SuppressWarnings({"rawtypes"})
public class Collection2String extends Castor<Collection, String> {

    @Override
    public String cast(Collection src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return Json.toJson(src, JsonFormat.compact());
    }

}
