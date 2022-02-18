package de.tomgrill.gdxtesting;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testCollegeAssetExists() {
        assertTrue("This test will only pass if COLLEGE.png exists",
                Gdx.files.internal("COLLEGE.png").exists());
    }
}
