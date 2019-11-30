package com.company.exercises.circularbuffer;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertThrows;

public class TestCircularBuffer {

    private CircularBuffer<String> circularBuffer;

    @Before
    public void create() {
        circularBuffer = new DefaultCircularBuffer<>(10);
        circularBuffer.addAll(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "i", "k", "l"));
    }

    @Test
    public void testGet() {
        String expected = "a";
        String actual = circularBuffer.get();
        assertSame(expected, actual);
    }

    @Test
    public void testGetThrowException() {
        CircularBuffer<String> clearCircularBuffer = new DefaultCircularBuffer<>(10);
        assertThrows(RuntimeException.class, clearCircularBuffer::get);
    }

    @Test
    public void testPutThrowException() {
        assertThrows(RuntimeException.class, () -> {
            circularBuffer.put("n");
        });
    }

    @Test
    public void testPut() {
        circularBuffer.get();
        circularBuffer.put("n");
    }

    @Test
    public void testFIFO() {
        circularBuffer.get(); //"a"
        circularBuffer.get(); //"b"
        circularBuffer.get(); //"c"
        circularBuffer.put("n");
        circularBuffer.put("o");
        circularBuffer.get(); //"d"
        circularBuffer.get(); //"e"
        circularBuffer.get(); //"f"
        circularBuffer.put("p");
        circularBuffer.put("r");

        assertSame("g", circularBuffer.get());
        assertSame("i", circularBuffer.get());
        assertSame("k", circularBuffer.get());
        assertSame("l", circularBuffer.get());
        assertSame("n", circularBuffer.get());
        assertSame("o", circularBuffer.get());
        assertSame("p", circularBuffer.get());
        assertSame("r", circularBuffer.get());
    }

    @Test
    public void testAsList() {
        circularBuffer.get(); //"a"
        circularBuffer.get(); //"b"
        circularBuffer.get(); //"c"
        circularBuffer.put("n");
        circularBuffer.put("o");
        circularBuffer.get(); //"d"
        circularBuffer.get(); //"e"
        circularBuffer.get(); //"f"
        circularBuffer.put("p");
        circularBuffer.put("r");
        List<String> actualList = circularBuffer.asList();
        List<String> expectedList = Arrays.asList("g", "i", "k", "l", "n", "o", "p", "r");
        //assertSame(expectedList, actualList);
    }

}
