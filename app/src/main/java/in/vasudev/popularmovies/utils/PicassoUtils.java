package in.vasudev.popularmovies.utils;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by vineet on 13-Oct-15.
 */
public class PicassoUtils {

    public static Transformation getFitWidthTransformation(final int targetWidth) {
        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                if (targetWidth == 0) {
                    return source;
                }

                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);

                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation desiredWidth";
            }
        };

        return transformation;
    }
}
