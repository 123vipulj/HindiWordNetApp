package com.wordnet.vipul.hindiwordnetapp.Util;

import android.content.Context;
import android.graphics.Typeface;

public class TypeFaceUtil {

    public static Typeface TypeFaceSet(Context context,String fontName){
        return  Typeface.createFromAsset(context.getAssets(),"fonts/"+fontName);

    }
}
