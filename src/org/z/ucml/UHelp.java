package org.z.ucml;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class UHelp extends Activity {
	private TextView tx;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.u_help);
		tx = (TextView) findViewById(R.id.hhh);
		
		String z = "User would choose one of the sensors using:\n<sensor name=\"accelerometer|orientation|magneticfield\">\n\nThen put conditions for acquiring data:\n<reading type=\"threshold\" threshold=\"\"/> \n|<reading type=\"threshold\" all=\"yes\" x=\"\" y=\"\" z=\"\"/> // \"yes\": all dimensions \n|<reading  type=\"threshold\" all=\"no\" x=\"\" y=\"\" z=\"\"/> // \"no\": any or all dimensions.\n|<reading type=\"range\" all=\"no\" xmin=\"\" ymin=\"\" zmin=\"\" xmax=\"\" ymax=\"\"  zmax=\"\"/> //\"no\": any or all dimensions. \n|<reading type=\"range\" all=\"yes\" xmin=\"\" ymin=\"\" zmin=\"\" xmax=\"\" ymax=\"\" zmax=\"\"/> //\"yes\": all dimensions.\n\nDepending on these conditions, one of the following responses should be chosen:  \n<response action=\"notify\" msg=\"\"/> \n|<response action=\"vibrate\"  pattern=\"values,of,msec\" repeat=\"number|no\"/>\n|<response action=\"sendsms\"  destination=\"\" sms=\"\"/>\n|<response action=\"senddata\"  destination=\"\" data=\"\" port=\"\"/>  \n|<response action=\"openweb\" web=\"\"/>\n\nAlso instead of reading and getting a response, the user can use LOG tag to save sensor data to an xml file for a specific period of time:\n<log filename=\"fn\" period=\"time in secs\" /> //path:/data/data/[package]/files/[fn].xml\n</sensor> \n<!-- just a quick reference till the complete documentation... -->";

		tx.setText(z);
		tx.setMovementMethod(new ScrollingMovementMethod());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.u_list, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.gotomain:
			Intent i = new Intent(this, UMainUi.class);
			startActivity(i);
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
