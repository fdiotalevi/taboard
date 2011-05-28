package org.taboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;

public class SourceChooserDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// String text = getArguments().getString("text");

		return new AlertDialog.Builder(getActivity())
				.setTitle(org.taboard.R.string.source_chooser_dialog_title)
				.setItems(R.array.arraylabels_source, new OnClickListener() {

					public void onClick(DialogInterface dialog, int whichButton) {
						Log.v("D", "" + whichButton);
						((Main) getActivity()).addSource(whichButton);
					}
				}).create();
	}

}
