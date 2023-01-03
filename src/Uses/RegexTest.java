package Uses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("(\\d*)(\\w*)");
        String s = "123asdf123";
        Matcher m = p.matcher(s);
        System.out.println(m.matches());
        System.out.println(m.group(0));
        System.out.println(m.group(1));
        System.out.println(s.matches(".*"));
    }
}
