Set UAC = CreateObject("Shell.Application")
Set FSO = CreateObject("Scripting.FileSystemObject")
currentDirectory = FSO.GetParentFolderName(WScript.ScriptFullName)
UAC.ShellExecute "cmd.exe", "/c start javaw -jar """ & currentDirectory & "\lol_black_box.jar""", "", "runas", 1
