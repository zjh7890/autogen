function getLayers() {
    return JSON.stringify({
        default: {
            // 内置 $TPL 变量
            projectPath: "${PROJECT_PATH}",
            layers: [
                // 1
                {
                    // 文件
                    // 包含path的变量一般指包路径
                    layerName: "controller",
                    name: "${ServiceName}Controller",
                    path: "${PROJECT_CONTROLLER_PATH}",
                    tpl: "${TPL}/controller/Controller.txt",
                    
                    couldDepends: {
                        CommonPageResponse: {
                            name: "CommonPageResponse",
                            path: "${TPL_BASE_OBJ_PATH}",
                            tpl: "${TPL}/controller/CommonPageResponse.txt"
                        },
                        CommonResponse: {
                            name: "CommonResponse",
                            path: "${TPL_BASE_OBJ_PATH}",
                            tpl: "${TPL}/controller/CommonResponse.txt"
                        },
                    },
                    // 模板的依赖，如果改变模板，模板相关
                    depends: [
                        {
                            dependName: "CommonPageResponse"
                        },
                        {
                            dependName: "CommonResponse"
                        }
                    ]
                },
                // 2
                {
                    layerName: "service",
                    name: "${ServiceName}Service",
                    path: "${PROJECT_SERVICE_PATH}",
                    tpl: "${TPL}/service/Service.txt",
                    typeinfo: [
                        {
                            specifyType: "type",
                            specify: "Date",
                            type: "String",
                        },
                        {
                            specifyType: "field",
                            specify: "createTime",
                            type: "String",
                        }
                    ],
                    couldDepends: {
                        PageRes: {
                            name: "PageRes",
                            path: "${TPL_BASE_OBJ_PATH}",
                            tpl: "${TPL}/service/PageRes.txt"
                        },
                        converter: {
                            name: "${ServiceName}Converter",
                            path: "${PROJECT_SERVICE_CONVERTER_PATH}",
                            tpl: "${TPL}/service/Converter.txt",
                        },
                    },
                    depends: [
                        {
                            dependName: "PageRes"
                        }
                    ]
                },
                {
                    // 没有 tpl，意味着纯纯从原来的文件中读取
                    layerName: "mapper",

                    interName: "${TableName}Mapper",
                    interPath: "${PROJECT_MAPPER_INTER_PATH}",

                    couldDepends: {
                        example: {
                            name: "${TableName}Example",
                            path: "${PROJECT_MAPPER_EXAMPLE_PATH}"
                        },
                        entity: {
                            name: "${TableName}",
                            path: "${PROJECT_MAPPER_ENTITY_PATH}",
                            __BASE: true,
                        }
                    },
                    depends: [
                        {
                            dependName: "example"
                        },
                        {
                            dependName: "entity"
                        }
                    ],
                }
            ],
            methodTpls: [
                {
                    type: "id",
                    mode: ["controller", "service", "mapper"],
                    // mode: ["controller"],
                    methodLayers: {
                        controller: {
                            // 函数
                            funcName: "query${ServiceName}ById",
                            http_method: "Post",
                            url: "/${ServiceName}/queryById",
                        },
                        service: {
                            funcName: "query${ServiceName}ById",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}IdReq",
                                    path: "${PROJECT_CONTROLLER_REQ_PATH}",
                                    tpl: "${TPL}/service/Req.txt"
                                },
                                dto: {
                                    name: "${ServiceName}Dto",
                                    path: "${PROJECT_CONTROLLER_RES_PATH}",
                                    tpl: "${TPL}/service/Dto.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "converter"
                                },
                                {
                                    dependName: "req"
                                },
                                {
                                    dependName: "dto"
                                }
                            ]
                        },
                        mapper: {
                            methodHello2: "hello"
                        }
                    },
                },
                {
                    type: "query",
                    mode: ["controller", "service", "mapper"],
                    // mode: ["controller"],
                    methodLayers: {
                        controller: {
                            // 函数
                            funcName: "query${ServiceName}",
                            http_method: "Post",
                            url: "/${ServiceName}/query",
                        },
                        service: {
                            funcName: "query${ServiceName}",
                            countFunc: "count${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}QueryReq",
                                    path: "${PROJECT_CONTROLLER_REQ_PATH}",
                                    tpl: "${TPL}/service/Req.txt"
                                },
                                dto: {
                                    name: "${ServiceName}QueryDto",
                                    path: "${PROJECT_CONTROLLER_RES_PATH}",
                                    tpl: "${TPL}/service/Dto.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "converter"
                                },
                                {
                                    dependName: "req"
                                },
                                {
                                    dependName: "dto"
                                }
                            ]
                        },
                        mapper: {
                            methodHello2: "hello"
                        }
                    },
                },
                {
                    type: "add",
                    mode: ["controller", "service", "mapper"],
                    returnId: true,
                    // mode: ["controller"],
                    methodLayers: {
                        controller: {
                            // 函数
                            funcName: "add${ServiceName}",
                            http_method: "Post",
                            url: "/${ServiceName}/add",
                        },
                        service: {
                            funcName: "add${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}AddReq",
                                    path: "${PROJECT_CONTROLLER_REQ_PATH}",
                                    // 这边复用了 Dto 的模板
                                    tpl: "${TPL}/service/Dto.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "converter"
                                },
                                {
                                    dependName: "req"
                                }
                            ]
                        },
                        mapper: {
                            methodHello2: "hello"
                        }
                    },
                },
                {
                    type: "update",
                    mode: ["controller", "service", "mapper"],
                    // mode: ["controller"],
                    methodLayers: {
                        controller: {
                            // 函数
                            funcName: "update${ServiceName}",
                            http_method: "Post",
                            url: "/${ServiceName}/update",
                        },
                        service: {
                            funcName: "update${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}UpdateReq",
                                    path: "${PROJECT_CONTROLLER_REQ_PATH}",
                                    tpl: "${TPL}/service/Dto.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "converter"
                                },
                                {
                                    dependName: "req"
                                },
                            ]
                        },
                        mapper: {
                            methodHello2: "hello"
                        }
                    },
                },
                {
                    type: "upsert",
                    mode: ["controller", "service", "mapper"],
                    returnId: true,
                    // mode: ["controller"],
                    methodLayers: {
                        controller: {
                            // 函数
                            funcName: "upsert${ServiceName}",
                            http_method: "Post",
                            url: "/${ServiceName}/upsert",
                        },
                        service: {
                            funcName: "upsert${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}UpsertReq",
                                    path: "${PROJECT_CONTROLLER_REQ_PATH}",
                                    // 这边复用了 Dto 的模板
                                    tpl: "${TPL}/service/Dto.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "converter"
                                },
                                {
                                    dependName: "req"
                                }
                            ]
                        },
                        mapper: {
                            methodHello2: "hello"
                        }
                    },
                },
                {
                    type: "delete",
                    mode: ["controller", "service", "mapper"],
                    // mode: ["controller"],
                    methodLayers: {
                        controller: {
                            // 函数
                            funcName: "delete${ServiceName}",
                            http_method: "Post",
                            url: "/${ServiceName}/delete",
                        },
                        service: {
                            funcName: "delete${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}DeleteReq",
                                    path: "${PROJECT_CONTROLLER_REQ_PATH}",
                                    tpl: "${TPL}/service/Req.txt"
                                }
                            },
                            depends: [
                                {
                                    on: "return (binding.hasVariable('repository') && repository['condition'] != null)",
                                    dependName: "converter"
                                },
                                {
                                    dependName: "req"
                                }
                            ]
                        },
                        mapper: {
                            methodHello2: "hello"
                        }
                    },
                }
            ]
        }
    })
}


