package com.marco.controller;

import com.marco.utils.MyCompiler.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by marco sun on 2017/12/11.
 */
@Controller
public class WordController {
    @RequestMapping(name = "/word", method = RequestMethod.POST)
    public String getWordAna(@RequestParam(value = "text")String text, Model model){
        List<String> grammarList = new ArrayList<String>();
        String[] grammar;
        List<String> v = new ArrayList<String>();
        List<String> vl = new ArrayList<String>();
        List<String> vn = new ArrayList<String>();
        List<String> vt = new ArrayList<String>();
        Queue<Character> queue = new LinkedList<Character>();
        boolean[][] firstVt;
        boolean[][] lastVt;
        int[][] tb;
        boolean errorM = false;
        String queueStr;
        List<String> stackStrM;
        //队列
        List<String> queueStrM;
        //1代表移进， 0代表规约
        List<Boolean> actionM;
        //当前字符
        List<Character> curM;
        //大小关系
        List<String> relationM;
        boolean isSuccessM;
        System.out.println(text);
        String[] str = text.split("\r\n");

//        for(int i = 0; i < str.length; i++)
//            System.out.println(str[i]);
//        System.out.println(str[0]);
        int len = Integer.parseInt(str[0]);
        queueStr = str[str.length-1];

        //得到文法
        for(int i = 1; i < str.length-1; i++){
            String each = str[i];
            int index = each.indexOf('|');
            if(index != -1){
                String preEach = each.substring(0, 3);
                String sufEach = each.substring(3, each.length());
                String []s = sufEach.split("\\|");
                for(int j = 0; j < s.length; j++)
                    grammarList.add(preEach + s[j]);
            }else {
                grammarList.add(each);
            }
        }
        for(int i = 0; i < grammarList.size(); i++) {
            System.out.println(grammarList.get(i));
        }

        //获得vn和vt
        for(int i = 0; i < grammarList.size(); i++){
            String each = grammarList.get(i);
            if(!vl.contains(each.charAt(0)+"")){
                vl.add(each.charAt(0)+"");
            }
            for(int j = 0; j < each.length(); j++){
                if(j == 1 || j == 2)
                    continue;
                if(!v.contains(each.charAt(j)+""))
                    v.add(each.charAt(j)+"");
            }
        }
        getV gv = new getV(v, vl);
        vn = gv.getVn();
        vt = gv.getVt();
        //获得firstVt和lastVt
        grammar = new String[grammarList.size()];
        for(int i = 0; i < grammar.length; i++)
            grammar[i] = grammarList.get(i);
        FirstVt fv = new FirstVt(grammar, vn.size(), vt.size());
        firstVt = fv.getFirstVt(vn, vt);
        LastVt lv = new LastVt(grammar, vn.size(), vt.size());
        lastVt = lv.getLastVt(vn, vt);
        //获得关系优先矩阵
        PrecedenceTb ptb = new PrecedenceTb(grammar, firstVt, lastVt, vn, vt);
        tb = ptb.getTb();
        errorM = ptb.getError();
        //算法开始
        for(int i = 0; i < queueStr.length(); i++)
            queue.add(queueStr.charAt(i));
        OperatorPriorityAlgorithm opa = new OperatorPriorityAlgorithm(queue, tb, vn, vt, grammar);
        opa.scan();
        stackStrM = opa.getStackStr();
        //队列
        queueStrM = opa.getQueueStr();
        //1代表移进， 0代表规约
        actionM = opa.getAction();
        //当前字符
        curM = opa.getCur();
        //大小关系
        relationM = opa.getRelation();
        isSuccessM = opa.getIsSuccess();
        int vtSize = vt.size();
        model.addAttribute(grammarList);

        model.addAttribute("v", v);
        model.addAttribute("vtSize", vtSize);
        model.addAttribute(vl);
        model.addAttribute("vn", vn);
        model.addAttribute("vt", vt);
        model.addAttribute("firstVt", firstVt);
        model.addAttribute("lastVt", lastVt);
        model.addAttribute("tb", tb);
        model.addAttribute("stackStrM", stackStrM);
        model.addAttribute("queueStrM", queueStrM);
        model.addAttribute("actionM", actionM);
        model.addAttribute("curM", curM);
        model.addAttribute("relationM", relationM);
        model.addAttribute("isSuccessM", isSuccessM);
        for(int i = 0; i < curM.size(); i++)
            System.out.println(curM.get(i));
        if(errorM){
            return "error";
        }
        return "wordResult";
    }
}
