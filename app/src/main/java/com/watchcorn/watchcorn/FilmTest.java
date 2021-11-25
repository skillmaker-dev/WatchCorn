package com.watchcorn.watchcorn;

public class FilmTest {
    int mImage;
    String Name;
    String Des;

    public FilmTest(int mImage, String name, String des) {
        this.mImage = mImage;
        Name = name;
        Des = des;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }
}
