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

                // 3
                {
                    layerName: "repository",
                    name: "${ServiceName}RepositoryImpl",
                    path: "${PROJECT_REPOSITORY_PATH}",
                    tpl: "${TPL}/repository/RepoImpl.txt",

                    interName: "${ServiceName}Repository",
                    interPath: "${PROJECT_REPOSITORY_INTER_PATH}",
                    interTpl: "${TPL}/repository/RepoInter.txt",

                    couldDepends: {
                        converter: {
                            name: "${ServiceName}DaoConverter",
                            path: "${PROJECT_REPOSITORY_CONVERTER_PATH}",
                            tpl: "${TPL}/repository/Converter.txt"
                        },
                        model: {
                            name: "${ServiceName}Model",
                            path: "${PROJECT_REPOSITORY_MODEL_PATH}",
                            tpl: "${TPL}/repository/Model.txt"
                        }
                    },
                    depends: [
                        ],
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
                // {
                //     type: "id",
                //     mode: ["controller", "service", "repository", "mapper"],
                //     // mode: ["controller"],
                //     methodLayers: {
                //         controller: {
                //             // 函数
                //             funcName: "query${ServiceName}",
                //             http_method: "Post",
                //             url: "/${ServiceName}/query",
                //         },
                //         service: {
                //             funcName: "query${ServiceName}",
                //             couldDepends: {
                //                 req: {
                //                     name: "${ServiceName}QueryReq",
                //                     path: "${PROJECT_CONTROLLER_REQ_PATH}",
                //                     tpl: "${TPL}/service/Req.txt"
                //                 },
                //                 dto: {
                //                     name: "${ServiceName}QueryDTO",
                //                     path: "${PROJECT_CONTROLLER_RES_PATH}",
                //                     tpl: "${TPL}/service/Dto.txt"
                //                 }
                //             },
                //             depends: [
                //                 {
                //                     dependName: "converter"
                //                 },
                //                 {
                //                     dependName: "req"
                //                 },
                //                 {
                //                     dependName: "dto"
                //                 }
                //             ]
                //         },
                //         repository: {
                //             funcName: "query${ServiceName}",
                //             couldDepends: {
                //                 condition: {
                //                     name: "${ServiceName}Condition",
                //                     path: "${PROJECT_REPOSITORY_CONDITION_PATH}",
                //                     tpl: "${TPL}/repository/Condition.txt"
                //                 }
                //             },
                //             depends: [
                //                 {
                //                     dependName: "converter",
                //                 },
                //                 {
                //                     dependName: "condition",
                //                 },
                //                 {
                //                     dependName: "model",
                //                 }
                //             ]
                //         },
                //         mapper: {
                //             methodHello2: "hello"
                //         }
                //     },
                // },
                {
                    type: "query",
                    mode: ["controller", "service", "repository", "mapper"],
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
                            couldDepends: {
                                req: {
                                    name: "${ServiceName}QueryReq",
                                    path: "${PROJECT_CONTROLLER_REQ_PATH}",
                                    tpl: "${TPL}/service/Req.txt"
                                },
                                dto: {
                                    name: "${ServiceName}QueryDTO",
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
                        repository: {
                            funcName: "query${ServiceName}",
                            couldDepends: {
                                condition: {
                                    name: "${ServiceName}Condition",
                                    path: "${PROJECT_REPOSITORY_CONDITION_PATH}",
                                    tpl: "${TPL}/repository/Condition.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "converter",
                                },
                                {
                                    dependName: "condition",
                                },
                                {
                                    dependName: "model",
                                }
                            ]
                        },
                        mapper: {
                            methodHello2: "hello"
                        }
                    },
                },
                // {
                //     type: "add",
                //     mode: ["controller", "service", "repository", "mapper"],
                //     returnId: true,
                //     // mode: ["controller"],
                //     methodLayers: {
                //         controller: {
                //             // 函数
                //             funcName: "add${ServiceName}",
                //             http_method: "Post",
                //             url: "/${ServiceName}/add",
                //         },
                //         service: {
                //             funcName: "add${ServiceName}",
                //             couldDepends: {
                //                 req: {
                //                     name: "${ServiceName}AddReq",
                //                     path: "${PROJECT_CONTROLLER_REQ_PATH}",
                //                     // 这边复用了 DTO 的模板
                //                     tpl: "${TPL}/service/Dto.txt"
                //                 }
                //             },
                //             depends: [
                //                 {
                //                     dependName: "converter"
                //                 },
                //                 {
                //                     dependName: "req"
                //                 }
                //             ]
                //         },
                //         repository: {
                //             funcName: "add${ServiceName}",
                //             depends: [
                //                 {
                //                     dependName: "converter",
                //                 },
                //                 {
                //                     dependName: "model",
                //                 }
                //             ]
                //         },
                //         mapper: {
                //             methodHello2: "hello"
                //         }
                //     },
                // },
                // {
                //     type: "update",
                //     mode: ["controller", "service", "repository", "mapper"],
                //     // mode: ["controller"],
                //     methodLayers: {
                //         controller: {
                //             // 函数
                //             funcName: "update${ServiceName}",
                //             http_method: "Post",
                //             url: "/${ServiceName}/update",
                //         },
                //         service: {
                //             funcName: "update${ServiceName}",
                //             couldDepends: {
                //                 req: {
                //                     name: "${ServiceName}UpdateReq",
                //                     path: "${PROJECT_CONTROLLER_REQ_PATH}",
                //                     tpl: "${TPL}/service/Req.txt"
                //                 },
                //                 dto: {
                //                     name: "${ServiceName}UpdateDTO",
                //                     path: "${PROJECT_CONTROLLER_RES_PATH}",
                //                     tpl: "${TPL}/service/Dto.txt"
                //                 }
                //             },
                //             depends: [
                //                 {
                //                     dependName: "converter"
                //                 },
                //                 {
                //                     dependName: "req"
                //                 },
                //                 {
                //                     dependName: "dto"
                //                 }
                //             ]
                //         },
                //         repository: {
                //             funcName: "update${ServiceName}",
                //             couldDepends: {
                //                 condition: {
                //                     name: "${ServiceName}Condition",
                //                     path: "${PROJECT_REPOSITORY_CONDITION_PATH}",
                //                     tpl: "${TPL}/repository/Condition.txt"
                //                 }
                //             },
                //             depends: [
                //                 {
                //                     dependName: "converter",
                //                 },
                //                 {
                //                     dependName: "condition",
                //                 },
                //                 {
                //                     dependName: "model",
                //                 }
                //             ]
                //         },
                //         mapper: {
                //             methodHello2: "hello"
                //         }
                //     },
                // },
                {
                    type: "delete",
                    mode: ["controller", "service", "repository", "mapper"],
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
                        repository: {
                            funcName: "delete${ServiceName}",
                            couldDepends: {
                                condition: {
                                    name: "${ServiceName}Delete",
                                    path: "${PROJECT_REPOSITORY_CONDITION_PATH}",
                                    tpl: "${TPL}/repository/Condition.txt"
                                }
                            },
                            depends: [
                                {
                                    dependName: "condition",
                                    on: "return sqlParsed.inputs != null && sqlParsed.inputs.size() > 0 && " +
                                        "(sqlParsed.inputs.size() > 1 || sqlParsed.inputs.get(0) != 'id')",
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


