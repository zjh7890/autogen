function getServices() {
    return JSON.stringify({
        rule: {
            ServiceName: "Rule",
            url_prefix: "/rule",
            TableName: "EagleDrlRule",
            withComment: true,
            methods: [
                {
                    type: "query",
                    sql: "id = $id and ruleName = $ruleName",
                    exclude: [
                        "ruleCode"
                    ],
                    methodLayers: {
                        mapper: {
                            methodHello: "hello"
                        }
                    }
                },
                // {
                //     type: "add",
                //     exclude: [
                //         "ruleCode"
                //     ],
                // },
                // {
                //     type: "update",
                //     exclude: [
                //         "ruleCode"
                //     ],
                // },
                // {
                //     type: "upsert",
                //     exclude: [
                //         "ruleCode"
                //     ],
                // },
                {
                    type: "delete",
                    sql: "id = $id and ruleName = $myRuleName",
                }
            ]
        }
    })
}
