package org.z.ucml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UCMLHandler extends DefaultHandler {

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean in_sensor = false;
	private boolean in_reading = false;
	private boolean in_response = false;
	private boolean in_log = false;

	private UParsedDataSet myParsedUcmlDataSet = new UParsedDataSet();

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public UParsedDataSet getParsedData() {
		return this.myParsedUcmlDataSet;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		this.myParsedUcmlDataSet = new UParsedDataSet();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	/**
	 * Gets be called on opening tags like: <tag> Can provide attribute(s), when
	 * xml was like: <tag attribute="attributeValue">
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equalsIgnoreCase("sensor")) {
			this.in_sensor = true;
			if (atts.getValue("name") != null)
				myParsedUcmlDataSet.setSensorName(atts.getValue("name"));
			if (atts.getValue("type") != null)
				myParsedUcmlDataSet.setSensorType(atts.getValue("type"));
		} else if (localName.equalsIgnoreCase("reading")) {
			this.in_reading = true;
			if (atts.getValue("type") != null)
				myParsedUcmlDataSet.setReadingType(atts.getValue("type"));
			if (atts.getValue("threshold") != null)
				myParsedUcmlDataSet.setReadingThreshold(Float.parseFloat(atts
						.getValue("threshold")));
			if (atts.getValue("all") != null)
				myParsedUcmlDataSet.setReadingAll(atts.getValue("all"));
			if (atts.getValue("x") != null)
				myParsedUcmlDataSet.setReadingThresholdX(Float.parseFloat(atts
						.getValue("x")));
			if (atts.getValue("y") != null)
				myParsedUcmlDataSet.setReadingThresholdY(Float.parseFloat(atts
						.getValue("y")));
			if (atts.getValue("z") != null)
				myParsedUcmlDataSet.setReadingThresholdZ(Float.parseFloat(atts
						.getValue("z")));
			if (atts.getValue("xmin") != null)
				myParsedUcmlDataSet.setReadingRangeXmin(Float.parseFloat(atts
						.getValue("xmin")));
			if (atts.getValue("ymin") != null)
				myParsedUcmlDataSet.setReadingRangeYmin(Float.parseFloat(atts
						.getValue("ymin")));
			if (atts.getValue("zmin") != null)
				myParsedUcmlDataSet.setReadingRangeZmin(Float.parseFloat(atts
						.getValue("zmin")));
			if (atts.getValue("xmax") != null)
				myParsedUcmlDataSet.setReadingRangeXmax(Float.parseFloat(atts
						.getValue("xmax")));
			if (atts.getValue("ymax") != null)
				myParsedUcmlDataSet.setReadingRangeYmax(Float.parseFloat(atts
						.getValue("ymax")));
			if (atts.getValue("zmax") != null)
				myParsedUcmlDataSet.setReadingRangeZmax(Float.parseFloat(atts
						.getValue("zmax")));
		} else if (localName.equalsIgnoreCase("response")) {
			this.in_response = true;

			if (atts.getValue("action") != null)
				myParsedUcmlDataSet.setResponseName(atts.getValue("action"));
			if (atts.getValue("type") != null)
				myParsedUcmlDataSet.setResponseType(atts.getValue("type"));
			if (atts.getValue("msg") != null)
				myParsedUcmlDataSet.setResponseNotifyMsg(atts.getValue("msg"));
			if (atts.getValue("pattern") != null) {
				String str = atts.getValue("pattern");
				String[] results = str.split(" ");
				int i = 0;
				long[] p = new long[results.length];
				while (i < results.length) {
					p[i] = Long.parseLong(results[i]);
					i++;
				}
				myParsedUcmlDataSet.setResponseVibratorPattern(p);
			}
			if(atts.getValue("web") != null)
				myParsedUcmlDataSet.setResponseWeb(atts.getValue("web"));
			if(atts.getValue("port") != null)
				myParsedUcmlDataSet.setResponseSendDataPort(Short.parseShort(atts.getValue("port")));
			if(atts.getValue("data") != null)
				myParsedUcmlDataSet.setResponseSendData((atts.getValue("data").getBytes()));
			if(atts.getValue("sms") != null)
				myParsedUcmlDataSet.setResponseSendSms(atts.getValue("sms"));
			if(atts.getValue("destination") != null)
				myParsedUcmlDataSet.setResponseSendMsgDest(atts.getValue("destination"));
			if(atts.getValue("number") != null)
				myParsedUcmlDataSet.setResponseDialNumber(atts.getValue("number"));
			if (atts.getValue("feature") != null)
			myParsedUcmlDataSet.setResponseFeature(atts.getValue("feature"));
			if (atts.getValue("continuous") != null)
				myParsedUcmlDataSet.setResponseContinous(atts.getValue("continuous"));

			
		}else if(localName.equalsIgnoreCase("log")) {
			this.in_log=true;
			if(atts.getValue("filename") != null)
				myParsedUcmlDataSet.setLogFileName(atts.getValue("filename"));
			if(atts.getValue("period") != null)
				myParsedUcmlDataSet.setLogPeriod(Long.parseLong(atts.getValue("period")));
		}

	}

	/**
	 * Gets called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase("sensor")) {
			this.in_sensor = false;
		} else if (localName.equalsIgnoreCase("reading")) {
			this.in_reading = false;
		} else if (localName.equalsIgnoreCase("response")) {
			this.in_response = false;
		}else if (localName.equalsIgnoreCase("log")) {
			this.in_response = false;}
	}

	/**
	 * Gets be called on the following structure: <tag>characters</tag>
	 */
	/*
	 * @Override public void characters(char ch[], int start, int length) {
	 * if(this.in_response){ myParsedUcmlDataSet.setExtractedString(new
	 * String(ch, start, length)); } }
	 */
}