package com.example.lab_03_listview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvFruit;
    ArrayList<Fruit> fruitList;
    FruitAdapter adapter;
    Button btnNewFruit;
    ImageView selectedImageView;
    Uri selectedImageUri;
    int selectedFruitPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        Binding();
        adapter = new FruitAdapter(fruitList, this, R.layout.activity_main);
        lvFruit.setAdapter(adapter);
        btnNewFruit.setOnClickListener(v -> AddFruitDialog());
        lvFruit.setOnItemClickListener((parent, view, position, id) -> showFruitDialog(position));
    }

    private void Binding() {
        lvFruit = findViewById(R.id.lvFruit);
        btnNewFruit = findViewById(R.id.btnNewFruit);
        fruitList = new ArrayList<>();
        fruitList.add(new Fruit("Banana", "Yellow", Uri.parse("https://th.bing.com/th/id/OIP.FZ0pu7WcurIoPGU9JX4ErQHaGu?rs=1&pid=ImgDetMain")));
        fruitList.add(new Fruit("Dragon Fruit", "Red", Uri.parse("https://foodrevolution.org/wp-content/uploads/iStock-503045095-1.jpg")));
        fruitList.add(new Fruit("Strawberry", "Red", Uri.parse("https://th.bing.com/th/id/OIP.CmbO_IgvScBpt9QSGcMG8QHaE7?rs=1&pid=ImgDetMain")));
        fruitList.add(new Fruit("Watermelon", "Green", Uri.parse("https://th.bing.com/th/id/R.2a086f5389f3de81def0b35f2848b43f?rik=wwfffvMeOLDSwA&pid=ImgRaw&r=0")));
    }

    private void AddFruitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_fruit_dialog, null);
        builder.setView(dialogView);

        final EditText addName = dialogView.findViewById(R.id.addName);
        final EditText addDescription = dialogView.findViewById(R.id.addDescription);
        Button btnSelectImage = dialogView.findViewById(R.id.btnSelectImage);
        selectedImageView = dialogView.findViewById(R.id.selectedImageView);

        btnSelectImage.setOnClickListener(v -> openImagePicker());

        builder.setTitle("Add New Fruit")
                .setPositiveButton("Add", (dialog, id) -> {
                    String name = addName.getText().toString();
                    String description = addDescription.getText().toString();

                    Fruit newFruit = new Fruit(name, description, selectedImageUri);
                    fruitList.add(newFruit);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showFruitDialog(int position) {
        Fruit selectedFruit = fruitList.get(position);
        selectedFruitPosition = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_fruit_dialog, null);
        builder.setView(dialogView);

        final EditText addName = dialogView.findViewById(R.id.addName);
        final EditText addDescription = dialogView.findViewById(R.id.addDescription);
        Button btnSelectImage = dialogView.findViewById(R.id.btnSelectImage);
        selectedImageView = dialogView.findViewById(R.id.selectedImageView);

        addName.setText(selectedFruit.getName());
        addDescription.setText(selectedFruit.getDescription());
        Glide.with(this).load(selectedFruit.getImageUri()).into(selectedImageView);

        btnSelectImage.setOnClickListener(v -> openImagePicker());

        builder.setTitle("Edit Fruit")
                .setPositiveButton("Update", (dialog, id) -> {
                    String updatedName = addName.getText().toString();
                    String updatedDescription = addDescription.getText().toString();

                    selectedFruit.setName(updatedName);
                    selectedFruit.setDescription(updatedDescription);
                    if (selectedImageUri != null) {
                        selectedFruit.setImageUri(selectedImageUri);
                    }

                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Delete", (dialog, id) -> {
                    showConfirmationDialog(position);
                })
                .setNeutralButton("Cancel", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showConfirmationDialog(int position) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
        deleteDialog.setTitle("Confirm Delete")
                .setMessage("Are you sure to delete this fruit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    fruitList.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        deleteDialog.create().show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null && selectedImageView != null) {
                        selectedImageView.setImageURI(selectedImageUri);
                    }
                }
            }
    );
}
