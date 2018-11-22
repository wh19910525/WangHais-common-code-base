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

############### func-003, 确认结果 ##############
confirm_selection(){
    if [ $# == 0 ];then
        result="default"
    else
        result=$@
    fi

    echo
    print_color "Your choose is: [${result}]"
    echo -n "is this correct [y/n]? "
    read t_Confirm
    #echo Confirm=$t_Confirm
    if [ x$t_Confirm != xy ];then
        exit 1
    fi
}



