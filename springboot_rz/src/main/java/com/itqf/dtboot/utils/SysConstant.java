package com.itqf.dtboot.utils;

import org.quartz.Scheduler;

/**
 * 系统常量
 */
public class SysConstant {

    public  static final String CODE_KEY="CODE";

    public static final String JOB_KEY_PREFIX="JOB_";
    public static final String TRIGGER_KET_PREFIX="TRIGGER_";
    public static  final String SCHEDULE_DATA_KEY="SCHEDULE_DATA_KEY";
   // public static final byte NONAL=0;

    public enum SchedulerStatus{

        NOMAL((byte)0),
        PAUSE((byte)1);

        private Byte value;
         SchedulerStatus(Byte value){
            this.value = value;
        }

        public Byte getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        System.out.println(SchedulerStatus.NOMAL.getValue());
        System.out.println(SchedulerStatus.PAUSE.getValue());
    }

}
