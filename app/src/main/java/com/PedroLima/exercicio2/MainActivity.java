package com.PedroLima.exercicio2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PHONE = 1;
    ArrayList<Students> listStudents;
    StudentsAdapter adapter;

    static final int REQUEST_CODE = 1;
    static final int CONTEXT_MENU_ID_VISUALIZAR = 0;
    static final int CONTEXT_MENU_ID_LIGAR_ALUNO = 1;
    static final int CONTEXT_MENU_ID_SMS_ALUNO = 2;
    static final int CONTEXT_MENU_ID_LOCALIZACAO_MAPA = 3;
    static final int CONTEXT_MENU_ID_SITE_ALUNO = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FormStudentsActivity.class);
                i.putExtra("VISUALIZAR", Boolean.FALSE);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        if (savedInstanceState != null) {
            listStudents = savedInstanceState.getParcelableArrayList("arraylist");
        } else {
            listStudents = obterlista();
        }

        final ListView listview = (ListView) findViewById(R.id.listview);
        adapter = new StudentsAdapter(listStudents, this);
        listview.setAdapter(adapter);
        listview.setDivider(null);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openContextMenu(view);
            }
        });

        registerForContextMenu(listview);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listview) {
            menu.setHeaderTitle(getString(R.string.menu));
            menu.add(Menu.NONE, CONTEXT_MENU_ID_VISUALIZAR, Menu.NONE, getString(R.string.visualizar));
            menu.add(Menu.NONE, CONTEXT_MENU_ID_LIGAR_ALUNO, Menu.NONE, getString(R.string.ligar_aluno));
            menu.add(Menu.NONE, CONTEXT_MENU_ID_SMS_ALUNO, Menu.NONE, getString(R.string.sms_aluno));
            menu.add(Menu.NONE, CONTEXT_MENU_ID_LOCALIZACAO_MAPA, Menu.NONE, getString(R.string.localizacao_mapa));
            menu.add(Menu.NONE, CONTEXT_MENU_ID_SITE_ALUNO, Menu.NONE, getString(R.string.site_aluno));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Students students = (Students) adapter.getItem(info.position);
        switch (item.getItemId()) {
            case CONTEXT_MENU_ID_VISUALIZAR:
                Intent i = new Intent(MainActivity.this, FormStudentsActivity.class);
                i.putExtra("students", students);
                i.putExtra("VISUALIZAR", Boolean.TRUE);
                startActivity(i);
                break;

            case CONTEXT_MENU_ID_LIGAR_ALUNO:
                Uri uri_CALL = Uri.parse("tel:" + MaskEditUtil.unmask(students.getTelefone()));
                Intent call = new Intent(Intent.ACTION_CALL, uri_CALL);
                int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_CALL_PHONE);
                } else {
                    startActivity(call);
                }

                break;

            case CONTEXT_MENU_ID_SMS_ALUNO:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + MaskEditUtil.unmask(students.getTelefone()))));
                break;

            case CONTEXT_MENU_ID_LOCALIZACAO_MAPA:
                String label = "Unibratec";
                String uriBegin = "geo:-8.1518767,-34.9199215,19.56z";
                String encodedQuery = Uri.encode( label  );
                String uriString = uriBegin + "?q=" + encodedQuery;
                Uri uri_geo = Uri.parse( uriString );
                Intent geo = new Intent(android.content.Intent.ACTION_VIEW, uri_geo );
                startActivity( geo );
                break;

            case CONTEXT_MENU_ID_SITE_ALUNO:
                Uri uri = Uri.parse("http://" + students.getSite().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("arraylist", listStudents);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_adicionar) {
            Intent i = new Intent(this, FormStudentsActivity.class);
            i.putExtra("VISUALIZAR", Boolean.FALSE);
            startActivityForResult(i, REQUEST_CODE);
            return true;
        }

        //  if (id == R.id.action_settings) {
        //     return true;
        //  }

        if (id == R.id.action_sobre) {
            Intent i = new Intent(this, SobreActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Students> obterlista() {
        ArrayList<Students> retorno = new ArrayList<Students>();
        for (int i = 0; i < 11; i++) {
            Students students = new Students();
            students.setNome("Pedro Lima" + i);
            students.setTelefone("9.97892681");
            students.setIdade(21);
            students.setNota(10.0);
            students.setEndereco("Rua atenas " + i);
            students.setSite("testedesite.com");
            students.setFotoId(i);
            retorno.add(students);
        }
        return retorno;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                Students aluno = data.getParcelableExtra("students");
                listStudents.add(aluno);
                adapter.notifyDataSetChanged();
            }
        }
    }


}
