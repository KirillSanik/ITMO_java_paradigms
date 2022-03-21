package queue;

import java.util.function.Predicate;

/*
Model: a[head] ... a[tail]
Invarint: for i = head ... tail: a[i] != null

Let immutable(start, end): for i = start ... end: a'[i] == a[i]
Let size := tail - head
 */

public interface Queue {
//    Pred: element != null
//    Post: tail' = tail + 1 && a[tail'] = element && immutable(head, tail)
    public void enqueue(Object element);

//    Pred: true
//    Post: Result = a[head] && immutable(head, tail)
    public Object element();

//    Pred: true
//    Post: head' = head + 1 && Result = a[head] && immutable(head', tail)
    public Object dequeue();

//    Pred: true
//    Post: size = 0 && tail = 0 && head = 0
    public void clear();

//    Pred: true
//    Post: Result = tail - head && immutable(head, tail)
    public int size();

//    Pred: true
//    Post: Result = size == 0 && immutable(head, tail)
    public boolean isEmpty();
    
//     Pred: predicate != null
//     Post: for i = head ... tail: Result = cnt(predicate.test(a[i])) && immutable(head, tail)
    public int countIf(Predicate<Object> predicate);
}
