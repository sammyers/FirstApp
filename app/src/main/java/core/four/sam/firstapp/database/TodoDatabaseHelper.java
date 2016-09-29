package core.four.sam.firstapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Sam on 9/29/2016.
 */

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Todos.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodoContract.TodoEntry.TABLE_NAME + " (" +
                    TodoContract.TodoEntry._ID + " integer PRIMARY KEY," +
                    TodoContract.TodoEntry.COLUMN_NAME_TEXT + " text," +
                    TodoContract.TodoEntry.COLUMN_NAME_COMPLETED + " integer" + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TodoContract.TodoEntry.TABLE_NAME;

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public ArrayList<Todo> getTodos() {
        final SQLiteDatabase db = getReadableDatabase();

        final ArrayList<Todo> allTodos = new ArrayList<>();

        final String[] projection = {
                TodoContract.TodoEntry._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TEXT,
                TodoContract.TodoEntry.COLUMN_NAME_COMPLETED
        };

        final Cursor cursor = db.query(TodoContract.TodoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                TodoContract.TodoEntry._ID + " DESC");

        if (!cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return allTodos;
        }

        do {
            long id = cursor.getLong(
                    cursor.getColumnIndex(TodoContract.TodoEntry._ID)
            );
            String text = cursor.getString(
                    cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_TEXT)
            );
            boolean isCompleted = cursor.getInt(
                    cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_COMPLETED)
            ) > 0;

            allTodos.add(new Todo(id, text, isCompleted));

        } while (!cursor.isLast());

        cursor.close();
        db.close();

        return allTodos;
    }

    public Todo createTodo(String text) {
        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(TodoContract.TodoEntry.COLUMN_NAME_TEXT, text);
        values.put(TodoContract.TodoEntry.COLUMN_NAME_COMPLETED, 0); // not completed by default

        final long id = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values);

        db.close();

        return new Todo(id, text, false);
    }

    public void updateTodo(Todo todo) {
        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(TodoContract.TodoEntry.COLUMN_NAME_TEXT, todo.getText());
        values.put(TodoContract.TodoEntry.COLUMN_NAME_COMPLETED, todo.isCompleted() ? 1 : 0);

        final String whereClause = TodoContract.TodoEntry._ID + " = ?";
        final String[] whereArgs = { String.valueOf(todo.getId()) };

        db.update(TodoContract.TodoEntry.TABLE_NAME, values, whereClause, whereArgs);

        db.close();
    }

    public void deleteTodo(Todo todo) {
        final SQLiteDatabase db = getWritableDatabase();

        final String whereClause = TodoContract.TodoEntry._ID + " = ?";
        final String[] whereArgs = { String.valueOf(todo.getId()) };

        db.delete(TodoContract.TodoEntry.TABLE_NAME, whereClause, whereArgs);

        db.close();
    }
}
