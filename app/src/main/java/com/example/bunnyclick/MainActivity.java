package com.example.bunnyclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewBunny;
    TextView textViewNumBunnies;
    ConstraintLayout mainLayout;

    TextView easterEggBasket;
    TextView carrotFarm;
    TextView easterEggFactory;

    TextView easterEggBasketNum;
    TextView carrotFarmNum;
    TextView easterEggFactoryNum;

    TextView easterEggBasketCost;
    TextView carrotFarmCost;
    TextView easterEggFactoryCost;

    ImageView easterEggBasketImage;
    ImageView carrotFarmImage;
    ImageView easterEggFactoryImage;

    ImageView bunnyHopLeft;
    ImageView bunnyHopRight;

    int bunniesTotal;
    int bps;
    //bunnies per second
    int bpc;
    //bunnies per click

    float hbBasket = 0.06f;
    float hbFarm = 0.06f;
    float hbFactory = 0.06f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewBunny = findViewById(R.id.imageView_bunny);
        textViewNumBunnies = findViewById(R.id.textView_num_bunnies);
        mainLayout = findViewById(R.id.mainLayout);

        easterEggBasket = findViewById(R.id.textView_easterEggBasket);
        carrotFarm = findViewById(R.id.textView_carrotFarm);
        easterEggFactory = findViewById(R.id.textView_easterEggFactory);

        easterEggBasketNum = findViewById(R.id.textView_num_easterEggBasket);
        carrotFarmNum = findViewById(R.id.textView_num_carrotFarm);
        easterEggFactoryNum = findViewById(R.id.textView_num_easterEggFactory);

        easterEggBasketCost = findViewById(R.id.textView_cost_easterEggBasket);
        carrotFarmCost = findViewById(R.id.textView_cost_carrotFarm);
        easterEggFactoryCost = findViewById(R.id.textView_cost_easterEggFactory);

        easterEggBasketImage = findViewById(R.id.imageView_easterEggBasket);
        carrotFarmImage = findViewById(R.id.imageView_carrotFarm);
        easterEggFactoryImage = findViewById(R.id.imageView_easterEggFactory);

        bunnyHopLeft = findViewById(R.id.imageView_bunnyHop_left);
        bunnyHopRight = findViewById(R.id.imageView_bunnyHop_right);

        bunniesTotal = 0;
        bps = 0;
        bpc = 1;

        Log.d("HELLO", "START");

        PassiveIncome passiveIncome = new PassiveIncome();
        passiveIncome.start();

        ObjectAnimator hopUpLeft = ObjectAnimator.ofFloat(bunnyHopLeft, "translationY", 0f, -400f);;
        hopUpLeft.setDuration(800);

        ObjectAnimator hopDownLeft = ObjectAnimator.ofFloat(bunnyHopLeft, "translationY", -400f, 0f);;
        hopDownLeft.setDuration(300);

        ObjectAnimator hopUpRight = ObjectAnimator.ofFloat(bunnyHopRight, "translationY", 0f, -400f);;
        hopUpRight.setDuration(800);

        ObjectAnimator hopDownRight = ObjectAnimator.ofFloat(bunnyHopRight, "translationY", -400f, 0f);;
        hopDownRight.setDuration(300);

        hopUpLeft.start();

        hopUpLeft.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                hopDownLeft.start();
                hopUpRight.start();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });

        hopUpRight.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                hopDownRight.start();
                hopUpLeft.start();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });

        ConstraintLayout.LayoutParams plusParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        final ScaleAnimation bunnyAnim = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        bunnyAnim.setDuration(100);

        imageViewBunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(bunnyAnim);
                bunniesTotal += bpc;
                textViewNumBunnies.setText(String.format("%,d", bunniesTotal) + " bunnies");

                TextView plus= new TextView(MainActivity.this);
                plus.setId(View.generateViewId());
                plus.setLayoutParams(plusParams);
                plus.setText("+"+bpc);
                mainLayout.addView(plus);
                ConstraintSet set = new ConstraintSet();
                set.clone(mainLayout);

                set.connect(plus.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP);
                set.connect(plus.getId(), ConstraintSet.BOTTOM, mainLayout.getId(), ConstraintSet.BOTTOM);
                set.connect(plus.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT);
                set.connect(plus.getId(), ConstraintSet.RIGHT, mainLayout.getId(), ConstraintSet.RIGHT);

                double x = ((Math.random()*36)+30)/100.0;
                //.30 - .65
                double y = ((Math.random()*14)+20)/100.0;
                //.20 - .33
                set.setHorizontalBias(plus.getId(), Float.parseFloat(String.valueOf(x)));
                set.setVerticalBias(plus.getId(), Float.parseFloat(String.valueOf(y)));
                plus.setTextColor(Color.rgb(158, 87, 150));
                plus.setTextSize(20);
                plus.setTypeface(plus.getTypeface(), Typeface.BOLD);
                set.applyTo(mainLayout);

                ObjectAnimator plusAnim = ObjectAnimator.ofFloat(plus, "translationY", 0f, -200f);
                plusAnim.setDuration(200);
                plusAnim.start();

                plusAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        plus.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });
            }
        });

        easterEggBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bunniesTotal >= 50 && !easterEggBasketNum.getText().equals("MAX")) {
                    bunniesTotal -= 50;
                    bpc += 10;
                    int i = Integer.parseInt(easterEggBasketNum.getText().toString());
                    i++;
                    if(i == 10){
                        easterEggBasketNum.setText("MAX");
                        easterEggBasket.setBackgroundColor(Color.rgb(60, 64, 82));
                        easterEggBasketNum.setBackgroundColor(Color.rgb(60, 64, 82));
                        easterEggBasketCost.setBackgroundColor(Color.rgb(60, 64, 82));
                    }
                    else{
                        easterEggBasketNum.setText(String.valueOf(i));
                    }
                    textViewNumBunnies.setText(String.format("%,d", bunniesTotal) + " bunnies");

                    ConstraintLayout.LayoutParams basketParams = new ConstraintLayout.LayoutParams(100, 100);

                    ImageView basket = new ImageView(MainActivity.this);
                    basket.setId(View.generateViewId());
                    basket.setLayoutParams(basketParams);
                    basket.setImageResource(R.drawable.basket);
                    mainLayout.addView(basket);
                    ConstraintSet set = new ConstraintSet();
                    set.clone(mainLayout);

                    set.connect(basket.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP);
                    set.connect(basket.getId(), ConstraintSet.BOTTOM, mainLayout.getId(), ConstraintSet.BOTTOM);
                    set.connect(basket.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT);
                    set.connect(basket.getId(), ConstraintSet.RIGHT, mainLayout.getId(), ConstraintSet.RIGHT);

                    set.setVerticalBias(basket.getId(), 0.83f);
                    set.setHorizontalBias(basket.getId(), hbBasket);
                    set.applyTo(mainLayout);

                    hbBasket += 0.1f;
                }

            }
        });

        carrotFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bunniesTotal >= 1000 && !carrotFarmNum.getText().equals("MAX")) {
                    bunniesTotal -= 1000;
                    bps += 200;
                    int i = Integer.parseInt(carrotFarmNum.getText().toString());
                    i++;
                    if(i == 10){
                        carrotFarmNum.setText("MAX");
                        carrotFarm.setBackgroundColor(Color.rgb(60, 64, 82));
                        carrotFarmNum.setBackgroundColor(Color.rgb(60, 64, 82));
                        carrotFarmCost.setBackgroundColor(Color.rgb(60, 64, 82));
                    }
                    else {
                        carrotFarmNum.setText(String.valueOf(i));
                    }
                    textViewNumBunnies.setText(String.format("%,d", bunniesTotal) + " bunnies");

                    ConstraintLayout.LayoutParams farmParams = new ConstraintLayout.LayoutParams(100, 100);

                    ImageView farm = new ImageView(MainActivity.this);
                    farm.setId(View.generateViewId());
                    farm.setLayoutParams(farmParams);
                    farm.setImageResource(R.drawable.farm);
                    mainLayout.addView(farm);
                    ConstraintSet set = new ConstraintSet();
                    set.clone(mainLayout);

                    set.connect(farm.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP);
                    set.connect(farm.getId(), ConstraintSet.BOTTOM, mainLayout.getId(), ConstraintSet.BOTTOM);
                    set.connect(farm.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT);
                    set.connect(farm.getId(), ConstraintSet.RIGHT, mainLayout.getId(), ConstraintSet.RIGHT);

                    set.setVerticalBias(farm.getId(), 0.89f);
                    set.setHorizontalBias(farm.getId(), hbFarm);
                    set.applyTo(mainLayout);

                    hbFarm += 0.1f;

                }


            }
        });

        easterEggFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bunniesTotal >= 50000 && !easterEggFactoryNum.getText().equals("MAX")) {
                    bunniesTotal -= 50000;
                    bps += 10000;
                    int i = Integer.parseInt(easterEggFactoryNum.getText().toString());
                    i++;
                    if(i == 10){
                        easterEggFactoryNum.setText("MAX");
                        easterEggFactory.setBackgroundColor(Color.rgb(60, 64, 82));
                        easterEggFactoryNum.setBackgroundColor(Color.rgb(60, 64, 82));
                        easterEggFactoryCost.setBackgroundColor(Color.rgb(60, 64, 82));
                    }
                    else {
                        easterEggFactoryNum.setText(String.valueOf(i));
                    }
                    textViewNumBunnies.setText(String.format("%,d", bunniesTotal) + " bunnies");

                    ConstraintLayout.LayoutParams factoryParams = new ConstraintLayout.LayoutParams(100, 100);

                    ImageView factory = new ImageView(MainActivity.this);
                    factory.setId(View.generateViewId());
                    factory.setLayoutParams(factoryParams);
                    factory.setImageResource(R.drawable.easteregg);
                    mainLayout.addView(factory);
                    ConstraintSet set = new ConstraintSet();
                    set.clone(mainLayout);

                    set.connect(factory.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP);
                    set.connect(factory.getId(), ConstraintSet.BOTTOM, mainLayout.getId(), ConstraintSet.BOTTOM);
                    set.connect(factory.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT);
                    set.connect(factory.getId(), ConstraintSet.RIGHT, mainLayout.getId(), ConstraintSet.RIGHT);

                    set.setVerticalBias(factory.getId(), 0.95f);
                    set.setHorizontalBias(factory.getId(), hbFactory);
                    set.applyTo(mainLayout);

                    hbFactory += 0.1f;
                }



            }
        });

    }

    public void updateBunnies(){

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(500);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);

        if(!easterEggBasketNum.getText().equals("MAX")){
            if(bunniesTotal >= 50){
                easterEggBasket.setBackgroundColor(Color.rgb(23, 32, 41));
                easterEggBasketNum.setBackgroundColor(Color.rgb(23, 32, 41));
                easterEggBasketCost.setBackgroundColor(Color.rgb(23, 32, 41));
                if(easterEggBasketImage.getVisibility() == View.INVISIBLE){
                    easterEggBasketImage.startAnimation(fadeIn);
                    easterEggBasketImage.setVisibility(View.VISIBLE);
                }
            }
            else {
                easterEggBasket.setBackgroundColor(Color.rgb(60, 64, 82));
                easterEggBasketNum.setBackgroundColor(Color.rgb(60, 64, 82));
                easterEggBasketCost.setBackgroundColor(Color.rgb(60, 64, 82));
                if(easterEggBasketImage.getVisibility() == View.VISIBLE){
                    easterEggBasketImage.startAnimation(fadeOut);
                    easterEggBasketImage.setVisibility(View.INVISIBLE);
                }
            }
        }

        if(!carrotFarmNum.getText().equals("MAX")){
            if(bunniesTotal >= 1000){
                carrotFarm.setBackgroundColor(Color.rgb(23, 32, 41));
                carrotFarmNum.setBackgroundColor(Color.rgb(23, 32, 41));
                carrotFarmCost.setBackgroundColor(Color.rgb(23, 32, 41));
                if(carrotFarmImage.getVisibility() == View.INVISIBLE){
                    carrotFarmImage.startAnimation(fadeIn);
                    carrotFarmImage.setVisibility(View.VISIBLE);
                }
            }
            else {
                carrotFarm.setBackgroundColor(Color.rgb(60, 64, 82));
                carrotFarmNum.setBackgroundColor(Color.rgb(60, 64, 82));
                carrotFarmCost.setBackgroundColor(Color.rgb(60, 64, 82));
                if(carrotFarmImage.getVisibility() == View.VISIBLE){
                    carrotFarmImage.startAnimation(fadeOut);
                    carrotFarmImage.setVisibility(View.INVISIBLE);
                }
            }
        }

        if(!easterEggFactoryNum.getText().equals("MAX")){
            if(bunniesTotal >= 50000){
                easterEggFactory.setBackgroundColor(Color.rgb(23, 32, 41));
                easterEggFactoryNum.setBackgroundColor(Color.rgb(23, 32, 41));
                easterEggFactoryCost.setBackgroundColor(Color.rgb(23, 32, 41));
                if(easterEggFactoryImage.getVisibility() == View.INVISIBLE){
                    easterEggFactoryImage.startAnimation(fadeIn);
                    easterEggFactoryImage.setVisibility(View.VISIBLE);
                }
            }
            else {
                easterEggFactory.setBackgroundColor(Color.rgb(60, 64, 82));
                easterEggFactoryNum.setBackgroundColor(Color.rgb(60, 64, 82));
                easterEggFactoryCost.setBackgroundColor(Color.rgb(60, 64, 82));
                if(easterEggFactoryImage.getVisibility() == View.VISIBLE){
                    easterEggFactoryImage.startAnimation(fadeOut);
                    easterEggFactoryImage.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public class PassiveIncome extends Thread{
        public void run(){
            while(true){
                try{
                    Thread.sleep(1000);
                    bunniesTotal += bps;
                    textViewNumBunnies.setText(String.format("%,d", bunniesTotal) + " bunnies");
                    updateBunnies();
                    Log.d("HELLO", "Bunnies per second: "+ bps);
                }catch(Exception e){

                }
            }
        }
    }
}