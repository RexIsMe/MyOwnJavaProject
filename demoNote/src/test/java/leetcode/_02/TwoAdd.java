package leetcode._02;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rex
 * @title: _02TwoAdd
 * @projectName demoNote
 * @description: TODO
 * @date 2019/7/2918:42
 */
public class TwoAdd {

    public static void main(String[] args) {
        ListNode listNode11 = tt(9L);
        ListNode listNode21 = tt(9999999991L);

        myPrint(listNode11);
        myPrint(listNode21);
        ListNode listNode = addTwoNumbers(listNode11, listNode21);
        myPrint(listNode);

    }

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     *
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     *
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *
     * 示例：
     *
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        long node1 = 0;
        int flag1 = 1;
        do{
            node1 += l1.val * flag1;
            flag1 *= 10;
            l1 = l1.next;
        } while (l1 != null);

        long node2 = 0;
        int flag2 = 1;
        do{
            node2 += l2.val * flag2;
            flag2 *= 10;
            l2 = l2.next;
        } while (l2 != null);

        long resultNode = node1 + node2;
//        int resultNode = 1000000000;

        List<ListNode> result = new ArrayList<>();
        ListNode ln;
        int flag3 = 10;
        do {
            long l = resultNode % flag3;
            ln = new ListNode(l);
            result.add(ln);
            resultNode /= flag3;
        } while (resultNode >= 1);

        for (int i = 0; i < result.size() - 1; i++) {
            result.get(i).next = result.get(i + 1);
        }
        
        return result.get(0);
    }


    public static ListNode tt(Long resultNode){

        List<ListNode> result = new ArrayList<>();
        ListNode ln;
        int flag3 = 10;
        do {
            Long l = resultNode % flag3;
            ln = new ListNode(l);
            result.add(ln);
            resultNode /= flag3;
        } while (resultNode >= 1);

        for (int i = 0; i < result.size() - 1; i++) {
            result.get(i).next = result.get(i + 1);
        }
        return result.get(0);
    }

    public static void myPrint(ListNode listNode){
        do {
            System.out.print(listNode.val);
            System.out.print("->");
            listNode = listNode.next;
        } while (listNode != null);

        System.out.println();
    }

}
