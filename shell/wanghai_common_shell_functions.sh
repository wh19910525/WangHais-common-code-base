#!/bin/bash

#######################################
# Author: wanghai
#######################################

:<<!
每一个 程序最好添加 这个判断;
##########################################
#
# Wang Hai's commonly used linux commands.
#
if [ -z ${WANGHAI_USUAL_SHELL_CMD} ];then
    echo -en "\033[32m"
    echo "please import Wang Hai's commonly used linux commands."
    echo -en "\033[0m"

    exit 4
else
    source ${WANGHAI_USUAL_SHELL_CMD}
fi
!

####### func-001, 添加颜色, 输出字符串 ########
start_color_value=31
end_color_value=36

color_value=$start_color_value
print_color () {
    echo -en "\033[${color_value}m"
    echo "$@"
    echo -en "\033[0m"
    if [ $color_value -eq $end_color_value ];then
        color_value=31
    else
        ((color_value++))
    fi
}

############### func-002, 执行命令 ##############
exit_code=5
exec_cmd(){
    #echo "$#"
    print_color "exec [${@}]"
    ${@}

    if [ ${?} -ne 0 ];then
        echo "exec [${@}] failed, exit!"
        exit ${exit_code}
    fi

    ((exit_code=${exit_code}+1))
    echo
}
