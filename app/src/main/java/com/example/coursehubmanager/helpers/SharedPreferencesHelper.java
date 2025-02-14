package com.example.coursehubmanager.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // اسم الملف الذي سيتم حفظ البيانات فيه
    private static final String PREF_NAME = "MyPrefs";
    private static final int PRIVATE_MODE = 0;

    // Constructor
    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // حفظ قيمة String
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply(); // أو editor.commit() إذا أردت الحفظ بشكل متزامن
    }

    // استرجاع قيمة String
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // حفظ قيمة int
    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    // استرجاع قيمة int
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    // حفظ قيمة boolean
    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    // استرجاع قيمة boolean
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // حفظ قائمة من الأرقام (List<Integer>)
    public void saveIntegerList(String key, List<Integer> numbers) {
        Gson gson = new Gson();
        String json = gson.toJson(numbers);
        editor.putString(key, json);
        editor.apply();
    }

    // استرجاع قائمة من الأرقام (List<Integer>)
    public List<Integer> getIntegerList(String key) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<List<Integer>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public void addItemToList(String key, int newItem) {
        // استرجاع القائمة المخزنة
        List<Integer> numbers = getIntegerList(key);  // الحصول على القائمة المخزنة

        if (numbers == null) {
            numbers = new ArrayList<>();  // إذا كانت القائمة فارغة أو غير موجودة، إنشاء قائمة جديدة
        }
        // إضافة العنصر الجديد
        numbers.add(newItem);
        // إعادة تخزين القائمة المحدثة في SharedPreferences
        saveIntegerList(key, numbers);
    }

    // حذف قيمة معينة من SharedPreferences
    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    // حذف جميع القيم المخزنة
    public void clear() {
        editor.clear();
        editor.apply();
    }

    // التحقق إذا كان المفتاح موجودًا أم لا
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }
}
