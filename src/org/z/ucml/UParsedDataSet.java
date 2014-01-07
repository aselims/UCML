package org.z.ucml;

import java.io.Serializable;

public class UParsedDataSet implements Serializable {

	/**
	 * schema should be: <sensor name="accelerometer|orientation|magneticfield">
	 * 		<reading type="threshold" threshold=""/> 
	 * 		|<reading type="threshold"
	 *all="yes" x="" y="" z=""/> // "yes": all dimensions |<reading
	 * type="threshold" all="no" x="" y="" z=""/> // "no": any or all dimensions
	 * |<reading type="range" all="no" xmin="" ymin="" zmin="" xmax="" ymax=""
	 * zmax=""/> //"no": any or all dimensions |<reading type="range" all="yes"
	 * xmin="" ymin="" zmin="" xmax="" ymax="" zmax=""/> //"yes": all dimensions
	 * 
	 * 		<response action="notify" msg=""/> 
	 * 		|<response action="vibrate"
	 * pattern="values of msec" repeat="number|no"/> 
	 * 		|<response action="sendsms"
	 * destination="" sms=""/>
	 * |<response action="senddata"
	 * destination="" data="" port=""/>
	 * |<response action="openweb" web=""/>
	 * 
	 * |<log filename="" period="" /> // path:/data/data/[package]/files/[fn].xml
	 *  </sensor>
	 * 
	 *  <!-- sensor_type to be implemented -->
	 */
	private static final long serialVersionUID = 1L;
	private String SensorType = null;
	private String SensorName = null;

	private String ReadingType = null;
	private String ReadingAll = "yon";
	private float ReadingThresholdX = 1000;
	private float ReadingThresholdY = 1000;
	private float ReadingThresholdZ = 1000;
	private float ReadingThreshold = 1000; // general threshold
	private float ReadingRangeXmin = 1000;
	private float ReadingRangeYmin = 1000;
	private float ReadingRangeZmin = 1000;
	private float ReadingRangeXmax = 1000;
	private float ReadingRangeYmax = 1000;
	private float ReadingRangeZmax = 1000;

	private String ResponseType = "builtin";
	private String ResponseName = "notify";
	private String ResponseNotifyMsg = null;
	private long[] ResponseVibratorPattern = null;
	private String ResponseVibratorRepeat = "no";
	private String ResponseSendMsgDest = null;
	private String ResponseSendSms = null;
	private byte[] ResponseSendData = null;
	private short ResponseSendDataPort;
	private String ResponseWeb = null;
	private String LogFileName = null;
	private long LogPeriod = 5;
	private String ResponseDialNumber = null;
	private String ResponseFeature=null;
	private String ResponseContinous="no";
	
	

	/**
	 * @return the responseContinous
	 */
	public String getResponseContinous() {
		return ResponseContinous;
	}

	/**
	 * @param responseContinous the responseContinous to set
	 */
	public void setResponseContinous(String responseContinous) {
		ResponseContinous = responseContinous;
	}

	/**
	 * @return the responseSendDataPort
	 */
	public short getResponseSendDataPort() {
		return ResponseSendDataPort;
	}

	/**
	 * @return the responseFeature
	 */
	public String getResponseFeature() {
		return ResponseFeature;
	}

	/**
	 * @param responseFeature the responseFeature to set
	 */
	public void setResponseFeature(String responseFeature) {
		ResponseFeature = responseFeature;
	}

	/**
	 * @param responseSendDataPort
	 *            the responseSendDataPort to set
	 */
	public void setResponseSendDataPort(short responseSendDataPort) {
		ResponseSendDataPort = responseSendDataPort;
	}

	/**
	 * @return the responseSendsms
	 */
	public String getResponseSendSms() {
		return ResponseSendSms;
	}

	/**
	 * @param responseSendsms
	 *            the responseSendsms to set
	 */
	public void setResponseSendSms(String responseSendSms) {
		ResponseSendSms = responseSendSms;
	}

	/**
	 * @return the responseSendData
	 */
	public byte[] getResponseSendData() {
		return ResponseSendData;
	}

	/**
	 * @param responseSendData
	 *            the responseSendData to set
	 */
	public void setResponseSendData(byte[] responseSendData) {
		ResponseSendData = responseSendData;
	}

	/**
	 * @return the logFileName
	 */
	public String getLogFileName() {
		return LogFileName;
	}

	/**
	 * @param logFileName
	 *            the logFileName to set
	 */
	public void setLogFileName(String logFileName) {
		LogFileName = logFileName;
	}

	/**
	 * @return the logPeriod
	 */
	public long getLogPeriod() {
		return LogPeriod;
	}

	/**
	 * @param logPeriod
	 *            the logPeriod to set
	 */
	public void setLogPeriod(long logPeriod) {
		LogPeriod = logPeriod;
	}

	/**
	 * @return the responseWeb
	 */
	public String getResponseWeb() {
		return ResponseWeb;
	}

	/**
	 * @param responseWeb
	 *            the responseWeb to set
	 */
	public void setResponseWeb(String responseWeb) {
		ResponseWeb = responseWeb;
	}

	/**
	 * @return the responseSendMsgDest
	 */
	public String getResponseSendMsgDest() {
		return ResponseSendMsgDest;
	}

	/**
	 * @param responseSendMsgDest
	 *            the responseSendMsgDest to set
	 */
	public void setResponseSendMsgDest(String responseSendMsgDest) {
		ResponseSendMsgDest = responseSendMsgDest;
	}

	/**
	 * @param responseSendMsgPort
	 *            the responseSendMsgPort to set
	 */

	/**
	 * @return the readingAll
	 */
	public String getReadingAll() {
		return ReadingAll;
	}

	/**
	 * @param readingAll
	 *            the readingAll to set
	 */
	public void setReadingAll(String readingAll) {
		ReadingAll = readingAll;
	}

	/**
	 * @return the responseVibratorRepeat
	 */
	public String getResponseVibratorRepeat() {
		return ResponseVibratorRepeat;
	}

	/**
	 * @param responseVibratorRepeat
	 *            the responseVibratorRepeat to set
	 */
	public void setResponseVibratorRepeat(String responseVibratorRepeat) {
		ResponseVibratorRepeat = responseVibratorRepeat;
	}

	/**
	 * @return the responseNotifyMsg
	 */
	public String getResponseNotifyMsg() {
		return ResponseNotifyMsg;
	}

	/**
	 * @param responseNotifyMsg
	 *            the responseNotifyMsg to set
	 */
	public void setResponseNotifyMsg(String responseNotifyMsg) {
		ResponseNotifyMsg = responseNotifyMsg;
	}

	/**
	 * @return the responseVibratorPattern
	 */
	public long[] getResponseVibratorPattern() {
		return ResponseVibratorPattern;
	}

	/**
	 * @param responseVibratorPattern
	 *            the responseVibratorPattern to set
	 */
	public void setResponseVibratorPattern(long[] responseVibratorPattern) {
		ResponseVibratorPattern = responseVibratorPattern;
	}

	/**
	 * @return the readingRangeXmin
	 */
	public float getReadingRangeXmin() {
		return ReadingRangeXmin;
	}

	/**
	 * @param readingRangeXmin
	 *            the readingRangeXmin to set
	 */
	public void setReadingRangeXmin(float readingRangeXmin) {
		ReadingRangeXmin = readingRangeXmin;
	}

	/**
	 * @return the readingRangeYmin
	 */
	public float getReadingRangeYmin() {
		return ReadingRangeYmin;
	}

	/**
	 * @param readingRangeYmin
	 *            the readingRangeYmin to set
	 */
	public void setReadingRangeYmin(float readingRangeYmin) {
		ReadingRangeYmin = readingRangeYmin;
	}

	/**
	 * @return the readingRangeZmin
	 */
	public float getReadingRangeZmin() {
		return ReadingRangeZmin;
	}

	/**
	 * @param readingRangeZmin
	 *            the readingRangeZmin to set
	 */
	public void setReadingRangeZmin(float readingRangeZmin) {
		ReadingRangeZmin = readingRangeZmin;
	}

	/**
	 * @return the readingRangeXmax
	 */
	public float getReadingRangeXmax() {
		return ReadingRangeXmax;
	}

	/**
	 * @param readingRangeXmax
	 *            the readingRangeXmax to set
	 */
	public void setReadingRangeXmax(float readingRangeXmax) {
		ReadingRangeXmax = readingRangeXmax;
	}

	/**
	 * @return the readingRangeYmax
	 */
	public float getReadingRangeYmax() {
		return ReadingRangeYmax;
	}

	/**
	 * @param readingRangeYmax
	 *            the readingRangeYmax to set
	 */
	public void setReadingRangeYmax(float readingRangeYmax) {
		ReadingRangeYmax = readingRangeYmax;
	}

	/**
	 * @return the readingRangeZmax
	 */
	public float getReadingRangeZmax() {
		return ReadingRangeZmax;
	}

	/**
	 * @param readingRangeZmax
	 *            the readingRangeZmax to set
	 */
	public void setReadingRangeZmax(float readingRangeZmax) {
		ReadingRangeZmax = readingRangeZmax;
	}

	/**
	 * @return the readingThresholdX
	 */
	public float getReadingThresholdX() {
		return ReadingThresholdX;
	}

	/**
	 * @param readingThresholdX
	 *            the readingThresholdX to set
	 */
	public void setReadingThresholdX(float readingThresholdX) {
		ReadingThresholdX = readingThresholdX;
	}

	/**
	 * @return the readingThresholdY
	 */
	public float getReadingThresholdY() {
		return ReadingThresholdY;
	}

	/**
	 * @param readingThresholdY
	 *            the readingThresholdY to set
	 */
	public void setReadingThresholdY(float readingThresholdY) {
		ReadingThresholdY = readingThresholdY;
	}

	/**
	 * @return the readingThresholdZ
	 */
	public float getReadingThresholdZ() {
		return ReadingThresholdZ;
	}

	/**
	 * @param readingThresholdZ
	 *            the readingThresholdZ to set
	 */
	public void setReadingThresholdZ(float readingThresholdZ) {
		ReadingThresholdZ = readingThresholdZ;
	}

	// getter for final app
	// setter for getting data from xml

	/**
	 * @return the readingThreshold
	 */
	public float getReadingThreshold() {
		return ReadingThreshold;
	}

	/**
	 * @param readingThreshold
	 *            the readingThreshold to set
	 */
	public void setReadingThreshold(float readingThreshold) {
		ReadingThreshold = readingThreshold;
	}

	/**
	 * @return the sensorType
	 */
	public String getSensorType() {
		return SensorType;
	}

	/**
	 * @param sensorType
	 *            the sensorType to set
	 */
	public void setSensorType(String sensorType) {
		SensorType = sensorType;
	}

	/**
	 * @return the sensorName
	 */
	public String getSensorName() {
		return SensorName;
	}

	/**
	 * @param sensorName
	 *            the sensorName to set
	 */
	public void setSensorName(String sensorName) {
		SensorName = sensorName;
	}

	/**
	 * @return the readingType
	 */
	public String getReadingType() {
		return ReadingType;
	}

	/**
	 * @param readingType
	 *            the readingType to set
	 */
	public void setReadingType(String readingType) {
		ReadingType = readingType;
	}

	/**
	 * @return the readingThreshold
	 */
	/**
	 * @return the responseType
	 */
	public String getResponseType() {
		return ResponseType;
	}

	/**
	 * @param responseType
	 *            the responseType to set
	 */
	public void setResponseType(String responseType) {
		ResponseType = responseType;
	}

	/**
	 * @return the responseName
	 */
	public String getResponseName() {
		return ResponseName;
	}

	/**
	 * @param responseName
	 *            the responseName to set
	 */
	public void setResponseName(String responseName) {
		ResponseName = responseName;
	}

	/**
	 * @return the responseDialNumber
	 */
	public String getResponseDialNumber() {
		return ResponseDialNumber;
	}

	/**
	 * @param responseDialNumber the responseDialNumber to set
	 */
	public void setResponseDialNumber(String responseDialNumber) {
		ResponseDialNumber = responseDialNumber;
	}
	

	

	/*
	 * public String toString(){ return "ExtractedString = " +
	 * this.extractedString + "\nExtractedInt = " + this.extractedInt; }
	 */
}