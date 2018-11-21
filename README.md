# Material-Animaltion

> 英文原文 [https://github.com/lgvalle/Material-Animations ](https://github.com/lgvalle/Material-Animations)

> 这是GitHub上的一个开源项目([点击传送门](https://github.com/lgvalle/Material-Animations))， 演示View的平移、缩放动画，activity进入和退出动画，界面间元素共享。


## Andorid Transitions Framework

>[了解 Transition Framework](https://developer.android.com/training/transitions/overview.html)

#### 作用

- 可以在activity之间跳转的时候添加动画

- 动画共享元素之间的转换活动

- activity中布局元素的过渡动画。

## 1. Transitions between Activitys

-  Animate existing activity layout content
    
![image](https://raw.githubusercontent.com/lgvalle/Material-Animations/master/screenshots/transition_A_to_B.png)

**当过渡从activity到activity内容布局是根据定义的过渡动画。有三个预定义的转换android.transition上可用。转换可以使用:Explode,Slide和Fade。所有这些转换跟踪更改目标的可见性活动视图布局和动画那些观点遵循转换规则。**



Explode | Slide | Fade
---|---|---
![image](https://raw.githubusercontent.com/lgvalle/Material-Animations/master/screenshots/transition_explode.gif)|![image](http://olcfylmob.bkt.clouddn.com/transition_slide.gif)|![image](https://raw.githubusercontent.com/lgvalle/Material-Animations/master/screenshots/transition_fade.gif)

 实现这些效果可以通过xml方式或者在直接在类中实现,下面是Fade的实现方式。
 
 ###  说明：Fade
 
 1. 如果是xml方式实现，首先在/res下创建transition文件夹。
 
> res/transition/slide_from_right

```
<?xml version = 1.0 encoding = "utf-8"?>
<transitionSet xmls:android = "http://schemas.android.com/apk/res/android">

<slide duration = "500"
       slideEage = "left"/>
       
</transitionSet>

```

 2.如果直接用代码实现，可以如下实现
 
 > MainActivity

 ```
 Slide slideTracition = newSlide();
 
 slideTracition.setSlideEdge(Gravity.LEFT);
 
 slideTracition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
    

 ```

3. 实现 

> MianActivity.onCreat();

设置MainActivty的进出动画代码如下

```
  private void setupWindowAnimations() {
        Transition slideTracition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_left);
        getWindow().setEnterTransition(slideTracition);
        getWindow().setExitTransition(slideTracition);
        //getWindow().setReenterTransition(buildExitTransition());
    }
    
```


4. 分析Fade的步骤是怎么发生的
   1. ActivatyA 启动 ActivityB
   2. Transition Framework找到一个ExitTransition，并将其应用于所有可见视图。
   3. 在返回之前过渡框架执行进入和退出分别反向动画（如果我们定义的输出returnTransition和reenterTransition，这些都已经转而执行）


###  ReturnTransition＆ReenterTransition

 返回和重新输入转换分别是Enter和Exit的反向动画。
 
  - EnterTransition < - > ReturnTransition
  - ExitTransition < - > ReenterTransition

如果未定义返回或重新输入，Android将按照你之前设定的默认的版本。但是如果你定义它们，你可以有不同的转换进入和退出活动。(如下图)

![image](http://olcfylmob.bkt.clouddn.com/transition_B_to_A.png)

 - 当你从ActivityB返回到ActivityA的时候。需要重新制定ActivityB的退出动画，可以通过如下方式

```
Visiable slide = new Slide();
slide.setDuraing(500);
getWindow.setReturnTransition(slide);
//别直接调用finish();
finshAfterTransition();

```


 **效果图**

Without Return Transition | With Return Transition
---|---
![image](https://raw.githubusercontent.com/lgvalle/Material-Animations/master/screenshots/transition_fade.gif) | ![image](https://raw.githubusercontent.com/lgvalle/Material-Animations/master/screenshots/transition_fade2.gif)


##  2.Share elements between Activity(元素共享)

**共享元素过度动画的背后是通过过度动画将两个不同布局中的不同view关联起来。Transition框架知道用适当的动画向用户展示从一个view向另外 一个view过度。请记住：共享元素过度的过程中，view并没有真正从一个布局跑到另外一个布局，整个过程基本都是在后一个布局中完成的。**

![image](https://raw.githubusercontent.com/lgvalle/Material-Animations/master/screenshots/shared_element.png)

### a) 允许过度动画

> 需要在/res/style.xml添加

```
   <item name="android:windowContentTransitions">true</item>
   
```

### b) 在对应的xml文件指定TransitionName属性

> res/layout/activity

- 指定ImageView和TextView

**注意：这里必须为共享的俩个元素指定同一个TransitionName，不然不会出现共享效果**

```
 <ImageView
        android:id="@+id/square_blue"
        style="@style/MaterialAnimations.Icon.Big"
        android:src="@drawable/circle_24dp"
        android:transitionName="@string/square_blue_name" />
```

```
<TextView
       android:id="@+id/title"   
       style="@style/MaterialAnimations.TextAppearance.Title.Invers"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_gravity="center_vertical|start"
       android:text="@{sharedSample.name}"
       android:transitionName="@string/sample_blue_title" />

```

### c) 启动Activity

```

Intent intent = new Intent(activity,target);
ActivityOptionCompat option = ActiviyoptionCampat.makeSceneTransitionAnimation(activity,  
new Pair<View, String>(viewHolder.binding.sampleIcon, activity.getString(R.string.square_blue_name)),
new Pair<View, String>(viewHolder.binding.sampleName, activity.getString(R.string.sample_blue_title)));

startActivity(intent,option.toBundle());

```

**这段代码将生成这个美丽的过渡动画:**

![image](https://raw.githubusercontent.com/lgvalle/Material-Animations/master/screenshots/shared_element_anim.gif)

## 那么在Fragment之间怎么实现呢？

### a) 允许过度动画

> 需要在/res/style.xml添加

```
   <item name="android:windowContentTransitions">true</item>
   
```

### b) 在对应的xml文件指定TransitionName属性

>之前的俩个步骤和Activity之间共享元素的没有太大差别。


### c) 通过Shared Element方式启动fragment

```
  private void addNextFragment(Sample sample, ImageView blue, boolean b) {
        SharedElementFragment2 elementFragment2 = SharedElementFragment2.newInstance(sample);
        Slide slide = new Slide();
        slide.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        slide.setSlideEdge(Gravity.RIGHT);
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        elementFragment2.setEnterTransition(slide);
        elementFragment2.setAllowEnterTransitionOverlap(b);
        elementFragment2.setAllowReturnTransitionOverlap(b);
        elementFragment2.setSharedElementEnterTransition(changeBounds);
        getFragmentManager().beginTransaction().replace(R.id.sample2_content, elementFragment2).addToBackStack(null).addSharedElement(blue, getString(R.string.square_blue_name)).commit();
    }

```


**效果如下**

![image](http://olcfylmob.bkt.clouddn.com/shared_element_no_overlap.gif-sizeImage)


## 3.动画视图布局元素

上面俩种方式都是运用于过度动画，那Transition FrameWork也可以被用做于改变布局中的某个特定的View，比如修改View的位置或者大小。我们需要确定想改变的结果即可。

### 1.我们需要告诉framework我们需要改动界面的Ui

```
TransitionManager.beginDelayedTransition(viewRoot);

```
- 这里的viewRoot是需要改变view当前所在的根布局

### 2.改变View的属性

- 改变大小

```
 ViewGroup.LayoutParams params = view.getLayoutParams();
 params.witdh = 200;
 view.setlayoutParams(params);

```


- 改变位置

```
  ViewGroup.LayoutParams params = view.getLayoutParams();
 params.gravity = Gravity.Left;
 view.setlayoutParams(params);

```


Size | Position
---|---
![image](http://olcfylmob.bkt.clouddn.com/view_layout_anim.gif-sizeImage) | ![image](http://olcfylmob.bkt.clouddn.com/change_size.gif-sizeImage)


## 4.共享元素+循环动画

> 循环显示只是一个动画显示或隐藏一组UI元素。它可以自API21或以上使用。

![image](
http://olcfylmob.bkt.clouddn.com/shared_reveal_anim.gif-sizeImage)


**上图发生了几个步骤？了解了之前的共享元素后我们知道。**

- 黄色的小球共享于MainActivity和RevealActivity。
- 当共享动画结束之后，在RevealActivity中发生了俩组动画效果
    - Toolbar上
    - 底部四个球执行了特定的动画


> 那我们应该如何监听共享元素的动画结束的时刻。

```
  private void setupEnterAnimations() {
        Transition transtion = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transtion);
        transtion.addListener(new Transition.TransitionListener() {
         
            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                hideTarget();
                animateRevealShow(toolbar);
                animateButtonIn();
            }

           
        });
    }
```


> res/transition/changebounds_with_arcmotion.xml

```
<?xml version="1.0" encoding="utf-8"?>
<transitionSet xmlns:android="http://schemas.android.com/apk/res/android"
               android:duration="@integer/anim_duration_long"
               android:interpolator="@android:interpolator/decelerate_cubic"
    >
    <changeBounds>
        <arcMotion
            android:maximumAngle="90"
            android:minimumHorizontalAngle="90"
            android:minimumVerticalAngle="0"/>
    </changeBounds>
</transitionSet>

```

> ToolBar Animation

```
private void animateRevealShow(View viewRoot) {

    int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
    int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
    int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

    Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
    viewRoot.setVisibility(View.VISIBLE);
    anim.setDuration(1000);
    anim.setInterpolator(new AccelerateInterpolator());
    anim.start();
}

```

> 四个小球的浮现的动画

```
private void animateButtonIn() {
        for (int i = 0; i < bgViewGroup.getChildCount(); i++) {
            View child = bgViewGroup.getChildAt(i);
            child.animate()
                    .setStartDelay(100 + i * DELAY)
                    .setInterpolator(new AccelerateInterpolator())
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1);
        }
    }
```
### 另一种效果

> 红色小球的效果

![image](http://olcfylmob.bkt.clouddn.com/reveal_red.gif-sizeImage)


**红球运动轨迹 res/transition/changebounds_with_arcmotion.xml(这里不是共享动画)**

> res/transition/changebounds_with_arcmotion.xml

```
<?xml version="1.0" encoding="utf-8"?>
<transitionSet xmlns:android="http://schemas.android.com/apk/res/android"
               android:duration="@integer/anim_duration_long"
               android:interpolator="@android:interpolator/decelerate_cubic"
    >
    <changeBounds>
        <arcMotion
            android:maximumAngle="90"
            android:minimumHorizontalAngle="90"
            android:minimumVerticalAngle="0"/>
    </changeBounds>
</transitionSet>

```

> 点击红色小球时 执行 revealRed()
 - 

```
  private void revealRed() {
        final ViewGroup.LayoutParams params = btnRed.getLayoutParams();
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealColor(bgViewGroup, R.color.sample_red);
                body.setText(R.string.reveal_body3);
                body.setTextColor(ContextCompat.getColor(RevealActivity.this, R.color.theme_red_background));
                btnRed.setLayoutParams(params);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        TransitionManager.beginDelayedTransition(bgViewGroup, transition);
        final RelativeLayout.LayoutParams relativeRarams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeRarams.addRule(RelativeLayout.CENTER_IN_PARENT);
        btnRed.setLayoutParams(relativeRarams);
    }
    
    
    
    private void animateRevealColor(ViewGroup viewRoot, @ColorRes int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        animateRevealColorFromCoordinates(viewRoot, color, cx, cy);
    }
    
    
    
     private Animator animateRevealColorFromCoordinates(ViewGroup root, @ColorRes int color, int cx, int cy) {
        int finalRadius = Math.max(root.getWidth(), root.getHeight());
        final Animator animator = ViewAnimationUtils.createCircularReveal(root, cx, cy, 0, finalRadius);
        root.setBackgroundColor(ContextCompat.getColor(this, color));
        animator.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        return animator;
    }
```

## 更多信息

-  如果你想深入了解和学习更多的有关于过度动画(Transition Framework)的内容。可以访问 Alex Lockwood 的帖子 [http : //www.androiddesignpatterns.com/2014/12/activity-fragment-transitions-in-android-lollipop-part1.html](http://www.androiddesignpatterns.com/2014/12/activity-fragment-transitions-in-android-lollipop-part1.html)
-  惊人的存储库与许多材料设计样本由Saul Molinero: [https://github.com/saulmm/Android-Material-Examples](https://github.com/saulmm/Android-Material-Examples)
-  Chet Hasse视频详细讲解了过渡框架: [https://www.youtube.com/watch?v=S3H7nJ4QaD8](https://www.youtube.com/watch?v=S3H7nJ4QaD8)







