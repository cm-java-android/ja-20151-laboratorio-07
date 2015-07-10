package br.java.android.laboratorio07;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PrincipalActivity extends Activity implements OnClickListener {
	
	//Crie constantes para que você possa diferenciar qual Dialog está sendo chamado
	private static final int DIALOG_OPERACAO = 1;
	private static final int DIALOG_TIPO_OPERACAO = 2;

	// Precisamos saber qual foi a operação selecionada
	private int indiceOperacao;
	private boolean[] tiposMotosSelecionadas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		// Aqui definimos os cliques para que possam ser abertas as caixas de dialogo
		findViewById(R.id.operacaoTextView).setOnClickListener(this);
		findViewById(R.id.tipoTextView).setOnClickListener(this);
		//Quando eu coloco o implements no OnClickListener, 
		// a própria classe se torna um ouvinte de clique
		
		// Sempre irá começar com a primeira opção selecionada
		indiceOperacao = 0;
		tiposMotosSelecionadas = new boolean[5];
		
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
	 * Qualquer toque em um dos campos Operação e Tipo 
	 * Será chamado esse metodo aquis
	 */
	@Override
	public void onClick(View v) {
		// É necessário diferenciar uma View da outra
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
		showDialog(DIALOG_TIPO_OPERACAO);
		// Existe o desestímulo de usar essa apresentação
		// e utilizar o uso do DialogFragment
		// Mas vamos ver isso no proximo laboratorio
	}

	private void abrirDialogOperacao() {
		showDialog(DIALOG_OPERACAO);
		// Existe o desestímulo de usar essa apresentação
		// e utilizar o uso do DialogFragment
		// Mas vamos ver isso no proximo laboratorio		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder montador = new AlertDialog.Builder(this);
		
		switch (id) {
		case DIALOG_OPERACAO:
			CharSequence[] operacoes = new CharSequence[]{"Aluguel", "Venda"};
			
			montador.setTitle("Tipo da Operação")
			// Dessa forma se impede que o usuario utilize o botão voltars
			  .setCancelable(false)			  
			  .setSingleChoiceItems(operacoes, 0, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Precisamos exibir qual foi a operação selecionada
					indiceOperacao = which;
					
				}
			})
			 .setNeutralButton("Ok", null);
			
			break;
			
		case DIALOG_TIPO_OPERACAO:
			CharSequence[] tipos = new CharSequence[]{"Street", "Trilha", 
					"Estrada", "Chopper", "Custom"};
			
			montador.setTitle("Estilo de Moto")
			.setCancelable(false)
			.setMultiChoiceItems(tipos, tiposMotosSelecionadas, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					// Dessa forma, no tipoMotosSelecionas, na posição which, eu marco o 
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
