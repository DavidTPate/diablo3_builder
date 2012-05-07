Loop, C:\git\diablo-3-character-planner\d3_android\res\drawable-ldpi\*.png
{
	StringReplace, newName, A_LoopFileName, -, _, A
	FileMove, %A_LoopFileDir%\%A_LoopFileName%, %A_LoopFileDir%\%newName%, 1
}