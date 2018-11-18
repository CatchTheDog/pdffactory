:: 第一个参数为kcc存放路径  第二个参数为 源文件存放路径
@echo off
cd /d %1
python kcc-c2e.py %2
exit