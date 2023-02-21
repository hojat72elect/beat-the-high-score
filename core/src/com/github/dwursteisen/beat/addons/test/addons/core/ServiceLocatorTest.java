package com.github.dwursteisen.beat.addons.test.addons.core;

import com.github.dwursteisen.beat.addons.core.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class ServiceLocatorTest {

    @Test
    public void register() {
        Random random = new Random();

        ServiceLocator.INSTANCE.register(random, Random.class);
        Random fromServiceLocator = ServiceLocator.INSTANCE.get(Random.class);

        Assert.assertSame(random, fromServiceLocator);

    }

}
