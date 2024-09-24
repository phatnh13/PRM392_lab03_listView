package com.example.lab_03_listview;

import android.net.Uri;

public class Fruit {
    private String name;
    private String description;
    private Uri imageUri; // Change from int to Uri

    public Fruit(String name, String description, Uri imageUri) {
        this.name = name;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getImageUri() {
        return imageUri; // Getter for image URI
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri; // Setter for image URI
    }
}
