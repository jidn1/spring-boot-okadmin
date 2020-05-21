package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;

public class Number2Character extends Castor<Number, Character> {

    @Override
    public Character cast(Number src, Class<?> toType, String... args) {
        return (char) src.intValue();
    }

}
