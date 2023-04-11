#!/usr/bin/env bash
#参数值由pom文件传递
activeProfile=${activatedProperties}
baseZipName="${project.artifactId}-${project.version}-${activatedProperties}" #压缩包名称  publish-test.zip的publish
packageName="${project.artifactId}-${project.version}" #命令启动包名 xx.jar的xx

#例子
# baseZipName="publish-test" #压缩包名称  publish-test.zip的publish
# packageName="publish" #命令启动包名 publish.jar的xx

#固定变量
basePath=$(cd `dirname $0`/; pwd)
baseZipPath="${basePath}/${baseZipName}.zip"  #压缩包路径
baseDirPath="${basePath}" #解压部署磁盘路径
pid= #进程pid

#解压
function unzip()
{
    echo "解压---------------------------------------------"
    echo "压缩包路径：${baseZipPath}"
    if [ ! `find ${baseZipPath}` ]
    then
        echo "不存在压缩包：${baseZipPath}"
    else
        echo "解压磁盘路径：${baseDirPath}/${baseZipName}"
        echo "开始解压..."

        #解压命令
        unzip -od ${baseDirPath}/${baseZipName} ${baseZipPath}

        #设置执行权限
        chmod +x ${baseDirPath}/${baseZipName}/${packageName}

        echo "解压完成。"  
    fi
}

#检测pid
function getPid()
{
    echo "检测状态---------------------------------------------"
    pid=`ps -ef | grep -n ${packageName}-${activeProfile} | grep -v grep | awk '{print $2}'`
    if [[ ${pid} ]]
    then
        echo "运行pid：${pid}"
    else
        echo "未运行"
    fi
}

#启动程序
function start()
{
    #启动前，先停止之前的
    stop
    if [[ ${pid} ]]
    then
        echo "停止程序失败，无法启动"
    else
        echo "启动程序---------------------------------------------"

        #进入运行包目录
        cd ${baseDirPath}/${baseZipName}

        nohup java -jar ${baseDirPath}/${baseZipName}/${packageName}.jar ${packageName}-${activeProfile} >/dev/null 2>&1 &

        #查询是否有启动进程
        getPid
        if [[ ${pid} ]]
        then
            echo "已启动"
            #nohup日志
            #tail -n 50 -f ${baseDirPath}/${packageName}-${activeProfile}.out
        else
            echo "启动失败"
        fi
    fi
}

#停止程序
function stop()
{
    getPid
    if [[ ${pid} ]]
    then
        echo "停止程序---------------------------------------------"
        kill -9 ${pid}
        
        getPid
        if [[ ${pid} ]]
        then
            #stop
            echo "停止失败"
        else
            echo "停止成功"
        fi
    fi
}

#启动时带参数，根据参数执行
if [ ${#} -ge 1 ] 
then
    case ${1} in
        "start") 
            start
        ;;
        "restart") 
            start
        ;;
        "stop") 
            stop
        ;;
        "unzip") 
            #执行解压
            unzip
            #执行启动
            start
        ;;
        *) 
            echo "${1}无任何操作"
        ;;
    esac
else
    echo "
    command如下命令：
    unzip：解压并启动
    start：启动
    stop：停止进程
    restart：重启

    示例命令如：./depoly start
    "
fi