Android Camera Util
===================

<h2>Introduction</h2>
It seems to be the simplest thing in the world: taking a picture within your Android app using the default camera activity. However, there are many pitfalls which are covered in several posts across the web as, for instance, null intents being passed back, the orientation of the picture not being correct or OutOfMemoryErrors. Finally, I managed to get it working on a wide variety of devices (see list below) and I wanted to share my solution with you. The code is partially based on the following posts:

* [Camera Force Closing issue in Samsung Galaxy S3 version 4.1.1](http://stackoverflow.com/questions/14495304/camera-force-closing-issue-in-samsung-galaxy-s3-version-4-1-1)
* [Null Intent passed back On Samsung Galaxy Tab](http://kevinpotgieter.wordpress.com/2011/03/30/null-intent-passed-back-on-samsung-galaxy-tab/)

I tried hard to avoid implementing different strategies based on the device configuration (e.g. manufacturer, model, ...). Unfortunately, I did not get around it. Going through hundreds of posts and talking to several developers, nobody found a solution that works on all devices without implementing device configuration specific code.

<h2>Code related notes</h2>
Please feel free to check out the code and test it on your devices. The [CameraIntentHelper](https://github.com/ralfgehrer/AndroidCameraUtil/blob/master/library/src/de/ecotastic/android/camerautil/lib/CameraIntentHelper.java) class provides the main functionality.

[CameraIntentActivity](https://github.com/ralfgehrer/AndroidCameraUtil/blob/master/sample/src/de/ecotastic/android/camerautil/sample/CameraIntentActivity.java) within the sample package is an example Activity on how to implement the CameraIntentHelper. [CameraIntentFragment](https://github.com/ralfgehrer/AndroidCameraUtil/blob/master/sample/src/de/ecotastic/android/camerautil/sample/CameraIntentFragment.java) held by the [ParentActivity](https://github.com/ralfgehrer/AndroidCameraUtil/blob/master/sample/src/de/ecotastic/android/camerautil/sample/ParentActivity.java) showcases the CameraIntentHelper implementation within a Fragment.


<h2>List of tested devices</h2>
The code was _successfully_ tested on the following devices with Android API-Level >= 8 and stock Android.

* Acer Liquid Z3 (Z130)
* Acer Liquid E2 (V370)
* Alcatel One Touch 992D
* Alps Cynus T1
* Alps Cynus T5
* Alps Cynus F3
* Asus Nexus 7
* Caterpillar CAT B15
* Fairphone FP1
* Fujitsu M532
* HTC Desire 500
* HTC Desire C
* HTC Desire S
* HTC Desire X
* HTC HD2
* HTC One
* HTC One Mini
* HTC One S
* HTC One SV
* HTC One X
* HTC One X+
* HTC One V
* HTC One M8
* HTC Sensation
* Huawei y300-0100
* Huawei p6-u06
* Huawei u8510
* Huawei u9200
* Huawei Ascend G300 (u8815)
* Huawei Ascend G6 (g6-u10)
* Huawei Ascend G700 (g700-u10)
* Huawei Ascend P6 (p6-u6)
* Huawei Ascend Y201 Pro (u8666e)
* Huawei Ascend Y530 (y530-u00)
* LGE Nexus 4
* LGE Nexus 5
* LGE Optimus L3 (lg-e400)
* LGE L4 (lg-d160)
* LGE Optimus L4 II (lg-e440)
* LGE Optimus L5 II (lg-e455 and lg-e460)
* LGE Optimus L7 (lg-p700)
* LGE Optimus L7 II (lg-p710)
* LGE Optimus L9 (lg-p760)
* LGE Optimus 4X (lg-p880)
* LGE Optimus Black (lg-p970)
* LGE Optimus G E975 (lg-e975)
* LGE Optimus 3D (lg-p920)
* LGE Prada (lg-p940)
* LGE G2 (lg-d802)
* Medion p4013
* Mobistel Cynus E1
* Motorola Defy (mb525)
* Motorola Moto G (xt1032)
* Motorola Moto G2 (xt1068)
* Motorola Moto X (xt1052)
* No.1 N3
* Samsung Galaxy W (gt-i8150)
* Samsung Galaxy Ace 2 (gt-i8160)
* Samsung Galaxy S3 mini (gt-i8190)
* Samsung Galaxy S (gt-i9000, yp-gs1)
* Samsung Galaxy S Plus (gt-i9001)
* Samsung Galaxy S Advanced (gt-i9070)
* Samsung Galaxy S Duos (gt-s7562)
* Samsung Galaxy S Duos 2 (gt-s7582)
* Samsung Galaxy SL (gt-i9003)
* Samsung Galaxy S2 (gt-i9100 and gt-i9100g)
* Samsung Galaxy S2 Plus (gt-i9105p)
* Samsung Galaxy S3 (gt-i9300 and gt-i9305 and gt-i9301i)
* Samsung Galaxy S3 mini (gt-i8190n)
* Samsung Galaxy S4 (gt-i9505, gt-i9515, and gt-i9506)
* Samsung Galaxy S4 Active (gt-i9295)
* Samsung Galaxy S4 Mini (gt-i9195)
* Samsung Galaxy S5 (sm-g900f)
* Samsung Galaxy R (gt-i9103)
* Samsung Galaxy Note (gt-n7000)
* Samsung Galaxy Note 2 (gt-n7100 and gt-n7105)
* Samsung Galaxy Note 3 (sm-n900t)
* Samsung Galaxy Note 10.1 (gt-n8000)
* Samsung Galaxy Tab (gt-p1000)
* Samsung Galaxy Tab 2 (gt-p3110 and gt-p5100)
* Samsung Galaxy Tab 3 (sm-t310 and sm-t315)
* Samsung Galaxy TabPRO (sm-t520)
* Samsung Galaxy Fit (gt-s5670)
* Samsung Galaxy Ace (gt-s5830 and gt-s5830i)
* Samsung Galaxy Ace Duos (gt-s6802)
* Samsung Galaxy Ace Plus (gt-s7500)
* Samsung Galaxy Ace 3 (gt-s7275r)
* Samsung Galaxy Young Duos (gt-s6312)
* Samsung Galaxy Mega (gt-i9205)
* Samsung Galaxy Mini (gt-s6500)
* Samsung Galaxy Note 3 (sm-n9005)
* Samsung Galaxy Note 8.0 (gt-n5110)
* Samsung Galaxy Note 10.1 (gt-p5210 and sm-p600 and sm-p605)
* Samsung Galaxy Note 10.1 LTE (gt-n8020)
* Samsung Galaxy Nexus
* Samsung Galaxy Pocket S530 (gt-s5300)
* Samsung Galaxy Xcover (gt-s5690)
* Samsung Galaxy Xcover 2 (gt-s7710)
* Samsung Galaxy Y (gt-s5360)
* Sony Xperia Active (st17i)
* Sony Xperia Arc (lt15i)
* Sony Xperia Arc S (lt18i and st18i)
* Sony Xperia E (c1505)
* Sony Xperia E dual (c1605)
* Sony Xperia Go (st27i)
* Sony Xperia Ion (lt28h)
* Sony Xperia Mini (st15i)
* Sony Xperia Neo V (mt11i)
* Sony Xperia P (lt22i)
* Sony Xperia Ray (st18i)
* Sony Xperia S (lt26i)
* Sony Xperia Sola (mt27i)
* Sony Xperia T (lt30p)
* Sony Xperia Tipo (st21i)
* Sony Xperia Tipo Dual (st21i2)
* Sony Xperia U (st25i)
* Sony Xperia V (lt25i)
* Sony Xperia Z (c6603)
* Sony Xperia Z1 (c6903)
* Sony Xperia Z1 Compact (d5503)
* Sony Xperia Z2 (d6503)


With regards to custom Roms, the code  was _successfully_ tested on the following devices with Android API-Level >= 8 and the CyanogenMod.

* Samsung Galaxy S Plus (gt-i9001)
* Samsung Galaxy Nexus S