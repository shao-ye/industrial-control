package com.ruoyi.industry.domain;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * 插槽串行参数对象 SlotSerialParam
 *
 * @author shao
 * @date 2022-04-06
 */
public class SlotSerialParam {

    private int bModifySend;
    /** 厂号 */
    private long machineNo;

    private long slotNo;

    /** 槽号 */
    private long realSlotNo;

    /** 修改时间 */
    private Date signTime;

    /** 编辑者 */
    private String author;

    /** 系列电流 */
    private long plCurrent0;

    private long plCurrent1;

    /** 停NB电流 */
    private long stopFeedCurrent0;

    private long stopFeedCurrent1;

    private long upCurrentRange0;

    private long downCurrentRange0;

    private long upCurrentRange1;

    private long downCurrentRange1;

    /** RC电压上限 */
    private long upVoltageRange;

    /** RC电压下限 */
    private long downVoltageRange;

    /** 高灵敏上限 */
    private long upResistRange0;

    /** 高灵敏下限 */
    private long downResistRange0;

    private long upResistRange1;

    private long downResistRange1;

    /** 出铝特殊加料时间 */
    private long processMetalFeedTime;

    /** 出铝附加电压 */
    private long processMetalAddVolt;

    /** 出铝附加电压时间 */
    private long processMetalAddVoltTime;

    /** 换极加料时间 */
    private long processAcFeedTime;

    /** 换极附加电压 */
    private long processAcAddVolt;

    /** 换极电压时间 */
    private long processAcAddVoltTime;

    private long addSideFeedTime;

    private long addSideAddVolt;

    private long addSideAddVoltTime;

    private long processBrFeedTime;

    private long aeStartVolt0;

    private long aeStartVolt1;

    private long aeEndVolt;

    private long aeFeedCnt;

    private long aeWaitLastTime;

    private long aeUpDownStep;

    private long aeTreatTime;

    private long aeJudgeDur;

    private long noiseAddMaxVolt;

    private long noiseAddVoltTime;

    private long noiseHigh;

    private long noiseLow;

    private long alf3Time;

    /** 浓度系数 */
    private long adptCycleCoef;

    /** 增量系数 */
    private long adptOverCoef;

    /** 减量系数 */
    private long adptUnderCoef;

    /** 正常期限定时间 */
    private long adptNormalDur;

    /** 减量限定时间 */
    private long adptUnderDur;

    /** 增量限定时间 */
    private long adptOverDur;

    /** 打壳动作时间 */
    private long actionCrustTime1;

    /** 打加间隔时间 */
    private long actionCrustTime2;

    /** 加料动作时间 */
    private long actionAl2o3FeedTime;

    private long actionAlf3FeedTime;

    /** 打壳加料次数比 */
    private long shellFeedCount;

    private long plCurrentSwitch;

    private long highVoltageAlarm;

    private long lowVoltageAlarm;

    private long serialTemp1;

    private long serialTemp2;

    public int getbModifySend() {
        return bModifySend;
    }

    public void setbModifySend(int bModifySend) {
        this.bModifySend = bModifySend;
    }

    public long getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(long machineNo) {
        this.machineNo = machineNo;
    }

    public long getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(long slotNo) {
        this.slotNo = slotNo;
    }

    public long getRealSlotNo() {
        return realSlotNo;
    }

    public void setRealSlotNo(long realSlotNo) {
        this.realSlotNo = realSlotNo;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getPlCurrent0() {
        return plCurrent0;
    }

    public void setPlCurrent0(long plCurrent0) {
        this.plCurrent0 = plCurrent0;
    }

    public long getPlCurrent1() {
        return plCurrent1;
    }

    public void setPlCurrent1(long plCurrent1) {
        this.plCurrent1 = plCurrent1;
    }

    public long getStopFeedCurrent0() {
        return stopFeedCurrent0;
    }

    public void setStopFeedCurrent0(long stopFeedCurrent0) {
        this.stopFeedCurrent0 = stopFeedCurrent0;
    }

    public long getStopFeedCurrent1() {
        return stopFeedCurrent1;
    }

    public void setStopFeedCurrent1(long stopFeedCurrent1) {
        this.stopFeedCurrent1 = stopFeedCurrent1;
    }

    public long getUpCurrentRange0() {
        return upCurrentRange0;
    }

    public void setUpCurrentRange0(long upCurrentRange0) {
        this.upCurrentRange0 = upCurrentRange0;
    }

    public long getDownCurrentRange0() {
        return downCurrentRange0;
    }

    public void setDownCurrentRange0(long downCurrentRange0) {
        this.downCurrentRange0 = downCurrentRange0;
    }

    public long getUpCurrentRange1() {
        return upCurrentRange1;
    }

    public void setUpCurrentRange1(long upCurrentRange1) {
        this.upCurrentRange1 = upCurrentRange1;
    }

    public long getDownCurrentRange1() {
        return downCurrentRange1;
    }

    public void setDownCurrentRange1(long downCurrentRange1) {
        this.downCurrentRange1 = downCurrentRange1;
    }

    public long getUpVoltageRange() {
        return upVoltageRange;
    }

    public void setUpVoltageRange(long upVoltageRange) {
        this.upVoltageRange = upVoltageRange;
    }

    public long getDownVoltageRange() {
        return downVoltageRange;
    }

    public void setDownVoltageRange(long downVoltageRange) {
        this.downVoltageRange = downVoltageRange;
    }

    public long getUpResistRange0() {
        return upResistRange0;
    }

    public void setUpResistRange0(long upResistRange0) {
        this.upResistRange0 = upResistRange0;
    }

    public long getDownResistRange0() {
        return downResistRange0;
    }

    public void setDownResistRange0(long downResistRange0) {
        this.downResistRange0 = downResistRange0;
    }

    public long getUpResistRange1() {
        return upResistRange1;
    }

    public void setUpResistRange1(long upResistRange1) {
        this.upResistRange1 = upResistRange1;
    }

    public long getDownResistRange1() {
        return downResistRange1;
    }

    public void setDownResistRange1(long downResistRange1) {
        this.downResistRange1 = downResistRange1;
    }

    public long getProcessMetalFeedTime() {
        return processMetalFeedTime;
    }

    public void setProcessMetalFeedTime(long processMetalFeedTime) {
        this.processMetalFeedTime = processMetalFeedTime;
    }

    public long getProcessMetalAddVolt() {
        return processMetalAddVolt;
    }

    public void setProcessMetalAddVolt(long processMetalAddVolt) {
        this.processMetalAddVolt = processMetalAddVolt;
    }

    public long getProcessMetalAddVoltTime() {
        return processMetalAddVoltTime;
    }

    public void setProcessMetalAddVoltTime(long processMetalAddVoltTime) {
        this.processMetalAddVoltTime = processMetalAddVoltTime;
    }

    public long getProcessAcFeedTime() {
        return processAcFeedTime;
    }

    public void setProcessAcFeedTime(long processAcFeedTime) {
        this.processAcFeedTime = processAcFeedTime;
    }

    public long getProcessAcAddVolt() {
        return processAcAddVolt;
    }

    public void setProcessAcAddVolt(long processAcAddVolt) {
        this.processAcAddVolt = processAcAddVolt;
    }

    public long getProcessAcAddVoltTime() {
        return processAcAddVoltTime;
    }

    public void setProcessAcAddVoltTime(long processAcAddVoltTime) {
        this.processAcAddVoltTime = processAcAddVoltTime;
    }

    public long getAddSideFeedTime() {
        return addSideFeedTime;
    }

    public void setAddSideFeedTime(long addSideFeedTime) {
        this.addSideFeedTime = addSideFeedTime;
    }

    public long getAddSideAddVolt() {
        return addSideAddVolt;
    }

    public void setAddSideAddVolt(long addSideAddVolt) {
        this.addSideAddVolt = addSideAddVolt;
    }

    public long getAddSideAddVoltTime() {
        return addSideAddVoltTime;
    }

    public void setAddSideAddVoltTime(long addSideAddVoltTime) {
        this.addSideAddVoltTime = addSideAddVoltTime;
    }

    public long getProcessBrFeedTime() {
        return processBrFeedTime;
    }

    public void setProcessBrFeedTime(long processBrFeedTime) {
        this.processBrFeedTime = processBrFeedTime;
    }

    public long getAeStartVolt0() {
        return aeStartVolt0;
    }

    public void setAeStartVolt0(long aeStartVolt0) {
        this.aeStartVolt0 = aeStartVolt0;
    }

    public long getAeStartVolt1() {
        return aeStartVolt1;
    }

    public void setAeStartVolt1(long aeStartVolt1) {
        this.aeStartVolt1 = aeStartVolt1;
    }

    public long getAeEndVolt() {
        return aeEndVolt;
    }

    public void setAeEndVolt(long aeEndVolt) {
        this.aeEndVolt = aeEndVolt;
    }

    public long getAeFeedCnt() {
        return aeFeedCnt;
    }

    public void setAeFeedCnt(long aeFeedCnt) {
        this.aeFeedCnt = aeFeedCnt;
    }

    public long getAeWaitLastTime() {
        return aeWaitLastTime;
    }

    public void setAeWaitLastTime(long aeWaitLastTime) {
        this.aeWaitLastTime = aeWaitLastTime;
    }

    public long getAeUpDownStep() {
        return aeUpDownStep;
    }

    public void setAeUpDownStep(long aeUpDownStep) {
        this.aeUpDownStep = aeUpDownStep;
    }

    public long getAeTreatTime() {
        return aeTreatTime;
    }

    public void setAeTreatTime(long aeTreatTime) {
        this.aeTreatTime = aeTreatTime;
    }

    public long getAeJudgeDur() {
        return aeJudgeDur;
    }

    public void setAeJudgeDur(long aeJudgeDur) {
        this.aeJudgeDur = aeJudgeDur;
    }

    public long getNoiseAddMaxVolt() {
        return noiseAddMaxVolt;
    }

    public void setNoiseAddMaxVolt(long noiseAddMaxVolt) {
        this.noiseAddMaxVolt = noiseAddMaxVolt;
    }

    public long getNoiseAddVoltTime() {
        return noiseAddVoltTime;
    }

    public void setNoiseAddVoltTime(long noiseAddVoltTime) {
        this.noiseAddVoltTime = noiseAddVoltTime;
    }

    public long getNoiseHigh() {
        return noiseHigh;
    }

    public void setNoiseHigh(long noiseHigh) {
        this.noiseHigh = noiseHigh;
    }

    public long getNoiseLow() {
        return noiseLow;
    }

    public void setNoiseLow(long noiseLow) {
        this.noiseLow = noiseLow;
    }

    public long getAlf3Time() {
        return alf3Time;
    }

    public void setAlf3Time(long alf3Time) {
        this.alf3Time = alf3Time;
    }

    public long getAdptCycleCoef() {
        return adptCycleCoef;
    }

    public void setAdptCycleCoef(long adptCycleCoef) {
        this.adptCycleCoef = adptCycleCoef;
    }

    public long getAdptOverCoef() {
        return adptOverCoef;
    }

    public void setAdptOverCoef(long adptOverCoef) {
        this.adptOverCoef = adptOverCoef;
    }

    public long getAdptUnderCoef() {
        return adptUnderCoef;
    }

    public void setAdptUnderCoef(long adptUnderCoef) {
        this.adptUnderCoef = adptUnderCoef;
    }

    public long getAdptNormalDur() {
        return adptNormalDur;
    }

    public void setAdptNormalDur(long adptNormalDur) {
        this.adptNormalDur = adptNormalDur;
    }

    public long getAdptUnderDur() {
        return adptUnderDur;
    }

    public void setAdptUnderDur(long adptUnderDur) {
        this.adptUnderDur = adptUnderDur;
    }

    public long getAdptOverDur() {
        return adptOverDur;
    }

    public void setAdptOverDur(long adptOverDur) {
        this.adptOverDur = adptOverDur;
    }

    public long getActionCrustTime1() {
        return actionCrustTime1;
    }

    public void setActionCrustTime1(long actionCrustTime1) {
        this.actionCrustTime1 = actionCrustTime1;
    }

    public long getActionCrustTime2() {
        return actionCrustTime2;
    }

    public void setActionCrustTime2(long actionCrustTime2) {
        this.actionCrustTime2 = actionCrustTime2;
    }

    public long getActionAl2o3FeedTime() {
        return actionAl2o3FeedTime;
    }

    public void setActionAl2o3FeedTime(long actionAl2o3FeedTime) {
        this.actionAl2o3FeedTime = actionAl2o3FeedTime;
    }

    public long getActionAlf3FeedTime() {
        return actionAlf3FeedTime;
    }

    public void setActionAlf3FeedTime(long actionAlf3FeedTime) {
        this.actionAlf3FeedTime = actionAlf3FeedTime;
    }

    public long getShellFeedCount() {
        return shellFeedCount;
    }

    public void setShellFeedCount(long shellFeedCount) {
        this.shellFeedCount = shellFeedCount;
    }

    public long getPlCurrentSwitch() {
        return plCurrentSwitch;
    }

    public void setPlCurrentSwitch(long plCurrentSwitch) {
        this.plCurrentSwitch = plCurrentSwitch;
    }

    public long getHighVoltageAlarm() {
        return highVoltageAlarm;
    }

    public void setHighVoltageAlarm(long highVoltageAlarm) {
        this.highVoltageAlarm = highVoltageAlarm;
    }

    public long getLowVoltageAlarm() {
        return lowVoltageAlarm;
    }

    public void setLowVoltageAlarm(long lowVoltageAlarm) {
        this.lowVoltageAlarm = lowVoltageAlarm;
    }

    public long getSerialTemp1() {
        return serialTemp1;
    }

    public void setSerialTemp1(long serialTemp1) {
        this.serialTemp1 = serialTemp1;
    }

    public long getSerialTemp2() {
        return serialTemp2;
    }

    public void setSerialTemp2(long serialTemp2) {
        this.serialTemp2 = serialTemp2;
    }

    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(216);
        buffer.put(longToHex(getbModifySend(),1));
        buffer.put(longToHex(getMachineNo(),1));
        buffer.put(longToHex(getSlotNo(),1));
        // 占位
        buffer.put(longToHex(0L,1));
        buffer.put(longToHex(getPlCurrent0(),2));
        buffer.put(longToHex(getPlCurrent1(),2));
        buffer.put(longToHex(getStopFeedCurrent0(),2));
        buffer.put(longToHex(getStopFeedCurrent1(),2));
        buffer.put(longToHex(getUpCurrentRange0(),2));
        buffer.put(longToHex(getDownCurrentRange0(),2));
        buffer.put(longToHex(getUpCurrentRange1(),2));
        buffer.put(longToHex(getDownCurrentRange1(),2));
        buffer.put(longToHex(getUpVoltageRange(),2));
        buffer.put(longToHex(getDownVoltageRange(),2));
        buffer.put(longToHex(getUpResistRange0(),2));
        buffer.put(longToHex(getDownResistRange0(),2));
        buffer.put(longToHex(getUpResistRange1(),2));
        buffer.put(longToHex(getDownResistRange1(),2));
        buffer.put(longToHex(getProcessMetalFeedTime(),2));
        buffer.put(longToHex(getProcessMetalAddVolt(),2));
        buffer.put(longToHex(getProcessMetalAddVoltTime(),2));
        buffer.put(longToHex(getProcessAcFeedTime(),2));
        buffer.put(longToHex(getProcessAcAddVolt(),2));
        buffer.put(longToHex(getProcessAcAddVoltTime(),2));
        buffer.put(longToHex(getAddSideFeedTime(),2));
        buffer.put(longToHex(getAddSideAddVolt(),2));
        buffer.put(longToHex(getAddSideAddVoltTime(),2));
        buffer.put(longToHex(getProcessBrFeedTime(),2));
        buffer.put(longToHex(getAeStartVolt0(),2));
        buffer.put(longToHex(getAeStartVolt1(),2));
        buffer.put(longToHex(getAeEndVolt(),2));
        buffer.put(longToHex(getAeFeedCnt(),2));
        buffer.put(longToHex(getAeWaitLastTime(),2));
        buffer.put(longToHex(getAeUpDownStep(),2));
        buffer.put(longToHex(getAeTreatTime(),2));
        buffer.put(longToHex(getAeJudgeDur(),2));
        buffer.put(longToHex(getNoiseAddMaxVolt(),2));
        buffer.put(longToHex(getNoiseAddVoltTime(),2));
        buffer.put(longToHex(getNoiseHigh(),2));
        buffer.put(longToHex(getNoiseLow(),2));
        buffer.put(longToHex(getAlf3Time(),2));
        buffer.put(longToHex(getAdptCycleCoef(),2));
        buffer.put(longToHex(getAdptOverCoef(),2));
        buffer.put(longToHex(getAdptUnderCoef(),2));
        buffer.put(longToHex(getAdptNormalDur(),2));
        buffer.put(longToHex(getAdptUnderDur(),2));
        buffer.put(longToHex(getAdptOverDur(),2));
        buffer.put(longToHex(getActionCrustTime1(),2));
        buffer.put(longToHex(getActionCrustTime2(),2));
        buffer.put(longToHex(getActionAl2o3FeedTime(),2));
        buffer.put(longToHex(getActionAlf3FeedTime(),2));
        buffer.put(longToHex(getShellFeedCount(),2));
        buffer.put(longToHex(getPlCurrentSwitch(),2));
        buffer.put(longToHex(getHighVoltageAlarm(),2));
        buffer.put(longToHex(getLowVoltageAlarm(),2));
        buffer.put(longToHex(getSerialTemp1(),2));
        buffer.put(longToHex(getSerialTemp2(),2));
        buffer.put(longToHex(getRealSlotNo(),2));
        buffer.put(longToHex(new Date().getTime(),4));
        buffer.put(strToHexString(getAuthor()).getBytes());
        return buffer.array();

    }

    public static byte[] longToHex(long longData, int length) {
        byte[] returnByte = new byte[length];
        if (length > 0) {
            for(int i = 0; i< length; i++){
                if(i == 0){
                    returnByte[i] = (byte) (longData & 0xff);// 最低位
                    continue;
                }
                if(i == 1){
                    returnByte[i] = (byte) ((longData >> 8) & 0xff);// 次低位
                    continue;
                }
                if(i == 2){
                    returnByte[i] = (byte) ((longData >> 16) & 0xff);// 次高位
                    continue;
                }
                if(i == 3){
                    returnByte[i] = (byte) (longData >>> 24);// 最高位,无符号右移。
                }
            }
        }
        return returnByte;
    }

    private String strToHexString(String s){
        String str = "";
        for (int i = 0; i <s.length(); i ++ ){
            int ch =s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        while (str.length() < 100) {
            str = "0" + str;
        }
        System.out.println(str.length());
        return str;
    }

    public static SlotSingleParam toSlotSerialParam(byte[] data_byte){
        SlotSingleParam slotSingleParam = new SlotSingleParam();
        byte[] byte_val;

        byte_val = new byte[2];
        byte_val[0] = data_byte[110];
        byte_val[1] = data_byte[111];
        slotSingleParam.setRealSlotNo(SlotSingleParam.byteArrayToLong(byte_val));

        return slotSingleParam;
    }
}
