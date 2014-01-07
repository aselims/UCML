package org.z.ucml;

/*to do:
 1- progress bar "start listening" and then send to background... done
 2- more sensor to support at the same time
 3- service
 4- logging
 */



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class UMainUi extends Activity {
	private static final String TAG = "UCML log";

	protected static String VALUE_TITLE = "noTitle";
	public static final String LIST_FLAG = "frmlist";

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// creating menu from XML UI
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// item = (MenuItem)findViewById(R.menu.main_menu);
		switch (item.getItemId()) {
		case R.id.new_file:
			new_file();
			return true;
		case R.id.list:
			list_docs();
			return true;
		case R.id.device_info:
			retrieve_device();
			return true;
		case R.id.about:
			about_ucml();
			return true;
		case R.id.helpu:
			help_ucml();
			return true;
		}
		return false;
	}

	private void about_ucml() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("About UCML");
		alert.setMessage("UCML Ver. 0.9a \nUbiquitous Control Modelling Language. \nAs a Ubiquitous Computing prototybe software...");

		
		final TextView c = new TextView(this);
		c.setText("  Created by: Abdulrahman M.S.Salman \n selim.2k@gmail.com");
		
		alert.setView(c);
		alert.setIcon(R.drawable.alertdoid);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				//
				return;
				}
		});
		alert.show();


	}

	private void retrieve_device() {
		// TODO Auto-generated method stub
		startActivity(new Intent(UMainUi.this, USensors.class));

	}

	private void list_docs() {
		Intent j = new Intent(this, UList.class);
		startActivity(j);

	}

	private void help_ucml() {
		
		startActivity(new Intent(UMainUi.this, UHelp.class));
		//finish();
	}

	public boolean new_file() {

		final Intent i = new Intent(this, UEdit.class);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("UCML Title");
		alert.setMessage("Plz Enter New UCML Module Title");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		input.setSingleLine(true);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				// Log.d(tag,value);
				if (value.equals(""))
					return;

				i.putExtra(VALUE_TITLE, value);
				i.putExtra(LIST_FLAG, "notalist");
				startActivity(i);

			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
		return true;

	}
}