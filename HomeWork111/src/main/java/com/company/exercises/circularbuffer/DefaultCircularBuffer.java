package com.company.exercises.circularbuffer;

import com.company.exercises.circularbuffer.exceptions.EmptyBufferException;
import com.company.exercises.circularbuffer.exceptions.FullBufferException;
import com.company.exercises.circularbuffer.exceptions.NotEnoughFreeSpaceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DefaultCircularBuffer<T> implements CircularBuffer<T> {
    private T[] arr;
    private int head = 0;
    private int tail = 0;
    private int counter = 0;
    private static final int DEFAULT_ARRAY_SIZE = 10;

    public DefaultCircularBuffer() {
        this.arr = (T[]) new Object[DEFAULT_ARRAY_SIZE];
    }

    public DefaultCircularBuffer(int size) {
        this.arr = (T[]) new Object[size];
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
        tail = (tail + 1) % arr.length;
        counter--;
        return result;
    }

    public Object[] toObjectArray() {
        Object[] arrResult = new Object[counter];
        int arrTail = tail;
        for (int i = 0; i < counter; i++) {
            arrResult[i] = arr[arrTail];
            arrTail = (arrTail + 1) % arr.length;
        }
        return arrResult;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(T[] a) {
        T[] arrResult = (T[]) java.lang.reflect.Array
                .newInstance(a.getClass().getComponentType(), counter);
        int arrTail = tail;
        for (int i = 0; i < counter; i++) {
            arrResult[i] = (T) arr[arrTail];
            arrTail = (arrTail + 1) % arr.length;
        }
        return (T[]) arrResult;
    }

    public List<T> asList() {
        List<T> list = new ArrayList<>();
        int arrTail = tail;
        for (int i = 0; i < counter; i++) {
            list.add(arr[arrTail]);
            arrTail = (arrTail + 1) % arr.length;
        }
        return list;
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
        if (isEmpty()) {
            if (head != 0 || tail != 0) {
                arr = (T[]) new Object[arr.length];
            }
            return;
        }
        T[] sortArr = (T[]) new Object[counter];
        int arrTail = tail;
        for (int i = 0; i < counter; i++) {
            sortArr[i] = arr[arrTail];
            arrTail = (arrTail + 1) % arr.length;
        }
        Arrays.sort(sortArr, comparator);
        arr = counter == arr.length ? sortArr : Arrays.copyOf(sortArr, arr.length);
        head = counter;
        tail = 0;
    }

    private boolean isFull() {
        return (head == tail) && !isEmpty();
    }

    public boolean isEmpty() {
        return counter == 0;
    }
}
