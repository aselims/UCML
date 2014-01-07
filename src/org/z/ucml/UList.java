package org.z.ucml;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class UList extends ListActivity {

	
	private static final int ACTIVITY_EDIT = 1;
	//private static final int ACTIVITY_RUN = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int OPEN_ID = Menu.FIRST + 2;
	private static final int RUN_ID = Menu.FIRST + 3;
	private static final String TAG = "Logging";
	static long id = 0;
	static final String LIST_FLAG = "frmlist";
	static final String itemtitle = "itemno";
	private UDbAdapter mDbHelper;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.u_list);
		mDbHelper = new UDbAdapter(this);
		mDbHelper.open();
		fillData();
		registerForContextMenu(getListView());
	}

	private void fillData() {
		Cursor notesCursor = mDbHelper.fetchAllNotes();
		startManagingCursor(notesCursor);

		// Create an array to specify the fields we want to display in the list
		// (only TITLE)
		String[] from = new String[] { UDbAdapter.KEY_TITLE };

		// and an array of the fields we want to bind those fields to (in this
		// case just text1)
		int[] to = new int[] { R.id.text1 };

		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.u_row, notesCursor, from, to);
		setListAdapter(notes);
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


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, RUN_ID, 0, "Run");
		menu.add(0, OPEN_ID, 0, "Edit");
		menu.add(0, DELETE_ID, 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//id = ((ListView) this.findViewById(R.id.list)).getSelectedItemPosition();
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		id = info.id;
		switch (item.getItemId()) {
		case DELETE_ID:
			
			mDbHelper.deleteNote(id);
			fillData();
			return true;
		case RUN_ID:
			Intent i1 = new Intent(this, UParse.class);
			i1.putExtra(UDbAdapter.KEY_ROWID, id);
			Log.d(TAG, "RUN CLICKED");
			
			startActivity(i1);
			
			return true;
		case OPEN_ID:
			Intent i = new Intent(this, UEdit.class);
			i.putExtra(UDbAdapter.KEY_ROWID, id);
			i.putExtra(LIST_FLAG, "frmlist");

			startActivityForResult(i, ACTIVITY_EDIT);
			return true;

		}
		return super.onContextItemSelected(item);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, UEdit.class);
		i.putExtra(UDbAdapter.KEY_ROWID, id);
		i.putExtra(LIST_FLAG, "frmlist");

		startActivityForResult(i, ACTIVITY_EDIT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();
	}

}
