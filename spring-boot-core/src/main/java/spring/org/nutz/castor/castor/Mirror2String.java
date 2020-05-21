package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.lang.Mirror;

@SuppressWarnings({"rawtypes"})
public class Mirror2String extends Castor<Mirror, String> {

    @Override
    public String cast(Mirror src, Class<?> toType, String... args) {
        return src.getType().getName();
    }

}
