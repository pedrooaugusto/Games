package com.pedroaugusto.jogo_da_velhaandroid;
import android.app.*;
import android.os.*;
import android.text.*;
import android.widget.*;
import android.view.View.*;
import android.content.Context;
public class Menu
{
	RadioButton single, two;
	EditText player1, player2;
	MainActivity main;
	public Menu(MainActivity m)
	{
		main = m;
	}
	public void start()
	{
		OnClickListener mudar = new OnClickListener(){
			public void onClick(android.view.View v){
				changeChecked(v);
			}
		};
		OnFocusChangeListener mudar2 = new OnFocusChangeListener(){
			public void onFocusChange(android.view.View v, boolean off)
			{
				changeFocus(v, off);
			}
		};
		AlertDialog.Builder form = new AlertDialog.Builder(main, android.R.style.Theme_Material_Dialog_Alert)
			.setView(R.layout.menu)
			.setCancelable(false);
		final AlertDialog al = form.create();
		al.show();
		single = (RadioButton) al.findViewById(R.id.singlePlayer);
		single.setChecked(true);
		two = (RadioButton) al.findViewById(R.id.multiPlayer);
		two.setOnClickListener(mudar);
		single.setOnClickListener(mudar);
		player1 = (EditText) al.findViewById(R.id.nameJogador1);
		player1.setOnFocusChangeListener(mudar2);
		player1.setEnabled(false);
		player2 = (EditText) al.findViewById(R.id.nameJogador2);
		player2.setOnFocusChangeListener(mudar2);
		Button btnGo = (Button) al.findViewById(R.id.go);
		btnGo.setOnClickListener(new OnClickListener(){
				public void onClick(android.view.View v){
					al.dismiss();
					main.setNames(getNames());
					Bot.playWithBot = playWithBot();
					main.clearQuadroOutsidePQADeadLineTaChegando();
				}
			});
	}
	private String[] getNames()
	{
		return new String[]{player1.getText().toString() + " (o)",
			player2.getText().toString() + " (x)"};
	}
	private boolean playWithBot()
	{
		return single.isChecked();
	}

	private void changeFocus(android.view.View b, boolean off)
	{
		EditText bb = (EditText) b;
		String num = (bb.getId()==R.id.nameJogador1 ? "1" : "2");
		if(bb.isEnabled())
		{
			if(off && bb.getText().toString().equals("Jogador "+num))
			{
				bb.setTextColor(0xFF6995C5);
				bb.setText("");
			}
			else if(!off && bb.getText().toString().equals(""))
			{
				bb.setTextColor(0xFFBBB7B6);
				bb.setText("Jogador "+num);
			}
		}
	}
	private void changeChecked(android.view.View v)
	{
		if(v.getId() == R.id.multiPlayer)
		{
			single.setChecked(false);
			player1.setEnabled(true);
			player1.setTextColor(0xFF6995C5);
			player1.setText("");
			player1.requestFocus();
		}
		else
		{
			two.setChecked(false);
			player1.setEnabled(false);
			player1.setTextColor(0xFF6995C5);
			player1.setText("Prudence");
		}
	}
}
