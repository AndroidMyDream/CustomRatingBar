package com.lidongdong.customratingbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 类名:CustomRatingBar.java <br>
 * 描述: 自定义ratingbar<br>
 * 创建者:lidongdong <br>
 * 创建日期:2015-10-23下午1:11:18 <br>
 * 版本: <br>
 * 修改者:<br>
 * 修改日期:<br>
 */
public class CustomRatingBar extends View {

	private Context mContext;

	private Bitmap mNormalBitmap;
	private Bitmap mSelectedBitmap;

	private int starWidth = 45;
	private int starHeight = 45;

	private int starsMargin = 10;

	private Paint mPaint;

	private int starCount = 5;

	private int mWidth;
	private int mHeight;


	private int currentStarPos = -1;
	
	private OnStarClickListener onStarClickListener;
	
	private boolean isIndicator = false;
	

	
	public boolean isIndicator() {
		return isIndicator;
	}

	public void setIndicator(boolean isIndicator) {
		this.isIndicator = isIndicator;
	}

	public OnStarClickListener getOnStarClickListener() {
		return onStarClickListener;
	}

	public void setOnStarClickListener(OnStarClickListener onStarClickListener) {
		this.onStarClickListener = onStarClickListener;
	}

	public interface OnStarClickListener{
		
		void starClick(int pos);
	}


	private GestureDetector mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {

		@Override
		public boolean onSingleTapUp(MotionEvent event) {

			float x = event.getX();
			currentStarPos = (int) (x/(starsMargin + starWidth));
			
			if(onStarClickListener != null){
				
				onStarClickListener.starClick(currentStarPos+1);
			}
			
			invalidate();
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}
	});


	public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData(context);

	}

	public CustomRatingBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initData(context);
	}

	public CustomRatingBar(Context context) {
		super(context);
		initData(context);
	}

	public static Bitmap getBitmapFromDrawable(
			BitmapDrawable paramBitmapDrawable) {
		return paramBitmapDrawable.getBitmap();
	}

	private void initData(Context context) {

		this.mContext = context;

		this.mNormalBitmap = getBitmapFromDrawable((BitmapDrawable) this.mContext
				.getResources().getDrawable(R.mipmap.room_unselect));

		this.mSelectedBitmap = getBitmapFromDrawable((BitmapDrawable) this.mContext
				.getResources().getDrawable(R.mipmap.room_select));

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.GRAY);
		mPaint.setStyle(Paint.Style.FILL);

	}


	@Override  
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
	{  
		int specMode = MeasureSpec.getMode(widthMeasureSpec);  
		int specSize = MeasureSpec.getSize(widthMeasureSpec);  

		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate  
		{  
			mWidth = specSize;  
		} else  
		{  

			if (specMode == MeasureSpec.AT_MOST)// wrap_content  
			{  
				mWidth =  starCount * starWidth + (starCount - 1)* starsMargin;
			}  
		}  


		specMode = MeasureSpec.getMode(heightMeasureSpec);  
		specSize = MeasureSpec.getSize(heightMeasureSpec);  
		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate  
		{  
			mHeight = specSize;  
		} else  
		{  
			if (specMode == MeasureSpec.AT_MOST)// wrap_content  
			{  
				mHeight = starHeight;  
			}  
		}  
		setMeasuredDimension(mWidth, mHeight);  

	}  

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i < starCount; i++) {

			int left = i*(starWidth+starsMargin);
			int top = 0;
			int right = left + starWidth;
			int bottom = top + starHeight;

			canvas.drawBitmap(mNormalBitmap, null, new Rect(left, top,right, bottom), mPaint);


		}

		// Ϊ������׼����
		//		int selectLeft = 0;
		//		int selectTop = 0;
		//		int selectRight = starWidth/2;
		//		int selectBottom = starHeight;
		//
		//		Rect selectRect = new Rect(selectLeft, selectTop,selectRight,selectBottom);
		//
		//		Bitmap bp = Bitmap.createBitmap(mSelectedBitmap,0,0,mSelectedBitmap.getWidth()/2,mSelectedBitmap.getHeight());
		//
		//		canvas.drawBitmap(bp, null,selectRect, mPaint);

		if(currentStarPos != -1){

			for (int i = 0; i < currentStarPos + 1; i++) {


				int left = i*(starWidth+starsMargin);
				int top = 0;
				int right = left + starWidth;
				int bottom = top + starHeight;

				canvas.drawBitmap(mSelectedBitmap, null, new Rect(left, top,right, bottom), mPaint);

			}

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {


		if (event.getPointerCount() == 1  && !isIndicator) {

			mGestureDetector.onTouchEvent(event);
		} 
		return true;

	}

}
