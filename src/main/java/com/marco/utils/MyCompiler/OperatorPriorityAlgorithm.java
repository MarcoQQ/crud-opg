package com.marco.utils.MyCompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

//算符优先分析
public class OperatorPriorityAlgorithm {

    //分析栈
    private List<Character> stack;
    //vt栈
    private Stack<Character> vtStack;
    //剩余输入串
    private Queue<Character> queue;
    //优先关系矩阵,左：栈内，右：栈外。 -1代表不存在，0代表小于，1代表大于，2代表等于
    private int[][] tb;

    private List<String> vt;
    private List<String> vn;
    private String[] grammar;

    //传到前端
    //栈
    private List<String> stackStr;
    //队列
    private List<String> queueStr;
    //1代表移进， 0代表规约
    private List<Boolean> action;
    //当前字符
    private List<Character> cur;
    //大小关系
    private List<String> relation;
    private boolean isSuccess;

    public OperatorPriorityAlgorithm(Queue<Character> queue, int[][] tb, List<String>vn, List<String> vt, String[] grammar) {
        this.tb = tb;
        this.action = new ArrayList();
        this.stack = new ArrayList();
        this.vtStack = new Stack();
        this.relation = new ArrayList<String>();
        this.stackStr = new ArrayList<String>();
        this.queueStr = new ArrayList<String>();
        this.cur = new ArrayList<Character>();
        this.queue = queue;
        this.vn = vn;
        this.vt = vt;
        this.isSuccess = true;
        stack.add('#');
        vtStack.push('#');
        this.grammar = grammar;
    }

    public List<Character> getStack() {
        return stack;
    }

    public Queue<Character> getQueue() {
        return queue;
    }

    //移近操作
    public void putIn(){
        char ch = queue.poll();
        stack.add(ch);
        vtStack.add(ch);
    }

    //规约操作
    public void update(){
        char ch = computeVn();
        stack.add(ch);
    }

    //得出规约的vn
    public char computeVn(){
        boolean flag = true;
        int index;
        int size = 0;
        for(int i = 0; i < grammar.length; i++){
            String each = grammar[i];
            flag = false;
            index = stack.size()-1;
            if(each.length() == 4 && vt.contains(each.charAt(3)+"") && each.charAt(3) == stack.get(index)){
                flag = true;
            } else if(each.length()  > 4) {
                for (int j = each.length() - 1; j >= 3; j--) {
                    if (each.charAt(j) == stack.get(index) || (vn.contains(each.charAt(j) + "") && vn.contains(stack.get(index) + ""))) {
                        flag = true;
                        index--;
                    } else {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                for (int k = each.length() - 1; k >= 3; k--) {
//                  System.out.println("vt:"+stack.get(stack.size()-1));
                    if (vt.contains(stack.get(stack.size() - 1) + "")) {
                        vtStack.pop();
                    }
                    stack.remove(stack.size() - 1);
                    //TODO 记录弹出的符号
                }
                return each.charAt(0);
            }
        }


        return ' ';
    }

    public char getLeft(){
        return vtStack.peek();
    }

    public char getRight(){
        return queue.peek();
    }
    //下标
    public int indexOf(List<String> v, char ch){
        if(ch == '#')return v.size();
        for(int i = 0; i < v.size(); i++){
            if(v.get(i).equals(ch+"")){
                return i;
            }
        }
        return -1;
    }

    public boolean getIsSuccess(){
        return isSuccess;
    }

    public List<String> getStackStr() {
        return stackStr;
    }

    public List<String> getQueueStr() {
        return queueStr;
    }

    public List<Character> getCur() {
        return cur;
    }

    public List<Boolean> getAction() {
        return action;
    }

    public List<String> getRelation() {
        return relation;
    }

    public void scan(){
        while(true){
            char left = getLeft();
            char right = getRight();
//            System.out.println(left);
//            System.out.println(right);
            int leftIndex = indexOf(vt, left);
//            System.out.println(leftIndex);
            int rightIndex = indexOf(vt, right);
//            System.out.println(rightIndex);


            int ans = tb[leftIndex][rightIndex];

            if(ans == -1){
                //不存在
                error();
                break;
            } else if(ans == 0 || ans == 2){
                //小于或等于
                String str = "";
                for(int i = 0; i < stack.size(); i++)
                    str += stack.get(i);
                stackStr.add(new String(str));
                str = "";
                int len = queue.size();
                for(int i = 0; i < len; i++)
                    str += queue.poll();
                for(int i = 0; i < str.length(); i++)
                    queue.add(str.charAt(i));
                queueStr.add(new String(str));
                cur.add(queue.peek());
                if(ans == 0)
                    relation.add("<");
                if(ans == 2)
                    relation.add("=");
                action.add(true);
                putIn();
            } else if(ans == 1){

                //大于
                String str = "";
                for(int i = 0; i < stack.size(); i++)
                    str += stack.get(i);
                stackStr.add(new String(str));
                System.out.println("stack1"+str);
                str = "";
                int len = queue.size();
                for(int i = 0; i < len; i++)
                    str += queue.poll();
                for(int i = 0; i < str.length(); i++)
                    queue.add(str.charAt(i));
                queueStr.add(new String(str));
                System.out.println("queue1"+str);
                cur.add(queue.peek());
                relation.add(">");
                action.add(false);

                update();


                if(stack.size() == 2 && queue.peek() == '#'){

                    cur.add(queue.peek());
                    str = "";
                    for(int i = 0; i < stack.size(); i++)
                        str += stack.get(i);
                    stackStr.add(new String(str));
                    str = "";
                    len = queue.size();

                    for(int i = 0; i < len; i++)
                        str += queue.poll();
                    for(int i = 0; i < str.length(); i++)
                        queue.add(str.charAt(i));
                    queueStr.add(new String(str));
                    System.out.println("hahaha"+str);
                    action.add(true);
                    relation.add("=");
                    isSuccess = true;
//                    for(int i = 0; i < stack.size(); i++){
//                        System.out.print(stack.get(i));
//                    }
//                    System.out.println();
                    break;
                }
            }
//            for(int i = 0; i < stack.size(); i++){
//                System.out.print(stack.get(i));
//            }
//            System.out.println();

//            for(int i = 0; i < vtStack.size(); i++){
//                System.out.print(vtStack.get(i));
//            }
//            System.out.println();

        }
    }

    public void error(){
        String str = "";
        cur.add(queue.peek());
        for(int i = 0; i < stack.size(); i++)
            str += stack.get(i);
        stackStr.add(new String(str));
        str = "";
        int len = queue.size();
        for(int i = 0; i < len; i++)
            str += queue.poll();
        for(int i = 0; i < str.length(); i++)
            queue.add(str.charAt(i));
        queueStr.add(new String(str));
        //如果失败，少一个action
        relation.add("-");
        isSuccess = false;
    }
}
