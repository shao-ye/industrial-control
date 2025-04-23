package com.ruoyi.industry.domain;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * 单槽参数对象 SlotSingleParam
 *
 * @author shao
 * @date 2022-04-05
 */
public class SlotSingleParam {

    private int bModifySend;
    /** 厂号 */
    private long machineNo;

    private long slotNo;

    /** 浓度自适应 */
    private long adaptiveSwitch;

    private long noiseSwitch;

    private long noiseMonSwitch;

    private long aeCancelSwitch;

    private long aeQuenchSwitch;

    private long alf3Switch;

    private long temperatureSwitch;

    private long valveSwitch;
    /** 联机开关 */
    private long onOffLineSwitch;
    /** 工作状态：焙烧/启动/运行/非生产 */
    private long onOffPrestaSwitch;
    /** 阳极升降控制开关 */
    private long electrodeSwitch;
    /** 备用控制开关1 */
    private long spareSwitch1;
    /** 备用控制开关2 */
    private long spareSwitch2;
    /* 未提供 */
    private long btSerialCurrentRepairSwitch;
    /* 未提供 */
    private long btStopFeedSwitch;
    /** 槽号 */
    private long realSlotNo;
    /** 设定电压 */
    private long volt;
    /** 设定间隔 */
    private long feedIntvl0;

    private long feedIntvl1;

    private long aeIntvl;

    /** 日氟盐量 */
    private long alf3Mass;

    private long alf3Intvl;

    private long bvAngle;

    private long currentUpLimit;

    private long currentDownLimit;

    private long paraSpare01;

    /** 修改时间 */
    private Date signTime;

    /** 编辑者 */
    private String author;

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

    public long getAdaptiveSwitch() {
        return adaptiveSwitch;
    }

    public void setAdaptiveSwitch(long adaptiveSwitch) {
        this.adaptiveSwitch = adaptiveSwitch;
    }

    public long getNoiseSwitch() {
        return noiseSwitch;
    }

    public void setNoiseSwitch(long noiseSwitch) {
        this.noiseSwitch = noiseSwitch;
    }

    public long getNoiseMonSwitch() {
        return noiseMonSwitch;
    }

    public void setNoiseMonSwitch(long noiseMonSwitch) {
        this.noiseMonSwitch = noiseMonSwitch;
    }

    public long getAeCancelSwitch() {
        return aeCancelSwitch;
    }

    public void setAeCancelSwitch(long aeCancelSwitch) {
        this.aeCancelSwitch = aeCancelSwitch;
    }

    public long getAeQuenchSwitch() {
        return aeQuenchSwitch;
    }

    public void setAeQuenchSwitch(long aeQuenchSwitch) {
        this.aeQuenchSwitch = aeQuenchSwitch;
    }

    public long getAlf3Switch() {
        return alf3Switch;
    }

    public void setAlf3Switch(long alf3Switch) {
        this.alf3Switch = alf3Switch;
    }

    public long getTemperatureSwitch() {
        return temperatureSwitch;
    }

    public void setTemperatureSwitch(long temperatureSwitch) {
        this.temperatureSwitch = temperatureSwitch;
    }

    public long getValveSwitch() {
        return valveSwitch;
    }

    public void setValveSwitch(long valveSwitch) {
        this.valveSwitch = valveSwitch;
    }

    public long getOnOffLineSwitch() {
        return onOffLineSwitch;
    }

    public void setOnOffLineSwitch(long onOffLineSwitch) {
        this.onOffLineSwitch = onOffLineSwitch;
    }

    public long getOnOffPrestaSwitch() {
        return onOffPrestaSwitch;
    }

    public void setOnOffPrestaSwitch(long onOffPrestaSwitch) {
        this.onOffPrestaSwitch = onOffPrestaSwitch;
    }

    public long getElectrodeSwitch() {
        return electrodeSwitch;
    }

    public void setElectrodeSwitch(long electrodeSwitch) {
        this.electrodeSwitch = electrodeSwitch;
    }

    public long getSpareSwitch1() {
        return spareSwitch1;
    }

    public void setSpareSwitch1(long spareSwitch1) {
        this.spareSwitch1 = spareSwitch1;
    }

    public long getSpareSwitch2() {
        return spareSwitch2;
    }

    public void setSpareSwitch2(long spareSwitch2) {
        this.spareSwitch2 = spareSwitch2;
    }

    public long getRealSlotNo() {
        return realSlotNo;
    }

    public void setRealSlotNo(long realSlotNo) {
        this.realSlotNo = realSlotNo;
    }

    public long getVolt() {
        return volt;
    }

    public void setVolt(long volt) {
        this.volt = volt;
    }

    public long getFeedIntvl0() {
        return feedIntvl0;
    }

    public void setFeedIntvl0(long feedIntvl0) {
        this.feedIntvl0 = feedIntvl0;
    }

    public long getFeedIntvl1() {
        return feedIntvl1;
    }

    public void setFeedIntvl1(long feedIntvl1) {
        this.feedIntvl1 = feedIntvl1;
    }

    public long getAeIntvl() {
        return aeIntvl;
    }

    public void setAeIntvl(long aeIntvl) {
        this.aeIntvl = aeIntvl;
    }

    public long getAlf3Mass() {
        return alf3Mass;
    }

    public void setAlf3Mass(long alf3Mass) {
        this.alf3Mass = alf3Mass;
    }

    public long getAlf3Intvl() {
        return alf3Intvl;
    }

    public void setAlf3Intvl(long alf3Intvl) {
        this.alf3Intvl = alf3Intvl;
    }

    public long getBvAngle() {
        return bvAngle;
    }

    public void setBvAngle(long bvAngle) {
        this.bvAngle = bvAngle;
    }

    public long getCurrentUpLimit() {
        return currentUpLimit;
    }

    public void setCurrentUpLimit(long currentUpLimit) {
        this.currentUpLimit = currentUpLimit;
    }

    public long getCurrentDownLimit() {
        return currentDownLimit;
    }

    public void setCurrentDownLimit(long currentDownLimit) {
        this.currentDownLimit = currentDownLimit;
    }

    public long getParaSpare01() {
        return paraSpare01;
    }

    public void setParaSpare01(long paraSpare01) {
        this.paraSpare01 = paraSpare01;
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

    public long getBtSerialCurrentRepairSwitch() {
        return btSerialCurrentRepairSwitch;
    }

    public void setBtSerialCurrentRepairSwitch(long btSerialCurrentRepairSwitch) {
        this.btSerialCurrentRepairSwitch = btSerialCurrentRepairSwitch;
    }

    public long getBtStopFeedSwitch() {
        return btStopFeedSwitch;
    }

    public void setBtStopFeedSwitch(long btStopFeedSwitch) {
        this.btStopFeedSwitch = btStopFeedSwitch;
    }

    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(164);
        buffer.put(longToHex(getbModifySend(),1));
        buffer.put(longToHex(getMachineNo(),1));
        buffer.put(longToHex(getSlotNo(),1));
        buffer.put(longToHex(getAdaptiveSwitch(),1));
        buffer.put(longToHex(getNoiseSwitch(),1));
        buffer.put(longToHex(getNoiseMonSwitch(),1));
        buffer.put(longToHex(getAeCancelSwitch(),1));
        buffer.put(longToHex(getAeQuenchSwitch(),1));
        buffer.put(longToHex(getAlf3Switch(),1));
        buffer.put(longToHex(getTemperatureSwitch(),1));
        buffer.put(longToHex(getValveSwitch(),1));
        buffer.put(longToHex(getOnOffLineSwitch(),1));
        buffer.put(longToHex(getOnOffPrestaSwitch(),1));
        buffer.put(longToHex(getElectrodeSwitch(),1));
        buffer.put(longToHex(getSpareSwitch1(),1));
        buffer.put(longToHex(getSpareSwitch2(),1));
        buffer.put(longToHex(getBtSerialCurrentRepairSwitch(),1));
        buffer.put(longToHex(getBtStopFeedSwitch(),1));

        buffer.put(longToHex(getRealSlotNo(),2));

        buffer.put(longToHex(getVolt(),4));
        buffer.put(longToHex(getFeedIntvl0(),4));
        buffer.put(longToHex(getFeedIntvl1(),4));
        buffer.put(longToHex(getAeIntvl(),4));
        buffer.put(longToHex(getAlf3Mass(),4));
        buffer.put(longToHex(getAlf3Intvl(),4));
        buffer.put(longToHex(getBvAngle(),4));
        buffer.put(longToHex(getCurrentUpLimit(),4));
        buffer.put(longToHex(getCurrentDownLimit(),4));
        buffer.put(longToHex(getParaSpare01(),4));
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

    public static int byteArrayToLong(byte[] b) {
        if(b.length == 1)
            return b[0] & 0xFF;
        if(b.length == 2)
            return b[0] & 0xFF | (b[1] & 0xFF) << 8;
        if(b.length == 3)
            return b[0] & 0xFF | (b[1] & 0xFF) << 8 | (b[2] & 0xFF) << 16;
        if(b.length == 4)
            return b[0] & 0xFF | (b[1] & 0xFF) << 8 | (b[2] & 0xFF) << 16 | (b[3] & 0xFF) << 24;
        return 0;
    }

    public static SlotSingleParam toSlotSingleParam(byte[] data_byte){
        SlotSingleParam slotSingleParam = new SlotSingleParam();
        byte[] byte_val;
        byte_val = new byte[1];
        byte_val[0] = data_byte[0];
        slotSingleParam.setbModifySend(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[1];
        slotSingleParam.setMachineNo(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[2];
        slotSingleParam.setSlotNo(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[3];
        slotSingleParam.setAdaptiveSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[4];
        slotSingleParam.setNoiseSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[5];
        slotSingleParam.setNoiseMonSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[6];
        slotSingleParam.setAeCancelSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[7];
        slotSingleParam.setAeQuenchSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[8];
        slotSingleParam.setAlf3Switch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[9];
        slotSingleParam.setTemperatureSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[10];
        slotSingleParam.setValveSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[11];
        slotSingleParam.setOnOffLineSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[12];
        slotSingleParam.setOnOffPrestaSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[13];
        slotSingleParam.setElectrodeSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[14];
        slotSingleParam.setSpareSwitch1(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[15];
        slotSingleParam.setSpareSwitch2(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[16];
        slotSingleParam.setBtSerialCurrentRepairSwitch(SlotSingleParam.byteArrayToLong(byte_val));
        byte_val[0] = data_byte[17];
        slotSingleParam.setBtStopFeedSwitch(SlotSingleParam.byteArrayToLong(byte_val));

        byte_val = new byte[2];
        byte_val[0] = data_byte[18];
        byte_val[1] = data_byte[19];
        slotSingleParam.setRealSlotNo(SlotSingleParam.byteArrayToLong(byte_val));

        byte_val = new byte[4];
        byte_val[0] = data_byte[20];
        byte_val[1] = data_byte[21];
        byte_val[2] = data_byte[22];
        byte_val[3] = data_byte[23];
        slotSingleParam.setVolt(SlotSingleParam.byteArrayToLong(byte_val));

        return slotSingleParam;
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
}
