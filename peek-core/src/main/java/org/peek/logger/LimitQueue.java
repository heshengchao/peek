package org.peek.logger;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/** 大小限定队列。可以有效节约内存空间
 * @author heshengchao
 * @param <E>
 */
public class LimitQueue<E> extends AbstractQueue<E> implements Queue<E>{  
       
    private int limit; // 队列长度  
          
    private LinkedList<E> queue = new LinkedList<E>();  
          
    public LimitQueue(int limit){      
        this.limit = limit;      
    }      
      
    /** 
     * 入列：当队列大小已满时，把队头的元素poll掉 
     */ 
    @Override
    public boolean offer(E e){      
        if(queue.size() >= limit){      
            queue.poll();      
        }    
        return queue.offer(e);      
    }      
      
    public E get(int position) {  
        return queue.get(position);  
    }  
      
    public E getLast() {  
        return queue.getLast();  
    }  
      
    public E getFirst() {  
        return queue.getFirst();  
    }  
      
    public int getLimit() {  
        return limit;  
    }  
      
    public int size() {      
        return queue.size();      
    }

	@Override
	public E poll() {
		return queue.poll(); 
	}

	@Override
	public E peek() {
		return queue.peek();
	}

	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}
      
}  
