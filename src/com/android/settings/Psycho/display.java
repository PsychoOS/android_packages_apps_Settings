package com.android.settings.Psycho;

import android.app.IThemeCallback;
import android.app.ThemeManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.content.res.TypedArray;
import android.view.MenuItem;
import android.provider.Settings;
import android.content.ContentResolver;

import com.android.settings.R;

import com.android.settings.fragments.DisplayFragment;

public class display extends AppCompatActivity {

  private int mTheme;

  private ThemeManager mThemeManager;
  private final IThemeCallback mThemeCallback = new IThemeCallback.Stub() {

      @Override
      public void onThemeChanged(int themeMode, int color) {
          onCallbackAdded(themeMode, color);
          display.this.runOnUiThread(() -> {
              display.this.recreate();
          });
      }

      @Override
      public void onCallbackAdded(int themeMode, int color) {
          mTheme = color;
      }
  };

  private static int getAccent(int acc, Context context) {
    TypedArray ta = context.getResources().obtainTypedArray(R.array.tint);
    int[] colors = new int[ta.length()];
    for (int i = 0; i < ta.length(); i++) {
        colors[i] = ta.getColor(i, 0);
    }
    ta.recycle();
    return colors[acc];
  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      final int themeMode = Secure.getInt(getContentResolver(),
                Secure.THEME_PRIMARY_COLOR, 0);
        final int accentColor = Secure.getInt(getContentResolver(),
                Secure.THEME_ACCENT_COLOR, 0);
                mThemeManager = (ThemeManager) getSystemService(Context.THEME_SERVICE);
                if (mThemeManager != null) {
                    mThemeManager.addCallback(mThemeCallback);
                }
                if (themeMode == 0) {
                    getTheme().applyStyle(R.style.stock_theme, true);
                }
                if (themeMode == 1) {
                    getTheme().applyStyle(R.style.dark_theme, true);
                }
                if (themeMode == 2) {
                  switch (accentColor) {
                    case 0 :
                      getTheme().applyStyle(R.style.pixel_theme_teal, true);
                      break;
                    case 1 :
                      getTheme().applyStyle(R.style.pixel_theme_green, true);
                      break;
                    case 2 :
                      getTheme().applyStyle(R.style.pixel_theme_cyan, true);
                      break;
                    case 3 :
                      getTheme().applyStyle(R.style.pixel_theme_blue, true);
                      break;
                    case 4 :
                        getTheme().applyStyle(R.style.pixel_theme_yellow, true);
                        break;
                    case 5 :
                        getTheme().applyStyle(R.style.pixel_theme_orange, true);
                        break;
                    case 6 :
                        getTheme().applyStyle(R.style.pixel_theme_red, true);
                        break;
                    case 7 :
                        getTheme().applyStyle(R.style.pixel_theme_pink, true);
                        break;
                    case 8 :
                        getTheme().applyStyle(R.style.pixel_theme_purple, true);
                        break;
                    case 9 :
                        getTheme().applyStyle(R.style.pixel_theme_grey, true);
                        break;
                  }
                }
                if (themeMode == 3) {
                    getTheme().applyStyle(R.style.black_theme, true);
                }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frags);
        int accent = getAccent(accentColor, display.this);

        Toolbar toolbar5 = (Toolbar) findViewById(R.id.toolbar5);
        toolbar5.setTitle(R.string.display);
        setSupportActionBar(toolbar5);
        if (themeMode == 0 || themeMode == 1) {
            toolbar5.setBackgroundColor(getResources().getColor(R.color.colorStock));
            toolbar5.setTitleTextColor(getResources().getColor(R.color.text_main_dark));
        }
        if (themeMode == 2) {
            toolbar5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            toolbar5.setTitleTextColor(accent);
        }
        if (themeMode == 3) {
            toolbar5.setBackgroundColor(getResources().getColor(R.color.black));
            toolbar5.setTitleTextColor(getResources().getColor(R.color.text_main_dark));
        }
        if (getSupportActionBar() != null) {
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            if (themeMode == 2) {
            upArrow.setColorFilter(accent, PorterDuff.Mode.SRC_ATOP);
            }
            else
            {
              upArrow.setColorFilter(getResources().getColor(R.color.text_main_dark), PorterDuff.Mode.SRC_ATOP);
            }
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.pref, new DisplayFragment())
                .commitAllowingStateLoss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
