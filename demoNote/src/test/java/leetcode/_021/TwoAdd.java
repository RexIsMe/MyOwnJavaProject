package leetcode._021;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rex
 * @title: TwoAdd
 * @projectName demoNote
 * @description: TODO
 * @date 2019/7/2919:34
 */
public class TwoAdd {

    public static void main(String[] args) {
        ListNode listNode11 = tt(0L);
        ListNode listNode21 = tt(81L);

        myPrint(listNode11);
        myPrint(listNode21);
        ListNode listNode = addTwoNumbers(listNode11, listNode21);
        myPrint(listNode);

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        List<ListNode> resultList = new ArrayList<>();
        ListNode ln;
        int flag = 0;
        boolean flag1 = true;
        boolean flag2 = true;
        do {
            int val1 = l1.val;
            int val2 = l2.val;

            int i = val1 + val2 + flag;
            if(i >= 10){
                flag = 1;
                i %= 10;
            } else {
                flag = 0;
            }
            ln = new ListNode(i);
            resultList.add(ln);

            l1 = l1.next;
            if (l1 == null) {
                l1 = new ListNode(0);
                flag1 = false;
            }
            l2 = l2.next;
            if (l2 == null) {
                l2 = new ListNode(0);
                flag2 = false;
            }

        } while (flag1 || flag2 || flag != 0);

        for (int i = 0; i < resultList.size() - 1; i++) {
            resultList.get(i).next = resultList.get(i + 1);
        }

        return resultList.get(0);
    }


    public static ListNode tt(Long resultNode){

        List<ListNode> result = new ArrayList<>();
        ListNode ln;
        int flag3 = 10;
        do {
            Long l = resultNode % flag3;
            ln = new ListNode(l.intValue());
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
