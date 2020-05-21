package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.lang.Lang;
import spring.org.nutz.lang.Mirror;

public class Boolean2Number extends Castor<Boolean, Number> {

    @Override
    public Number cast(Boolean src, Class<?> toType, String... args) {
        try {
            return (Number) Mirror    .me(toType)
                                    .getWrapperClass()
                                    .getConstructor(String.class)
                                    .newInstance(src ? "1" : "0");
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
    }

}
