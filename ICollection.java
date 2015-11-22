import tester.Tester;

interface ICollection<T> {
    // Is this collection empty?
    boolean isEmpty();
    // Add the given item to this collection
    void add(T elt);
    // Remove an element from this collection
    // and return it
    T remove();
}
// Represents a queue with items added to the back
// and removed from the front
class Queue<T> implements ICollection<T>{
    Deque<T> src;
    
    // Creates a queue with the given deque
    Queue(Deque<T> src) {
        this.src = src;
    }
    // Creates a queue with an empty deque
    Queue() {
        this.src = new Deque<T>();
    }
    // Is this Queue empty?
    public boolean isEmpty() {
        return this.src.size() == 0;
    }
    // Add the given item to the back of this queue
    // EFFECT: modifies this.src
    public void add(T elt) {
        this.src.addAtTail(elt);
    }
    // Remove the item at the head of this queue
    // and return it
    // EFFECT: modifies this.src
    public T remove() {
        return this.src.removeFromHead();
    }
}
// Represents a stack with items added to front
// and removed from the front
class Stack<T> implements ICollection<T> {
    Deque<T> src;

    // Creates a stack with the given deque
    Stack(Deque<T> src) {
        this.src = src;
    }
    // Creates a stack with an empty deque
    Stack() {
        this.src = new Deque<T>();
    }
    // Is this stack empty?
    public boolean isEmpty() {
        return this.src.size() == 0;
    }
    // Push the given item on the top of this stack
    // EFFECT: modifies this.src
    public void add(T elt) {
        this.src.addAtHead(elt);
    }
    // Pop the first item from the top of this stack
    // and return it
    // EFFECT: modifies this.src
    public T remove() {
        return this.src.removeFromHead();
    }
}

class ExamplesICollection {
    Stack<Integer> stack1;
    Stack<Integer> stack2;
    Queue<Integer> queue1;
    Queue<Integer> queue2;
    void initialize() {
        Deque<Integer> deq1 = new Deque<Integer>();
        deq1.addAtHead(3);
        deq1.addAtHead(2);
        deq1.addAtHead(1);
        Deque<Integer> deq2 = new Deque<Integer>();
        deq2.addAtHead(1);
        deq2.addAtHead(2);
        deq2.addAtHead(3);
        //  deq1 is now (1 2 3)
        //  deq2 is now (3 2 1)
        this.stack1 = new Stack<Integer>();
        this.stack2 = new Stack<Integer>(deq1);
        this.queue1 = new Queue<Integer>();
        this.queue2 = new Queue<Integer>(deq2);
    }
    void testIsEmpty(Tester t) {
        this.initialize();
        t.checkExpect(this.stack1.isEmpty(), true);
        t.checkExpect(this.stack2.isEmpty(), false);
        t.checkExpect(this.queue1.isEmpty(), true);
        t.checkExpect(this.queue2.isEmpty(), false);
    }
    void testAdd(Tester t) {
        this.initialize();
        Deque<Integer> tempDeq = new Deque<Integer>();
        Deque<Integer> tempDeq2 = new Deque<Integer>();
        Deque<Integer> tempDeq3 = new Deque<Integer>();
        Deque<Integer> tempDeq4 = new Deque<Integer>();
        tempDeq.addAtHead(1);
        this.stack1.add(1);
        t.checkExpect(stack1, new Stack<Integer>(tempDeq));
        tempDeq2.addAtHead(3);
        tempDeq2.addAtHead(2);
        tempDeq2.addAtHead(1);
        tempDeq2.addAtHead(4);
        this.stack2.add(4);
        t.checkExpect(stack2, new Stack<Integer>(tempDeq2));
        this.queue1.add(7);
        tempDeq3.addAtTail(7);
        t.checkExpect(queue1, new Queue<Integer>(tempDeq3));
        this.queue2.add(7);
        tempDeq4.addAtTail(3);
        tempDeq4.addAtTail(2);
        tempDeq4.addAtTail(1);
        tempDeq4.addAtTail(7);
        t.checkExpect(queue2, new Queue<Integer>(tempDeq4));
    }
    void testRemove(Tester t) {
        this.initialize();
        t.checkException(new RuntimeException("Can't remove from an empty list."),
                         this.stack1, "remove");
        t.checkException(new RuntimeException("Can't remove from an empty list."),
                         this.queue1, "remove");
        t.checkExpect(this.stack2.remove(), 1);
        t.checkExpect(this.stack2.remove(), 2);
        t.checkExpect(this.stack2.remove(), 3);
        t.checkExpect(this.stack2.isEmpty(), true);
        t.checkExpect(this.queue2.remove(), 3);
        t.checkExpect(this.queue2.remove(), 2);
        t.checkExpect(this.queue2.remove(), 1);
        t.checkExpect(this.queue2.isEmpty(), true);
    }
}
