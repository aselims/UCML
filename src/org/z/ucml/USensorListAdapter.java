package org.z.ucml;

import java.util.ArrayList;
import java.util.List;

import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;


import android.content.Context;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static android.hardware.SensorManager.*;


public class USensorListAdapter extends BaseAdapter {
	private final LayoutInflater inflater;
	//private SensorManagerSimulator mSensorManager;
	SensorManager mSensorManager;
	private final List<USensorInfo> sensors = new ArrayList<USensorInfo>();
	
	public USensorListAdapter(Context context) {
		// cache the inflater
		inflater = LayoutInflater.from(context);
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		/*SensorManager sensorMgr = (SensorManager) 
				context.getSystemService(Context.SENSOR_SERVICE);*/
		/*mSensorManager = SensorManagerSimulator.getSystemService(context,
				Context.SENSOR_SERVICE);
		mSensorManager.connectSimulator();*/
		int sensorIds = mSensorManager.getSensors();
		sensors.add(new USensorInfo("Accelerometer", 
				(sensorIds & SENSOR_ACCELEROMETER) == SENSOR_ACCELEROMETER));
		sensors.add(new USensorInfo("Light",
				(sensorIds & SENSOR_LIGHT) == SENSOR_LIGHT));
		sensors.add(new USensorInfo("Magnetic Field",
				(sensorIds & SENSOR_MAGNETIC_FIELD) == SENSOR_MAGNETIC_FIELD));
		sensors.add(new USensorInfo("Orientation",
				(sensorIds & SENSOR_ORIENTATION) == SENSOR_ORIENTATION));
		sensors.add(new USensorInfo("Orientation Raw",
				(sensorIds & SENSOR_ORIENTATION_RAW) == SENSOR_ORIENTATION_RAW));
		sensors.add(new USensorInfo("Proximity",
				(sensorIds & SENSOR_PROXIMITY) == SENSOR_PROXIMITY));
		sensors.add(new USensorInfo("Temperature",
				(sensorIds & SENSOR_TEMPERATURE) == SENSOR_TEMPERATURE));
		sensors.add(new USensorInfo("Tricorder",
				(sensorIds & SENSOR_TRICORDER) == SENSOR_TRICORDER));
	}

	public int getCount() {
		return sensors.size();
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}

	public Object getItem(int position) {
		return sensors.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {		
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_sensor, null);
			
			holder = new ViewHolder();
			holder.sensorName = (TextView) convertView.findViewById(R.id.sensor_name);
			holder.sensorSupported = (TextView) convertView.findViewById(R.id.sensor_supported);
			
			// store the holder for reuse later
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		USensorInfo si = sensors.get(position);
		holder.sensorName.setText(si.getName());
		holder.sensorSupported.setText(Boolean.toString(si.isSupported()));
		
		return convertView;
	}
	
	private static class ViewHolder {
		TextView sensorName;
		TextView sensorSupported;
	}

}
