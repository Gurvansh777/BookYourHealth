package com.example.bookyourhealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "BookYourHealth.db";
    final static int DATABASE_VERSION = 1;

    //User Table
    final static String TABLE1_NAME = "User_tb";
    final static String T1COL_1 = "UserId";
    final static String T1COL_2 = "UserName";
    final static String T1COL_3 = "Password";
    final static String T1COL_4 = "UserType";
    final static String T1COL_5 = "IsApproved";
    final static String T1COL_6 = "Email";
    final static String T1COL_7 = "ContactName";
    final static String T1COL_8 = "Dob";
    final static String T1COL_9 = "Gender";
    final static String T1COL_10 = "ContactNumber";
    final static String T1COL_11 = "PostalCode";

    //Appointment Table
    final static String TABLE2_NAME = "Appointment_tb";
    final static String T2COL_1 = "AptId";
    final static String T2COL_2 = "CustId";
    final static String T2COL_3 = "CustName";
    final static String T2COL_4 = "DocId";
    final static String T2COL_5 = "DocName";
    final static String T2COL_6 = "Date";
    final static String T2COL_7 = "Time";
    final static String T2COL_8 = "IsBooked";

    //Complaint Table
    final static String TABLE3_NAME = "Complaint_tb";
    final static String T3COL_1 = "CompId";
    final static String T3COL_2 = "CustId";
    final static String T3COL_3 = "CustName";
    final static String T3COL_4 = "DocId";
    final static String T3COL_5 = "DocName";
    final static String T3COL_6 = "Complaint";
    final static String T3COL_7 = "Solution";

    //Invoice Table
    final static String TABLE4_NAME = "Invoice_tb";
    final static String T4COL_1 = "InvoiceId";
    final static String T4COL_2 = "AptId";
    final static String T4COL_3 = "CompId";
    final static String T4COL_4 = "CustId";
    final static String T4COL_5 = "DocId";
    final static String T4COL_6 = "Amount";
    final static String T4COL_7 = "IsMSP";
    final static String T4COL_8 = "IsPaid";
    final static String T4COL_9 = "IsNotified";

    //Fees Table
    final static String TABLE5_NAME = "Fees_tb";
    final static String T5COL_1 = "FeesId";
    final static String T5COL_2 = "DocId";
    final static String T5COL_3 = "DocName";
    final static String T5COL_4 = "OnlineFees";
    final static String T5COL_5 = "BookingFees";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //User Table
        String qryUser = "CREATE TABLE " + TABLE1_NAME +
                " ( "
                + T1COL_1 + " INTEGER PRIMARY KEY, "
                + T1COL_2 + " TEXT, "
                + T1COL_3 + " TEXT, "
                + T1COL_4 + " TEXT, "
                + T1COL_5 + " INTEGER, "
                + T1COL_6 + " TEXT, "
                + T1COL_7 + " TEXT, "
                + T1COL_8 + " TEXT, "
                + T1COL_9 + " TEXT, "
                + T1COL_10 + " TEXT, "
                + T1COL_11 + " TEXT "
                + " ) ";

        db.execSQL(qryUser);

        //Appointment Table
        String qryAppointment = "CREATE TABLE " + TABLE2_NAME +
                " ( "
                + T2COL_1 + " INTEGER PRIMARY KEY, "
                + T2COL_2 + " INTEGER, "
                + T2COL_3 + " TEXT, "
                + T2COL_4 + " INTEGER, "
                + T2COL_5 + " TEXT, "
                + T2COL_6 + " TEXT, "
                + T2COL_7 + " TEXT, "
                + T2COL_8 + " INTEGER " +
                " ) ";

        db.execSQL(qryAppointment);

        //Complaint Table
        String qryComplaint = "CREATE TABLE " + TABLE3_NAME +
                " ( "
                + T3COL_1 + " INTEGER PRIMARY KEY, "
                + T3COL_2 + " INTEGER, "
                + T3COL_3 + " TEXT, "
                + T3COL_4 + " INTEGER, "
                + T3COL_5 + " TEXT, "
                + T3COL_6 + " TEXT, "
                + T3COL_7 + " TEXT " +
                " ) ";

        db.execSQL(qryComplaint);

        //Invoice Table
        String qryInvoice = "CREATE TABLE " + TABLE4_NAME +
                " ( "
                + T4COL_1 + " INTEGER PRIMARY KEY, "
                + T4COL_2 + " INTEGER, "
                + T4COL_3 + " INTEGER, "
                + T4COL_4 + " INTEGER, "
                + T4COL_5 + " INTEGER, "
                + T4COL_6 + " REAL, "
                + T4COL_7 + " INTEGER, "
                + T4COL_8 + " INTEGER, "
                + T4COL_9 + " INTEGER " +
                " ) ";

        db.execSQL(qryInvoice);

        //Fees Table
        String qryFees = "CREATE TABLE " + TABLE5_NAME +
                " ( "
                + T5COL_1 + " INTEGER PRIMARY KEY, "
                + T5COL_2 + " INTEGER, "
                + T5COL_3 + " TEXT, "
                + T5COL_4 + " REAL, "
                + T5COL_5 + " REAL " +
                " ) ";

        db.execSQL(qryFees);

        //Insert Test Users
        db.execSQL("INSERT INTO User_tb VALUES (1, 'Admin', 'Admin', 'ADMIN', 1, 'admin@example.com', 'Ravneet Saini', '23', 'Female', '0987654321', 'V3W9C6')");
        db.execSQL("INSERT INTO User_tb VALUES (2, 'User', 'User', 'USER', 1, 'user@example.com', 'Harman Malhotra', '23', 'Male', '987654321', 'V3W9C6')");
        db.execSQL("INSERT INTO User_tb VALUES (3, 'Cashier', 'Cashier', 'CASHIER', 1, 'cashier@example.com',  'Jasmeet Brar', '23', 'Female', '1234567890', 'V3V7Y5')");
        db.execSQL("INSERT INTO User_tb VALUES (4, 'Doctor', 'Doctor', 'DOCTOR', 1, 'doctor@example.com', 'Dr. Gurvansh Singh', '35', 'Male', '1234567890', 'V3W9C6')");
        db.execSQL("INSERT INTO User_tb VALUES (5, 'Doctor1', 'Doctor1', 'DOCTOR', 1, 'doctor1@example.com', 'Dr. Test 1', '55', 'Female', '1234567890', 'V4C5C5')");
        db.execSQL("INSERT INTO User_tb VALUES (6, 'Doctor2', 'Doctor2', 'DOCTOR', 1, 'doctor2@example.com', 'Dr. Test 2', '50', 'Male', '1234567890', 'V4C8B2')");
        db.execSQL("INSERT INTO User_tb VALUES (7, 'Doctor3', 'Doctor3', 'DOCTOR', 1, 'doctor3@example.com', 'Dr. Test 3', '48', 'Male', '1234567890', 'V3X8T6')");
        db.execSQL("INSERT INTO User_tb VALUES (8, 'User2', 'User2', 'USER', 1, 'user@example.com', 'User Test 2', '35', 'Female', '1234567890', 'V3T5D3')");

        //Insert Fees for every doctor
        db.execSQL("INSERT INTO Fees_tb VALUES (1, 4, 'Dr. Gurvansh Singh', 30.00, 60.00)");
        db.execSQL("INSERT INTO Fees_tb VALUES (2, 5, 'Dr. Test 1', 50.00, 80.00)");
        db.execSQL("INSERT INTO Fees_tb VALUES (3, 6, 'Dr. Test 2', 60.00, 90.00)");
        db.execSQL("INSERT INTO Fees_tb VALUES (4, 7, 'Dr. Test 3', 45.00, 100.00)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE5_NAME);
        onCreate(db);
    }

    //Return corresponding user details
    public Cursor validateUser(String nameOrEmail, String pword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE (UPPER(UserName)=? OR UPPER(Email)=?) AND UPPER(Password)=?";
        Cursor c = db.rawQuery(query, new String[]{nameOrEmail, nameOrEmail, pword});
        return c;
    }

    //Add a new user
    public boolean addNewUser(String userName, String password, String userType, String email, String contactName, String dob, String gender, String contactNumber, String postalCode) {
        int isApproved = 0;
        if (userType.equals("ADMIN")) {
            isApproved = 1;
        }
        long r = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1COL_2, userName);
        contentValues.put(T1COL_3, password);
        contentValues.put(T1COL_4, userType);
        contentValues.put(T1COL_5, isApproved);
        contentValues.put(T1COL_6, email);
        contentValues.put(T1COL_7, contactName);
        contentValues.put(T1COL_8, dob);
        contentValues.put(T1COL_9, gender);
        contentValues.put(T1COL_10, contactNumber);
        contentValues.put(T1COL_11, postalCode);
        r = db.insert(TABLE1_NAME, null, contentValues);
        if (r > 0) {
            //Additional work - Set Default fees when new doctor is created so that cashier can update it later!
            if (userType.equals("DOCTOR")) {
                Cursor cDoc = db.rawQuery("SELECT UserId FROM User_tb WHERE userName = '" + userName + "' AND email = '" + email + "'", null);
                cDoc.moveToNext();
                ContentValues cv = new ContentValues();
                cv.put(T5COL_2, cDoc.getString(0));
                cv.put(T5COL_3, contactName);
                cv.put(T5COL_4, 50.00);
                cv.put(T5COL_5, 100.00);
                r = db.insert(TABLE5_NAME, null, cv);
            }

        }
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor updateUser(int userId, String contactName, String dob, String gender, String contactNumber, String postalCode) {
        String query = "UPDATE " + TABLE1_NAME + " SET "
                + T1COL_7 + "=?, "  + T1COL_8 + "=?, "   + T1COL_9 + "=?, "  + T1COL_10 + "=?, "  + T1COL_11 + "=? "
                + " WHERE " + T1COL_1 + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, new String[]{contactName, dob, gender, contactNumber, postalCode, String.valueOf(userId)});
        long r = c.getCount();
        return getAnyUserById(userId);
    }

    public Cursor updateUserPassword(int userId, String newPassword) {
        String query = "UPDATE " + TABLE1_NAME + " SET "
                + T1COL_3 + "=?"
                + " WHERE " + T1COL_1 + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, new String[]{newPassword, String.valueOf(userId)});
        long r = c.getCount();
        return getAnyUserById(userId);
    }

    //Return corresponding user details
    public Cursor getAnyUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE " + T1COL_1 + " = ?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(userId)});
        return c;
    }

    //Return corresponding user details
    public Cursor getDoctorsByPostalCode(String postalCode) {
        String userType = "DOCTOR";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE UPPER(UserType)=? AND " + T1COL_11 + " LIKE ?";
        Cursor c = db.rawQuery(query, new String[]{userType, postalCode+"%"});
        return c;
    }

    //Return corresponding user details
    public Cursor getAllDoctors() {
        String userType = "DOCTOR";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE UPPER(UserType)=?";
        Cursor c = db.rawQuery(query, new String[]{userType});
        return c;
    }

    public Cursor getAllUserAppointmentHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE4_NAME + " inv INNER JOIN " + TABLE2_NAME + " apt ON inv.AptId = apt.AptId";
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public Cursor getAllUserComplaintHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE4_NAME + " inv INNER JOIN " + TABLE3_NAME + " cpt ON inv.CompId = cpt.CompId";
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    //Return corresponding user details
    public Cursor getDoctorById(int docId) {
        String userType = "DOCTOR";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " u INNER JOIN Fees_tb f ON u.UserId = f.DocId WHERE UPPER(u.UserType)=? AND u." + T1COL_1 + " = ?";
        Cursor c = db.rawQuery(query, new String[]{userType, String.valueOf(docId)});
        return c;
    }

    public boolean setDoctorAvailability(int custId, String custName, int docId, String docName, String date, String time, int isBooked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T2COL_2, custId);
        contentValues.put(T2COL_3, custName);
        contentValues.put(T2COL_4, docId);
        contentValues.put(T2COL_5, docName);
        contentValues.put(T2COL_6, date);
        contentValues.put(T2COL_7, time);
        contentValues.put(T2COL_8, isBooked);
        long r = db.insert(TABLE2_NAME, null, contentValues);
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editComplaint(int complaintId, String complaint) {

        String query = "UPDATE " + TABLE3_NAME + " SET " + T3COL_6 + "=? WHERE " + T3COL_1 + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, new String[]{complaint, String.valueOf(complaintId)});
        long r = c.getCount();
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editSolution(int complaintId, String solution) {

        String query = "UPDATE " + TABLE3_NAME + " SET " + T3COL_7 + "=? WHERE " + T3COL_1 + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, new String[]{solution, String.valueOf(complaintId)});
        long r = c.getCount();
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean bookAppointment(int custId, String custName, int docId, String date, String time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T2COL_2, custId);
        contentValues.put(T2COL_3, custName);
        contentValues.put(T2COL_8, 1);
        long r = db.update(TABLE2_NAME, contentValues, T2COL_4 + " =? AND " + T2COL_6 + " =? " + "AND " + T2COL_7 + " =?", new String[]{String.valueOf(docId), date, time});
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateFees(int docId, double onlineFees, double bookingFees) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T5COL_4, onlineFees);
        contentValues.put(T5COL_5, bookingFees);

        long r = db.update(TABLE5_NAME, contentValues, T5COL_2 + "=?" , new String[]{String.valueOf(docId)});
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean addNewComplaint(int custId, String custName, int docId, String docName, String complaint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T3COL_2, custId);
        contentValues.put(T3COL_3, custName);
        contentValues.put(T3COL_4, docId);
        contentValues.put(T3COL_5, docName);
        contentValues.put(T3COL_6, complaint);
        long r = db.insert(TABLE3_NAME, null, contentValues);
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean generateInvoice(int aptId, int compId, int custId, int docId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T4COL_2, aptId);
        contentValues.put(T4COL_3, compId);
        contentValues.put(T4COL_4, custId);
        contentValues.put(T4COL_5, docId);
        contentValues.put(T4COL_6, 0.00);
        contentValues.put(T4COL_7, 0);
        contentValues.put(T4COL_8, 0);
        contentValues.put(T4COL_9, 0);
        long r = db.insert(TABLE4_NAME, null, contentValues);
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getAllInvoices() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE4_NAME ;
        Cursor c = db.rawQuery(query, new String[]{});
        return c;
    }

    public Cursor getPendingInvoices() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE4_NAME + " inTb INNER JOIN " + TABLE1_NAME + " usTb ON inTb." + T4COL_4 +" = usTb." + T1COL_1 + " WHERE inTb." + T4COL_8 + " = 0";
        Cursor c = db.rawQuery(query, new String[]{});
        return c;
    }

    public Cursor getPendingInvoicesNotified() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE4_NAME + " inTb INNER JOIN " + TABLE1_NAME + " usTb ON inTb." + T4COL_4 +" = usTb." + T1COL_1 + " WHERE inTb." + T4COL_8 + " = 0 AND " + T4COL_9 + " = 1";
        Cursor c = db.rawQuery(query, new String[]{});
        return c;
    }
    public boolean updateInvoiceNotified(int invId, int isNotified) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T4COL_9, isNotified);
        long r = db.update(TABLE4_NAME, contentValues, T4COL_1 + "=?" , new String[]{String.valueOf(invId)});
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateInvoice(int invId, double amount, int isMsp, int isPaid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T4COL_6, amount);
        contentValues.put(T4COL_7, isMsp);
        contentValues.put(T4COL_8, isPaid);

        long r = db.update(TABLE4_NAME, contentValues, T4COL_1 + "=?" , new String[]{String.valueOf(invId)});
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getAllComplaints() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME;
        Cursor c = db.rawQuery(query, new String[]{});
        return c;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME;
        Cursor c = db.rawQuery(query, new String[]{});
        return c;
    }

    public Cursor getComplaintsByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " c INNER JOIN Invoice_tb i ON c.CompId = i.CompId WHERE c." + T3COL_2 + "=? AND i.IsPaid = 1";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(userId)});
        return c;
    }

    public Cursor getComplaintsByDocId(int docId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " WHERE " + T3COL_4 + "=?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(docId)});
        return c;
    }

    public Cursor getComplaintByComId(int comId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE3_NAME + " WHERE " + T3COL_1 + "=?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(comId)});
        return c;
    }


    public Cursor getAvailableDatesByDocId(int docId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT Date FROM " + TABLE2_NAME + " WHERE " + T2COL_4 + "=?" + " AND " + T2COL_8 + "=0";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(docId)});
        return c;
    }

    public Cursor getAvailableTimeByDocId(int docId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE2_NAME + " WHERE " + T2COL_4 + "=?" + " AND " + T2COL_8 + "=0 AND " + T2COL_6 + "=?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(docId), date});
        return c;
    }

    public Cursor getFeesByDocId(int docId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE5_NAME + " WHERE " + T5COL_2 + "=?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(docId)});
        return c;
    }

    public Cursor getBookedAppointmentDetails(int docId, String date, String time) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE2_NAME + " WHERE " + T2COL_4 + "=? AND " + T2COL_6 + "=? AND " +T2COL_7 + "=?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(docId), date, time});
        return c;
    }
    public Cursor getAllBookedAppointments(int custId, int docId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "";
        Cursor c = null;
        if (docId == 0) {
            query = "SELECT * FROM " + TABLE2_NAME + " apTb INNER JOIN Invoice_tb inTb ON apTb.AptId = inTb.AptId WHERE apTb." + T2COL_2 + " =? AND apTb." + T2COL_8 + "=1";
            c = db.rawQuery(query, new String[]{String.valueOf(custId)});
        }
        if (custId == 0) {
            query = "SELECT * FROM " + TABLE2_NAME + " apTb INNER JOIN Invoice_tb inTb ON apTb.AptId = inTb.AptId WHERE apTb." + T2COL_4 + " =? AND apTb." + T2COL_8 + "=1";
            c = db.rawQuery(query, new String[]{String.valueOf(docId)});
        }
        return c;
    }
}


