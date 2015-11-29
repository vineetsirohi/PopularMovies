package in.vasudev.popularmovies.provider;

import android.content.Context;
import android.net.Uri;

import de.triplet.simpleprovider.AbstractProvider;
import de.triplet.simpleprovider.Column;
import de.triplet.simpleprovider.Table;
import in.vasudev.popularmovies.R;

/**
 * Created by vineet on 28-Nov-15.
 */
public class FavouritesProvider extends AbstractProvider {
    @Override
    protected String getAuthority() {
        return getContext().getString(R.string.authority);
    }

    @Table
    public class FavouriteMovie {

        @Column(value = Column.FieldType.INTEGER, primaryKey = true)
        public static final String KEY_ID = "_id";

        @Column(Column.FieldType.TEXT)
        public static final String KEY_TITLE = "title";

        @Column(Column.FieldType.TEXT)
        public static final String KEY_IMAGE_URL = "image_url";

    }


}
