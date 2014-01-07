package org.z.ucml;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class USensors extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.u_sensors);
        
		ListView sensorList = (ListView) findViewById(R.id.sensor_list);
		sensorList.setAdapter(new USensorListAdapter(this));
    }
}