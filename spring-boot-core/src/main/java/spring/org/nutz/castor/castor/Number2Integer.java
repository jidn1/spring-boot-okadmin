package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;

public class Number2Integer extends Castor<Number, Integer> {

    @Override
    public Integer cast(Number src, Class<?> toType, String... args) {
        return src.intValue();
    }

}
