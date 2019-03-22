package com.arcsoft.sdk_demo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arcsoft.sdk_demo.model.Tb_outaccount;

import com.arcsoft.sdk_demo.model.*;

public class OutaccountDAO {
	private DbopenHelper helper;
	private SQLiteDatabase db;

	public OutaccountDAO(Context context) {
		helper = new DbopenHelper(context);
	}


	public void add(Tb_outaccount tb_outaccount) {
		db = helper.getWritableDatabase();// SQLiteDatabase

		db.execSQL(
				"insert into tb_outaccount (_id,money,date,catgory,payer,remarts) values (?,?,?,?,?,?)",
				new Object[] { tb_outaccount.get_id(), tb_outaccount.getMoney (),
						tb_outaccount.getDate (), tb_outaccount.getCatgory (),
						tb_outaccount.getPayer (), tb_outaccount.getRemarts () });
	}

	/**
	 *
	 * 
	 * @param tb_outaccount
	 */
	public void update(Tb_outaccount tb_outaccount) {
		db = helper.getWritableDatabase();

		db.execSQL(
				"update tb_outaccount set set  money=?,date=?,catgory=?,payer=?,remarts=?where _id = ?",
				new Object[] { tb_outaccount.getMoney(),
						tb_outaccount.getDate (), tb_outaccount.getCatgory (),
						tb_outaccount.getPayer (), tb_outaccount.getRemarts (),
						tb_outaccount.get_id() });
	}

	/**
	 *
	 * 
	 * @param _id
	 * @return
	 */
	public Tb_outaccount find(int _id) {
		db = helper.getWritableDatabase();// ��ʼ��SQLiteDatabase����
		Cursor cursor = db
				.rawQuery(
						"select _id,money,date,catgory,payer,remarts  from tb_outaccount where _id = ?",
						new String[] { String.valueOf(_id) });
		if (cursor.moveToNext())
		{

			return new Tb_outaccount(
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("date")),
					cursor.getString(cursor.getColumnIndex("catgory")),
					cursor.getString(cursor.getColumnIndex("payer")),
					cursor.getString(cursor.getColumnIndex("remarts")));
		}
		return null;
	}

	/**
	 *
	 * 
	 * @param idin
	 */
	public void detele(Integer... idin) {
		if (idin.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < idin.length; i++) {
				sb.append('?').append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
			db = helper.getWritableDatabase();

			db.execSQL("delete from tb_outaccount where _id in (" + sb + ")",
					(Object[]) idin);
		}
	}

	/**
	 *
	 * 
	 * @param start
	 *
	 * @param count
	 *
	 * @return
	 */
	public List<Tb_outaccount> getScrollData(int start, int count) {
		List<Tb_outaccount> tb_outaccount = new ArrayList<Tb_outaccount>();
		db = helper.getWritableDatabase();

		Cursor cursor = db.rawQuery("select * from tb_outaccount limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()) {

			tb_outaccount.add(new Tb_outaccount(cursor.getInt(cursor
					.getColumnIndex("_id")), cursor.getDouble(cursor
					.getColumnIndex("money")), cursor.getString(cursor
					.getColumnIndex("date")), cursor.getString(cursor
					.getColumnIndex("catgory")), cursor.getString(cursor
					.getColumnIndex("payer")), cursor.getString(cursor
					.getColumnIndex("remarts"))));
		}
		return tb_outaccount;
	}

	/**
	 *
	 * @return
	 */
	public long getCount() {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(_id) from tb_outaccount",
				null);
		if (cursor.moveToNext())
		{
			return cursor.getLong(0);
		}
		return 0;
	}

	/**
	 *
	 * 
	 * @return
	 */
	public int getMaxId() {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select max(_id) from tb_outaccount", null);
		while (cursor.moveToLast()) {
			return cursor.getInt(0);
		}
		return 0;
	}
}
