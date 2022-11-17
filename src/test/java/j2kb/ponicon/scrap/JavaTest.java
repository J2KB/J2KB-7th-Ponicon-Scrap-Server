package j2kb.ponicon.scrap;

import org.junit.jupiter.api.Test;

public class JavaTest {

    @Test
    public void checkLongValue(){
        Long id = 2L;

        if(id.equals(2L)){
            System.out.println("ture");
        }
        else {
            System.out.println("false");
        }
    }
}
