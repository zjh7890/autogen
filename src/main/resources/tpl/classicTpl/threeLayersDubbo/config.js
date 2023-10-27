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
                    layerName: "dubbo",
                    name: "${ServiceName}ServiceImpl",
                    path: "${PROJECT_DUBBO_PATH}",
                    tpl: "${TPL}/dubbo/Dubbo.txt",

                    interName: "${TableName}Service",
                    interPath: "${PROJECT_DUBBO_INTER_PATH}",
                    interTpl: "${TPL}/dubbo/DubboInter.txt",

                    couldDepends: {
                    },
                    // 模板的依赖，如果改变模板，模板相关
                    depends: [
                    ]
                },
                // 2
                {
                    layerName: "service",
                    name: "${ServiceName}Manager",
                    path: "${PROJECT_SERVICE_PATH}",
                    tpl: "${TPL}/service/Service.txt",
                    typeinfo: [
                        {
                            specifyType: "type",
                            specify: "Date",
                            type: "String",
                        }
                    ],
                    couldDepends: {
                    },
                    depends: [
                    ]
                },
                {
                    // 没有 tpl，意味着纯纯从原来的文件中读取
                    layerName: "mapper",

                    interName: "${TableName}Mapper",
                    interPath: "${PROJECT_MAPPER_INTER_PATH}",

                    couldDepends: {
                        entity: {
                            name: "${TableName}",
                            path: "${PROJECT_MAPPER_ENTITY_PATH}",
                            __BASE: true,
                        }
                    },
                    depends: [
                        {
                            dependName: "entity"
                        }
                    ],
                }
            ],
            methodTpls: [
                {
                    type: "id",
                    mode: ["dubbo", "service", "mapper"],
                    // mode: ["dubbo"],
                    methodLayers: {
                        dubbo: {
                            // 函数
                            funcName: "find${ServiceName}ById",
                        },
                        service: {
                            funcName: "find${ServiceName}ById",
                            couldDepends: {
                                dto: {
                                    name: "${ServiceName}DTO",
                                    path: "${PROJECT_DUBBO_RES_PATH}",
                                    tpl: "${TPL}/service/DTO.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "dto"
                                }
                            ]
                        },
                        mapper: {
                        }
                    },
                },
                {
                    type: "query",
                    mode: ["dubbo", "service", "mapper"],
                    // mode: ["dubbo"],
                    methodLayers: {
                        dubbo: {
                            // 函数
                            funcName: "query${ServiceName}",
                        },
                        service: {
                            funcName: "query${ServiceName}",
                            countFunc: "count${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}QueryReq",
                                    path: "${PROJECT_DUBBO_REQ_PATH}",
                                    tpl: "${TPL}/service/Req.txt"
                                },
                                dto: {
                                    name: "${ServiceName}QueryDTO",
                                    path: "${PROJECT_DUBBO_RES_PATH}",
                                    tpl: "${TPL}/service/DTO.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "req"
                                },
                                {
                                    dependName: "dto"
                                }
                            ]
                        },
                        mapper: {
                        }
                    },
                },
                {
                    type: "add",
                    mode: ["dubbo", "service", "mapper"],
                    returnId: true,
                    // mode: ["dubbo"],
                    methodLayers: {
                        dubbo: {
                            // 函数
                            funcName: "add${ServiceName}",
                            http_method: "Post",
                            url: "/add",
                        },
                        service: {
                            funcName: "add${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}AddReq",
                                    path: "${PROJECT_DUBBO_REQ_PATH}",
                                    // 这边复用了 DTO 的模板
                                    tpl: "${TPL}/service/DTO.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "req"
                                }
                            ]
                        },
                        mapper: {
                        }
                    },
                },
                {
                    type: "update",
                    mode: ["dubbo", "service", "mapper"],
                    // mode: ["dubbo"],
                    methodLayers: {
                        dubbo: {
                            // 函数
                            funcName: "update${ServiceName}",
                            http_method: "Post",
                            url: "/update",
                        },
                        service: {
                            funcName: "update${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}UpdateReq",
                                    path: "${PROJECT_DUBBO_REQ_PATH}",
                                    tpl: "${TPL}/service/DTO.txt"
                                }
                            },
                            depends: [
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
                    mode: ["dubbo", "service", "mapper"],
                    returnId: true,
                    // mode: ["dubbo"],
                    methodLayers: {
                        dubbo: {
                            // 函数
                            funcName: "upsert${ServiceName}",
                            http_method: "Post",
                            url: "/upsert",
                        },
                        service: {
                            funcName: "upsert${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}UpsertReq",
                                    path: "${PROJECT_DUBBO_REQ_PATH}",
                                    // 这边复用了 DTO 的模板
                                    tpl: "${TPL}/service/DTO.txt"
                                }
                            },
                            depends: [
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
                    mode: ["dubbo", "service", "mapper"],
                    // mode: ["dubbo"],
                    methodLayers: {
                        dubbo: {
                            // 函数
                            funcName: "delete${ServiceName}",
                            http_method: "Post",
                            url: "/delete",
                        },
                        service: {
                            funcName: "delete${ServiceName}",
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}DeleteReq",
                                    path: "${PROJECT_DUBBO_REQ_PATH}",
                                    tpl: "${TPL}/service/Req.txt"
                                }
                            },
                            depends: [
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


