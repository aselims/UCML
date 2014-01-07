package org.z.ucml;

//XmlDocument doc = new XmlDocument();
//doc.LoadXml(xmlstring);

//InputSource s = new InputSource(new StringReader(myString)));

/*StrinReader reader = new StringReader(yourString);
 InputSource source = new InputSource(reader);
 parser.parse(source, someHandlerObject);
 */

import static android.hardware.SensorManager.SENSOR_ACCELEROMETER;
import static android.hardware.SensorManager.SENSOR_DELAY_UI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
import org.openintents.sensorsimulator.hardware.SensorNames;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UParse extends Activity implements SensorListener {

	private Long mRowId;
	private UDbAdapter mDbHelper;
	private String XMLString = null;
	private String XMLTitle = null;
	private static final String TAG = "Logging UParse";
	private boolean ResponseFlag = false;
	private int i = 0;
	//private SensorManagerSimulator mSensorManager;
	SensorManager mSensorManager;
	WifiManager wifi;
	private float acc_x, acc_y, acc_z;
	private float mag_x, mag_y, mag_z;
	private float ori_x, ori_y, ori_z;
	private float tem_x;
	UCMLHandler ucmlHandler = new UCMLHandler();
	UParsedDataSet myParsedDataSet;
	long EndTime;
	StringBuilder T;

	TextView mTextView1;
	TextView mTextView2;
	TextView mTextView3;
	TextView mTextView4;
	Button mB;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.u_fun);
		mDbHelper = new UDbAdapter(this);
		mDbHelper.open();

		Bundle extras3 = getIntent().getExtras();
		if (extras3 != null) {
			mRowId = (long) extras3.getLong(UDbAdapter.KEY_ROWID);
			Log.d(TAG, mRowId.toString());
		}

		Cursor note = mDbHelper.fetchNote(mRowId);
		startManagingCursor(note);
		XMLTitle = note.getString(
				note.getColumnIndexOrThrow(UDbAdapter.KEY_TITLE)).toString();
		XMLString = note.getString(
				note.getColumnIndexOrThrow(UDbAdapter.KEY_BODY)).toString();
		// XMLString =
		// "<?xml version='1.0'?> <outertag><innertag sampleattribute='innertagAttribute'><mytag>anddev.org rulez =)</mytag><tagwithnumber thenumber='1337'/></innertag></outertag>";

		// mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		/*mSensorManager = SensorManagerSimulator.getSystemService(this,
				SENSOR_SERVICE);
		mSensorManager.connectSimulator();*/
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		mTextView1 = (TextView) findViewById(R.id.xx);
		mTextView2 = (TextView) findViewById(R.id.yy);
		mTextView3 = (TextView) findViewById(R.id.zz);
		mTextView4 = (TextView) findViewById(R.id.describe);
		mB = (Button) findViewById(R.id.goback);

		try {

			InputSource s = new InputSource(new StringReader(XMLString));
			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();
			/* Create a new ContentHandler and apply it to the XML-Reader */
			UCMLHandler ucmlHandler = new UCMLHandler();

			xr.setContentHandler(ucmlHandler);

			/* Parse the xml-data from our URL. */
			xr.parse(s);
			/* Parsing has finished. */
			
			/* Our ExampleHandler now provides the parsed data to us. */
			myParsedDataSet = ucmlHandler.getParsedData();
			// ParsedExampleDataSet parsedExampleDataSet = new
			// ParsedExampleDataSet();

			// String[] x=SensorNames.getSensorNames(0);
			// int z;
			/*
			 * if(myParsedDataSet.getLogFileName() != null) startLogging();
			 */
			/*
			 * new Thread() {
			 * 
			 * @Override public void run() { try { while (!isInterrupted()) { if
			 * (myParsedDataSet.getSensorName()
			 * .equalsIgnoreCase("accelerometer")) callAcc(); else if
			 * (myParsedDataSet.getSensorName()
			 * .equalsIgnoreCase("orientation")) callOri(); else if
			 * (myParsedDataSet.getSensorName()
			 * .equalsIgnoreCase("magneticfield")) callMag(); else if
			 * (myParsedDataSet.getSensorName() .equalsIgnoreCase("temprature"))
			 * callTem(); Thread.sleep(1000); } } catch (Exception e) {
			 * Log.e(TAG, "threadingggggg", e); } } }.start();
			 */
			wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			// startSensing();

			/* Set the result to be displayed in our GUI. */
			// tv.setText(parsedUcmlDataSet.toString());

		} catch (Exception e) {
			/* Display any Error to the GUI. */
			mTextView3.setText("Error: " + e.getMessage());
			// Log.e(MY_DEBUG_TAG, "hasta", e);
		}
		/* Display the TextView. */
		this.mB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UParse.this.moveTaskToBack(true);
				/*
				 * Object x = null; Intent int1 = new Intent(UParse.this,
				 * UService.class); int1.putExtra((String) x, myParsedDataSet);
				 * startService(int1);
				 */
			}
		});
		T = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		if (myParsedDataSet.getLogFileName() != null) {
			Date d = new Date();
			EndTime = System.currentTimeMillis()
					+ myParsedDataSet.getLogPeriod() * 1000;

			T.append("<!-- Generated by UCML at " + d.toString() + " for "
					+ myParsedDataSet.getLogPeriod() + " Sec" + " --> \n");
			T.append("<sensor name = \"" + myParsedDataSet.getSensorName()
					+ " \"> \n");
			mTextView4.setText("Now logging...");
		}

	}

	private void startLogging() {

		if (myParsedDataSet.getSensorName().equalsIgnoreCase("orientation")) {
			T.append("<reading>");
			T.append("<x>" + ori_x + "</x>\n");
			T.append("<y>" + ori_y + "</y>\n");
			T.append("<z>" + ori_z + "</z>\n");
			T.append("</reading>");
		} else if (myParsedDataSet.getSensorName().equalsIgnoreCase(
				"magneticfield")) {
			T.append("<reading>");
			T.append("<x>" + mag_x + "</x>\n");
			T.append("<y>" + mag_y + "</y>\n");
			T.append("<z>" + mag_z + "</z>\n");
			T.append("</reading>");
		} else if (myParsedDataSet.getSensorName().equalsIgnoreCase(
				"accelerometer")) {
			T.append("<reading>");
			T.append("<x>" + acc_x + "</x>\n");
			T.append("<y>" + acc_y + "</y>\n");
			T.append("<z>" + acc_z + "</z>\n");
			T.append("</reading>");
		}

		/*
		 * // ##### Read the file back in #####
		 * 
		 * We have to use the openFileInput()-method the ActivityContext
		 * provides. Again for security reasons with openFileInput(...)
		 * FileInputStream fIn = openFileInput("samplefile.txt");
		 * InputStreamReader isr = new InputStreamReader(fIn); Prepare a
		 * char-Array that will hold the chars we read back in. char[]
		 * inputBuffer = new char[TESTSTRING.length()]; // Fill the Buffer with
		 * data from the file isr.read(inputBuffer); // Transform the chars to a
		 * String String readString = new String(inputBuffer);
		 * 
		 * // Check if we read back the same chars that we had written out
		 * boolean isTheSame = TESTSTRING.equals(readString); // Celebrate =)
		 * 
		 * Log.i("File Reading stuff", "success = " + isTheSame);
		 */

	}

	boolean getSensorsplz() {
		// List<Sensor> getSensorList
		/*
		 * List<Sensor> xxx= new List<sensor>;
		 * SensorManager.getSensorList(Sensor.TYPE_ALL);
		 */
		boolean accelSupported = mSensorManager.registerListener(this,
				SENSOR_ACCELEROMETER, SENSOR_DELAY_UI);
		return accelSupported;

	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this,
				SensorManager.SENSOR_ACCELEROMETER
						| SensorManager.SENSOR_MAGNETIC_FIELD
						| SensorManager.SENSOR_ORIENTATION
						| SensorManager.SENSOR_TEMPERATURE,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onStop() {
		mSensorManager.unregisterListener(this);
		super.onStop();
	}

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);

	}

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		// TODO Auto-generated method stub
		switch (sensor) {
		case SensorManager.SENSOR_ACCELEROMETER:
			mTextView1.setText("Accelerometer: " + values[0] + ", " + values[1]
					+ ", " + values[2]);
			acc_x = values[0];
			acc_y = values[1];
			acc_z = values[2];
			Log.d(TAG, Float.toString(acc_y));

			break;
		case SensorManager.SENSOR_MAGNETIC_FIELD:

			mTextView2.setText("Compass: " + values[0] + ", " + values[1]
					+ ", " + values[2]);

			mag_x = values[0];
			mag_y = values[1];
			mag_z = values[2];
			break;
		case SensorManager.SENSOR_ORIENTATION:

			mTextView3.setText("Orientation: " + values[0] + ", " + values[1]
					+ ", " + values[2]);

			ori_x = values[0];
			ori_y = values[1];
			ori_z = values[2];
			break;
		case SensorManager.SENSOR_TEMPERATURE:
			tem_x = values[0];
		}
		if (myParsedDataSet.getLogFileName() != null) {
			startLogging();
			if (EndTime <= System.currentTimeMillis()) {
				this.onStop();
				writeToFile();
			}

		} else {
			if (myParsedDataSet.getSensorName().equalsIgnoreCase(
					"accelerometer"))
				callAcc();
			else if (myParsedDataSet.getSensorName().equalsIgnoreCase(
					"orientation"))
				callOri();
			else if (myParsedDataSet.getSensorName().equalsIgnoreCase(
					"magneticfield"))
				callMag();
			else if (myParsedDataSet.getSensorName().equalsIgnoreCase(
					"temprature"))
				callTem();
		}

	}

	private void writeToFile() {
		// TODO Auto-generated method stub
		try {
			T.append("</sensor>");
			FileOutputStream fOut;
			fOut = openFileOutput(myParsedDataSet.getLogFileName() + ".xml",
					MODE_WORLD_READABLE);

			OutputStreamWriter osw = new OutputStreamWriter(fOut);

			osw.write(T.toString());
			/*
			 * ensure that everything is really written out and close
			 */
			osw.flush();
			osw.close();
			mTextView4.setText("It is logged ;-)...");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	private void callTem() {
		// TODO Auto-generated method stub
		// after satisfying the conditions coming from reading atts, call
		// response
		if (ResponseFlag == true) { // for synchornizing with changes in sensor
			// readings
			if(myParsedDataSet.getResponseContinous().equalsIgnoreCase("no")){
				this.onStop();

			}

			i++;
			if (i == 100) {
				i = 0;
				ResponseFlag = false;
			}
			return;
		}

		if (myParsedDataSet.getReadingType().equalsIgnoreCase("threshold")) {

			if (tem_x >= myParsedDataSet.getReadingThreshold()) {
				Log.d(TAG, "fired");
				callResponse(1);
			}

		} else if (myParsedDataSet.getReadingType().equalsIgnoreCase("range")) {

			if (tem_x >= myParsedDataSet.getReadingRangeXmin()
					&& tem_x <= myParsedDataSet.getReadingRangeXmax())
				callResponse(1);

		}

	}

	public void callAcc() {
		// TODO Auto-generated method stub
		if (ResponseFlag == true) { // for synchornizing with changes in sensor
			// readings
			if(myParsedDataSet.getResponseContinous().equalsIgnoreCase("no")){
				this.onStop();

			}

			i++;
			if (i == 100) {
				i = 0;
				ResponseFlag = false;
			}
			return;
		}

		if (myParsedDataSet.getReadingType().equalsIgnoreCase("threshold")) {

			if (acc_x >= myParsedDataSet.getReadingThreshold()
					|| acc_y >= myParsedDataSet.getReadingThreshold()
					|| acc_z >= myParsedDataSet.getReadingThreshold()) {
				Log.d(TAG, "fired");
				callResponse(1);
			} else if (myParsedDataSet.getReadingAll().equalsIgnoreCase("no")) {
				if (acc_x >= myParsedDataSet.getReadingThresholdX()
						|| acc_y >= myParsedDataSet.getReadingThresholdY()
						|| acc_z >= myParsedDataSet.getReadingThresholdZ())
					callResponse(1);
			} else if (myParsedDataSet.getReadingAll().equalsIgnoreCase("yes")) {
				if (acc_x >= myParsedDataSet.getReadingThresholdX()
						&& acc_y >= myParsedDataSet.getReadingThresholdY()
						&& acc_z >= myParsedDataSet.getReadingThresholdZ())
					callResponse(1);
			}
		} else if (myParsedDataSet.getReadingType().equalsIgnoreCase("range")) {
			if (myParsedDataSet.getReadingAll().equalsIgnoreCase("yes")) {
				if (acc_x >= myParsedDataSet.getReadingRangeXmin()
						&& acc_x <= myParsedDataSet.getReadingRangeXmax()
						&& acc_y >= myParsedDataSet.getReadingRangeYmin()
						&& acc_y <= myParsedDataSet.getReadingRangeYmax()
						&& acc_z >= myParsedDataSet.getReadingRangeZmin()
						&& acc_z <= myParsedDataSet.getReadingRangeZmax())
					callResponse(1);
			} else if (myParsedDataSet.getReadingAll().equalsIgnoreCase("no")) {
				if (acc_x >= myParsedDataSet.getReadingRangeXmin()
						&& acc_x <= myParsedDataSet.getReadingRangeXmax()
						|| acc_y >= myParsedDataSet.getReadingRangeYmin()
						&& acc_y <= myParsedDataSet.getReadingRangeYmax()
						|| acc_z >= myParsedDataSet.getReadingRangeZmin()
						&& acc_z <= myParsedDataSet.getReadingRangeZmax())
					callResponse(1);
			}
		}

	}

	private void callMag() {
		// TODO Auto-generated method stub
		if (ResponseFlag == true) { // for synchornizing with changes in sensor
			// readings
			if(myParsedDataSet.getResponseContinous().equalsIgnoreCase("no")){
				this.onStop();

			}
			i++;
			if (i == 100) {
				i = 0;
				ResponseFlag = false;
			}
			return;
		}
		if (myParsedDataSet.getReadingType().equalsIgnoreCase("threshold")) {

			if (mag_x >= myParsedDataSet.getReadingThreshold()
					|| mag_y >= myParsedDataSet.getReadingThreshold()
					|| mag_z >= myParsedDataSet.getReadingThreshold()) {
				Log.d(TAG, "fired");
				callResponse(2);
			} else if (myParsedDataSet.getReadingAll().equalsIgnoreCase("no")) {
				if (mag_x >= myParsedDataSet.getReadingThresholdX()
						|| mag_y >= myParsedDataSet.getReadingThresholdY()
						|| mag_z >= myParsedDataSet.getReadingThresholdZ())
					callResponse(2);
			} else if (myParsedDataSet.getReadingAll().equalsIgnoreCase("yes")) {
				if (mag_x >= myParsedDataSet.getReadingThresholdX()
						&& mag_y >= myParsedDataSet.getReadingThresholdY()
						&& mag_z >= myParsedDataSet.getReadingThresholdZ())
					callResponse(2);
			}
		} else if (myParsedDataSet.getReadingType().equalsIgnoreCase("range")) {
			if (myParsedDataSet.getReadingAll().equalsIgnoreCase("yes")) {
				if (mag_x >= myParsedDataSet.getReadingRangeXmin()
						&& mag_x <= myParsedDataSet.getReadingRangeXmax()
						&& mag_y >= myParsedDataSet.getReadingRangeYmin()
						&& mag_y <= myParsedDataSet.getReadingRangeYmax()
						&& mag_z >= myParsedDataSet.getReadingRangeZmin()
						&& mag_z <= myParsedDataSet.getReadingRangeZmax())
					callResponse(2);
			} else if (myParsedDataSet.getReadingAll().equalsIgnoreCase("no")) {
				if (mag_x >= myParsedDataSet.getReadingRangeXmin()
						&& mag_x <= myParsedDataSet.getReadingRangeXmax()
						|| mag_y >= myParsedDataSet.getReadingRangeYmin()
						&& mag_y <= myParsedDataSet.getReadingRangeYmax()
						|| mag_z >= myParsedDataSet.getReadingRangeZmin()
						&& mag_z <= myParsedDataSet.getReadingRangeZmax())
					callResponse(2);
			}
		}

	}

	private void callOri() {
		// TODO Auto-generated method stub to be modified with ori_?
		if (ResponseFlag == true) { // for synchornizing with changes in sensor
			// readings
			if(myParsedDataSet.getResponseContinous().equalsIgnoreCase("no")){
				this.onStop();

			}

			i++;
			if (i == 100) {
				i = 0;
				ResponseFlag = false;
			}
			return;
		}
		if (myParsedDataSet.getReadingType().equalsIgnoreCase("threshold")) {

			if (ori_x >= myParsedDataSet.getReadingThreshold()
					|| ori_y >= myParsedDataSet.getReadingThreshold()
					|| ori_z >= myParsedDataSet.getReadingThreshold()) {
				Log.d(TAG, "fired");
				callResponse(3);
			} else if (myParsedDataSet.getReadingAll().equalsIgnoreCase("no")) {
				if (ori_x >= myParsedDataSet.getReadingThresholdX()
						|| ori_y >= myParsedDataSet.getReadingThresholdY()
						|| ori_z >= myParsedDataSet.getReadingThresholdZ())
					callResponse(3);
			} else if (myParsedDataSet.getReadingAll().equalsIgnoreCase("yes")) {
				if (ori_x >= myParsedDataSet.getReadingThresholdX()
						&& ori_y >= myParsedDataSet.getReadingThresholdY()
						&& ori_z >= myParsedDataSet.getReadingThresholdZ())
					callResponse(3);
			}
		} else if (myParsedDataSet.getReadingType().equalsIgnoreCase("range")) {
			if (myParsedDataSet.getReadingAll().equalsIgnoreCase("yes")) {
				if (ori_x >= myParsedDataSet.getReadingRangeXmin()
						&& ori_x <= myParsedDataSet.getReadingRangeXmax()
						&& ori_y >= myParsedDataSet.getReadingRangeYmin()
						&& ori_y <= myParsedDataSet.getReadingRangeYmax()
						&& ori_z >= myParsedDataSet.getReadingRangeZmin()
						&& ori_z <= myParsedDataSet.getReadingRangeZmax())
					callResponse(3);
			} else if (myParsedDataSet.getReadingAll().equalsIgnoreCase("no")) {
				if (ori_x >= myParsedDataSet.getReadingRangeXmin()
						&& ori_x < myParsedDataSet.getReadingRangeXmax()
						|| ori_y > myParsedDataSet.getReadingRangeYmin()
						&& ori_y < myParsedDataSet.getReadingRangeYmax()
						|| ori_z > myParsedDataSet.getReadingRangeZmin()
						&& ori_z < myParsedDataSet.getReadingRangeZmax())
					callResponse(3);
			}
		}

	}

	private boolean callResponse(int sensorType) {
		SmsManager sm = SmsManager.getDefault();

		if (myParsedDataSet.getResponseName().equalsIgnoreCase("notify")) {
			Log.d(TAG, "in response");
			String msg = myParsedDataSet.getResponseNotifyMsg();
			Toast.makeText(this, msg + sensorType, Toast.LENGTH_SHORT).show();
			/*
			 * try { wait(1000); } catch (InterruptedException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		} else if (myParsedDataSet.getResponseName()
				.equalsIgnoreCase("vibrate")) {
			long[] pattern = myParsedDataSet.getResponseVibratorPattern();
			int rep = (myParsedDataSet.getResponseVibratorRepeat()
					.equalsIgnoreCase("no")) ? -1 : Integer
					.parseInt(myParsedDataSet.getResponseVibratorRepeat());
			Vibrator vb = (Vibrator) getApplication().getSystemService(
					Service.VIBRATOR_SERVICE);
			vb.vibrate(pattern, rep);
			Log.d(TAG, "vibration...");
			// Vibrator.vibrate(pattern, -1);
		} else if (myParsedDataSet.getResponseName()
				.equalsIgnoreCase("openweb")) {
			Intent myIntent = null;
			Uri da = Uri.parse(myParsedDataSet.getResponseWeb());
			// Uri da = Uri.parse("file:///system/app/AlarmClock.apk");
			myIntent = new Intent("android.intent.action.VIEW", da);
			// Start the activity
			startActivity(myIntent);
		} else if (myParsedDataSet.getResponseName()
				.equalsIgnoreCase("sendsms")) {
			Log.d(TAG, "sms sent");
			String destination = myParsedDataSet.getResponseSendMsgDest();
			String msg = myParsedDataSet.getResponseSendSms();
			sm.sendTextMessage(destination, null, msg, null, null);
		} else if (myParsedDataSet.getResponseName().equalsIgnoreCase(
				"sendData")) {
			String destination = myParsedDataSet.getResponseSendMsgDest();
			byte[] data = myParsedDataSet.getResponseSendData();
			short port = myParsedDataSet.getResponseSendDataPort();
			sm.sendDataMessage(destination, null, port, data, null, null);
		} else if (myParsedDataSet.getResponseName().equalsIgnoreCase("dial")) {
			Intent iDial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ myParsedDataSet.getResponseDialNumber()));
			startActivity(iDial);
		} else if (myParsedDataSet.getResponseName().equalsIgnoreCase("enable")) {
			if (myParsedDataSet.getResponseFeature().equalsIgnoreCase("wifi"))
				wifi.setWifiEnabled(true);
		} else if (myParsedDataSet.getResponseName()
				.equalsIgnoreCase("disable")){
			if (myParsedDataSet.getResponseFeature().equalsIgnoreCase("wifi"))
				wifi.setWifiEnabled(false);
		}

		ResponseFlag = true;
		return true;

	}

}
