#!/bin/bash

#本地更新后通过rsync同步到远程，在远程通过maven打包成war，在通过ssh部署到web服务器中
WORKSPACE_DIR=/Users/apple/Documents/workspace/eyunda
#上传文件根目录
DESDIR=/mnt/file/src
cd $WORKSPACE_DIR
svn update
echo "sync file!"
rsync --rsh=ssh --exclude-from=$WORKSPACE_DIR/doc/exclude.list -vzrtopg --progress --delete $WORKSPACE_DIR root@192.168.0.210:$DESDIR
echo "package war and deploy war"
curl -X POST "http://api.eyd98.com/jenkins/job/UpdateWebSite/build?delay=0sec" --user guoqiang:guoqiang
for i in `seq -w 120 -1 1`
  do
    echo -ne "........\b\b$i";
    sleep 1;
  done
echo -e "........\b\bdeploy eyunda finished!"