package com.PedroLima.exercicio2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

import static com.PedroLima.exercicio2.MoneyTextWatcher.removerMascaraMonetaria;


/**
 * Created by Pedro Lima on 12/03/2018.
 */

public class FormStudentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_students);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getString(R.string.voltar));

        aplicarStyleEditText();
    }

    /**
     * Função do Botão Voltar na ToolBar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * Botão BACK padrão do android
     */
    @Override
    public void onBackPressed() {
        finish();
        return;
    }

    public void onClickConfirmar(View v) {

        EditText nome = (EditText) findViewById(R.id.nome);
        EditText telefone = (EditText) findViewById(R.id.telefone);
        EditText endereco = (EditText) findViewById(R.id.endereco);
        EditText site = (EditText) findViewById(R.id.site_pessoal);
        EditText idade = (EditText) findViewById(R.id.idade);
        EditText nota = (EditText) findViewById(R.id.nota);

        Students students = new Students();
        students.setNome(nome.getText().toString());
        students.setTelefone(telefone.getText().toString());
        students.setEndereco(endereco.getText().toString());
        students.setSite(site.getText().toString());
        students.setIdade(Integer.valueOf(idade.getText().toString()));
        students.setNota(Double.valueOf(removerMascaraMonetaria(nota.getText().toString())));

        Intent intent = getIntent().putExtra("students", students);
        setResult(RESULT_OK, intent);
        finish();


    }

    public void aplicarStyleEditText() {
        EditText telefone = (EditText) findViewById(R.id.telefone);
        telefone.addTextChangedListener(MaskEditUtil.mask(telefone, MaskEditUtil.FORMAT_FONE));
        Locale mLocale = new Locale("pt", "BR");
        EditText nota = (EditText) findViewById(R.id.nota);

        Intent i = this.getIntent();
        if (i.getBooleanExtra("VISUALIZAR", false)) {
            Students viewStudents = (Students) i.getParcelableExtra("students");

            EditText nome = (EditText) findViewById(R.id.nome);
            EditText telefonetxt = (EditText) findViewById(R.id.telefone);
            EditText endereco = (EditText) findViewById(R.id.endereco);
            EditText site = (EditText) findViewById(R.id.site_pessoal);
            EditText idade = (EditText) findViewById(R.id.idade);
            EditText notatxt = (EditText) findViewById(R.id.nota);

            nome.setText(viewStudents.getNome());
            telefonetxt.setText(viewStudents.getTelefone());
            endereco.setText(viewStudents.getEndereco());
            site.setText(viewStudents.getSite());
            idade.setText(viewStudents.getIdade().toString());
            notatxt.setText(viewStudents.getNota().toString().replace(".", ","));

            disableEditText(nome);
            disableEditText(telefonetxt);
            disableEditText(endereco);
            disableEditText(site);
            disableEditText(idade);
            disableEditText(notatxt);

            Button theButton = (Button) findViewById(R.id.bt_confirmar);
            theButton.setVisibility(View.INVISIBLE);

        } else {
            nota.addTextChangedListener(new MoneyTextWatcher(nota, mLocale));
        }
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

}
