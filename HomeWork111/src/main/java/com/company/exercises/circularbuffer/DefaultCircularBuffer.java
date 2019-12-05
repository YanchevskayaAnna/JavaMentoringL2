package com.company.exercises.circularbuffer;

import com.company.exercises.circularbuffer.exceptions.EmptyBufferException;
import com.company.exercises.circularbuffer.exceptions.FullBufferException;
import com.company.exercises.circularbuffer.exceptions.NotEnoughFreeSpaceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DefaultCircularBuffer<T> implements CircularBuffer<T> {
    private static final int DEFAULT_ARRAY_SIZE = 10;
    private T[] arr;
    private int head = 0;
    private int tail = 0;
    private int counter = 0;

    public DefaultCircularBuffer() {
        this(DEFAULT_ARRAY_SIZE);
    }

    public DefaultCircularBuffer(int size) {
        this.arr = (T[]) new Object[size];
    }

    public void put(T t) {
        if (isFull()) {
            throw new FullBufferException("Buffer is full");
        }
        arr[head] = t;
        head = increaseInCircle(head);
        counter++;
    }

    public T get() {
        if (isEmpty()) {
            throw new EmptyBufferException("Buffer is empty");
        }
        T result = arr[tail];
        tail = increaseInCircle(tail);
        counter--;
        return result;
    }

    public Object[] toObjectArray() {
        Object[] arrResult = new Object[counter];
        int arrTail = tail;
        for (int i = 0; i < counter; i++) {
            arrResult[i] = arr[arrTail];
            arrTail = increaseInCircle(arrTail);
        }
        return arrResult;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] arrResult = (T[]) java.lang.reflect.Array
                .newInstance(arr.getClass().getComponentType(), counter);
        int arrTail = tail;
        for (int i = 0; i < counter; i++) {
            arrResult[i] = (T) arr[arrTail];
            arrTail = increaseInCircle(arrTail);
        }
        return arrResult;
    }

    public List<T> asList() {
        List<T> list = new ArrayList<>();
        int arrTail = tail;
        for (int i = 0; i < counter; i++) {
            list.add(arr[arrTail]);
            arrTail = increaseInCircle(arrTail);
        }
        return list;
    }

    public void addAll(List<? extends T> toAdd) {
        if (isFull() || ((counter + toAdd.size() > arr.length))) {
            throw new NotEnoughFreeSpaceException("there is not enough free space in the buffer");
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
            arrTail = increaseInCircle(arrTail);
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

    private int increaseInCircle(int point) {
        return (point + 1) % arr.length;
    }
}
