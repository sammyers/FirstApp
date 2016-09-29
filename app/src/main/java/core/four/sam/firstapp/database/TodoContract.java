package core.four.sam.firstapp.database;

import android.provider.BaseColumns;

/**
 * Created by Sam on 9/29/2016.
 */

public final class TodoContract {
    private TodoContract() {}

    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_NAME_TEXT = "todo_text";
        public static final String COLUMN_NAME_COMPLETED = "completed";
    }
}
