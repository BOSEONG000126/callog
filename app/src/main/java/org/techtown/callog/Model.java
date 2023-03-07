package org.techtown.callog;

public class Model {
    private String imageUrl;
    private String text;


    Model(){}




    public Model(String imageUrl ,String text){
        this.imageUrl = imageUrl ;
        this.text = text;
    }

    public  String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

}
