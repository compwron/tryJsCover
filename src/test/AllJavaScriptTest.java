package test;

import be.klak.junit.jasmine.JasmineSuite;
import com.olabini.jescov.junit.JasmineTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(JasmineTestRunner.class)
@JasmineSuite(sources = {
        "main/webapp/js/Player.js",
        "main/webapp/js/Song.js"
},
        sourcesRootDir = "src",
        jsRootDir = "src",
        specs = {
                "PlayerSpec.js"
        })
public class AllJavaScriptTest {
    @Test
    public void foo(){
        assertEquals(true, true);
    }
}
