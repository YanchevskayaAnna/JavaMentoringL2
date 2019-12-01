package com.company.exercises.circularbuffer;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.*;

public class TestDefaultCircularBuffer {

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
            circularBuffer.put("m");
        });
    }

    @Test
    public void testPut() {
        circularBuffer.get();
        circularBuffer.put("m");
    }

    @Test
    public void testAddAllThrowException() {
        assertThrows(RuntimeException.class, () -> {
            circularBuffer.addAll(Arrays.asList("m", "n", "o"));
        });

        CircularBuffer<String> clearCircularBuffer = new DefaultCircularBuffer<>(10);
        assertThrows(RuntimeException.class, () -> {
            clearCircularBuffer.addAll(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "i", "k", "l", "m", "n", "o"));
        });
    }

    @Test
    public void testAddAll() {
        circularBuffer = new DefaultCircularBuffer<>(10);
        List<String> expected = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "i", "k", "l");
        circularBuffer.addAll(expected);
        List<String> actual = circularBuffer.asList();
        assertEquals(expected, actual);

        circularBuffer = new DefaultCircularBuffer<>(10);
        circularBuffer.addAll(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "i"));
        circularBuffer.get(); // "a"
        circularBuffer.get(); // "b"
        circularBuffer.get(); // "c"
        expected = Arrays.asList("d", "e", "f", "g", "i");
        assertEquals(expected, circularBuffer.asList());
    }

    @Test
    public void testFIFO() {
        circularBuffer.get(); //"a"
        circularBuffer.get(); //"b"
        circularBuffer.get(); //"c"
        circularBuffer.put("m");
        circularBuffer.put("n");
        circularBuffer.get(); //"d"
        circularBuffer.get(); //"e"
        circularBuffer.get(); //"f"
        circularBuffer.put("o");
        circularBuffer.put("p");
        circularBuffer.put("r");

        assertSame("g", circularBuffer.get());
        assertSame("i", circularBuffer.get());
        assertSame("k", circularBuffer.get());
        assertSame("l", circularBuffer.get());
        assertSame("m", circularBuffer.get());
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
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testToArray() {
        circularBuffer.get(); //"a"
        circularBuffer.put("n");
        String[] expected = {"b", "c", "d", "e", "f", "g", "i", "k", "l", "n"};
        assertArrayEquals(expected, circularBuffer.toArray());
    }

    @Test
    public void testToObjectArray() {
        circularBuffer.get(); //"a"
        circularBuffer.put("n");
        Object[] expected = {"b", "c", "d", "e", "f", "g", "i", "k", "l", "n"};
        assertArrayEquals(expected, circularBuffer.toObjectArray());
    }

    @Test
    public void testSort() {
        circularBuffer = new DefaultCircularBuffer<>(10);
        circularBuffer.addAll(Arrays.asList("c", "b", "abcd", "abc", "e", "f", "g", "abcddefg", "k", ""));
        circularBuffer.get(); // "c"
        circularBuffer.get(); // "b"
        circularBuffer.sort(Comparator.comparing(String::length));
        List<String> expected = Arrays.asList("", "e", "f", "g", "k", "abc", "abcd", "abcddefg");
        List<String> actual = circularBuffer.asList();
        assertEquals(expected, actual);

        String expectedValue = "";
        String actualValue = circularBuffer.get(); // ""
        assertSame(expectedValue, actualValue);

        circularBuffer.addAll(Arrays.asList("1", "2", "3"));
        expected = Arrays.asList("e", "f", "g", "k", "abc", "abcd", "abcddefg", "1", "2", "3");
        actual = circularBuffer.asList();
        assertEquals(expected, actual);

        circularBuffer = new DefaultCircularBuffer<>(3);
        circularBuffer.addAll(Arrays.asList("c", "b", "abcd"));
        circularBuffer.get(); // "c"
        circularBuffer.get(); // "b"
        circularBuffer.get(); // "abcd"
        circularBuffer.sort(Comparator.comparing(String::length));
        expected = Collections.EMPTY_LIST;
        actual = circularBuffer.asList();
        assertEquals(expected, actual);
    }
}
