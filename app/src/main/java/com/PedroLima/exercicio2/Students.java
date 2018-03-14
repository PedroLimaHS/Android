package com.PedroLima.exercicio2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro Lima on 12/03/2018.
 */

public class Students implements Parcelable {
    private String nome;
    private Integer idade;
    private Double nota;
    private String telefone;
    private String endereco;
    private String site;
    private int fotoId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getFotoId() {
        return fotoId;
    }

    public void setFotoId(int fotoId) {
        this.fotoId = fotoId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nome);
        dest.writeValue(this.idade);
        dest.writeValue(this.nota);
        dest.writeString(this.telefone);
        dest.writeString(this.endereco);
        dest.writeString(this.site);
        dest.writeInt(this.fotoId);
    }

    public Students() {
    }

    protected Students(Parcel in) {
        this.nome = in.readString();
        this.idade = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nota = (Double) in.readValue(Double.class.getClassLoader());
        this.telefone = in.readString();
        this.endereco = in.readString();
        this.site = in.readString();
        this.fotoId = in.readInt();
    }

    public static final Creator<Students> CREATOR = new Creator<Students>() {
        @Override
        public Students createFromParcel(Parcel source) {
            return new Students(source);
        }

        @Override
        public Students[] newArray(int size) {
            return new Students[size];
        }
    };
}


