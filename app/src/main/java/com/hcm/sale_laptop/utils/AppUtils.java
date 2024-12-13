package com.hcm.sale_laptop.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hcm.sale_laptop.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppUtils {

    public static void loadImageUrl(ImageView imageView, String url) {
        if (stringNullOrEmpty(url)) {
            url = "https://laptop88.vn/media/news/724_banner_800x300_4.png";
        }

        final Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_downloading_24)
                .error(R.drawable.ic_close_24)
                .into(imageView);
    }

    public static boolean isNetworkConnected(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public static String getDateFormat(String format) {
        return new SimpleDateFormat(format, new Locale("vi", "VN")).format(new Date());
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean stringNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static <T> boolean checkListHasData(List<T> list) {
        return list != null && !list.isEmpty();
    }

    @SuppressLint("DefaultLocale")
    public static SpannableString customPrice(double price) {
        double roundPrice = (double) (Math.round(price));
        final NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        // Số tiền
        final String priceString = formatter.format(roundPrice);
        // Đơn vị tiền tệ
        final String currency = "VNĐ";
        // Tạo SpannableString
        final SpannableString spannableString = new SpannableString(priceString + " " + currency);

        // Đổi màu cho phần "VNĐ"
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0808")),
                priceString.length() + 1,
                spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    @SuppressLint("DefaultLocale")
    public static SpannableString customPriceReduced(double price) {
        double roundPrice = (double) (Math.round(price));
        final NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        // Số tiền
        final String priceString = "-" + formatter.format(roundPrice);
        // Đơn vị tiền tệ
        final String currency = "VNĐ";
        // Tạo SpannableString
        final SpannableString spannableString = new SpannableString(priceString + " " + currency);

        // Đổi màu cho phần "VNĐ"
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0808")),
                priceString.length() + 1,
                spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    // Format currency (e.g., 1,900,000)
    public static String formatCurrency(long price) {
        return NumberFormat.getInstance(Locale.getDefault()).format(price) + " VND";
    }

    // Format date (e.g., 17/11/2024)
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }
}
