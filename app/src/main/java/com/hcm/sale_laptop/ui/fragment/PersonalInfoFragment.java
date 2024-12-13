package com.hcm.sale_laptop.ui.fragment;

import static com.hcm.sale_laptop.utils.Constants.getUserModel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hcm.sale_laptop.R;
import com.hcm.sale_laptop.data.api.ApiService;
import com.hcm.sale_laptop.data.api.RetrofitClient;
import com.hcm.sale_laptop.data.model.network.response.ApiResponse;
import com.hcm.sale_laptop.data.model.other.UserModel;
import com.hcm.sale_laptop.databinding.FragmentPersonalInformationBinding;
import com.hcm.sale_laptop.utils.Constants;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInfoFragment extends Fragment {

    private FragmentPersonalInformationBinding mBinding;
    private Uri avatarUri;
    private StorageReference storageReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentPersonalInformationBinding.inflate(inflater, container, false);
        setup();
        return mBinding.getRoot();
    }

    private void setup() {
        hideBottomNavigation();
        setupSpinner();
        setupImagePicker();
        mBinding.btnPay.setOnClickListener(v -> updateProfile());
        filldata();
        mBinding.btnBackArrow.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void filldata() {
        UserModel userModel = getUserModel();
        Glide.with(this)
                .load(getUserModel().getAvatar())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(mBinding.imageViewCamera);
        mBinding.edtAccountName.setText(userModel.getName());
        mBinding.edtPhoneNumber.setText(userModel.getPhoneNumber());


    }


    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.gender_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spnGender.setAdapter(adapter);

        // Set selected item
        UserModel userModel = getUserModel();
        if (userModel.getGender() != null) {
            int position = adapter.getPosition(userModel.getGender());
            mBinding.spnGender.setSelection(position);
        }
        else {
            mBinding.spnGender.setSelection(0);
        }
    }

    private void setupImagePicker() {
        mBinding.imageViewCamera.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 101);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK && data != null) {
            avatarUri = data.getData();
            mBinding.imageViewCamera.setImageURI(avatarUri);
        }
    }

    private void updateProfile() {
        String name = mBinding.edtAccountName.getText().toString().trim();
        String phone = mBinding.edtPhoneNumber.getText().toString().trim();
        String gender = mBinding.spnGender.getSelectedItem().toString().trim();
        String dateOfBirth = "2021-01-01";

        if (name.isEmpty() || phone.isEmpty() || gender.isEmpty() || dateOfBirth.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        //+84 -> 0
        if (phone.startsWith("+84")) {
            phone = "0" + phone.substring(3);
        }

        if (avatarUri != null) {
            uploadImageToFirebase(name, phone, gender, dateOfBirth);
        } else {
            updateUserProfile(name, phone, gender, dateOfBirth, null);
        }
    }

    private void uploadImageToFirebase(String name, String phone, String gender, String dateOfBirth) {
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        String fileName = System.currentTimeMillis() + ".jpg";
        StorageReference fileReference = storageReference.child(fileName);

        fileReference.putFile(avatarUri)
                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            Log.d("Firebase", "URL ảnh tải lên: " + imageUrl);
                            updateUserProfile(name, phone, gender, dateOfBirth, imageUrl);
                        })
                ).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi tải ảnh: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Firebase", "Lỗi tải ảnh: " + e.getMessage());
                });
    }

    private void updateUserProfile(String name, String phone, String gender, String dateOfBirth, String imageUrl) {
        // Tạo các phần dữ liệu text
        RequestBody userIdPart = RequestBody.create(MediaType.parse("text/plain"), getUserModel().getId());
        RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody phonePart = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody genderPart = RequestBody.create(MediaType.parse("text/plain"), gender);
        RequestBody dobPart = RequestBody.create(MediaType.parse("text/plain"), dateOfBirth);

        // Tạo RequestBody cho link ảnh nếu có
        RequestBody imagePart = imageUrl != null
                ? RequestBody.create(MediaType.parse("text/plain"), imageUrl)
                : RequestBody.create(MediaType.parse("text/plain"), "");

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Gửi yêu cầu API
        apiService.updateProfile(userIdPart, namePart, phonePart, genderPart, dobPart, imagePart)
                .enqueue(new Callback<ApiResponse<UserModel>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<UserModel>> call, Response<ApiResponse<UserModel>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                            Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            response.body().getData().setId(getUserModel().getId());
                            Constants.setUserModel(response.body().getData());
                        } else {
                            Toast.makeText(getContext(), "Cập nhật thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<UserModel>> call, Throwable t) {
                        Log.e("Upload Error", t.getMessage());
                        Toast.makeText(getContext(), "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void hideBottomNavigation() {
        View bottomNavigation = requireActivity().findViewById(R.id.bottom_navigation);
        if (bottomNavigation != null) {
            bottomNavigation.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showBottomNavigation(); // Hiển thị lại Navigation khi Fragment bị huỷ
    }

    private void showBottomNavigation() {
        View bottomNavigation = requireActivity().findViewById(R.id.bottom_navigation);
        if (bottomNavigation != null) {
            bottomNavigation.setVisibility(View.VISIBLE);
        }
    }

}
