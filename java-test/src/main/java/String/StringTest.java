package String;

import org.testng.annotations.Test;

public class StringTest {
    @Test
    public void testSubString(){
        String test = "01234";

        System.out.println(test.substring(1,2));
    }
}
