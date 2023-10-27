function getProjects() {
    return JSON.stringify({
        eagle: {
            PROJECT_PATH: "/Users/zjh/IdeaProjects/eagle",
            PROJECT_CONTROLLER_PATH: "/rule-api/src/main/java/com/example/ruleapi/controller",
            PROJECT_CONTROLLER_REQ_PATH: "/rule-api/src/main/java/com/example/ruleapi/controller/dto/req",
            PROJECT_CONTROLLER_RES_PATH: "/rule-api/src/main/java/com/example/ruleapi/controller/dto/res",
            PROJECT_SERVICE_PATH: "/rule-api/src/main/java/com/example/ruleapi/service",
            PROJECT_SERVICE_CONVERTER_PATH: "/rule-api/src/main/java/com/example/ruleapi/service/converter",
            PROJECT_REPOSITORY_PATH: "/rule-api/src/main/java/com/example/ruleapi/repository/impl",
            PROJECT_REPOSITORY_INTER_PATH: "/rule-api/src/main/java/com/example/ruleapi/repository",
            PROJECT_REPOSITORY_CONDITION_PATH: "/rule-api/src/main/java/com/example/ruleapi/repository/condition",
            PROJECT_REPOSITORY_MODEL_PATH: "/rule-api/src/main/java/com/example/ruleapi/repository/dao",
            PROJECT_REPOSITORY_CONVERTER_PATH: "/rule-api/src/main/java/com/example/ruleapi/repository/converter",
            PROJECT_MAPPER_INTER_PATH: "/rule-api/src/main/java/com/example/ruleapi/mapper",
            PROJECT_MAPPER_PATH: "/rule-api/src/main/java/com/example/ruleapi/mapper",
            PROJECT_MAPPER_EXAMPLE_PATH: "/rule-api/src/main/java/com/example/ruleapi/entity",
            PROJECT_MAPPER_ENTITY_PATH: "/rule-api/src/main/java/com/example/ruleapi/entity",

            // TPL 都是模板相关的，不一定都需要
            TPL_BASE_OBJ_PATH: "/rule-api/src/main/java/com/example/ruleapi/controller/dto"
        },
        dubboMyServer: {

            PROJECT_PATH: "/Users/zjh/IdeaProjects/dubbo-demo",
            PROJECT_DUBBO_PATH: "/my-server/src/main/java/com/example/myserver/dubbo",
            PROJECT_DUBBO_REQ_PATH: "/my-client/src/main/java/org/example/client/dubbo/dto/req",
            PROJECT_DUBBO_RES_PATH: "/my-client/src/main/java/org/example/client/dubbo/dto/res",
            PROJECT_DUBBO_INTER_PATH: "/my-client/src/main/java/org/example/client/dubbo",
            // TPL 都是模板相关的，不一定都需要
            TPL_BASE_OBJ_PATH: "/my-client/src/main/java/org/example/client/dubbo/dto",

            PROJECT_SERVICE_PATH: "/my-server/src/main/java/com/example/myserver/service",
            PROJECT_SERVICE_CONVERTER_PATH: "/my-server/src/main/java/com/example/myserver/service/converter",
            PROJECT_REPOSITORY_PATH: "/my-server/src/main/java/com/example/myserver/repository/impl",
            PROJECT_REPOSITORY_INTER_PATH: "/my-server/src/main/java/com/example/myserver/repository",
            PROJECT_REPOSITORY_CONDITION_PATH: "/my-server/src/main/java/com/example/myserver/repository/condition",
            PROJECT_REPOSITORY_MODEL_PATH: "/my-server/src/main/java/com/example/myserver/repository/dao",
            PROJECT_REPOSITORY_CONVERTER_PATH: "/my-server/src/main/java/com/example/myserver/repository/converter",
            PROJECT_MAPPER_INTER_PATH: "/my-server/src/main/java/com/example/myserver/mapper",
            PROJECT_MAPPER_PATH: "/my-server/src/main/java/com/example/myserver/mapper",
            PROJECT_MAPPER_EXAMPLE_PATH: "/my-server/src/main/java/com/example/myserver/entity",
            PROJECT_MAPPER_ENTITY_PATH: "/my-server/src/main/java/com/example/myserver/entity",
        },
        livePortrait: {
            PROJECT_PATH: "/Users/dz0400186/IdeaProjects/live-portrait-service",
            PROJECT_DUBBO_PATH: "/live-portrait-core/src/main/java/com/yupaopao/xxq/live/potrait/core/service",
            PROJECT_DUBBO_REQ_PATH: "/live-portrait-api/src/main/java/com/yupaopao/xxq/live/potrait/api/request",
            PROJECT_DUBBO_RES_PATH: "/live-portrait-api/src/main/java/com/yupaopao/xxq/live/potrait/api/dto",
            PROJECT_DUBBO_INTER_PATH: "/live-portrait-api/src/main/java/com/yupaopao/xxq/live/potrait/api/service",
            // TPL 都是模板相关的，不一定都需要
            // TPL_BASE_OBJ_PATH: "/live-portrait-api/src/main/java/org/example/client/dubbo/dto",

            PROJECT_SERVICE_PATH: "/live-portrait-core/src/main/java/com/yupaopao/xxq/live/potrait/core/manager",
            // PROJECT_SERVICE_CONVERTER_PATH: "/live-portrait-core/src/main/java/com/example/myserver/service/converter",
            PROJECT_MAPPER_INTER_PATH: "/live-portrait-core/src/main/java/com/yupaopao/xxq/live/potrait/core/dao",
            // PROJECT_MAPPER_PATH: "/live-portrait-core/src/main/java/com/yupaopao/xxq/live/potrait/core/dao/",
            // PROJECT_MAPPER_EXAMPLE_PATH: "/live-portrait-core/src/main/java/com/example/myserver/entity",
            PROJECT_MAPPER_ENTITY_PATH: "/live-portrait-core/src/main/java/com/yupaopao/xxq/live/potrait/core/dao/data",
        }
    })
}
