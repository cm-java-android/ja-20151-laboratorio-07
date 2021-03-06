package br.java.android.laboratorio07;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;

public class PrincipalActivity extends Activity implements OnClickListener {
	
	//Crie constantes para que voc� possa diferenciar qual Dialog est� sendo chamado
	private static final int DIALOG_OPERACAO = 1;
	private static final int DIALOG_ESTILO_MOTO = 2;

	// Precisamos saber qual foi a opera��o selecionada
	private int indiceOperacao;
	private boolean[] tiposMotosSelecionadas;
	
	// Valores selecionados do SeekBar
	private TextView minimoTextView;
	private TextView maximoTextView;
	private SeekBar minimoSeekBar;
	private SeekBar maximoSeekBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		// Aqui definimos os cliques para que possam ser abertas as caixas de dialogo
		findViewById(R.id.operacaoTextView).setOnClickListener(this);
		findViewById(R.id.tipoTextView).setOnClickListener(this);
		//Quando eu coloco o implements no OnClickListener, 
		// a pr�pria classe se torna um ouvinte de clique
		
		// Sempre ir� come�ar com a primeira op��o selecionada
		indiceOperacao = 0;
		tiposMotosSelecionadas = new boolean[5];
		
		minimoTextView = (TextView) findViewById(R.id.minimoTextView);
		minimoSeekBar = (SeekBar) findViewById(R.id.minimoSeekBar);
		maximoTextView = (TextView) findViewById(R.id.maximoTextView);
		maximoSeekBar = (SeekBar) findViewById(R.id.maximoSeekBar);
		// Aqui eu passo os valores do SeekBar para o TextView associado
		minimoTextView.setText(Integer.toString(minimoSeekBar.getProgress()));
		maximoTextView.setText(Integer.toString(maximoSeekBar.getProgress()));
		
		minimoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// Aqui, apenas quando o usu�rio soltar o seekbar que o valor � capturados
				// Melhor op��o caso tenha que enviar os dados para algum servidor
				Log.d("Laboratorio 07", "Valor minimo recebido" + minimoSeekBar.getProgress());
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// Aqui, a cada toque, � capturado o valor do SeekBar
				// Este � ruim caso tenha que enviar os dados para algum servidor
				// Como n�o � o caso..
				minimoTextView.setText(String.valueOf(minimoSeekBar.getProgress()));
				
			}
		});
		
		maximoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Log.d("Laboratorio 07", "Valor maximo recebido" + maximoSeekBar.getProgress());
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				maximoTextView.setText(String.valueOf(maximoSeekBar.getProgress()));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Qualquer toque em um dos campos Opera��o e Tipo 
	 * Ser� chamado esse metodo aquis
	 */
	@Override
	public void onClick(View v) {
		// � necess�rio diferenciar uma View da outra
		switch (v.getId()) {
		case R.id.operacaoTextView:
			abrirDialogOperacao();
			break;

		case R.id.tipoTextView:
			abrirDialogTipo();
			break;
		default:
			break;
		}	
		
	}

	private void abrirDialogTipo() {
		showDialog(DIALOG_ESTILO_MOTO);
		// Existe o desest�mulo de usar essa apresenta��o
		// e utilizar o uso do DialogFragment
		// Mas vamos ver isso no proximo laboratorio
	}

	private void abrirDialogOperacao() {
		showDialog(DIALOG_OPERACAO);
		// Existe o desest�mulo de usar essa apresenta��o
		// e utilizar o uso do DialogFragment
		// Mas vamos ver isso no proximo laboratorio		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder montador = new AlertDialog.Builder(this);
		
		switch (id) {
		case DIALOG_OPERACAO:
			CharSequence[] operacoes = new CharSequence[]{"Aluguel", "Venda"};
			
			montador.setTitle("Tipo da Opera��o")
			// Dessa forma se impede que o usuario utilize o bot�o voltars
			  .setCancelable(false)			  
			  .setSingleChoiceItems(operacoes, 0, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Precisamos exibir qual foi a opera��o selecionada
					indiceOperacao = which;
					
				}
			})
			 .setNeutralButton("Ok", null);
			
			break;
			
		case DIALOG_ESTILO_MOTO:
			CharSequence[] tipos = new CharSequence[]{"Street", "Trilha", 
					"Estrada", "Chopper", "Custom"};
			
			montador.setTitle("Estilo de Moto")
			.setCancelable(false)
			.setMultiChoiceItems(tipos, tiposMotosSelecionadas, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					// Dessa forma, no tipoMotosSelecionas, na posi��o which, eu marco o 
					// estilo da moto
					tiposMotosSelecionadas[which] = isChecked;
				}
			}).setPositiveButton("Ok", null);
			
			break;
		default:
			break;
		}
		
		return montador.create();
	}
}
