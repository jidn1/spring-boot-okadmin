package spring.org.nutz.castor.castor;

import java.io.File;

import spring.org.nutz.castor.Castor;
import spring.org.nutz.castor.FailToCastObjectException;
import spring.org.nutz.lang.Files;

public class String2File extends Castor<String, File> {

    @Override
    public File cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
        return Files.findFile(src);
    }

}
