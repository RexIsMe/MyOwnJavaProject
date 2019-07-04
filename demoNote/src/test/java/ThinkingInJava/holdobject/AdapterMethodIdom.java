package ThinkingInJava.holdobject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>Title: AdapterMethodIdom</p>
 * <p>Description: </p>
 * <p>Company: Intellifusion</p>
 *
 * @author Lzq
 * @version V1.0
 * @date 2019/6/19 19:53
 */

public class AdapterMethodIdom {

    public static void main(String[] args) {

        ReversibleArrayList<String> ral = new ReversibleArrayList<>(
                Arrays.asList("To be or not to be".split(" "))
        );

        for (String s : ral)
            System.out.println(s + " ");
        System.out.println();

        for(String s : ral.reversed())
            System.out.println(s + " ");
    }


}


class ReversibleArrayList<T> extends ArrayList<T> {
    public ReversibleArrayList(Collection<T> c) {
        super(c);
    }

    public Iterable<T> reversed() {

        /**
         * lamada写法
         */
        return (() ->{
                return new Iterator<T>() {
                    int current = size() - 1;
                    @Override
                    public boolean hasNext() {
                        return current > -1;
                    }

                    @Override
                    public T next() {
                        return get(current--);
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };

            });

        /*return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {

                return new Iterator<T>() {
                    int current = size() - 1;
                    @Override
                    public boolean hasNext() {
                        return current > -1;
                    }

                    @Override
                    public T next() {
                        return get(current--);
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };

            }

        };*/
    }

}