package org.z.ucml;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class UEdit extends Activity {
	private TextView mTitleText;
	private EditText mBodyText;
	private Long mRowId;
	private UDbAdapter mDbHelper;
	private static String title1 = "untitled";
	private static String FrmWhere = "none";
	long zid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.u_edit);

		mDbHelper = new UDbAdapter(this);
		mDbHelper.open();

		mTitleText = (TextView) this.findViewById(R.id.title);
		mBodyText = (EditText) this.findViewById(R.id.body);

		Bundle extras1 = getIntent().getExtras();

		if (extras1 != null) {
			FrmWhere = extras1.getString(UList.LIST_FLAG);
			title1 = extras1.getString(UMainUi.VALUE_TITLE);
			mTitleText.setText(title1);
		}
			
		if (FrmWhere.equals("frmlist")) {
				mRowId = (long) extras1.getLong(UDbAdapter.KEY_ROWID);
				fillFields(mRowId);
			}
		
			
		
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.u_edit_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.save:
			save_uc();
			return true;
		case R.id.list:
			Intent i = new Intent(this, UList.class); 
			startActivity(i);
		}
		return super.onMenuItemSelected(featureId, item);
	}

	
	private void fillFields(long RId) {
		if (RId != -1) {
			Cursor note = mDbHelper.fetchNote(mRowId);
			startManagingCursor(note);
			mTitleText.setText(note.getString(note
					.getColumnIndexOrThrow(UDbAdapter.KEY_TITLE)));
			mBodyText.setText(note.getString(note
					.getColumnIndexOrThrow(UDbAdapter.KEY_BODY)));
		}
	}

	/*@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(UDbAdapter.KEY_ROWID, mRowId);
	}*/

	/*@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}
*/
	private void save_uc()  {
		
		String title = mTitleText.getText().toString();
		String body = mBodyText.getText().toString();

		if (mRowId == null) {
			long id = mDbHelper.createNote(title, body);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateNote(mRowId, title, body);
		}
	}

}
