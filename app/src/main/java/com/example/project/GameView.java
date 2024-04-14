package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.Choreographer;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

//Choreographer.FrameCallback 인터페이스를 구현하여 애니메이션 및 다른 화면 업데이트를 위한 프레임 콜백을 처리한다.
// 이는 GameView가 게임의 그래픽을 렌더링하거나 애니메이션을 업데이트하는 등의 작업을 각 프레임에 맞추어 수행할 수 있게 된다는 의미이다.
// 이를 통해 게임이나 그래픽 애플리케이션이 더 부드럽고 반응하게된다.

public class GameView extends View implements Choreographer.FrameCallback{

    //final : 선언된 요소가 변경될 수 없음.
    private final Activity activity;
    private final Paint borderPaint;
    private final ArrayList<Ball> balls = new ArrayList<>();
    public GameView(Context context){
        super(context);
        this.activity = (Activity)context;

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(0.1f);
        borderPaint.setColor(Color.RED);

        setFullScreen(); // default behavior?

        Resources res = getResources();
        Bitmap ballBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
    }




    public static final float SCREEN_WIDTH = 16.0f;
    public static final float SCREEN_HEIGHT = 9.0f;
    private final RectF borderRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    private final Matrix transformMatrix = new Matrix();

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.save();
        canvas.concat(transformMatrix);
        canvas.drawRect(borderRect, borderPaint);

        for (Ball ball : balls) {
            ball.draw(canvas);
        }

        canvas.restore();
    }



    private void setFullScreen() {
        int flags = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | //필요할때만 UI요소 잠깐 보여주는 효과 제공
                View.SYSTEM_UI_FLAG_FULLSCREEN | //상태 바를 숨기고 전체화면 모드 활성화
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //네비게이션 바 숨기기
        setSystemUiVisibility(flags); //위의 요소들로 시스템 UI 가시성 설정
    }

    //프레임마다 Choreographer에 의해 호출된다.
    public void doFrame(long nanos) {
        update();
        invalidate();//View가 다시 그려질 필요가 있다는 것을 시스템에 알린다.
                    // 이는 주로 update() 메소드에 의해 뷰의 상태가 변경되었을 때 필요하다.
        if (isShown()) { //View 가 화면에 보이는지 확인한다. 보이는 경우에만 다음 프레임 업데이트를 예약한다.
            scheduleUpdate();
        }
    }

    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void update() {

    }

}
