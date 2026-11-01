package com.yan.tool.shell;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.yan.tool.R;

/* loaded from: classes.dex */
public class MainActivity extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130968576);
        TextView textView = (TextView) findViewById(R.dimen.compat_button_inset_horizontal_material);
        StringBuilder sb = new StringBuilder();
        sb.append("dpt-shell seem not working.\nApplication: ");
        sb.append(getApplication().getClass().getName());
        textView.setText(sb);
    }
}
