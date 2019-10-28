package queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName MyQueue
 * @Description java 队列
 * @Author zoujiulong
 * @Date 2019/10/28 17:20
 * @Version 1.0
 **/
public class MyQueue {
        public static void main(String[] args) {
            //add()和remove()方法在失败的时候会抛出异常(不推荐)
            Queue<String> queue = new LinkedList<String>();
            //添加元素
            queue.offer("a");
            queue.offer("b");
            queue.offer("c");
            queue.offer("d");
            queue.offer("e");
            for(String q : queue){
                System.out.println(q);
            }
            System.out.println("===");
            System.out.println("poll="+queue.poll()); //返回第一个元素，并在队列中删除
            for(String q : queue){
                System.out.println(q);
            }
            System.out.println("===");
            System.out.println("element="+queue.element()); //返回第一个元素
            for(String q : queue){
                System.out.println(q);
            }
            System.out.println("===");
            System.out.println("peek="+queue.peek()); //返回第一个元素
            for(String q : queue){
                System.out.println(q);
            }
        }
}
