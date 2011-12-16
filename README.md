Virtual Piano
-------------
Play piano (or any instrument) using your computer keyboard in a positionally-natural manner!
Play any of the 128 provided MIDI instruments!
Simulate sustain pedal! (by holding space bar)

This program is running in Java Virtual Machine, using Java MIDI library.


How to run
----------
Don't worry, you're not required to compile code :)

Simply save this file from the source tree, and run:  `out/VirtualPiano.jar`

You must have java runtime in your machine (most computers these days already have it).
Usually double-clicking the .jar file is sufficient to launch it.

In the rare case that launching it directly doesn't work, you may need to launch with command line.
e.g. do

<pre>
java -jar "<path to the file>/VirtualPiano.jar" &
</pre>


Keyboard use
------------

<pre>
(3rd and 4th rows of qwerty keyboard)
zsxdcvgbhnjm           = C C# D ... through B, first octave

(1st and 2nd rows of qwerty keyboard)
q2w3er5t6y7u           = C C# D ... through B, second octave
i9o0p[=] BKSPC BKSLASH = C C# D ... through A, third octave

spacebar               = sustain pedal

left arrow             = previous instrument
right arrow            = next instrument

page up                = change octave +1
page dn                = change octave -1

enter                  = reset (stop all notes)
</pre>


Instruments
-----------
The list of MIDI instruments correspond to the standard list (you can find it on the web).
Some highlights:

<pre>
0   acoustic grand piano
40  violin
73  flute
123 bird tweet
</pre>


Mouse use
---------
If you want to enable mouse use, modify the source code on file Piano.java
(uncomment code in PianoMouseListener) and rebuild.


About the source code
---------------------
There are resource files in the project.  If your IDE's compiler does not recognize some of the
resource files, please modify your IDE settings to recognize resource files with those
file extensions.  (e.g. IntelliJ has problem running in the first attempt)


Troubleshooting
---------------

**There is no sound!**

Remember that this program uses MIDI sound.  Please check if your volume settings for MIDI is not
at zero level or muted.

If you are a Windows user, you may not have java soundbank.  Just download one of this zipped
sound files:

<pre>
http://java.sun.com/products/java-media/sound/soundbank-min.gm.zip
http://java.sun.com/products/java-media/sound/soundbank-mid.gm.zip
http://java.sun.com/products/java-media/sound/soundbank-deluxe.gm.zip
</pre>
(the last is the biggest file, 5 MB, but has the best quality sound)

Unzip it, and put the file in your "C:\Program Files\Java\jre1.6.0\lib\audio" folder.
Your jre version may be different 1.6.0, but that's no problem.
Just put it in the jreX.X.X\lib\audio directory.


**In Linux, pressing a key results in the piano key being pressed rapidly multiple times**

This is a known OS bug that affects many Linux distros.  Unfortunately we can do nothing about it.
A workaround is to set your keyboard settings (through system settings) to disable key repeats
while you are using this program.


**Sound quality is very bad**

Well, it's MIDI :)

Note that different computers may produce different sounds.  It really depends on your sound card /
the embedded sound card being used.


**Sometimes pressing many keys at the same time will produce an error beep instead of sounding a note**

This is normal and is caused by the internal wiring of a computer keyboard.  There are always some
pairs of keys that cannot be pressed together in any keyboard.  This affects all programs, not just
this program.  Unfortunately we can do nothing about it.


Why was this program written?
-----------------------------
It was 2007.  I just finished my first semester in college abroad, far away from home.
It was unrealistic to fly home halfway across the globe, so during the time my school
dormitory was closed during the winter break, my kind relatives let me stay in their house.

There was not much to do that could stimulate myself enough, so I decided to write a program
to turn my computer keyboard into a piano keyboard, so I can play myself some little
Christmas songs and other tunes in the cold workday afternoons, when the house was quiet and empty
but my heart doesn't need to be.


END
