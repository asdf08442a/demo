package com.demo.common.util.other;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要用来调试程序，观察程序运行每一步所用的时间
 * Created by jinzg on 2016年12月2日.
 */
public class TimeWatch {
    /**
     * 创建开始时间
     */
    private long start;
    /**
     * 节点数据（一般保存结束时间数据）
     */
    private List<Object> sections;
    /**
     * 节点名称
     */
    private List<Object> sectionNames;
    
    /**
     * 观察名称，下面包括很多的sectionNames，watchName属于sectionNames的整体，便于观察定位
     */
    private String watchName;
    
    public static TimeWatch createTimeWatch(String watchName){
        TimeWatch timeWatch = new TimeWatch();
        timeWatch.watchName = watchName;
        return timeWatch;
    }
    
    protected TimeWatch(){
        sections = new ArrayList<>();//初始化sections
        sectionNames = new ArrayList<>();//初始化sectionNames
        start = System.currentTimeMillis();//当前系统开始时间
    }
    
    public void addStep(String stepName){
        sectionNames.add(stepName);//每一步节点的名称
        sections.add(Long.valueOf(System.currentTimeMillis()));//节点开始时间
    }
    
    public String outputTimeList(){
        watchName = watchName.trim();
        StringBuffer outStr = new StringBuffer();
        //整体观察名称
        outStr.append("[TIMEWATCH] ");
        outStr.append(watchName);
        outStr.append(":");
        outStr.append(" [DETAILS] ");
        //输出每一个节点的名称和花费时间
        long last = start;
        for(int i=0;i < sections.size();i++){
            long temp = ((Long)sections.get(i)).longValue();
            outStr.append("" + (String)sectionNames.get(i) + ":" );
            outStr.append((double)(temp - last));
            outStr.append("ms ");
            last = temp;
        }
        //总体花费时间
        long totalWaste = 0;
        if(sections != null && sections.size() > 0){
            totalWaste = ((Long)sections.get(sections.size()-1)).longValue() - start;
        }
        //将总体花费时间插入到名称watchName的后面。"[TIMEWATCH] "和"："长度是13
        outStr.insert(watchName.length() + 13, totalWaste);
        
        return outStr.toString();
    }
    
    public static void main(String[] args) throws InterruptedException {
        TimeWatch tw = TimeWatch.createTimeWatch("TestWatch");
        Thread.sleep(1000);
        tw.addStep("1");
        Thread.sleep(2000);
        tw.addStep("2");
        Thread.sleep(3000);
        tw.addStep("3");
        System.out.println(tw.outputTimeList());
    }

}
