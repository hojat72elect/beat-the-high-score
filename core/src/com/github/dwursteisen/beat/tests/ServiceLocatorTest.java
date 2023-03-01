package com.github.dwursteisen.beat.tests;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import com.github.dwursteisen.beat.addons.core.ServiceLocator;

public class ServiceLocatorTest {

    @Test
    public void register() {
        Random random = new Random();

        ServiceLocator.INSTANCE.register(random, java.util.Random.class);
        Random fromServiceLocator = ServiceLocator.INSTANCE.get(Random.class);

        Assert.assertSame(random, fromServiceLocator);

    }

}
