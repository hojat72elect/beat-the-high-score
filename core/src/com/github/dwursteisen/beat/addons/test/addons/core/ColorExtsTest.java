package com.github.dwursteisen.beat.addons.test.addons.core;

import com.badlogic.gdx.graphics.Color;
import com.github.dwursteisen.beat.addons.core.ColorExtsKt;
import org.junit.Assert;
import org.junit.Test;

public class ColorExtsTest {

    @Test
    public void toColor() {
        Color color = ColorExtsKt.toColor("#FFFFFF");
        Assert.assertEquals(color, Color.WHITE);
    }
}
