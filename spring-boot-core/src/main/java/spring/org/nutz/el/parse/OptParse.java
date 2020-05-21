package spring.org.nutz.el.parse;

import spring.org.nutz.el.ElException;
import spring.org.nutz.el.Parse;
import spring.org.nutz.el.opt.arithmetic.LBracketOpt;
import spring.org.nutz.el.opt.arithmetic.ModOpt;
import spring.org.nutz.el.opt.arithmetic.MulOpt;
import spring.org.nutz.el.opt.arithmetic.PlusOpt;
import spring.org.nutz.el.opt.arithmetic.RBracketOpt;
import spring.org.nutz.el.opt.arithmetic.SubOpt;
import spring.org.nutz.el.opt.bit.BitNot;
import spring.org.nutz.el.opt.bit.BitOr;
import spring.org.nutz.el.opt.bit.BitXro;
import spring.org.nutz.el.opt.bit.LeftShift;
import spring.org.nutz.el.opt.bit.RightShift;
import spring.org.nutz.el.opt.bit.UnsignedLeftShift;
import spring.org.nutz.el.opt.logic.AndOpt;
import spring.org.nutz.el.opt.logic.GTEOpt;
import spring.org.nutz.el.opt.logic.GTOpt;
import spring.org.nutz.el.opt.logic.LTEOpt;
import spring.org.nutz.el.opt.logic.LTOpt;
import spring.org.nutz.el.opt.logic.NEQOpt;
import spring.org.nutz.el.opt.logic.NotOpt;
import spring.org.nutz.el.opt.logic.OrOpt;
import spring.org.nutz.el.opt.logic.QuestionOpt;
import spring.org.nutz.el.opt.logic.QuestionSelectOpt;
import spring.org.nutz.el.opt.object.ArrayOpt;
import spring.org.nutz.el.opt.object.CommaOpt;
import spring.org.nutz.el.opt.object.FetchArrayOpt;
import spring.org.nutz.el.opt.arithmetic.DivOpt;
import spring.org.nutz.el.opt.bit.BitAnd;
import spring.org.nutz.el.opt.logic.EQOpt;
import spring.org.nutz.el.opt.object.AccessOpt;

/**
 * 操作符转换器
 * @author juqkai(juqkai@gmail.com)
 *
 */
public class OptParse implements Parse {

    public Object fetchItem(CharQueue exp){
        switch(exp.peek()){
        case '+':
            exp.poll();
            return new PlusOpt();
        case '-':
            exp.poll();
            return new SubOpt();
        case '*':
            exp.poll();
            return new MulOpt();
        case '/':
            exp.poll();
            return new DivOpt();
        case '%':
            exp.poll();
            return new ModOpt();
        case '(':
            exp.poll();
            return new LBracketOpt();
        case ')':
            exp.poll();
            return new RBracketOpt();
        case '>':
            exp.poll();
            switch(exp.peek()){
            case '=':
                exp.poll();
                return new GTEOpt();
            case '>':
                exp.poll();
                if(exp.peek() == '>'){
                    exp.poll();
                    return new UnsignedLeftShift();
                }
                return new RightShift();
            }
            return new GTOpt();
        case '<':
            exp.poll();
            switch(exp.peek()){
            case '=':
                exp.poll();
                return new LTEOpt();
            case '<':
                exp.poll();
                return new LeftShift();
            }
            return new LTOpt();
        case '=':
            exp.poll();
            switch(exp.peek()){
            case '=':
                exp.poll();
                return new EQOpt();
            }
            throw new ElException("表达式错误,请检查'='后是否有非法字符!");
        case '!':
            exp.poll();
            switch(exp.peek()){
            case '=':
                exp.poll();
                return new NEQOpt();
            }
            return new NotOpt();
        case '|':
            exp.poll();
            switch(exp.peek()){
            case '|':
                exp.poll();
                return new OrOpt();
            }
            return new BitOr();
        case '&':
            exp.poll();
            switch(exp.peek()){
            case '&':
                exp.poll();
                return new AndOpt();
            }
            return new BitAnd();
        case '~':
            exp.poll();
            return new BitNot();
        case '^':
            exp.poll();
            return new BitXro();
        case '?':
            exp.poll();
            return new QuestionOpt();
        case ':':
            exp.poll();
            return new QuestionSelectOpt();

        case '.':
            if(!Character.isJavaIdentifierStart(exp.peek(1))){
                return nullobj;
            }
            exp.poll();
            return new AccessOpt();
        case ',':
            exp.poll();
            return new CommaOpt();
        case '[':
            exp.poll();
            return new Object[]{new ArrayOpt(),new LBracketOpt()};
        case ']':
            exp.poll();
            return new Object[]{new RBracketOpt(), new FetchArrayOpt()};
        }
        return nullobj;
    }

}
