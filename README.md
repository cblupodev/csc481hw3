Link to project description [csc481-f16-hw4.pdf - github.com](https://github.com/cblupodev/csc481hw3/blob/master/csc481-f16-hw4.pdf)

The packages are labeled by section however its really all one big thing

I included a screencast of the code running in DEMO.mp4
- These instructions are for Windows
- The subdirectories are in "src\csc481hw3"

- Open a cmd.exe terminal and make working directory the folder you extracted the files to
- run this command:
   java -jar Hw3Server.jar
- Open three cmd.exe terminals and make working directory the folder you extracted the files to
- In each terminal run this command:
   java -jar Hw3Client.jar 127.0.0.1
- Every once in a while when you run the third Client3.jar application it will crap out and hang. 
  I know the line that causes the problem but I can't figure out why that is happening. 
  If you kill all applications and run everything again (including the server)
  eventually the third  Client3.jar will work. DEMO.mp4 shows a run where all three are working

- To start recording hit "r"
- To stop recording and start the repaly hit "s"
