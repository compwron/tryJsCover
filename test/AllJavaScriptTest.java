
//import be.klak.junit.jasmine.JasmineSuite;
//import com.olabini.jescov.junit.JasmineTestRunner;
//import org.junit.Test;
//import org.junit.runner.RunWith;

import be.klak.junit.jasmine.JasmineSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(com.olabini.jescov.junit.JasmineTestRunner.class)
@JasmineSuite(sources = {
        "test/js/Player.js"
},
        sourcesRootDir = "src",
        jsRootDir = "test/js",
        specs = {
                "PlayerSpec.js"
        })
public class AllJavaScriptTest {
    @Test
    public void foo(){
        assertEquals(true, true);
    }
}
