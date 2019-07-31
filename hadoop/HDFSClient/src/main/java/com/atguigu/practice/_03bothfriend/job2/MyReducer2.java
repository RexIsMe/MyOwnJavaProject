package com.atguigu.practice._03bothfriend.job2;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

/**
 * @author Rex
 * @title: MyReducer2
 * @projectName HDFSclient
 * @description: TODO
 * @date 2019/7/3114:12
 */
public class MyReducer2 extends Reducer<KeyBean, Text, Text, Text> {

    private Text k = new Text();
    private Text valuesss = new Text();

    @Override
    protected void reduce(KeyBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        Map<String, String> map1 = new HashMap<String, String>();
        Map<String, String> map2 = new HashMap<String, String>();

        Map<String, String> map;
        String splitStr;
        int keyIndex;
        int valueIndex;
        String[] split;
        String[] split22;
        for (Text value : values) {
            split22 = value.toString().split("=");
            if("friends.txt".equals(split22[0])){
                map = map1;
                splitStr = ":";
                keyIndex = 0;
                valueIndex = 1;
            } else {
                map = map2;
                splitStr = "\t";
                keyIndex = 1;
                valueIndex = 0;
            }

            split = split22[1].split(splitStr);
            String k = split[keyIndex];
            String v = split[valueIndex];
            map.put(k, v);
        }

        Map<String, String> resultMap = new TreeMap<String, String>();
        List<String> insect;
        Iterator<String> iterator = map1.keySet().iterator();
        while (iterator.hasNext()) {
            String mapKey = iterator.next();
            String s = map1.get(mapKey);
            String[] split1 = s.split(",");
            String s1 = map2.get(mapKey);
            if (StringUtils.isNotBlank(s1)){
                String[] split2 = s1.split(",");
                insect = getInsect(split1, split2);

                for (int i = 0; i < insect.size(); i++) {
                    String s2 = mapKey + "-" + insect.get(i);
                    if(resultMap.get(insect.get(i) + "-" + mapKey) == null){
                        k.set(s2);
                        resultMap.put(s2, "1");
//                        context.write(k, v);
                    }
                }
            }
        }


        getResult(resultMap, map1,context);

    }


    /**
     *  根据result、demo得到共同关注的用户，用“,”连接
     * @param result 该map中key为关联的两个对象，用“-”连接
     * @param demo 该map中key为用户，value为这个对象关注的用户
     */
    public void getResult(Map<String, String> result, Map<String,String> demo, Context context) throws IOException, InterruptedException{
        Iterator<String> iterator = result.keySet().iterator();
        List<String> insect;
        while (iterator.hasNext()) {
            String next = iterator.next();
            String[] split = next.split("-");

            insect = getInsect(demo.get(split[0]).split(","), demo.get(split[1]).split(","));
//            result.put(next, listToString(insect, ","));
            k.set(next);
            valuesss.set(listToString(insect, ","));
            context.write(k, valuesss);
        }

    }


    /**
     * 将list转成以splitStr连接的字符串
     * @param list
     * @param splitStr
     * @return
     */
    public String listToString(List<String> list, String splitStr){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i));
            stringBuilder.append(splitStr);
        }

        String result = "";
        if(stringBuilder.length() > 0){
            result = stringBuilder.subSequence(0, stringBuilder.length() - 1).toString();
        }

        return result;
    }


    /**
     * 获取两个字符数组中的相同元素
     * @param str1
     * @param str2
     * @return
     */
    public List<String> getInsect(String[] str1, String[] str2){
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < str1.length; i++) {
            String s = str1[i];
            for (int i1 = 0; i1 < str2.length; i1++) {
                if(s.equals(str2[i1])){
                    list.add(s);
                    break;
                }
            }
        }

        return list;
    }

}
