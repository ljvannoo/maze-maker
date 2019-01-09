cd \Workspaces\workspace\MazeMaker\classes
jar cvf MazeMaker.jar ..\webfiles\MANIFEST.MF *
move /Y MazeMaker.jar \Workspaces\workspace\MazeMaker\webfiles
cd \Workspaces\workspace\MazeMaker\webfiles
jarsigner -keystore delvern.ks MazeMaker.jar blackmud
pscp MazeMaker.jar delvern@delvern.com:blackmud.delvern.com/mazemaker/