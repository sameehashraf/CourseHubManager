package com.example.coursehubmanager.activities.admin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.coursehubmanager.R;
import com.example.coursehubmanager.database.AppDatabase;
import com.example.coursehubmanager.database.entities.Category;
import com.example.coursehubmanager.database.entities.Course;
import com.example.coursehubmanager.database.viewmodel.MainViewModel;
import com.example.coursehubmanager.databinding.ActivityAddCourseBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AddCourseActivity extends AppCompatActivity {
    ActivityAddCourseBinding binding;
    AppDatabase db;
    MainViewModel viewModel;

    String editCourseName;
    String editCategory;
    String editHours;
    String editPrice;
    String editMentorName;
    String editAbout;
    String imagePath;
    int categoryId = -1;
    private static final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getInstance(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.imgBack.setOnClickListener(v -> finish());
        binding.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        binding.imgCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        binding.btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(() -> {

                    editCourseName = binding.editCourseName.getText().toString().trim();
                    editCategory = binding.editCategory.getText().toString().trim();
                    editHours = binding.editHours.getText().toString().trim();
                    editPrice = binding.editPrice.getText().toString().trim();
                    editMentorName = binding.editMentorName.getText().toString().trim();
                    editAbout = binding.editAbout.getText().toString().trim();

                    if (isValid(editCourseName, editCategory, editHours, editPrice, editMentorName, editAbout)) {
//                        Course course = new Course(editCourseName, editAbout, editMentorName, categoryId,
//                                false, Integer.parseInt(editPrice), 0,
//                                Integer.parseInt(editHours), imagePath, 0);
                        Course course = new Course(editCourseName, editAbout, editMentorName, categoryId,
                                 Integer.parseInt(editPrice), 0,
                                Integer.parseInt(editHours), imagePath);
                        viewModel.addCourse(course);

                    }
                });
                Toast.makeText(AddCourseActivity.this, R.string.added_successfully, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private boolean isValid(String editCourseName,
                            String editCategory,
                            String editHours,
                            String editPrice,
                            String editMentorName,
                            String editAbout) {
        if (
                editCourseName.isEmpty() || editCategory.isEmpty() ||
                        editHours.isEmpty() || editPrice.isEmpty() ||
                        editMentorName.isEmpty() || editAbout.isEmpty()
        ) {
            Log.d("TAG", "isValid: " + editCourseName);
            Log.d("TAG", "isValid: " + editCategory);
            Log.d("TAG", "isValid: " + editHours);
            Log.d("TAG", "isValid: " + editPrice);
            Log.d("TAG", "isValid: " + editMentorName);
            Log.d("TAG", "isValid: " + editAbout);
            showError(getString(R.string.data_cant_be_empty));
            return false;
        }
        if (categoryId == -1) {
            showError(getString(R.string.select_Category));
            return false;
        }

        return true;
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


    private void showPopupMenu(View view) {
        List<Category> categoryList = new ArrayList<>();

        viewModel.getCategories().observe(this, categories -> {
            if (categories != null && !categories.isEmpty()) {
                categoryList.addAll(categories);
            }
        });

        PopupMenu popupMenu = new PopupMenu(this, view);

        for (Category category : categoryList) {
            popupMenu.getMenu().add(category.getCategoryName());
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for (Category category : categoryList) {
                    if (category.getCategoryName() == item.getTitle()) {
                        categoryId = category.getCategoryId();
                        editCategory = category.getCategoryName();
                        binding.editCategory.setText(editCategory);
                    }
                }
                return true;
            }
        });

        // عرض القائمة
        popupMenu.show();
    }

    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
        if (result != null) {
            Uri imageUri = result;
            imagePath = imageUri.toString();

            try {
                imagePath = encodeImageToBase64(imageUri);
                byte[] decodedString = Base64.decode(imagePath, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                Glide.with(binding.getRoot())
                        .load(decodedByte)
                        .placeholder(R.drawable.course)
                        .into(binding.imgCourse);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    });

    // Open gallery to select image
    private void openGallery() {
        pickImageLauncher.launch("image/*");  // لتمكين اختيار الصور فقط

    }

    private String encodeImageToBase64(Uri imageUri) throws IOException {
        // تحويل URI إلى InputStream
        InputStream inputStream = getContentResolver().openInputStream(imageUri);

        // قراءة الصورة إلى byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // تحويل byte array إلى Base64 String
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}