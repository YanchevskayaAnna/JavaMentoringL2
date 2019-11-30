package com.company.exercises.circularbuffer;

import com.company.exercises.circularbuffer.exceptions.EmptyBufferException;
import com.company.exercises.circularbuffer.exceptions.FullBufferException;
import com.company.exercises.circularbuffer.exceptions.NotEnoughFreeSpaceException;

import java.util.Comparator;
import java.util.List;

public class DefaultCircularBuffer<T> implements CircularBuffer<T>{

    private T[] arr;
    private int head = 0;
    private int tail = 0;
    private int counter = 0;
    private static final int DEFAULT_ARRAY_SIZE = 10;

    private boolean isFull(){
        return (head == tail) && !isEmpty();
    }

    public DefaultCircularBuffer() {
        this.arr = (T[])new Object[DEFAULT_ARRAY_SIZE];
    }

    public DefaultCircularBuffer(int size) {
        this.arr = (T[])new Object[size];
    }

    public int size(){
        return counter;
    }

    public void put(T t) {
        if (isFull()) {
            throw new FullBufferException();
        }
        arr[head] = t;
        head = (head + 1) % arr.length;
        counter++;
    }

    public T get() {
        if (isEmpty()) {
            throw new EmptyBufferException();
        }
        T result = arr[tail];
        tail = (tail + arr.length - 1) % arr.length;
        counter--;
        return result;
    }

    public Object[] toObjectArray() {
        return new Object[0];
    }

    public T[] toArray() {
        //return new T[0];
        return null;
    }

    public List<T> asList() {
        return null;
    }

    public void addAll(List<? extends T> toAdd) {
        if (isFull() || ((counter + toAdd.size() > arr.length))) {
            throw new NotEnoughFreeSpaceException();
        }
        for (T t : toAdd) {
            put(t);
        }
    }

    public void sort(Comparator<? super T> comparator) {
    }

    public boolean isEmpty() {
        return counter == 0;
    }
}
