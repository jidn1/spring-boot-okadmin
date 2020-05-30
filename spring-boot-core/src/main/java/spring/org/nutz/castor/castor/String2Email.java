package spring.org.nutz.castor.castor;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.lang.meta.Email;

public class String2Email extends Castor<String, Email> {

    @Override
    public Email cast(String src, Class<?> toType, String... args) {
        return new Email(src);
    }

}
