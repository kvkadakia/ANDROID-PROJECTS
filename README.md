# ANDROID-PROJECTS

Threading game contains a game which involves 2 threads playing againts each other based on the following description:

In this project you will design and code a strategy game called Guess Four as an Android app. In this game,
each of two players sets up a secret sequence of four decimal digits, where each digit is not repeated. For
instance, 2017 is a legal sequence, but 2012 is not because the digit 2 is repeated. The two players take turns
guessing the sequence of digits of their opponent. Each guess consists of a 4 digit number, without repeated
digits. A player responds to an opponents guess by specifying the number of digits that were successfully
guessed in the correct position and the number of digits that were successfully guessed but in the wrong
positions. Thus, if a players chosen number is 2017 and the opponent guesses 1089, the opponent would be
told that one digit was guessed correctly in the correct position (i.e., the 0), and another digit was guessed in
the wrong position, (i.e., the 1). You are to write an application that has two background threads play against
each other while also updating the user interface with their moves. The ﬁrst thread to correctly guess the
opposing thread’s number wins the game.
Your implementation will have two Java worker threads play against each other. The UI thread is responsible
for creating and starting the two worker threads and for maintaining and updating the display. Each
worker thread will take turns taking the following actions:
1.Waiting for a short time (1-2 seconds) in order for a human viewer to take note of the previous move on
the display.
2.Figuring out the next guess of this thread.
3.Communicating this guess to the opponent thread.
4.Waiting for a response from the opponent thread.
5.Communicating both the guess and the response from the opposite thread to the UI thread.
While carrying out the above steps each worker thread must also be able to respond to guesses from the
opponent thread. Whenever a guess from the opponent thread is received, a worker thread must respond by
communicating the number of correctly positioned and incorrectly positioned digits contained in the guess to
the opponent thread.
Furthermore, the game must proceed in lockstep between the two threads. A thread is not allowed to make
two consecutive guesses without handling an intervening guess from the opponent thread.
The UI thread is speciﬁcally responsible for the following functionality:
1.Showing the two initial numbers chosen by the worker threads.
2.Receiving notiﬁcations of guesses and their outcomes by the worker threads.
3.Displaying the guesses and responses of each worker thread in an appropriate format as each worker
thread communicates this information to the UI thead.
