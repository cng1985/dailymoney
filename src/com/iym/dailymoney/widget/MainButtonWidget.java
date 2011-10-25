package com.iym.dailymoney.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iym.dailymoney.R;

public class MainButtonWidget extends LinearLayout {

	private ImageView imageView;
	private TextView textView;
	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	public TextView getTextView() {
		return textView;
	}
	public void setTextView(TextView textView) {
		this.textView = textView;
	}
	
	public MainButtonWidget(Context context) {
		super(context);
		LayoutInflater.from(getContext()).inflate(R.layout.desktop_item, this);
		imageView=(ImageView)findViewById(R.id.dt_icon);
		textView=(TextView)findViewById(R.id.dt_label);

	}
	public void setImageResourceId(int id){
		imageView.setImageResource(id);
	}
	public void setText(String text){
		textView.setText(text);
	}

}
