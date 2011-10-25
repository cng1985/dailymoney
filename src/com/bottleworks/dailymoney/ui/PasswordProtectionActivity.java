package com.bottleworks.dailymoney.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bottleworks.commons.util.GUIs;
import com.bottleworks.dailymoney.context.ContextsActivity;
import com.iym.dailymoney.R;
import com.iym.dailymoney.Res;

/**
 * 
 * @author dennis
 *
 */
public class PasswordProtectionActivity extends ContextsActivity implements OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Res.layout.pdprotection);
        findViewById(Res.id.pdprot_ok).setOnClickListener(this);
    }
    

    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.pdprot_ok:
            doPasswordOk();
        }
    }

    private void doPasswordOk() {
        String password = getContexts().getPrefPassword();
        String pd = ((TextView)findViewById(Res.id.pdprot_text)).getText().toString();
        if(password.equals(pd)){
           setResult(RESULT_OK);
           finish();
        }else{
            GUIs.shortToast(this,Res.string.msg_wrong_password);
        }
    }
}
