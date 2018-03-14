package com.PedroLima.exercicio2;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pedro Lima on 12/03/2018.
 */

public class StudentsAdapter extends BaseAdapter {

    List<Students> students;
    private final Activity act;

    StudentsAdapter(List<Students> students, Activity act) {
        this.students = students;
        this.act = act;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int i) {
        return students.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item, parent, false);
        Students student = students.get(i);

        TextView nome = (TextView) view.findViewById(R.id.students_nome);
        TextView nota = (TextView) view.findViewById(R.id.students_nota);
        TextView idade = (TextView) view.findViewById(R.id.students_idade);
        ImageView imagem = (ImageView) view.findViewById(R.id.students_foto);

        nome.setText(student.getNome());
        nota.setText(" " + student.getNota().toString().replace(".", ","));
        idade.setText(" " + student.getIdade().toString());
        imagem.setImageResource(R.drawable.photo);

        return view;
    }

}