package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;

public class Number2String extends Castor<Number, String> {

    @Override
    public String cast(Number src, Class<?> toType, String... args) {
        return src.toString();
    }

}
