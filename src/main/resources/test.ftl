<#import "./utils" as utils>
<#assign a = "       ">
    if (<#list values as v><#if v_index == 0>${v}<#else>
        && ${v}</#if></#list>) {
    }
