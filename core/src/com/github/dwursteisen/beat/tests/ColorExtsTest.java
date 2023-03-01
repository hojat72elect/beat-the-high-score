package com.github.dwursteisen.beat.tests;

import com.badlogic.gdx.graphics.Color;

import org.junit.Assert;

import com.github.dwursteisen.beat.addons.core.ColorExtsKt;

import org.testng.annotations.Test;

public class ColorExtsTest {

    @Test
    public void toColor() {
        Color color = ColorExtsKt.toColor("#FFFFFF");
        Assert.assertEquals(color, Color.WHITE);
    }
}
