package spring.org.nutz.castor.castor;

import java.util.Set;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.json.Json;

@SuppressWarnings("rawtypes")
public class String2Set extends Castor<String, Set> {

    @Override
    public Set cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
        return Json.fromJson(Set.class, src);
    }

}
