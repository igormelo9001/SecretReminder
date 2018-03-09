package br.com.igor.androidloginrestfulwebservice.model;

import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by igor on 09/03/2018.
 */

public class ResImage {
    @SerializedName("imagem")
    @Expose
    private ImageView img;

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
}
